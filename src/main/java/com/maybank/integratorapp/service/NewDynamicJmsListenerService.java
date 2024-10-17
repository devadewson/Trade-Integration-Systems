package com.maybank.integratorapp.service;

import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.listener.*;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.config.*;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewDynamicJmsListenerService {
    @Autowired
    private AccountBalanceMessageListener accountBalanceMessageListener;
    @Autowired
    private AccountInformationMessageListener accountInformationMessageListener;
    @Autowired
    private CustomerSearchMessageListener customerSearchMessageListener;
    @Autowired
    private BatchPostingMessageListener batchPostingMessageListener;
    @Autowired
    private SwiftOutMessageListener swiftOutMessageListener;
    @Autowired
    private AccountInquiryMessageListener accountInquiryMessageListener;
    @Autowired
    private CustomerDetailMessageListener customerDetailMessageListener;
    @Autowired
    private ApplicationContext context;

    @Autowired
    @Qualifier("jmsListenerEndpointRegistry")
    private JmsListenerEndpointRegistry registry;

    private Map<String, Session> sessions = new HashMap<>();
    private Map<String, Connection> connections = new HashMap<>();

    public void configureListeners(List<MsQueueConfig> queueConfigs) {
        for (MsQueueConfig config : queueConfigs) {

            if ((!config.getRequest_Queue_Name().equals(null)
                    && !config.getRequest_Queue_Name().equals(""))
            ) {
                if (config.getEnableStatus() == 1) {
                    createAndRegisterNewListener(config);
                } else {
                    stopExistingListener(config);
                }
            }
        }
    }

    private void stopListener(MsQueueConfig config) {

        MessageListenerContainer container = registry.getListenerContainer(config.getServiceName());
        if (container != null && container.isRunning()) {
            container.stop();
            System.out.println("Stopped listener for queue " + config.getRequest_Queue_Name());
        }
    }

    private void createAndRegisterNewListener(MsQueueConfig config) {
        Connection connection = null;
        Session session = null;
        try {
            //Development Use Only
            if (!config.getServiceName().equals("CustomerDetails"))
                return;

            // Create a new connection
            ConnectionFactory connectionFactory = createConnectionFactory(
                    config.getRequest_Queue_Address(),
                    config.getRequest_Queue_Username(),
                    config.getRequest_Queue_Password()
            );
            connection = connectionFactory.createConnection();
            connection.start();

            // Create a new session
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            // Create a queue and a message consumer
            Destination destination = session.createQueue(config.getRequest_Queue_Name());
            CustomMessageListener listener = (CustomMessageListener) chooseListener(config.getServiceName());
            MessageConsumer consumer = session.createConsumer(destination);
            if (!config.getResponse_Queue_Address().isEmpty()) {
                MessagePublisher publisher = new MessagePublisher(config.getResponse_Queue_Address(),
                        config.getResponse_Queue_Username(), config.getResponse_Queue_Password(),
                        config.getResponse_Queue_Name());
                listener.setPublisher(publisher);
            }
            // Set the message listener
            consumer.setMessageListener(listener);

            // Store the new connection and session for later use
            connections.put(config.getServiceName(), connection);
            sessions.put(config.getServiceName(), session);

            System.out.println("Reconfigured and started listener for queue " + config.getRequest_Queue_Name());

        } catch (JMSException e) {
            e.printStackTrace(); // Handle exception
        }
    }

    private void stopExistingListener(MsQueueConfig config) {
        // Assuming you maintain a map or list of connections/sessions
        Connection existingConnection = connections.get(config.getServiceName());
        Session existingSession = sessions.get(config.getServiceName());

        if (existingSession != null) {
            try {
                existingSession.close(); // Close the session
                sessions.remove(config.getServiceName());
                System.out.println("Stopping listener session for queue " + config.getRequest_Queue_Name());

            } catch (JMSException e) {
                e.printStackTrace(); // Handle exception
            }
        }
        if (existingConnection != null) {
            try {
                System.out.println("Stopping listener connection for queue " + config.getRequest_Queue_Name());
                existingConnection.close(); // Close the connection
                connections.remove(config.getServiceName());
            } catch (JMSException e) {
                e.printStackTrace(); // Handle exception
            }
        }
    }

    private void closeConnection(Session session, Connection connection) {
        try {
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            e.printStackTrace(); // Handle exception as needed
        }
    }


    private ConnectionFactory createConnectionFactory(String brokerUrl, String username, String password) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    private MessageListener chooseListener(String queueName) {
        switch (queueName) {
            case "QBatchPostingReq":
                return batchPostingMessageListener;
            case "QCustomerSearchReq":
                return customerSearchMessageListener;
            case "AccountInquiry":
                return accountInquiryMessageListener;
            case "SwiftOut":
                return swiftOutMessageListener;
            case "CustomerDetails":
                return customerDetailMessageListener;
            case "AccountBalance":
            default:
                return accountBalanceMessageListener;

        }
    }
}
