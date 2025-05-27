package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsFacility;
import com.maybank.integratorapp.data.repository.MsFacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsFacilityService {
    @Autowired
    MsFacilityRepository msFacilityRepository;

    public List<MsFacility> findByCompanyLimitId(Long id) {
        return msFacilityRepository.findByCompanyLimitId(id);
    }
}
