package com.maybank.integratorapp.controller;

import com.maybank.integratorapp.data.entity.*;
import com.maybank.integratorapp.data.service.*;
import com.maybank.integratorapp.model.soap.fcclimit.response.Limit;
import com.maybank.integratorapp.model.soap.fcclimit.response.OFAResponse;
import com.maybank.integratorapp.service.SendingEmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class FtiTransactionController {
//    @Autowired
//    EmailService emailService;
    private static Logger log = LoggerFactory.getLogger(FtiTransactionController.class);
    @Autowired
    SendingEmailServiceImpl emailService;
    @Autowired
    private FtiTransactionService ftiTransactionService;

    @Autowired
    private FtiTransactionDetailService ftiTransactionDetailService;
    @Autowired
    private LogInterfaceProcessService logInterfaceProcessService;
//    @Autowired
//    FtiTransactionPostingService ftiPostingService;

    @Autowired
    FtiTransactionDetailPostingService ftiPostingService;

    @Autowired
    FtiTransactionDetailPostingGroupService ftiPostingGroupService;

    @Autowired
    MsParameterService parameterService;

    @Autowired
    private LogQueueDataService logQueueDataService;
    // Display all FtiTransactions in a grid
    @GetMapping("/transactions")
    public String getAllFtiTransactions(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("search") Optional<String> search,
            @RequestParam("reservationid") Optional<String> reservationid,
            Model model) {

        // Set default values for pagination
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        // Fetch a page of FtiTransactions
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id").descending());

        // Fetch a page of FtiTransactions
        Page<FtiTransaction> transactionPage = ftiTransactionService.getAllWithSearch(pageable, search, reservationid);
//        if (search.isPresent() && !search.get().isEmpty()) {
//            // If search term is provided, search by masterRefNo
//            transactionPage = ftiTransactionService.searchByMasterRefNo(search.get(), pageable);
//        }else if (reservationid.isPresent() && !reservationid.get().isEmpty()) {
//            // If search term is provided, search by masterRefNo
//            transactionPage = ftiTransactionService.searchByReservationId(reservationid.get(), pageable);
//        }
//        else {
//            // Otherwise, fetch all transactions with default sorting
//            transactionPage = ftiTransactionService.getAllFtiTransactions(pageable);
//        }
        // Add data to the model
        model.addAttribute("transactionPage", transactionPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", transactionPage.getTotalPages());
        model.addAttribute("search", search.orElse(""));
        model.addAttribute("reservationid", reservationid.orElse(""));

        return "layouts/transactions/index"; // Thymeleaf template name
    }

    // Display FtiTransactionDetails for a specific FtiTransaction
    @GetMapping("/transactions/{id}/details")
    public String getFtiTransactionDetails(@PathVariable Long id, Model model) {
        FtiTransaction transaction = ftiTransactionService.getFtiTransactionById(id);
        List<FtiTransactionDetail> details = ftiTransactionDetailService.getDetailsByHeaderId(id);
        String relatedLink = "/log-queue?transref="+transaction.getMasterRefNo();
        List<LogQueueData> logQueue = logQueueDataService.searchByTransactionId(transaction.getMasterRefNo());
        model.addAttribute("logQueuePage", logQueue);
        model.addAttribute("transaction", transaction);
        model.addAttribute("details", details);
        model.addAttribute("relatedLink", relatedLink);
        return "layouts/transactions/details"; // Thymeleaf template name
    }

    // Endpoint to fetch logs by transMessageLogId (for AJAX)
    @GetMapping("/transaction-details/{transMessageLogId}/logs")
    @ResponseBody
    public List<LogInterfaceProcess> getLogsByTransMessageLogId(@PathVariable Long transMessageLogId) {
        return logInterfaceProcessService.getLogsByParentId(transMessageLogId);
    }

    @GetMapping("/transaction-details/{transMessageLogId}/log-header")
    @ResponseBody
    public Optional<LogQueueData> getLogQueueByTransMessageLogId(@PathVariable Long transMessageLogId) {
        return logQueueDataService.findById(transMessageLogId);
    }

    @GetMapping("/transaction-details/{transLogId}/postings")
    @ResponseBody
    public List<PostingExtender> getPostingsByTransMessageLogId(@PathVariable Long transLogId) {
        List<PostingExtender> listOfPostingGroups = new ArrayList<>();
        // Fetch the list of postings from the database
        List<FtiTransactionDetailPostingGroup> postingGroups = ftiPostingGroupService.getByDetailId(transLogId);
        postingGroups.forEach(x->{
            PostingExtender _new = new PostingExtender();
            _new.setFlagCrossValas(x.getFlagCrossValas());
            _new.setFlagMdmc(x.getFlagMdmc());
            _new.setGroupId(x.getGroupId());
            _new.setFlagMdmc(x.getFlagMdmc());
            _new.setMappingType(x.getMappingType());
            _new.setTbrCode(x.getTbrCode());
            _new.setDetailId(x.getDetailId());
            _new.setId(x.getId());
            List<FtiTransactionDetailPosting> _postings = new ArrayList<>();
            List<LogInterfaceProcess> _logInterfaceProcessList = new ArrayList<>();
            if (!_new.getGroupId().isEmpty()) {
                _postings = ftiPostingService.getByIdGroup(_new.getId());
                String activityName = "Posting "+_new.getId();
                _logInterfaceProcessList = logInterfaceProcessService.getLogsByActivityName(activityName);

            }

            _new.setPostings(_postings);
            _new.setLogInterfaces(_logInterfaceProcessList);

            listOfPostingGroups.add(_new);
        });

        return listOfPostingGroups;
    }

//    @PostMapping("/transaction-details/{transMessageLogId}/update-posting-sequence")
//    public String updateSequence(@PathVariable Long transMessageLogId,@RequestParam("postings") List<FtiTransactionPosting> postings) {
//        // Update the sequence numbers in the database
//        ftiPostingService.updatePostingSequence(postings);
//        return "redirect:/transaction-details/" + transMessageLogId + "/postings";
//    }

    @GetMapping(value = "/TestEmail")
    public ResponseEntity<OFAResponse> RefreshLimitByCif(@PathVariable Long idnotif){
        OFAResponse response = new OFAResponse();
        List<Limit> limitList = new ArrayList<>();

        try{

//            emailService.sendTransactionNotification(ftiTransactionDetailService.getById(idnotif));
            String from = parameterService.findValueByPrmKey("EmailNotificationFrom");
            String to = parameterService.findValueByPrmKey("EmailNotificationTo");
            String message = "Testing Email";
            String subject = "Testing Email";

            emailService.sendEmail(from,to,message,subject);

            return new ResponseEntity<OFAResponse>(response, HttpStatus.OK);

        }
        catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<OFAResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public class PostingExtender extends FtiTransactionDetailPostingGroup{
        private List<FtiTransactionDetailPosting> postings;
        private List<LogInterfaceProcess> logInterfaces;

        public List<LogInterfaceProcess> getLogInterfaces() {
            return logInterfaces;
        }

        public void setLogInterfaces(List<LogInterfaceProcess> logInterfaces) {
            this.logInterfaces = logInterfaces;
        }

        public List<FtiTransactionDetailPosting> getPostings() {
            return postings;
        }

        public void setPostings(List<FtiTransactionDetailPosting> postings) {
            this.postings = postings;
        }
    }
}
