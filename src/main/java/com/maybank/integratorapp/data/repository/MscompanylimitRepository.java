package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsCompanyLimit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MscompanylimitRepository extends CrudRepository<MsCompanyLimit,Long> {
    boolean existsByCifno (String cifno);
    MsCompanyLimit findByCifno(String cifno);
}
