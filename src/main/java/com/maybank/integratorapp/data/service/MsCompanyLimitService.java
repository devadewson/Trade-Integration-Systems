package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsCompanyLimit;
import com.maybank.integratorapp.data.entity.MsTBR;
import com.maybank.integratorapp.data.repository.MscompanylimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MsCompanyLimitService {
    @Autowired
    MscompanylimitRepository mscompanylimitRepository;
    public Page<MsCompanyLimit> findAll(Pageable pageable) {
        return mscompanylimitRepository.findAll(pageable);
    }

    public Page<MsCompanyLimit> searchByCIFNo(String s, Pageable pageable) {
        return mscompanylimitRepository.findByCIFNoContainingIgnoreCase(s,pageable);
    }

    public MsCompanyLimit findById(Long id) {
        return mscompanylimitRepository.findById(id).get();
    }
}
