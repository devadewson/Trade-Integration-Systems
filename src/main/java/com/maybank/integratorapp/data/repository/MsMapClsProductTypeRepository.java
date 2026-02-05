package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsMapClsProductType;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MsMapClsProductTypeRepository extends CrudRepository<MsMapClsProductType,Long> {
    @Query("SELECT f.ProductType001 FROM MsMapClsProductType f " +
            "WHERE f.ProductType999 = ?1 " +
            "AND f.LineOfBusiness LIKE %?2% " +
            "AND f.EventCode LIKE %?3%")
    String findDraw001Product(String draw999Product,@Nullable String lineofBusiness,@Nullable String eventCode);

    @Query("SELECT f.ProductType001 FROM MsMapClsProductType f " +
            "WHERE f.ProductType999 = ?1 " +
            "AND f.LineOfBusiness LIKE %?2% " +
            "AND f.LiabilityCode LIKE %?3% " +
            "AND f.EventCode LIKE %?4%")
    String findDraw001ProductWithLiabCode(String draw999Product, @Nullable String lineofBusiness, @Nullable String liabilityCode, @Nullable String eventCode);

    @Query("SELECT f FROM MsMapClsProductType f where f.ProductType999=?1")
    List<MsMapClsProductType> findDraw001Products(String draw999Product);
}
