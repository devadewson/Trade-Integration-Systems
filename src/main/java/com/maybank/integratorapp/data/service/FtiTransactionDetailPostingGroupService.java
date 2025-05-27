package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.FtiTransactionDetailPosting;
import com.maybank.integratorapp.data.entity.FtiTransactionDetailPostingGroup;
import com.maybank.integratorapp.data.repository.FtiTransactionDetailPostingGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FtiTransactionDetailPostingGroupService {
    @Autowired
    private FtiTransactionDetailPostingGroupRepository ftiTransactionDetailPostingGroupRepository;

    public List<FtiTransactionDetailPostingGroup> getByDetailId(Long detailId){
        return  ftiTransactionDetailPostingGroupRepository.findByDetailId(detailId);
    }
    public FtiTransactionDetailPostingGroup save(FtiTransactionDetailPostingGroup entry) {

        return ftiTransactionDetailPostingGroupRepository.save(entry);
    }
}
