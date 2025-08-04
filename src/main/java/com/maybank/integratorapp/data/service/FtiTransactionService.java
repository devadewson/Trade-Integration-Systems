package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.FtiTransaction;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.repository.FtiTransactionRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FtiTransactionService {

    @Autowired
    private FtiTransactionRepository ftiTransactionRepository;

    public Page<FtiTransaction> getAllWithSearch(Pageable pageable,
                                               Optional<String> transref,
                                               Optional<String> reservationid)
    {
        Specification<FtiTransaction> spec = (root, query, cb) ->
        {
            List<Predicate> predicates = new ArrayList<>();

            if (reservationid.isPresent() && !reservationid.get().isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("reservationId")),
                        "%" + reservationid.get().toLowerCase() + "%"
                ));
            }

            if (transref.isPresent() && !transref.get().isEmpty()) {
                predicates.add(cb.like(
                        root.get("masterRefNo"),
                        "%" + transref.get().toLowerCase() + "%"
                ));
            }

            return predicates.isEmpty()
                    ? cb.conjunction()  // no filter if both null/empty
                    : cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<FtiTransaction> result = ftiTransactionRepository.findAll(spec, pageable);

        return result;
    }
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

    public Page<FtiTransaction> searchByReservationId(String s, Pageable pageable) {
        return ftiTransactionRepository.findByReservationId(s,pageable);
    }
}
