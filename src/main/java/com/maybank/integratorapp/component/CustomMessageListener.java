package com.maybank.integratorapp.component;

import jakarta.jms.MessageListener;
import org.springframework.stereotype.Component;

@Component
public interface CustomMessageListener extends MessageListener {

    public void setPublisher(MessagePublisher publisher);

}
