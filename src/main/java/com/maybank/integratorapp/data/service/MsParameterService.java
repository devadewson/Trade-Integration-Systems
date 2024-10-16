package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.data.repository.MsQueueConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsParameterService {
    @Autowired
    private MsParameterRepository repo;

    public String findValueByPrmKey(String prmKey){
        return repo.findValueByPrmKey(prmKey);
    }
}
