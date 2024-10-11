package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MsQueueConfig",schema = "dbo")
@NoArgsConstructor
public class MsQueueConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "service_name")
    private String ServiceName;
    private String ListenerName;
    private String Request_Queue_Name;
    private String Request_Queue_Address;

    private String Request_Queue_Username;
    private String Request_Queue_Password;

    private String Response_Queue_Name;
    private String Response_Queue_Address;

    private String Response_Queue_Username;

    public String getListenerName() {
        return ListenerName;
    }

    public void setListenerName(String listenerName) {
        ListenerName = listenerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getRequest_Queue_Name() {
        return Request_Queue_Name;
    }

    public void setRequest_Queue_Name(String request_Queue_Name) {
        Request_Queue_Name = request_Queue_Name;
    }

    public String getRequest_Queue_Address() {
        return Request_Queue_Address;
    }

    public void setRequest_Queue_Address(String request_Queue_Address) {
        Request_Queue_Address = request_Queue_Address;
    }

    public String getRequest_Queue_Username() {
        return Request_Queue_Username;
    }

    public void setRequest_Queue_Username(String request_Queue_Username) {
        Request_Queue_Username = request_Queue_Username;
    }

    public String getRequest_Queue_Password() {
        return Request_Queue_Password;
    }

    public void setRequest_Queue_Password(String request_Queue_Password) {
        Request_Queue_Password = request_Queue_Password;
    }

    public String getResponse_Queue_Name() {
        return Response_Queue_Name;
    }

    public void setResponse_Queue_Name(String response_Queue_Name) {
        Response_Queue_Name = response_Queue_Name;
    }

    public String getResponse_Queue_Address() {
        return Response_Queue_Address;
    }

    public void setResponse_Queue_Address(String response_Queue_Address) {
        Response_Queue_Address = response_Queue_Address;
    }

    public String getResponse_Queue_Username() {
        return Response_Queue_Username;
    }

    public void setResponse_Queue_Username(String response_Queue_Username) {
        Response_Queue_Username = response_Queue_Username;
    }

    public String getResponse_Queue_Password() {
        return Response_Queue_Password;
    }

    public void setResponse_Queue_Password(String response_Queue_Password) {
        Response_Queue_Password = response_Queue_Password;
    }

    public int getEnableStatus() {
        return EnableStatus;
    }

    public void setEnableStatus(int enableStatus) {
        EnableStatus = enableStatus;
    }

    private String Response_Queue_Password;

    private int EnableStatus;



}
