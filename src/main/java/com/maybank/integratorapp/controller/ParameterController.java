package com.maybank.integratorapp.controller;

import com.maybank.integratorapp.data.entity.FtiAccountType;
import com.maybank.integratorapp.data.entity.MsParameter;
import com.maybank.integratorapp.data.entity.MsTBR;
import com.maybank.integratorapp.data.entity.MsTBRMapping;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.data.service.FtiAccountTypeService;
import com.maybank.integratorapp.data.service.MsParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/parameter")
public class ParameterController {
    @Autowired
    MsParameterService parameterService;
    @Autowired
    private MsParameterRepository msParameterRepository;

    @GetMapping()
    public String listParameters( @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            @RequestParam("search") Optional<String> search,
                            Model model) {

        // Set default values for pagination
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        // Fetch a page of FtiTransactions
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id").descending());

        // Fetch a page of FtiTransactions
        Page<MsParameter> parameterPage;
        if (search.isPresent() && !search.get().isEmpty()) {
            // If search term is provided, search by masterRefNo
            parameterPage = parameterService.searchByParamKey(search.get(), pageable);
        } else {
            // Otherwise, fetch all transactions with default sorting
            parameterPage = parameterService.findAll(pageable);
        }
        // Add data to the model
        model.addAttribute("parameterPage", parameterPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", parameterPage.getTotalPages());
        model.addAttribute("search", search.orElse(""));

        return "layouts/parameter/index";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        MsParameter parameter = parameterService.findById(id);

        model.addAttribute("parameter", parameter);

        return "layouts/parameter/details";
    }
    @PostMapping("/detail/update")
    public String updateDetail(@RequestParam Long ParameterId, @ModelAttribute MsParameter parameter) {

        MsParameter parameterData = parameterService.findById(ParameterId);
        parameterData.setPrmKey(parameter.getPrmKey());
        parameterData.setPrmValue(parameter.getPrmValue());
        parameterData.setPrmDesc(parameter.getPrmDesc());
        parameterData.setUpdateDate(new Date());
        parameterData.setUpdatedBy("SYSTEM");

        msParameterRepository.save(parameterData);


        return "redirect:/parameter/detail/" + ParameterId;
    }
}
