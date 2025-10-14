package com.maybank.integratorapp.controller;

import com.maybank.integratorapp.component.system.messageprocessor.AccountInquiryMessageProcessor;
import com.maybank.integratorapp.data.service.LogQueueDataService;
import com.maybank.integratorapp.service.LogMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.math.BigDecimal;

@Controller
public class DashboardController {
    private static Logger log = LoggerFactory.getLogger(DashboardController.class);
    @Autowired
    private LogMonitorService logMonitorService;

    @Autowired
    private LogQueueDataService logQueueDataService;

    @GetMapping("/")
    public String showDashboard(Model model) {

        String pattern = "yyyy-MM-dd";

        DateFormat df = new SimpleDateFormat(pattern);

        Date _startDate = new Date();
        Date _endDate = new Date(_startDate.getTime()+(24*60*60*1000));

        String startDate = df.format(_startDate); // Or whatever
        String endDate = df.format(_endDate);

        Map<String, Object> chartData = logQueueDataService.getHourlyMessageCountsByOrigin(startDate, endDate);
        model.addAllAttributes(chartData);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("queueStats", logQueueDataService.getTodayQueueStats());
        // This will check files only when the page is requested
        model.addAttribute("logFiles", logMonitorService.getAllLogFiles());
        model.addAttribute("lastChecked", LocalDateTime.now());
        return "layouts/home/index";
    }


    @GetMapping("/logs/view")
    public String viewLog(@RequestParam int id, Model model) {
        try {
            Optional<String> content = logMonitorService.getLogContent(id);
            Optional<LogMonitorService.LogFile> logFile = logMonitorService.getLogFileById(id);

            if (content.isPresent() && logFile.isPresent()) {
                model.addAttribute("logContent", content.get());
                model.addAttribute("logName", logFile.get().getName());

            }
        } catch (IOException e) {
            // Log error
        }
        return "layouts/home/details";
    }


    public static class QueueStats {
        private String queueName;
        private Long totalMessages;
        private Long avgProcessingSeconds;
        private Long maxProcessingSeconds;
        private Long minProcessingSeconds;

        // Constructors
        public QueueStats() {}

        public QueueStats(String queueName, Long totalMessages, Long avgProcessingSeconds,
                          Long maxProcessingSeconds, Long minProcessingSeconds) {
            this.queueName = queueName;
            this.totalMessages = totalMessages;
            this.avgProcessingSeconds = avgProcessingSeconds;
            this.maxProcessingSeconds = maxProcessingSeconds;
            this.minProcessingSeconds = minProcessingSeconds;
        }

        // Getters and Setters
        public String getQueueName() { return queueName; }
        public void setQueueName(String queueName) { this.queueName = queueName; }

        public Long getTotalMessages() { return totalMessages; }
        public void setTotalMessages(Long totalMessages) { this.totalMessages = totalMessages; }

        public Long getAvgProcessingSeconds() { return avgProcessingSeconds; }
        public void setAvgProcessingSeconds(Long avgProcessingSeconds) { this.avgProcessingSeconds = avgProcessingSeconds; }

        public Long getMaxProcessingSeconds() { return maxProcessingSeconds; }
        public void setMaxProcessingSeconds(Long maxProcessingSeconds) { this.maxProcessingSeconds = maxProcessingSeconds; }

        public Long getMinProcessingSeconds() { return minProcessingSeconds; }
        public void setMinProcessingSeconds(Long minProcessingSeconds) { this.minProcessingSeconds = minProcessingSeconds; }
    }
}
