package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.FtiTransactionDetailPostingGroup;
import com.maybank.integratorapp.data.repository.FtiTransactionDetailPostingGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FtiTransactionDetailPostingGroupService {
    @Autowired
    private FtiTransactionDetailPostingGroupRepository ftiTransactionDetailPostingGroupRepository;

    public FtiTransactionDetailPostingGroup save(FtiTransactionDetailPostingGroup entry) {

        return ftiTransactionDetailPostingGroupRepository.save(entry);
    }
}
