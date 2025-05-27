package com.maybank.integratorapp.controller;

import com.maybank.integratorapp.data.service.LogQueueDataService;
import com.maybank.integratorapp.service.LogMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Controller
public class DashboardController {
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
}
