package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsBranch;
import com.maybank.integratorapp.data.repository.MsBranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsBranchService {
    @Autowired
    MsBranchRepository repository;

    public MsBranch getByBranchCode(String code){
        return repository.findByBranchCode(code);
    }


}
