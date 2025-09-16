package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.FtiTransactionDetailPosting;
import com.maybank.integratorapp.data.entity.SwiftXmlTemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwiftXmlTemplateRepository extends CrudRepository<SwiftXmlTemplate, Long> {
    @Query("SELECT t.TemplateContent FROM SwiftXmlTemplate t WHERE t.MxType = ?1 and t.SwiftStandardReleaseNumber = ?2")
    String findActiveTemplate(@Param("MxType") String targetMxType, @Param("SwiftStandardReleaseNumber") String srVersion);
}
