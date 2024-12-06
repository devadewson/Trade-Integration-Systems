package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsFacilityUtilize;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MsFacilityUtilizeRepository extends CrudRepository<MsFacilityUtilize,Long> {

}
