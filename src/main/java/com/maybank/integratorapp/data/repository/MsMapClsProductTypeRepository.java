package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsFacility;
import com.maybank.integratorapp.data.entity.MsMapClsProductType;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MsMapClsProductTypeRepository extends CrudRepository<MsMapClsProductType,Long> {
    @Query("SELECT f.ProductType001 FROM MsMapClsProductType f where f.ProductType999=?1 and f.LineOfBusiness=?2 and f.EventCode=?3")
    String findDraw001Product(String draw999Product,@Nullable String lineofBusiness,@Nullable String eventCode);
}
