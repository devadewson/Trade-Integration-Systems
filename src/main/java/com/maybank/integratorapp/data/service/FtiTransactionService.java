package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.FtiTransaction;
import com.maybank.integratorapp.data.repository.FtiTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FtiTransactionService {

    @Autowired
    private FtiTransactionRepository ftiTransactionRepository;
    // Get all transactions with default sorting by ID descending
    public Page<FtiTransaction> getAllFtiTransactions(Pageable pageable) {
        return ftiTransactionRepository.findAll(pageable);
    }

    // Search transactions by masterRefNo
    public Page<FtiTransaction> searchByMasterRefNo(String masterRefNo, Pageable pageable) {
        return ftiTransactionRepository.findByMasterRefNoContainingIgnoreCase(masterRefNo, pageable);
    }

    public FtiTransaction getFtiTransactionById(Long id) {
        return ftiTransactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FtiTransaction not found with id: " + id));
    }
    @Transactional
    public FtiTransaction createOrUpdateFtiTransaction(FtiTransaction ftiTransaction) {
        // Check if the transaction already exists by masterRefNo
        Optional<FtiTransaction> existingTransaction = ftiTransactionRepository.findByMasterRefNo(ftiTransaction.getMasterRefNo());

        if (existingTransaction.isPresent()) {
            // Update the existing transaction
            FtiTransaction transactionToUpdate = existingTransaction.get();
            transactionToUpdate.setLastStep(ftiTransaction.getLastStep());
            transactionToUpdate.setLastEvent(ftiTransaction.getLastEvent());
            transactionToUpdate.setReservationId(ftiTransaction.getReservationId());
            transactionToUpdate.setDrawNumber(ftiTransaction.getDrawNumber());
            transactionToUpdate.setUpdateDate(new Date());
            return ftiTransactionRepository.save(transactionToUpdate);
        } else {
            // Create a new transaction
            ftiTransaction.setCreatedDate(new Date());
            ftiTransaction.setUpdateDate(new Date());
            return ftiTransactionRepository.save(ftiTransaction);
        }
    }

    public Optional<FtiTransaction> findByMasterRefNo(String masterRefNo) {
        return ftiTransactionRepository.findByMasterRefNo(masterRefNo);
    }
}
