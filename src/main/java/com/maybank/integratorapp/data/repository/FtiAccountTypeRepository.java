package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.FtiAccountType;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsAccountType;
import com.maybank.integratorapp.data.entity.MsTBRField;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface FtiAccountTypeRepository extends CrudRepository<FtiAccountType, Long> {

    @Query("select m from MsAccountType m join FtiAccountType fat on m.id =fat.AccountTypeId where fat.AccountType = ?1")
    MsAccountType findByFtiAccountType(String ftiAccountType);


}
