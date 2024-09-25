package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MsTBR",schema = "dbo")
@NoArgsConstructor
public class MsTBR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String TBRCode;
    private String TBRName;
    private String TBRMethod;

    public String getTBRCode() {
        return TBRCode;
    }

    public void setTBRCode(String TBRCode) {
        this.TBRCode = TBRCode;
    }

    public String getTBRName() {
        return TBRName;
    }

    public void setTBRName(String TBRName) {
        this.TBRName = TBRName;
    }

    public String getTBRMethod() {
        return TBRMethod;
    }

    public void setTBRMethod(String TBRMethod) {
        this.TBRMethod = TBRMethod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
