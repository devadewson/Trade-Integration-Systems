package com.maybank.integratorapp.component;

import jakarta.jms.MessageListener;

public interface CustomMessageListener extends MessageListener {

    public void setPublisher(MessagePublisher publisher);

}
