package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsTBRMapping;
import com.maybank.integratorapp.data.repository.MsTBRMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsTBRMappingService {
    @Autowired
    private MsTBRMappingRepository msTBRMappingRepository;

//    public List<MsTBRMapping> findByTBRId(Long id){
//        return msTBRMappingRepository.findByTBRId(id);
//    }

    public List<MsTBRMapping> findByTBRId(Long tbrId) {
        return msTBRMappingRepository.findByTBRIdWithAccountType(tbrId);
    }

    public MsTBRMapping save(MsTBRMapping data){
        return msTBRMappingRepository.save(data);
    }

    public void deleteById(Long id) {
        msTBRMappingRepository.deleteById(id);
    }
}
