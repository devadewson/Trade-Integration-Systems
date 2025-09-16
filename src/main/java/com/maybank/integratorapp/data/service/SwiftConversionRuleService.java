package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.repository.SwiftConversionRuleRepository;
import com.maybank.integratorapp.data.repository.SwiftMtMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwiftConversionRuleService {
    private static Logger log = LoggerFactory.getLogger(SwiftConversionRuleService.class);

    @Autowired
    private SwiftConversionRuleRepository repo;
}
