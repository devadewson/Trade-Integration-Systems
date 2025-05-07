package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.FtiTransactionDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FtiTransactionDetailRepository extends CrudRepository<FtiTransactionDetail, Long> {
    List<FtiTransactionDetail> findByHeaderId(Long headerId);
    List<FtiTransactionDetail> findByTransMessageLogId(Long transMessageLogId);
}
