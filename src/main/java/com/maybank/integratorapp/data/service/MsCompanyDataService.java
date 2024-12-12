package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsCompanyData;
import com.maybank.integratorapp.data.repository.MsCompanyDataRepository;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsCompanyDataService {
    @Autowired
    private MsCompanyDataRepository repo;

    public String findCifByGcif(String gcif){
        return repo.findCifByGcif(gcif);
    }
    public String findGcifByCif(String cif){
        return repo.findGcifByCif(cif);
    }
    public MsCompanyData findByGcif(String gcif){
        return repo.findByGcif(gcif);
    }
}
