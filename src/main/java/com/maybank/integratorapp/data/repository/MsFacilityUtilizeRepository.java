package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsFacility;
import com.maybank.integratorapp.data.entity.MsFacilityUtilize;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MsFacilityUtilizeRepository extends CrudRepository<MsFacilityUtilize,Long> {

    @Query("SELECT f.keyLoanAcc FROM MsFacilityUtilize f")
    List<String> findAllKeyLoanAcc();
    @Query("SELECT f FROM MsFacilityUtilize f where f.keyLoanAcc = ?1")
    MsFacilityUtilize findByKeyLoanAcc(String keyLoanAcc);
    @Query("SELECT f FROM MsFacilityUtilize f join MsCompanyLimit c on c.id = f.companyLimitId where c.cifno=?1")
    List<MsFacilityUtilize> findAllFacilityUtilizeByCif(String cifNo);
}
