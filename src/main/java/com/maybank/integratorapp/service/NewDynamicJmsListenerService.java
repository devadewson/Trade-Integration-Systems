package com.maybank.integratorapp.service;

import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import com.ibm.msg.client.jakarta.wmq.common.CommonConstants;
import com.ibm.msg.client.jakarta.wmq.compat.jms.internal.JMSC;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.listener.*;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import jakarta.jms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.config.*;
import org.springframework.jms.connection.CachingConnectionFactory;
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
    private FacilitiesMessageListener facilitiesMessageListener;
    @Autowired
    private FacilitiesDetailMessageListener facilitiesDetailMessageListener;
    @Autowired
    private ReservationListener reservationListener;
    @Autowired
    private ReservationReversalListener reservationReversalListener;
    @Autowired
    private LimitUtilizationListener utilizationListener;

    @Autowired
    @Qualifier("jmsListenerEndpointRegistry")
    private JmsListenerEndpointRegistry registry;

    private Map<String, Session> sessions = new HashMap<>();
    private Map<String, Connection> connections = new HashMap<>();

    public void configureListeners(List<MsQueueConfig> queueConfigs) {
        for (MsQueueConfig config : queueConfigs) {
            if(!sessions.containsKey(config.getServiceName())) {
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
    }

    private void stopListener(MsQueueConfig config) {

        MessageListenerContainer container = registry.getListenerContainer(config.getServiceName());
        if (container != null && container.isRunning()) {
            container.stop();
            System.out.println("Stopped listener for queue " + config.getRequest_Queue_Name());
        }
    }
    private void createAndRegisterNewListener(MsQueueConfig config) {

        try {
            Connection connection = connections.get(config.getServiceName());
            if (connection == null) {
                // Create a new connection only if not already present
                ConnectionFactory connectionFactory = createIBMConnectionFactory(
                        config.getRequest_Queue_Address(),
                        Integer.parseInt(config.getRequest_Queue_Port()),
                        config.getRequest_Queue_Manager(),
                        config.getRequest_Queue_Channel(),
                        config.getRequest_Queue_Username(),
                        config.getRequest_Queue_Password()
                );
                connection = connectionFactory.createConnection();
                connection.start();
                connections.put(config.getServiceName(), connection);
            }

            // Create a new session (reuse the connection)
            Session session = sessions.get(config.getServiceName());
            if (session == null) {
                session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
                sessions.put(config.getServiceName(), session);
            }

            // Create a queue and a message consumer
            Destination destination = session.createQueue(config.getRequest_Queue_Name());
            CustomMessageListener listener = (CustomMessageListener) chooseListener(config.getServiceName());
            MessageConsumer consumer = session.createConsumer(destination);

            // Attach response publisher if needed
            if (!config.getResponse_Queue_Address().isEmpty()) {
                MessagePublisher publisher = new MessagePublisher(
                        config.getResponse_Queue_Address(),
                        Integer.parseInt(config.getResponse_Queue_Port()),
                        config.getResponse_Queue_Manager(),
                        config.getResponse_Queue_Channel(),
                        config.getResponse_Queue_Username(),
                        config.getResponse_Queue_Password(),
                        config.getResponse_Queue_Name());
                listener.setPublisher(publisher);
            }

            // Set the message listener
            consumer.setMessageListener(listener);
            System.out.println("Reconfigured and started listener for queue " + config.getRequest_Queue_Name());

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

//    private void createAndRegisterNewListener(MsQueueConfig config) {
//        Connection connection = null;
//        Session session = null;
//        try {
//            //Development Use Only
////            if (!config.getServiceName().equals("Facilities")
////                    && !config.getServiceName().equals("FacilityReservation")
////                    && !config.getServiceName().equals("FacilityUtilization")
////                    && !config.getServiceName().equals("ReservationReversal"))
////                return;
////            if (!config.getServiceName().equals("BatchPosting"))
////                return;
//
//            // Create a new connection
//            ConnectionFactory connectionFactory = createIBMConnectionFactory(
//                    config.getRequest_Queue_Address(),
//                    Integer.parseInt(config.getRequest_Queue_Port()),
//                    config.getRequest_Queue_Manager(),
//                    config.getRequest_Queue_Channel(),
//                    config.getRequest_Queue_Username(),
//                    config.getRequest_Queue_Password()
//            );
//            connection = connectionFactory.createConnection();
//            connection.start();
//
//            // Create a new session
//            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
//
//            // Create a queue and a message consumer
//            Destination destination = session.createQueue(config.getRequest_Queue_Name());
//            CustomMessageListener listener = (CustomMessageListener) chooseListener(config.getServiceName());
//            MessageConsumer consumer = session.createConsumer(destination);
//            if (!config.getResponse_Queue_Address().isEmpty()) {
//                MessagePublisher publisher = new MessagePublisher(
//                        config.getResponse_Queue_Address(),
//                        Integer.parseInt(config.getResponse_Queue_Port()),
//                        config.getResponse_Queue_Manager(),
//                        config.getResponse_Queue_Channel(),
//                        config.getResponse_Queue_Username(),
//                        config.getResponse_Queue_Password(),
//                        config.getResponse_Queue_Name());
//                listener.setPublisher(publisher);
//            }
//            // Set the message listener
//            consumer.setMessageListener(listener);
//
//            // Store the new connection and session for later use
//            connections.put(config.getServiceName(), connection);
//            sessions.put(config.getServiceName(), session);
//
//            System.out.println("Reconfigured and started listener for queue " + config.getRequest_Queue_Name());
//
//        } catch (JMSException e) {
//            e.printStackTrace(); // Handle exception
//        }
//    }

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

    public ConnectionFactory createIBMConnectionFactory(String brokerUrl,int port, String manager, String channel, String username, String password) {
        try {
            MQConnectionFactory connectionFactory = new MQConnectionFactory();
            connectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
//            connectionFactory.setIntProperty(CommonConstants.WMQ_CONNECTION_MODE, CommonConstants.WMQ_CM_CLIENT);
//            connectionFactory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
            connectionFactory.setQueueManager(manager); // Replace with your queue manager name
            connectionFactory.setHostName(brokerUrl); // Replace with your hostname
            connectionFactory.setPort(port); // Replace with your port number
            connectionFactory.setChannel(channel); // Replace with your channel name
//            connectionFactory.setStringProperty(WMQConstants.WMQ_CCSID, "1208"); // Set CCSID if necessary

//            Connection connection = connectionFactory.createConnection(username, password);
            // Wrap MQConnectionFactory with CachingConnectionFactory
            CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
            cachingConnectionFactory.setSessionCacheSize(5); // Adjust session pool size as needed
            cachingConnectionFactory.setReconnectOnException(true); // Reuse connection on failures


            return connectionFactory;
        } catch (JMSException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create IBM MQ connection factory", e);
        }
    }
//    private ConnectionFactory createConnectionFactory(String brokerUrl, String username, String password) {
//        IBMM connectionFactory = new ActiveMQConnectionFactory();
//        connectionFactory.setBrokerURL(brokerUrl);
//        connectionFactory.setUserName(username);
//        connectionFactory.setPassword(password);
//        return connectionFactory;
//    }

    private MessageListener chooseListener(String queueName) {
        switch (queueName) {
            case "BatchPosting":
                return batchPostingMessageListener;
            case "CustomerSearch":
                return customerSearchMessageListener;
            case "AccountInquiry":
                return accountInquiryMessageListener;
            case "SwiftOut":
                return swiftOutMessageListener;
            case "CustomerDetails":
                return customerDetailMessageListener;
            case "Facilities":
                return facilitiesMessageListener;
            case "FacilityReservation":
                return reservationListener;
            case "ReservationReversal":
                return reservationReversalListener;
            case "FacilityUtilization":
                return utilizationListener;
            case "FacilitiesDetails":
                return facilitiesDetailMessageListener;
            case "AccountBalance":
                return accountInquiryMessageListener;

        }
        return null;
    }
}
