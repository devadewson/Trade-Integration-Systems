package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.SwiftMtMessage;
import com.maybank.integratorapp.data.entity.SwiftMtTag;
import com.maybank.integratorapp.data.repository.SwiftMtTagRepository;
import com.maybank.integratorapp.util.swiftconverter.SwiftMtParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwiftMtTagService {
    @Autowired
    private SwiftMtTagRepository mtTagRepository;

    public void parseAndStoreTags(SwiftMtMessage mtMessage, String rawMessage) {
        // Logic to parse the raw MT message into lines starting with :XX:
        List<SwiftMtParser.ParsedTag> parsedTags = SwiftMtParser.parse(rawMessage);

        int sequenceOrder = 0;
        for (SwiftMtParser.ParsedTag parsedTag : parsedTags) {
            SwiftMtTag tag = new SwiftMtTag();
            tag.setSwiftMtMessageId(mtMessage.getId());
            tag.setTagId(parsedTag.getTagId());
            tag.setTagValue(parsedTag.getTagValue());
            tag.setSequenceOrder(sequenceOrder++);
            mtTagRepository.save(tag);
        }
    }
}
