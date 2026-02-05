package com.maybank.integratorapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mq.*;
import com.ibm.mq.constants.CMQC;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.data.entity.*;
import com.maybank.integratorapp.data.repository.MsQueueConfigRepository;
import com.maybank.integratorapp.data.service.LogInterfaceProcessService;
import com.maybank.integratorapp.data.service.LogQueueDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger log = LoggerFactory.getLogger(MQController.class);
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
//        if (search.isPresent() && !search.get().isEmpty()) {
//            // If search term is provided, search by masterRefNo
//            logQueuePage = logQueueDataService.searchByCorrelationId(search.get(), pageable);
//        }
//        else if (transref.isPresent() && !transref.get().isEmpty()) {
//            // If search term is provided, search by masterRefNo
//            logQueuePage = logQueueDataService.searchByTransactionId(transref.get(), pageable);
//        }
//        else {
//            // Otherwise, fetch all transactions with default sorting
//            logQueuePage = logQueueDataService.getAll(pageable);
//        }
//
//        if (queueorigin.isPresent() && !queueorigin.get().isEmpty()) {
//            // If search term is provided, search by masterRefNo
//            List<LogQueueData> _logQueueData = logQueuePage.stream().filter(x->
//                    x.getOrigin().contains(queueorigin.get())).toList();
//            logQueuePage = new PageImpl<>(
//                    _logQueueData,
//                    pageable,
//                    _logQueueData.size()
//            );
//        }
        logQueuePage = logQueueDataService.getAllWithSearch(pageable,search,transref,queueorigin);
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
        if(logQueueData.getReqMessage() !=null){

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
            else if(logQueueData.getOrigin().contains("swiftOutgoing")){
                String text = logQueueData.getReqMessage();
                int startIndex = text.indexOf(":20:") + 4; // `+4` to skip `:20:`
                int endIndex = text.indexOf("\n", startIndex); // Find the next newline

                if (startIndex >= 4 && endIndex != -1) {
                    relatedTransaction = text.substring(startIndex, endIndex).trim();

                    relatedLink+= relatedTransaction;
                }

            }

        }
        model.addAttribute("relatedLink",relatedLink);
        model.addAttribute("relatedTransaction",relatedTransaction);
        model.addAttribute("logQueue", logQueueData);
        model.addAttribute("logs", details);
        return "layouts/queue/details"; // Thymeleaf template name
    }
}
