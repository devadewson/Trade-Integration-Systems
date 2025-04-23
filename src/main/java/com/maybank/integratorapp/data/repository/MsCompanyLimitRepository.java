package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsCompanyLimit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MsCompanyLimitRepository extends CrudRepository<MsCompanyLimit,Long> {
    boolean existsByCifno (String cifno);
    MsCompanyLimit findByCifno(String cifno);
    Page<MsCompanyLimit> findAll(Pageable pageable);
    @Query("SELECT t FROM MsCompanyLimit t WHERE LOWER(t.cifno) LIKE LOWER(CONCAT('%', :CIFNo, '%'))")
    Page<MsCompanyLimit> findByCIFNoContainingIgnoreCase(@Param("CIFNo") String CIFNo, Pageable pageable);
    @Query("SELECT t FROM MsCompanyLimit t WHERE LOWER(t.cifno) LIKE LOWER(CONCAT('%', :CIFNo, '%'))")
    MsCompanyLimit findByCIFNo(@Param("CIFNo") String CIFNo);
}
