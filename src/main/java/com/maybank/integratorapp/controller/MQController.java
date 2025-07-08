package com.maybank.integratorapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mq.*;
import com.ibm.mq.constants.CMQC;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.data.entity.*;
import com.maybank.integratorapp.data.repository.MsQueueConfigRepository;
import com.maybank.integratorapp.data.service.LogInterfaceProcessService;
import com.maybank.integratorapp.data.service.LogQueueDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.EOFException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
public class MQController {

    @Autowired
    private MsQueueConfigRepository queueConfigRepository;
    @Autowired
    private LogQueueDataService logQueueDataService;
    @Autowired
    private LogInterfaceProcessService logInterfaceProcessService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/sandbox")
    public String sandboxPage(Model model) throws Exception {
        List<MsQueueConfig> listQueue = (List<MsQueueConfig>) queueConfigRepository.findAll();
//        model.addAttribute("queueConfigs", objectMapper.writeValueAsString(listQueue));
        model.addAttribute("queueConfigs", listQueue);
        return "layouts/sandbox/index";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(
            @RequestParam("MQAddress") String MQAddress,
            @RequestParam("MQPort") String MQPort,
            @RequestParam("MQManager") String MQManager,
            @RequestParam("MQChannel") String MQChannel,
            @RequestParam("MQUsername") String MQUsername,
            @RequestParam("MQPassword") String MQPassword,
            @RequestParam("MQQueueName") String MQQueueName,
            @RequestParam("message") String message,
            @RequestParam("correlationId") String correlationId,
            Model model) {
//        MsQueueConfig config = queueConfigRepository.findByServiceName(serviceName);

        MessagePublisher publisher = new MessagePublisher(
                MQAddress,
                Integer.parseInt(MQPort),
                MQManager,
                MQChannel,
                MQUsername,
                MQPassword,
                MQQueueName);

        publisher.PublishMessage(message, correlationId);
        publisher.close();
        List<MsQueueConfig> listQueue = (List<MsQueueConfig>) queueConfigRepository.findAll();
//        model.addAttribute("queueConfigs", objectMapper.writeValueAsString(listQueue));
        model.addAttribute("queueConfigs", listQueue);
        model.addAttribute("message", "Message with CorrelationID sent to IBM MQ successfully!");
        // Redirect to avoid circular view error
        return "layouts/sandbox/index"; // Use the new view name here
    }
    @PostMapping("/queuecheck")
    public String queuecheck(@RequestParam("MQAddress") String MQAddress,
                             @RequestParam("MQPort") String MQPort,
                             @RequestParam("MQManager") String MQManager,
                             @RequestParam("MQChannel") String MQChannel,
                             @RequestParam("MQUsername") String MQUsername,
                             @RequestParam("MQPassword") String MQPassword,
                             @RequestParam("MQQueueName") String MQQueueName,
                             @RequestParam("message") String message,
                             @RequestParam("correlationId") String correlationId,
                             Model model){




        MQQueueManager queueManager = null;
        MQQueue queue = null;
        List<LogQueueData> queueMessages = new ArrayList<>();

        try {
            // Set MQ environment properties
            MQEnvironment.hostname = MQAddress;
            MQEnvironment.port = Integer.parseInt(MQPort);
            MQEnvironment.channel = MQChannel;
            MQEnvironment.userID = MQUsername;
            MQEnvironment.password = MQPassword;
//            MQEnvironment.disableTracing ();
//            MQException.log = null;
            // Create MQ connection to the queue manager
            queueManager = new MQQueueManager(MQManager);
            // Get a list of queues (You can also specify specific queue names here)

            int openOptions = CMQC.MQOO_BROWSE | CMQC.MQOO_INQUIRE | CMQC.MQOO_INPUT_AS_Q_DEF | CMQC.MQOO_FAIL_IF_QUIESCING;
            try {
                queue = queueManager.accessQueue(MQQueueName, openOptions);
                // Get current depth (number of messages)
                int depth = queue.getCurrentDepth();

                // Retrieve messages from the queue
                MQGetMessageOptions gmo = new MQGetMessageOptions();
                gmo.options = CMQC.MQGMO_BROWSE_FIRST | CMQC.MQGMO_ALL_MSGS_AVAILABLE | CMQC.MQGMO_NO_WAIT | CMQC.MQGMO_FAIL_IF_QUIESCING;

                MQMessage mqMessage = new MQMessage();
                LogQueueData data = new LogQueueData();

                int i = 0;
                while(true){
                    i++;
                    try {
                        // Attempt to get a message without waiting
                        queue.get(mqMessage, gmo);
                        byte[] correlationIdByte = mqMessage.correlationId;
                        String msgCorrelationId = new String(correlationIdByte, "UTF-8").trim();
                        GregorianCalendar msgPutDateTime = mqMessage.putDateTime;
                        String msgContent = mqMessage.readStringOfByteLength(mqMessage.getMessageLength());

                        data.setId((long) i);
                        data.setCorrelationID(msgCorrelationId);
                        data.setCreated_date(msgPutDateTime.getTime());
                        data.setResMessage(msgContent);

                        queueMessages.add(data);
                        // Move to the next message in the queue
//                        gmo.options = CMQC.MQGMO_BROWSE_NEXT | CMQC.MQGMO_NO_WAIT | CMQC.MQGMO_FAIL_IF_QUIESCING;

                    } catch (MQException e) {
                        if (e.reasonCode == CMQC.MQRC_NO_MSG_AVAILABLE) {
                            System.out.println("No more messages available in the queue.");
                        } else {
                            System.err.println("Error reading message from queue: " + e.getMessage());
                        }
                        break;
                    } catch (EOFException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                // Add the messages to the model
                model.addAttribute("queueMessages", queueMessages);

                System.out.printf("Queue: %s, Message Count: %d%n", MQQueueName, depth);
            } catch (MQException e) {
                System.err.printf("Error accessing queue %s: %s%n", MQQueueName, e.getMessage());
            }

            // Disconnect from the queue manager
            queueManager.disconnect();
        } catch (MQException e) {
            System.err.printf("Error connecting to queue manager: %s%n", e.getMessage());
        }
        finally {
            // Close the queue and disconnect the queue manager
            try {
                if (queue != null) {
                    queue.close();
                    System.out.println("Queue closed successfully.");
                }
            } catch (MQException e) {
                System.err.println("Error closing queue: " + e.getMessage());
            }

            try {
                if (queueManager != null && queueManager.isConnected()) {
                    queueManager.disconnect();
                    System.out.println("Queue manager disconnected successfully.");
                }
            } catch (MQException e) {
                System.err.println("Error disconnecting queue manager: " + e.getMessage());
            }

            // Reset MQEnvironment to default values
            MQEnvironment.hostname = null;
            MQEnvironment.port = 0;
            MQEnvironment.channel = null;
            MQEnvironment.userID = null;
            MQEnvironment.password = null;
            System.out.println("MQEnvironment reset to default values.");
        }

        return "layouts/sandbox/queuecheck";
    }

    @GetMapping("/log-queue")
    public String getAllLogQueue(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("search") Optional<String> search,
            @RequestParam("transref") Optional<String> transref,
            @RequestParam("queueorigin") Optional<String> queueorigin,
            Model model) {

        // Set default values for pagination
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        // Fetch a page of FtiTransactions
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id").descending());

        // Fetch a page of FtiTransactions
        Page<LogQueueData> logQueuePage;
        if (search.isPresent() && !search.get().isEmpty()) {
            // If search term is provided, search by masterRefNo
            logQueuePage = logQueueDataService.searchByCorrelationId(search.get(), pageable);
        }
        else if (transref.isPresent() && !transref.get().isEmpty()) {
            // If search term is provided, search by masterRefNo
            logQueuePage = logQueueDataService.searchByTransactionId(transref.get(), pageable);
        }
        else {
            // Otherwise, fetch all transactions with default sorting
            logQueuePage = logQueueDataService.getAll(pageable);
        }

        if (queueorigin.isPresent() && !queueorigin.get().isEmpty()) {
            // If search term is provided, search by masterRefNo
            List<LogQueueData> _logQueueData = logQueuePage.getContent().stream().filter(x->x.getOrigin().contains(queueorigin.get())).toList();
            logQueuePage = new PageImpl<>(
                    _logQueueData,
                    pageable,
                    _logQueueData.size()
            );
        }
        // Add data to the model
        model.addAttribute("logQueuePage", logQueuePage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", logQueuePage.getTotalPages());
        model.addAttribute("search", search.orElse(""));
        model.addAttribute("transref", transref.orElse(""));
        model.addAttribute("queueorigin", queueorigin.orElse(""));

        return "layouts/queue/index"; // Thymeleaf template name
    }

    @GetMapping("/log-queue/{id}/details")
    public String getLogQueueDetails(@PathVariable Long id, Model model) {
        LogQueueData logQueueData = logQueueDataService.findById(id).get();
        List<LogInterfaceProcess> details = logInterfaceProcessService.getLogsByParentId(id);


        String relatedTransaction = "";
        String relatedLink = "/transactions?search=";
        if(logQueueData.getReqMessage().contains("<reference>")){
            Pattern pattern = Pattern.compile("<reference>(.*?)</reference>");
            Matcher matcher = pattern.matcher(logQueueData.getReqMessage());

            if (matcher.find()) {
                relatedTransaction = matcher.group(1);
                relatedLink+= relatedTransaction;
            }

        }else if(logQueueData.getReqMessage().contains("MasterReference>")){
            Pattern pattern = Pattern.compile("<[^:>]+:MasterReference>(.*?)</[^:>]+:MasterReference>");
            Matcher matcher = pattern.matcher(logQueueData.getReqMessage());

            if (matcher.find()) {
                relatedTransaction = matcher.group(1);
                relatedLink+= relatedTransaction;
            }

        }
        model.addAttribute("relatedLink",relatedLink);
        model.addAttribute("relatedTransaction",relatedTransaction);
        model.addAttribute("logQueue", logQueueData);
        model.addAttribute("logs", details);
        return "layouts/queue/details"; // Thymeleaf template name
    }
}
