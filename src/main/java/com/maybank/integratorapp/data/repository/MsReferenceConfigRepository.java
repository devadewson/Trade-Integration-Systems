package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsReferenceConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MsReferenceConfigRepository extends CrudRepository<MsReferenceConfig, Long> {

    @Query("SELECT m FROM MsReferenceConfig m WHERE m.RefType = :refType AND m.RefPurpose = :refPurpose AND m.RefProduct = :refProduct")
    MsReferenceConfig getConfig(@Param("refType") String refType,@Param("refPurpose") String refPurpose, @Param("refProduct") String refProduct);
}