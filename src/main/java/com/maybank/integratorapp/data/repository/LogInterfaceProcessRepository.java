package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.LogInterfaceProcess;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LogInterfaceProcessRepository extends CrudRepository<LogInterfaceProcess, Long> {

}