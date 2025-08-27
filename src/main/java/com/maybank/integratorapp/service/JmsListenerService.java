package com.maybank.integratorapp.service;

import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import com.ibm.msg.client.wmq.internal.BalancingApplicationType;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.listener.*;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import jakarta.jms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class JmsListenerService {
//    @Autowired
//    private AccountBalanceMessageListener accountBalanceMessageListener;
//    @Autowired
//    private AccountInformationMessageListener accountInformationMessageListener;
//    @Autowired
//    private CustomerSearchMessageListener customerSearchMessageListener;
//    @Autowired
//    private BatchPostingMessageListener batchPostingMessageListener;
//    @Autowired
//    private SwiftOutMessageListener swiftOutMessageListener;
//    @Autowired
//    private AccountInquiryMessageListener accountInquiryMessageListener;
//    @Autowired
//    private CustomerDetailMessageListener customerDetailMessageListener;
//    @Autowired
//    private FacilitiesMessageListener facilitiesMessageListener;
//    @Autowired
//    private FacilitiesDetailMessageListener facilitiesDetailMessageListener;
//    @Autowired
//    private ReservationListener reservationListener;
//    @Autowired
//    private ReservationReversalListener reservationReversalListener;
//    @Autowired
//    private LimitUtilizationListener utilizationListener;
    @Autowired
    private ApplicationContext context;
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
    private void createAndRegisterNewListener(MsQueueConfig config) {
        //Development Use Only
//            if (!config.getServiceName().equals("Facilities")
//                    && !config.getServiceName().equals("FacilityReservation")
//                    && !config.getServiceName().equals("FacilityUtilization")
//                    && !config.getServiceName().equals("ReservationReversal"))
//                return;
//        if (!config.getServiceName().equals("CustomerSearch"))
//            return;
        try {
            // Reuse existing connection if available
            Connection connection = connections.computeIfAbsent(config.getServiceName(), key -> createConnection(config));
            connection.start();

            // Reuse existing session if available
            Session session = sessions.computeIfAbsent(config.getServiceName(), key -> createSession(connection));

            Destination destination = session.createQueue(config.getRequest_Queue_Name());
            MessageConsumer consumer = session.createConsumer(destination);

            // Choose the correct listener
//            String listenerPackages = "com.maybank.integratorapp.component.listener."+config.getListenerName();
            Class listenerClass = chooseListener(config.getServiceName());

            if(listenerClass !=null){
                CustomMessageListener listener = (CustomMessageListener) context.getBean(listenerClass);
                // If response queue exists, create a publisher
                if (!config.getResponse_Queue_Address().isEmpty()) {
                    MessagePublisher publisher = new MessagePublisher(config);
                    listener.setPublisher(publisher);
                }

                consumer.setMessageListener(listener);
                System.out.println("Listener started for queue: " + config.getRequest_Queue_Name());
            }

//            CustomMessageListener listener = (CustomMessageListener) chooseListener(config.getServiceName());

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    // Helper method to create a connection
    private Connection createConnection(MsQueueConfig config) {
        try {
            ConnectionFactory connectionFactory = createIBMConnectionFactory(
                    config.getRequest_Queue_Address(),
                    Integer.parseInt(config.getRequest_Queue_Port()),
                    config.getRequest_Queue_Manager(),
                    config.getRequest_Queue_Channel(),
                    config.getRequest_Queue_Username(),
                    config.getRequest_Queue_Password()
            );
            return connectionFactory.createConnection();
        } catch (JMSException e) {
            throw new RuntimeException("Failed to create connection", e);
        }
    }

    // Helper method to create a session
    private Session createSession(Connection connection) {
        try {
//            return connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            throw new RuntimeException("Failed to create session", e);
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
            connectionFactory.setAppName("IntegratorListener");
//            connectionFactory.setStringProperty(WMQConstants.WMQ_CCSID, "1208"); // Set CCSID if necessary

//            Connection connection = connectionFactory.createConnection(username, password);
            // Wrap MQConnectionFactory with CachingConnectionFactory
            CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
            cachingConnectionFactory.setSessionCacheSize(5); // Adjust session pool size as needed
            cachingConnectionFactory.setReconnectOnException(true); // Reuse connection on failures


            return cachingConnectionFactory;
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
//


    private Class chooseListener(String queueName) {
        switch (queueName) {
            case "BatchPosting":
                return BatchPostingMessageListener.class;
            case "CustomerSearch":
                return CustomerSearchMessageListener.class;
            case "AccountInquiry":
                return AccountInquiryMessageListener.class;
            case "SwiftOut":
                return SwiftOutMessageListener.class;
            case "CustomerDetails":
                return CustomerDetailMessageListener.class;
            case "Facilities":
                return FacilitiesMessageListener.class;
            case "FacilityReservation":
                return ReservationListener.class;
            case "ReservationReversal":
                return ReservationReversalListenerXL41.class;
            case "FacilityUtilization":
                return LimitUtilizationListener.class;
            case "FacilitiesDetails":
                return FacilitiesDetailMessageListener.class;
            case "AccountBalance":
                return AccountBalanceMessageListener.class;

        }
        return null;
    }
//    private MessageListener chooseListener(String queueName) {
//        switch (queueName) {
//            case "BatchPosting":
//                return batchPostingMessageListener;
//            case "CustomerSearch":
//                return customerSearchMessageListener;
//            case "AccountInquiry":
//                return accountInquiryMessageListener;
//            case "SwiftOut":
//                return swiftOutMessageListener;
//            case "CustomerDetails":
//                return customerDetailMessageListener;
//            case "Facilities":
//                return facilitiesMessageListener;
//            case "FacilityReservation":
//                return reservationListener;
//            case "ReservationReversal":
//                return reservationReversalListener;
//            case "FacilityUtilization":
//                return utilizationListener;
//            case "FacilitiesDetails":
//                return facilitiesDetailMessageListener;
//            case "AccountBalance":
//                return accountInquiryMessageListener;
//
//        }
//        return null;
//    }
}
