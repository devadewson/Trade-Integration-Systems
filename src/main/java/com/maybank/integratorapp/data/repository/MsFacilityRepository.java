package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsCompanyLimit;
import com.maybank.integratorapp.data.entity.MsFacility;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MsFacilityRepository extends CrudRepository<MsFacility,Long> {
    MsFacility findByKeyDigitNote(String keyDigitNote);
    List<MsFacility> findByCompanyLimitId(Long companyLimitId);
    MsFacility findByKeyLoanAcc(String keyLoanAcc);


}
