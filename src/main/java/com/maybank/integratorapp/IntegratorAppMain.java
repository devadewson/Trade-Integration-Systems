package com.maybank.integratorapp;


//import com.maybank.integratorapp.component;
//import com.maybank.integratorapp.service.DynamicJmsListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.maybank.integratorapp")
@EnableJpaRepositories(basePackages = "com.maybank.integratorapp.data.repository")
@EnableScheduling
//@ComponentScan(basePackages = {
//        "com.maybank.integratorapp.config"
//        ,"com.maybank.integratorapp.data"
//        ,"com.maybank.integratorapp.component.coresystem"
//        ,"com.maybank.integratorapp.component.listener"
//        ,"com.maybank.integratorapp.service"
//        ,"com.maybank.integratorapp.controller"})
@ComponentScan
public class IntegratorAppMain  extends SpringBootServletInitializer {


    public static void main(String[] args) {

        SpringApplication.run(IntegratorAppMain.class, args);

        System.out.println("Application is Running...");
    }


}