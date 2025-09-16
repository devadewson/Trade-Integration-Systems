package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.component.system.messageprocessor.AccountInquiryMessageProcessor;
import com.maybank.integratorapp.data.entity.SwiftMtMessage;
import com.maybank.integratorapp.data.repository.SwiftMtMessageRepository;
import com.maybank.integratorapp.util.swiftconverter.SwiftMtMessageStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SwiftMtMessageService {
    private static Logger log = LoggerFactory.getLogger(SwiftMtMessageService.class);

    @Autowired
    private SwiftMtMessageRepository repo;

    public SwiftMtMessage createMessage(String rawSwiftMessage, String messageType, String srVersion) {
        SwiftMtMessage _new = new SwiftMtMessage();
        _new.setRawMessage(rawSwiftMessage);
        _new.setMessageType(messageType);
        _new.setSwiftStandardReleaseVersion(srVersion);
        _new.setCreatedDate(new Date());
        try{
            _new = repo.save(_new);
        }catch (Exception e){
            log.error(e.getMessage());
        }


        return _new;
    }

    public void updateStatus(Long id, SwiftMtMessageStatus status) {
        SwiftMtMessage _data = repo.findById(id).get();
        try{
            _data.setMessageStatus(status.name());
            _data = repo.save(_data);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
