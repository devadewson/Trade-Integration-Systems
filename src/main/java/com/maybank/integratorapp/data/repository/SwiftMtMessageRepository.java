package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.FtiAccountType;
import com.maybank.integratorapp.data.entity.MsAccountType;
import com.maybank.integratorapp.data.entity.SwiftMtMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwiftMtMessageRepository extends CrudRepository<SwiftMtMessage, Long> {


}