package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.FtiAccountType;
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

    public List<FtiAccountType> getAll(){
        return (List<FtiAccountType>)repo.findAll();
    }

    public List<FtiAccountType> findAll() {
        return (List<FtiAccountType>) repo.findAll();
    }

    public FtiAccountType findById(Long accountTypeId) {
        return repo.findById(accountTypeId).get();
    }
}
