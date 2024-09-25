package com.maybank.integratorapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.web.client.RestTemplate;
@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public JmsListenerEndpointRegistry jmsListenerEndpointRegistry() {
        return new JmsListenerEndpointRegistry();
    }
}
