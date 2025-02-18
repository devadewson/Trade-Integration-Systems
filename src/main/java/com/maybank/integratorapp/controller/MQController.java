package com.maybank.integratorapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mq.*;
import com.ibm.mq.constants.CMQC;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.repository.MsQueueConfigRepository;
import com.maybank.integratorapp.service.QueueConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;

@Controller
public class MQController {

    @Autowired
    private MsQueueConfigRepository queueConfigRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/")
    public String home(Model model){
        return "layouts/home/index";
    }
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
}
