package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.FtiTransactionPosting;
import com.maybank.integratorapp.data.repository.FtiTransactionPostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FtiTransactionPostingService {
    @Autowired
    private FtiTransactionPostingRepository postingRepository;

    public List<FtiTransactionPosting> getAllPostingByHeaderId(Long idHeader){
        return postingRepository.getAllPostingByHeaderId(idHeader);
    }
    public void updatePostingSequence(List<FtiTransactionPosting> postings) {
        for (FtiTransactionPosting posting : postings) {
            postingRepository.updateSequenceNo(posting.getId(), posting.getTransactionSeqNo());
        }
    }
}
