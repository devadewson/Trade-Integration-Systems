package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.SwiftMtMessage;
import com.maybank.integratorapp.data.entity.SwiftMxMessage;
import com.maybank.integratorapp.data.repository.SwiftMtMessageRepository;
import com.maybank.integratorapp.data.repository.SwiftMxMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwiftMxMessageService {
    private static Logger log = LoggerFactory.getLogger(SwiftMxMessageService.class);

    @Autowired
    private SwiftMxMessageRepository repo;

    public void createMxMessage(SwiftMtMessage mtMessage, String targetMxType, String finalXml, String srVersion) {
        SwiftMxMessage _new = new SwiftMxMessage();
        _new.setSwiftMtMessageId(mtMessage.getId());
        _new.setMessageType(targetMxType);
        _new.setGeneratedXml(finalXml);
        _new.setSwiftStandardReleaseVersion(srVersion);
        try{
            _new = repo.save(_new);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
