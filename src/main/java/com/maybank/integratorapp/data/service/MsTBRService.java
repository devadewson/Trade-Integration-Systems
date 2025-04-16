package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsTBR;
import com.maybank.integratorapp.data.repository.MsTBRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsTBRService {
    @Autowired
    private MsTBRRepository msTBRRepository;

    public List<MsTBR> findAll(){
        return (List<MsTBR>) msTBRRepository.findAll();
    }

    public MsTBR findById(Long id){
        return msTBRRepository.findById(id).get();
    }

    public Page<MsTBR> findAll(Pageable pageable) {
        return msTBRRepository.findAll(pageable);
    }

    public Page<MsTBR> searchByTBRNo(String s, Pageable pageable) {
        return msTBRRepository.findByTBRNoContainingIgnoreCase(s, pageable);
    }
}
