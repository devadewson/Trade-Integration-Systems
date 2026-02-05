package com.maybank.integratorapp.component;

import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import com.maybank.integratorapp.component.coresystem.ProcessCompositeTBR;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import jakarta.jms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

//@Component
public class MessagePublisher {
    private static Logger log = LoggerFactory.getLogger(MessagePublisher.class);

    public MessagePublisher(String brokerUrl,int port, String manager,String channel, String username,String password,String destinationQueue){
        this.connectionFactory= createIBMConnectionFactory(brokerUrl,port,manager,channel,username,password);
        try {
            this.connection = this.connectionFactory.createConnection();
            this.connection.start();
            this.session = createSession(this.connection);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        this.destinationQueue = destinationQueue;
    }

    public void close(){
        try {
            this.session.close();
            this.connection.close();
            if (connectionFactory instanceof CachingConnectionFactory) {
                ((CachingConnectionFactory) connectionFactory).destroy();
            }

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
    public MessagePublisher(Connection conn, Session sess, String destinationQueue){
        this.connection = conn;
        this.session = sess;
        this.destinationQueue = destinationQueue;
    }
    public MessagePublisher(MsQueueConfig config){
        this.config = config;
        this.connection = createConnection(config);
        try {
            this.connection.start();
            this.session = createSession(this.connection);
            this.destinationQueue = config.getResponse_Queue_Name();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }


    }
    private MsQueueConfig config;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private final String destinationQueue;

    public String getDestinationQueue() {
        return destinationQueue;
    }
    public Connection getConnection(){return this.connection;}
    public Session getSession(){return this.session;}

    // Helper method to create a connection
    private Connection createConnection(MsQueueConfig config) {
        try {
            ConnectionFactory connectionFactory = createIBMConnectionFactory(
                    config.getResponse_Queue_Address(),
                    Integer.parseInt(config.getResponse_Queue_Port()),
                    config.getResponse_Queue_Manager(),
                    config.getResponse_Queue_Channel(),
                    config.getResponse_Queue_Username(),
                    config.getResponse_Queue_Password()
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
            connectionFactory.setAppName("IntegratorPublisher");
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
//        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
//        connectionFactory.setBrokerURL(brokerUrl);
//        connectionFactory.setUserName(username);
//        connectionFactory.setPassword(password);
//
//        return connectionFactory;
//    }
//    @Value("${queue.address}")
//    private String queueAddress;
//    @Value("${queue.accountinquiry.response}")
//    private String accountInquiryQueueName;
//    @Autowired
//    private Environment env;
    public void PublishMessage(String message,String correlationId){

//        AppConfig config = new AppConfig();
        // Connection settings
//        String brokerUrl = "tcp://10.235.83.44:61616"; // URL of the ActiveMQ broker
//        String brokerUrl = env.getProperty("queue.address"); // URL of the ActiveMQ broker
//
////        String queueName = "QResponse"; // Name of the queue you want to send the message to
////        String queueName = env.getProperty("queue.accountinquiry.response");
//        String mqUser = env.getProperty("spring.activemq.user");
//        String mqPassword = env.getProperty("spring.activemq.password");

        // Create a connection factory
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(mqUser,mqPassword,brokerUrl);

        try {

//            ObjectMapper mapper = new ObjectMapper();
//            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(message);

            // Create a connection
//            Connection connection = connectionFactory.createConnection();

            // Create a session
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (queue)
            Destination destination = session.createQueue(destinationQueue);

            // Create a message producer
            jakarta.jms.MessageProducer producer = session.createProducer(destination);

            // Create a text message
            TextMessage _message = session.createTextMessage();
            _message.setJMSCorrelationID(correlationId);
            _message.setText(message);

            // Send the message
            producer.setTimeToLive(6000_000);
            producer.send(_message);

            log.info("Message sent successfully With CorrelationID : "+_message.getJMSCorrelationID());

            // Clean up
            if(this.config == null){
                producer.close();
                session.close();
                connection.close();
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }


    }


}
