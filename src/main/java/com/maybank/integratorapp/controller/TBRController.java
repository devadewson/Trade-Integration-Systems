package com.maybank.integratorapp.controller;

import com.maybank.integratorapp.data.entity.*;
import com.maybank.integratorapp.data.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tbr")
public class TBRController {

    @Autowired
    private MsTBRService tbrService;

    @Autowired
    private MsTBRMappingService mappingService;

    @Autowired
    private MsAccountTypeService accountTypeService;

    @Autowired
    private MsTBRFieldService msTBRFieldService;

    @GetMapping()
    public String listTBRs( @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            @RequestParam("search") Optional<String> search,
                            Model model) {

        // Set default values for pagination
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        // Fetch a page of FtiTransactions
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id").descending());

        // Fetch a page of FtiTransactions
        Page<MsTBR> tbrPage;
        if (search.isPresent() && !search.get().isEmpty()) {
            // If search term is provided, search by masterRefNo
            tbrPage = tbrService.searchByTBRNo(search.get(), pageable);
        } else {
            // Otherwise, fetch all transactions with default sorting
            tbrPage = tbrService.findAll(pageable);
        }
        // Add data to the model
        model.addAttribute("tbrPage", tbrPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", tbrPage.getTotalPages());
        model.addAttribute("search", search.orElse(""));

        return "layouts/tbr/index";
    }

    @GetMapping("/detail/{id}")
    public String showTBRDetail(@PathVariable Long id, Model model) {
        MsTBR tbr = tbrService.findById(id);
        List<MsTBRMapping> mappings = mappingService.findByTBRId(id);
        List<MsAccountType> accountTypes = accountTypeService.findAll();
        List<MsTBRField> fields = msTBRFieldService.findByTBRId(id);

        model.addAttribute("tbr", tbr);
        model.addAttribute("mappings", mappings);
        model.addAttribute("accountTypes", accountTypes);
        model.addAttribute("fields", fields);

        return "layouts/tbr/details";
    }

    @PostMapping("/mapping/add")
    public String addMapping(@ModelAttribute MsTBRMapping mapping, @RequestParam Long TBR_Id) {
        MsTBR tbr = tbrService.findById(TBR_Id);
        MsAccountType accountType = accountTypeService.findById(mapping.getAccountType_Id());

        mapping.setTBR_Id(TBR_Id);
        mapping.setAccountType_Id(mapping.getAccountType_Id());
        mapping.setTbr(tbr);
        mapping.setAccountType(accountType);

        mappingService.save(mapping);
        return "redirect:/tbr/detail/" + TBR_Id;
    }

    @PostMapping("/mapping/update")
    public String updateMapping(@ModelAttribute MsTBRMapping mapping, @RequestParam Long TBR_Id) {
        MsTBR tbr = tbrService.findById(TBR_Id);
        MsAccountType accountType = accountTypeService.findById(mapping.getAccountType_Id());

        mapping.setTBR_Id(TBR_Id);
        mapping.setAccountType_Id(mapping.getAccountType_Id());
        mapping.setTbr(tbr);
        mapping.setAccountType(accountType);

        mappingService.save(mapping);
        return "redirect:/tbr/detail/" + TBR_Id;
    }

    @GetMapping("/mapping/delete/{id}")
    public String deleteMapping(@PathVariable Long id, @RequestParam Long tbrId) {
        mappingService.deleteById(id);
        return "redirect:/tbr/detail/" + tbrId;
    }

    @PostMapping("/field/add")
    public String addField(@ModelAttribute MsTBRField field, @RequestParam Long TBR_Id) {
        MsTBR tbr = tbrService.findById(TBR_Id);

        field.setTBR_Id(TBR_Id);
        field.setTbr(tbr);

        msTBRFieldService.save(field);
        return "redirect:/tbr/detail/" + TBR_Id;
    }

    @PostMapping("/field/update")
    public String updateField(@ModelAttribute MsTBRField field, @RequestParam Long TBR_Id) {
        MsTBR tbr = tbrService.findById(TBR_Id);

        field.setTBR_Id(TBR_Id);
        field.setTbr(tbr);

        msTBRFieldService.save(field);
        return "redirect:/tbr/detail/" + TBR_Id;
    }

    @GetMapping("/field/delete/{id}")
    public String deleteField(@PathVariable Long id, @RequestParam Long tbrId) {
        msTBRFieldService.deleteById(id);
        return "redirect:/tbr/detail/" + tbrId;
    }
}