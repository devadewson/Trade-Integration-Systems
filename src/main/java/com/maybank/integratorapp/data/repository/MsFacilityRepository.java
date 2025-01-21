package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsCompanyLimit;
import com.maybank.integratorapp.data.entity.MsFacility;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MsFacilityRepository extends CrudRepository<MsFacility,Long> {
    MsFacility findByKeyDigitNote(String keyDigitNote);
    List<MsFacility> findByCompanyLimitId(Long companyLimitId);
    MsFacility findByKeyLoanAcc(String keyLoanAcc);

    @Query("SELECT f.keyLoanAcc FROM MsFacility f")
    List<String> findAllKeyLoanAcc();

    @Query("SELECT f FROM MsFacility f join MsCompanyLimit c on c.id = f.companyLimitId where c.cifno=?1")
    List<MsFacility> findAllFacilitiesByCif(String cifNo);


}
