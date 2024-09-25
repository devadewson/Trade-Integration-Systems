package com.maybank.integratorapp.component;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

//@Component
public class MessagePublisher {

//    @Autowired
//    private JmsTemplate jmsTemplate;

    @Autowired
    private Environment env;

    public MessagePublisher(String brokerUrl, String username,String password,String destinationQueue){
        this.connectionFactory= createConnectionFactory(brokerUrl,username,password);
        this.destinationQueue = destinationQueue;
    }

    private ConnectionFactory connectionFactory;
    private final String destinationQueue;

    public String getDestinationQueue() {
        return destinationQueue;
    }

    private ConnectionFactory createConnectionFactory(String brokerUrl, String username, String password) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(username);
        connectionFactory.setPassword(password);

        return connectionFactory;
    }
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
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (queue)
            Destination destination = session.createQueue(destinationQueue);

            // Create a message producer
            jakarta.jms.MessageProducer producer = session.createProducer(destination);

            // Create a text message
            TextMessage _message = session.createTextMessage();
            _message.setJMSCorrelationID(correlationId);
            _message.setText(message);

            // Send the message
            producer.send(_message);

            System.out.println("Message sent successfully With CorrelationID : "+_message.getJMSCorrelationID());

            // Clean up
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }


    }


}
