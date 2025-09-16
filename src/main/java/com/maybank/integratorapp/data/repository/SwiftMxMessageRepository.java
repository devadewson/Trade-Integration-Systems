package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.SwiftMxMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwiftMxMessageRepository extends CrudRepository<SwiftMxMessage, Long> {

}