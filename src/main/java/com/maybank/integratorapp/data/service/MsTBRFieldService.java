package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsTBRField;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.data.repository.MsTBRFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsTBRFieldService {
    @Autowired
    private MsTBRFieldRepository repo;

    public List<MsTBRField> findFieldsByTbrCode(String tbrCode){
        return repo.findFieldsByTbrCode(tbrCode);
    }
}
