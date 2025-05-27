package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsFacilityUtilize;
import com.maybank.integratorapp.data.repository.MsFacilityUtilizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsFacilityUtilizeService {
    @Autowired
    MsFacilityUtilizeRepository msFacilityUtilizeRepository;

    public List<MsFacilityUtilize> findByCompanyLimitId(Long id) {
        return msFacilityUtilizeRepository.findByCompanyLimitId(id);
    }
}
