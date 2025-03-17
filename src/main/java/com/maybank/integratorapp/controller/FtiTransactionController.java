package com.maybank.integratorapp.controller;

import com.maybank.integratorapp.data.entity.LogInterfaceProcess;
import com.maybank.integratorapp.data.service.LogInterfaceProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.maybank.integratorapp.data.service.FtiTransactionDetailService;
import com.maybank.integratorapp.data.service.FtiTransactionService;
import com.maybank.integratorapp.data.entity.FtiTransaction;
import com.maybank.integratorapp.data.entity.FtiTransactionDetail;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
public class FtiTransactionController {

    @Autowired
    private FtiTransactionService ftiTransactionService;

    @Autowired
    private FtiTransactionDetailService ftiTransactionDetailService;
    @Autowired
    private LogInterfaceProcessService logInterfaceProcessService;
    // Display all FtiTransactions in a grid
    @GetMapping("/transactions")
    public String getAllFtiTransactions(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("search") Optional<String> search,
            Model model) {

        // Set default values for pagination
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        // Fetch a page of FtiTransactions
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id").descending());

        // Fetch a page of FtiTransactions
        Page<FtiTransaction> transactionPage;
        if (search.isPresent() && !search.get().isEmpty()) {
            // If search term is provided, search by masterRefNo
            transactionPage = ftiTransactionService.searchByMasterRefNo(search.get(), pageable);
        } else {
            // Otherwise, fetch all transactions with default sorting
            transactionPage = ftiTransactionService.getAllFtiTransactions(pageable);
        }
        // Add data to the model
        model.addAttribute("transactionPage", transactionPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", transactionPage.getTotalPages());
        model.addAttribute("search", search.orElse(""));

        return "layouts/transactions/index"; // Thymeleaf template name
    }

    // Display FtiTransactionDetails for a specific FtiTransaction
    @GetMapping("/transactions/{id}/details")
    public String getFtiTransactionDetails(@PathVariable Long id, Model model) {
        FtiTransaction transaction = ftiTransactionService.getFtiTransactionById(id);
        List<FtiTransactionDetail> details = ftiTransactionDetailService.getDetailsByHeaderId(id);
        model.addAttribute("transaction", transaction);
        model.addAttribute("details", details);
        return "layouts/transactions/details"; // Thymeleaf template name
    }

    // Endpoint to fetch logs by transMessageLogId (for AJAX)
    @GetMapping("/transaction-details/{transMessageLogId}/logs")
    @ResponseBody
    public List<LogInterfaceProcess> getLogsByTransMessageLogId(@PathVariable Long transMessageLogId) {
        return logInterfaceProcessService.getLogsByParentId(transMessageLogId);
    }
}
