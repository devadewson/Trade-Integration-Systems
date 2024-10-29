package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsAccountType;
import com.maybank.integratorapp.data.entity.MsTBRField;
import com.maybank.integratorapp.data.repository.FtiAccountTypeRepository;
import com.maybank.integratorapp.data.repository.MsTBRFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FtiAccountTypeService {
    @Autowired
    private FtiAccountTypeRepository repo;

    public MsAccountType findByFtiAccountType(String ftiAccountType){
        return repo.findByFtiAccountType(ftiAccountType);
    }
}
