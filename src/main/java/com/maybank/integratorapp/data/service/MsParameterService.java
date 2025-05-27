package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsParameter;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.entity.MsTBR;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.data.repository.MsQueueConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MsParameterService {
    @Autowired
    private MsParameterRepository repo;

    public String findValueByPrmKey(String prmKey){
        return repo.findValueByPrmKey(prmKey);
    }

    public Page<MsParameter> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<MsParameter> searchByParamKey(String s, Pageable pageable) {
        return repo.findByParamKey(s, pageable);
    }

    public MsParameter findById(Long id) {
        return repo.findById(id).get();
    }
}
