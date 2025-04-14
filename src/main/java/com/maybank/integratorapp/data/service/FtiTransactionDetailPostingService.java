package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.FtiTransactionDetailPosting;
import com.maybank.integratorapp.data.repository.FtiTransactionDetailPostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FtiTransactionDetailPostingService {
    @Autowired
    private FtiTransactionDetailPostingRepository ftiTransactionDetailPostingRepository;
    
    public List<FtiTransactionDetailPosting> getByIdGroup(Long idGroup){
        return ftiTransactionDetailPostingRepository.findByIdGroup(idGroup);
    }

    public FtiTransactionDetailPosting save(FtiTransactionDetailPosting entry) {

        return ftiTransactionDetailPostingRepository.save(entry);
    }

}
