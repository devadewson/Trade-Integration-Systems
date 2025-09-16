package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.repository.SwiftMtMessageRepository;
import com.maybank.integratorapp.data.repository.SwiftXmlTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwiftXmlTemplateService {
    private static Logger log = LoggerFactory.getLogger(SwiftXmlTemplateService.class);

    @Autowired
    private SwiftXmlTemplateRepository repo;

    public String getActiveTemplate(String targetMxType, String srVersion) {
        return repo.findActiveTemplate(targetMxType,srVersion);
    }
}
