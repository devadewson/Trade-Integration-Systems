package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsTBRMapping;
import com.maybank.integratorapp.data.entity.MsUtilizeRunningNumber;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MsUtilizeRunningNumberRepository extends CrudRepository<MsUtilizeRunningNumber,Long> {
    MsUtilizeRunningNumber findByFacilityId(Long facilityId);

    @Query("SELECT m FROM MsUtilizeRunningNumber m JOIN FETCH m.facility WHERE m.companyLimitId = :companyLimitId")
    List<MsUtilizeRunningNumber> findByIdWithFacility(@Param("companyLimitId") Long companyLimitId);
}
