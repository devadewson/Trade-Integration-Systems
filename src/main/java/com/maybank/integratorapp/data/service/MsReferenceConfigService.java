package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsBranch;
import com.maybank.integratorapp.data.entity.MsReferenceConfig;
import com.maybank.integratorapp.data.repository.MsBranchRepository;
import com.maybank.integratorapp.data.repository.MsReferenceConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsReferenceConfigService {
    @Autowired
    MsReferenceConfigRepository repository;


    public MsReferenceConfig getConfig(String reftype, String refpurpose, String refproduct) {
        return repository.getConfig(reftype,refpurpose,refproduct);
    }

    public String generateReference(String reftype,String refpurpose,String refproduct,String refstring){
        String formatted = "";
        MsReferenceConfig _config = getConfig(reftype,refpurpose,refproduct);
        if(_config!=null){
            String _refConfig = _config.getRefConfig();
            StringBuilder _formatted = new StringBuilder();
            for(String _substringFormat : _refConfig.split("#")){
                if(_substringFormat.contains("@")){
                    _formatted.append(_substringFormat.replace("@",""));

                }else{
                    int _StartSubstring = Integer.parseInt(_substringFormat.split(",")[0]);
                    int _EndSubstring = _StartSubstring+ Integer.parseInt(_substringFormat.split(",")[1]);

                    _formatted.append(refstring, _StartSubstring, _EndSubstring);
                }


            }
            formatted = _formatted.toString();

        }
        return formatted;
    }
}
