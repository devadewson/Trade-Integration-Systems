package com.maybank.integratorapp.component.coresystem;

import com.maybank.integratorapp.component.SftpFileTransfer;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.data.service.LogInterfaceProcessService;
import com.maybank.integratorapp.data.service.MsParameterService;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.util.MQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProcessSwiftOut {

    @Autowired
    MsParameterService repo;
    @Autowired
    LogInterfaceProcessService logger;

    public void putFileContent(List<String> fileContent, String correlationId, Long idLogParent) {

        try {

            this.logger.SetLogParent(idLogParent);
            // Sftp Config
            String sftpHost = repo.findValueByPrmKey("SwiftOutSftpAddress");
            String sftpUsername = repo.findValueByPrmKey("SwiftOutSftpUsername");
            String sftpPassword = repo.findValueByPrmKey("SwiftOutSftpPassword");
            String sftpKey = repo.findValueByPrmKey("SwiftOutSftpKey");
            String sftpPath = repo.findValueByPrmKey("SwiftOutSftpPath");
            String localpath = repo.findValueByPrmKey("SwiftOutLocalPath");
            String additionalPath = "FTI_"+correlationId+"_"+ MQUtil.generateRandomString(4).toUpperCase();
            String specificPath = localpath+"\\"+additionalPath;

            File folder = new File(specificPath);

            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    System.out.println("Folder created successfully.");
                }
            }
            // Write All Files
            int i = 1;
            for (String str: fileContent) {
//                String formattedString = str.substring(header.length(),(str.length() - header.length() - footer.length()));
                String endingFile =  "_"+i+".txt";
                String fileName = additionalPath+endingFile;
                String completePath = specificPath+"\\"+fileName;
                try (PrintWriter out = new PrintWriter(completePath)) {
                    out.println(str.trim());
                    logger.Log("SwiftOut - Creating Swift File","Creating swift file from data","DATA-LOCAL",str);

                }
                i++;
            }

            // Transfer all file
            SftpFileTransfer sftp = new SftpFileTransfer(sftpHost,sftpUsername,sftpPassword,sftpKey,sftpPath);
            sftp.putSwiftFile(specificPath,logger);


        } catch (Exception e) {
            logger.Log("SwiftOut - Creating Swift File","Creating swift file from data","ERROR",e.getMessage());

        }

    }
}
