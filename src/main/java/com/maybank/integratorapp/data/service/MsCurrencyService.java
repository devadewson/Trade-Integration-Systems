package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsAccountType;
import com.maybank.integratorapp.data.entity.MsCurrency;
import com.maybank.integratorapp.data.repository.FtiAccountTypeRepository;
import com.maybank.integratorapp.data.repository.MsCurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsCurrencyService {
    @Autowired
    private MsCurrencyRepository repo;

    public MsCurrency findByIsoCode(String isoCode){
        return repo.findByIsoCode(isoCode);
    }

    public List<MsCurrency> getAll(){
        return (List<MsCurrency>)repo.findAll();
    }
}
