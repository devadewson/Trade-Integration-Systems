package com.maybank.integratorapp.controller;

import com.maybank.integratorapp.data.entity.MsCompanyLimit;
import com.maybank.integratorapp.data.entity.MsFacility;
import com.maybank.integratorapp.data.entity.MsFacilityUtilize;
import com.maybank.integratorapp.data.entity.MsUtilizeRunningNumber;
import com.maybank.integratorapp.data.service.MsCompanyLimitService;
import com.maybank.integratorapp.data.service.MsFacilityService;
import com.maybank.integratorapp.data.service.MsFacilityUtilizeService;
import com.maybank.integratorapp.data.service.MsUtilizeRunningNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/limit")
public class FacilityController {

    @Autowired
    MsCompanyLimitService msCompanyLimitService;
    @Autowired
    MsFacilityService msFacilityService;

    @Autowired
    MsFacilityUtilizeService msFacilityUtilizeService;

    @Autowired
    MsUtilizeRunningNumberService msUtilizeRunningNumberService;

    @GetMapping()
    public String lists( @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size,
                                 @RequestParam("search") Optional<String> search,
                                 Model model) {

        // Set default values for pagination
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        // Fetch a page of FtiTransactions
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id").descending());

        // Fetch a page of FtiTransactions
        Page<MsCompanyLimit> limitPage;
        if (search.isPresent() && !search.get().isEmpty()) {
            // If search term is provided, search by masterRefNo
            limitPage = msCompanyLimitService.searchByCIFNo(search.get(), pageable);
        } else {
            // Otherwise, fetch all transactions with default sorting
            limitPage = msCompanyLimitService.findAll(pageable);
        }
        // Add data to the model
        model.addAttribute("limitPage", limitPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", limitPage.getTotalPages());
        model.addAttribute("search", search.orElse(""));

        return "layouts/limit/index";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        MsCompanyLimit limit = msCompanyLimitService.findById(id);
        List<MsFacility> facilities = msFacilityService.findByCompanyLimitId(id);
        List<MsFacilityUtilize> facilityUtilizes = msFacilityUtilizeService.findByCompanyLimitId(id);
        List<MsUtilizeRunningNumber> utilizeRunningNumbers = msUtilizeRunningNumberService.findByCompanyLimitId(id);
//        List<FtiAccountType> accountTypes = accountTypeService.findAll();

        model.addAttribute("limit", limit);
        model.addAttribute("facilities", facilities);
        model.addAttribute("facilityUtilizes", facilityUtilizes);
        model.addAttribute("utilizeRunningNumbers", utilizeRunningNumbers);
//        model.addAttribute("accountTypes", accountTypes);

        return "layouts/limit/details";
    }
}
