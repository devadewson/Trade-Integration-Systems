package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsAccountType;
import com.maybank.integratorapp.data.repository.MsAccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsAccountTypeService {
    @Autowired
    MsAccountTypeRepository msAccountTypeRepository;

    public MsAccountType findById(long id){
        return msAccountTypeRepository.findById(id).get();
    }


    public List<MsAccountType> findAll() {
        return (List<MsAccountType>) msAccountTypeRepository.findAll();

    }
}
