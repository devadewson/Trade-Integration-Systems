package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsCurrency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MsCurrencyRepository extends CrudRepository<MsCurrency, Long> {
    @Query("select m from MsCurrency m where m.IsoCode = ?1")
    MsCurrency findByIsoCode(String currencyCode);

    @Query("select m from MsCurrency m where m.InternalCode = ?1")
    MsCurrency findByInternalCode(String internalCode);

}
