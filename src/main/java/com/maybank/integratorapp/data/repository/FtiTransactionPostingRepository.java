package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.FtiTransactionPosting;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface FtiTransactionPostingRepository extends CrudRepository<FtiTransactionPosting, Long> {
    @Modifying
    @Query("UPDATE FtiTransactionPosting p SET p.transactionSeqNo = :sequenceNo WHERE p.id = :id")
    void updateSequenceNo(@Param("id") Long id, @Param("sequenceNo") String sequenceNo);

    @Query("select m from FtiTransactionPosting m where m.idHeader = ?1")
    List<FtiTransactionPosting> getAllPostingByHeaderId(Long idHeader);
}
