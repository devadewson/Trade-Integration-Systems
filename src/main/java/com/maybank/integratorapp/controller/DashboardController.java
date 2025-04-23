package com.maybank.integratorapp.controller;

import com.maybank.integratorapp.service.LogMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class DashboardController {
    @Autowired
    private LogMonitorService logMonitorService;

    @GetMapping("/")
    public String showDashboard(Model model) {
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
