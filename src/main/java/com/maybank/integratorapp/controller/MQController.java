package com.maybank.integratorapp.controller;

import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.repository.MsQueueConfigRepository;
import com.maybank.integratorapp.service.QueueConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MQController {

    @Autowired
    private MsQueueConfigRepository queueConfigRepository;

    public MQController() {
    }

    @GetMapping("/sandbox")
    public String sandboxPage(Model model) {
        var listQueue = queueConfigRepository.findAll();

        model.addAttribute("queueConfigs", listQueue);
        return "vwsandbox";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(
            @RequestParam("serviceName") String serviceName,
            @RequestParam("message") String message,
            @RequestParam("correlationId") String correlationId,
            Model model) {
        MsQueueConfig config = queueConfigRepository.findByServiceName(serviceName);

        MessagePublisher publisher = new MessagePublisher(
                config.getRequest_Queue_Address(),
                Integer.parseInt(config.getRequest_Queue_Port()),
                config.getRequest_Queue_Manager(),
                config.getRequest_Queue_Channel(),
                config.getRequest_Queue_Username(),
                config.getRequest_Queue_Password(),
                config.getRequest_Queue_Name());

        publisher.PublishMessage(message, correlationId);
        model.addAttribute("message", "Message with CorrelationID sent to IBM MQ successfully!");
        // Redirect to avoid circular view error
        return "vwsandbox"; // Use the new view name here
    }
}
