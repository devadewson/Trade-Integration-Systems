package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MsCurrency",schema = "dbo")
@NoArgsConstructor
public class MsCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String IsoCode;
    private String InternalCode;
    private String CountryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsoCode() {
        return IsoCode;
    }

    public void setIsoCode(String isoCode) {
        IsoCode = isoCode;
    }

    public String getInternalCode() {
        return InternalCode;
    }

    public void setInternalCode(String internalCode) {
        InternalCode = internalCode;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }
}
