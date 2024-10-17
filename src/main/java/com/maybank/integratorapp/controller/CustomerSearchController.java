package com.maybank.integratorapp.controller;

import com.maybank.integratorapp.component.coresystem.ProcessCostumerSearch;
import com.maybank.integratorapp.component.listener.CustomerSearchMessageListener;
import com.maybank.integratorapp.model.mq.customersearch.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.customersearch.response.CustomerSearchResult;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerSearchController {
    @Autowired
    private CustomerSearchMessageListener customerSearchMessageListener;
    @PostMapping("/Customer")
    public ResponseEntity<String> GetCostumer(){
        try{

            return new ResponseEntity<>("Received.", HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

