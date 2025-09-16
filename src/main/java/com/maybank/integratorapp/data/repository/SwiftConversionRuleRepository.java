package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.SwiftConversionRule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwiftConversionRuleRepository extends CrudRepository<SwiftConversionRule, Long> {
    @Query("SELECT cr FROM SwiftConversionRule cr WHERE cr.SourceMtType = :mtType " +
            "AND cr.TargetMxType = :mxType AND cr.SwiftStandardReleaseNumber = :srVersion " +
            "AND cr.IsActive = 'Y' ORDER BY cr.id")
    List<SwiftConversionRule> findActiveRules(@Param("mtType") String mtType,
                                              @Param("mxType") String mxType,
                                              @Param("srVersion") String srVersion);
}