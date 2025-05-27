package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsTBRField;
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

    public List<MsTBRField> findByTBRId(Long id) {
        return repo.findFieldsByTBRId(id);
    }

    public MsTBRField save(MsTBRField field) {
        return repo.save(field);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
