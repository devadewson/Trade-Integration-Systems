package com.maybank.integratorapp.component.coresystem;

import com.maybank.integratorapp.component.SftpFileTransfer;
import com.maybank.integratorapp.component.listener.SwiftOutMessageListener;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.data.service.LogInterfaceProcessService;
import com.maybank.integratorapp.data.service.MsParameterService;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.util.MQUtil;
import com.maybank.integratorapp.util.MTtoMXConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProcessSwiftOut {
    private static Logger log = LoggerFactory.getLogger(ProcessSwiftOut.class);

    @Autowired
    MsParameterService repo;
    @Autowired
    LogInterfaceProcessService logger;

    @Autowired
    MTtoMXConverter converter;


    public void putFileContent(List<String> fileContent, String correlationId, Long idLogParent) {

        try {

//            this.logger.SetLogParent(idLogParent);

            String mxConversion = repo.findValueByPrmKey("SwiftOutMXConversion");
            String mxConversionTypes = repo.findValueByPrmKey("SwiftOutMXConversionTypes");
            // Sftp Config
            String sftpHost = repo.findValueByPrmKey("SwiftOutSftpAddress");
            String sftpUsername = repo.findValueByPrmKey("SwiftOutSftpUsername");
            String sftpPassword = repo.findValueByPrmKey("SwiftOutSftpPassword");
            String sftpPath = repo.findValueByPrmKey("SwiftOutSftpPath");
            String localpath = repo.findValueByPrmKey("SwiftOutLocalPath");
            String sftpPort = repo.findValueByPrmKey("SwiftOutSftpPort");
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String additionalPath = "FTI_"+correlationId+"_"+ MQUtil.generateRandomString(4).toUpperCase();
            String specificPath = localpath+File.separator+additionalPath;

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
                String completePath = specificPath+File.separator+fileName;
                try (PrintWriter out = new PrintWriter(completePath)) {
                    String updatedContent = str.replace("\n", "\r\n");

                    out.print(updatedContent);
//                    if (!updatedContent.endsWith("\r\n")) {
//                        out.print("\r\n");
//                    }
                    logger.Log(idLogParent,"SwiftOut - Creating Swift File","Creating swift file from data","DATA-LOCAL",str);

                }
                i++;
            }

            // Transfer all file
            SftpFileTransfer sftp = new SftpFileTransfer(sftpHost,sftpUsername,sftpPassword,sftpPath);
            if(sftpPort.equals("22"))
                sftp.putSwiftFile(specificPath,logger,idLogParent);
            else
                sftp.putSwiftFileFTP(specificPath,logger,idLogParent);

            // MX CONVERSION
            if(mxConversion.equals("true")){
//                MTtoMXConverter converter = new MTtoMXConverter();
                additionalPath = "FTI_MX_"+correlationId+"_"+ MQUtil.generateRandomString(4).toUpperCase();
                specificPath = localpath+File.separator+additionalPath;

                folder = new File(specificPath);

                if (!folder.exists()) {
                    if (folder.mkdirs()) {
                        System.out.println("Folder created successfully.");
                    }
                }

                i = 1;
                for (String str: fileContent) {
//                String formattedString = str.substring(header.length(),(str.length() - header.length() - footer.length()));
                    String endingFile =  "_"+i+".xml";
                    String fileName = additionalPath+endingFile;
                    String completePath = specificPath+File.separator+fileName;

                    try (PrintWriter out = new PrintWriter(completePath)) {
                        String updatedContent = str.replace("\n", "\r\n");
                        try {
                            String mtType = converter.extractMTType(updatedContent);
                            if(Arrays.asList(mxConversionTypes.split(",")).contains(mtType)){
                                String mxMessage = converter.convertMTtoMX(updatedContent);
//                                System.out.println(mxMessage);
                                out.print(mxMessage);
                                logger.Log(idLogParent,"SwiftOut - Creating Swift MX File","Creating swift MX file from data","DATA-LOCAL",mxMessage);

                            }else{
                                log.error("MT to MX Conversion error: "+mtType+" Unsupported for Conversion");
                            }
                        } catch (Exception e) {
                            log.error("MT to MX Conversion error: " + e.getMessage());
                        }
//                    if (!updatedContent.endsWith("\r\n")) {
//                        out.print("\r\n");
//                    }

                    }

                    i++;
                }


            }


        } catch (Exception e) {
            logger.Log(idLogParent,"SwiftOut - Creating Swift File","Creating swift file from data","ERROR",e.getMessage());

        }

    }
}
