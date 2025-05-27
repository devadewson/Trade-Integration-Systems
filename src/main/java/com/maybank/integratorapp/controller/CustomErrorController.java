package com.maybank.integratorapp.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Get error information
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object path = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        // Add attributes to model
        model.addAttribute("status", status);
        model.addAttribute("error", exception != null ? exception.getClass().getSimpleName() : null);
        model.addAttribute("message", message);
        model.addAttribute("path", path);
        model.addAttribute("timestamp", new Date());

        // Include stack trace if available
        if (exception instanceof Exception) {
            StringWriter sw = new StringWriter();
            ((Exception) exception).printStackTrace(new PrintWriter(sw));
            model.addAttribute("trace", sw.toString());
        }

        // Return appropriate error page
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            }
        }
        return "error/500";
    }
}