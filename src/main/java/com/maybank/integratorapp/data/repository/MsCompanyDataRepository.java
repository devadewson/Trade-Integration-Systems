package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsCompanyData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MsCompanyDataRepository extends CrudRepository<MsCompanyData, Long> {
    @Query("select cifno from MsCompanyData m where m.gcifno = ?1")
    String findCifByGcif(String gcif);

    @Query("select gcifno from MsCompanyData m where m.cifno = ?1")
    String findGcifByCif(String cif);

    @Query("select m from MsCompanyData m where m.gcifno = ?1")
    MsCompanyData findByGcif(String gcif);

    @Query("select m from MsCompanyData m where m.cifno = ?1")
    MsCompanyData findByCif(String cif);
}
