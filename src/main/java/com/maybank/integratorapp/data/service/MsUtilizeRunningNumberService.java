package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsUtilizeRunningNumber;
import com.maybank.integratorapp.data.repository.MsUtilizeRunningNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsUtilizeRunningNumberService {
    @Autowired
    MsUtilizeRunningNumberRepository msUtilizeRunningNumberRepository;


    public List<MsUtilizeRunningNumber> findByCompanyLimitId(Long id) {
        return msUtilizeRunningNumberRepository.findByIdWithFacility(id);
    }
}
