package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.FtiTransaction;
import com.maybank.integratorapp.data.entity.FtiTransactionDetail;
import com.maybank.integratorapp.data.repository.FtiTransactionDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FtiTransactionDetailService {

    @Autowired
    private FtiTransactionDetailRepository ftiTransactionDetailRepository;

    @Autowired
    private FtiTransactionService ftiTransactionService;
    public List<FtiTransactionDetail> getDetailsByHeaderId(Long headerId) {
        return ftiTransactionDetailRepository.findByHeaderId(headerId);
    }
    public FtiTransactionDetail createDetailByMasterRefNo(String masterRefNo, FtiTransactionDetail detail) {
        // Find the FtiTransaction by masterRefNo
        FtiTransaction ftiTransaction = ftiTransactionService.findByMasterRefNo(masterRefNo)
                .orElseGet(() -> {
                    // If not found, create a new FtiTransaction
                    FtiTransaction newTransaction = new FtiTransaction();
                    newTransaction.setMasterRefNo(masterRefNo);
                    newTransaction.setLastEvent(detail.getFtiEvent());
                    newTransaction.setLastStep(detail.getTransName());
                    newTransaction.setCreatedDate(new Date());
                    newTransaction.setUpdateDate(new Date());
                    return ftiTransactionService.createOrUpdateFtiTransaction(newTransaction);
                });

        // Set the headerId of the detail to the ID of the found FtiTransaction
        detail.setHeaderId(ftiTransaction.getId());

        // Set the createdDate for the detail
        detail.setCreatedDate(new Date());

        // Save the detail
        FtiTransactionDetail savedDetail = ftiTransactionDetailRepository.save(detail);

        // Update the FtiTransaction's lastEvent with the detail's ftiEvent
        ftiTransaction.setLastStep(detail.getTransName());
        ftiTransaction.setLastEvent(detail.getFtiEvent());
        ftiTransaction.setUpdateDate(new Date());
        ftiTransactionService.createOrUpdateFtiTransaction(ftiTransaction);

        return savedDetail;
    }
}