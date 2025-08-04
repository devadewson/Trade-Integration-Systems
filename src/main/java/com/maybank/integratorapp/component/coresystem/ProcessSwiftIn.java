package com.maybank.integratorapp.component.coresystem;

import com.maybank.integratorapp.component.SftpFileTransfer;
import com.maybank.integratorapp.component.system.messageprocessor.LimitReservationMessageProcessor;
import com.maybank.integratorapp.data.service.LogInterfaceProcessService;
import com.maybank.integratorapp.data.service.MsParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProcessSwiftIn {
    private static Logger log = LoggerFactory.getLogger(ProcessSwiftIn.class);

    @Autowired
    MsParameterService repo;
    
    @Autowired
    LogInterfaceProcessService logger;
    public String removeHeaderAck(String swiftMessage) {
        // Define the pattern to match the header ACK tag
        String headerAckPattern = "\\{1:F21[^}]*\\}\\{4:\\{177:[^}]*\\}\\{451:[^}]*\\}\\}";

        // Remove the header ACK tag
        String cleanedMessage = swiftMessage.replaceFirst(headerAckPattern, "");

        return cleanedMessage.trim();
    }
    public Map<String, List<String>> getFileContent(Long loggerId){
//        Path folderPath = Paths.get("D:\\Agung\\Projects\\BankTrade Trade Transformation\\IntegrationList");
        Map<String, List<String>> fileContents = new HashMap<>();

        try {

            // Sftp Config
//            String sftpHost = repo.findValueByPrmKey("FTISwiftInSftpAddress");
//            String sftpUsername = repo.findValueByPrmKey("SwiftInSftpUsername");
//            String sftpPassword = repo.findValueByPrmKey("SwiftInSftpPassword");
//            String sftpPath = repo.findValueByPrmKey("SwiftInSftpPath");
            String localpath = repo.findValueByPrmKey("SwiftInLocalPath");

            String sftpHost = repo.findValueByPrmKey("FTISwiftInSftpAddress");
            String sftpUsername = repo.findValueByPrmKey("FTISwiftInSftpUsername");
            String sftpPassword = repo.findValueByPrmKey("FTISwiftInSftpPassword");
            String sftpPath = repo.findValueByPrmKey("FTISwiftInSftpPath");
            String sftpPort = repo.findValueByPrmKey("FTISwiftInSftpPort");

            SftpFileTransfer sftp = new SftpFileTransfer(sftpHost,sftpUsername,sftpPassword,sftpPath);
            List<String> listFileProcessed = sftp.getSwiftFile(localpath,logger,loggerId);
            Path folderPath = Paths.get(localpath);

            for (String pathFile:listFileProcessed) {
                Path file = Paths.get(pathFile);  // Convert the String path into a Path object

                // Check if the file exists before attempting to read it
                if (Files.exists(file) && Files.isReadable(file)) {
                    log.info("Reading file: " + file.getFileName());
                    logger.Log(loggerId,"SwiftIn - Reading Swift File", "Reading swift file", "READ-LOCAL",file.getFileName().toString());

                    List<String> fileContent = Files.readAllLines(file, StandardCharsets.UTF_8);  // Read the file content
                    fileContent.removeIf(s -> s == null || s.trim().isEmpty());
                    fileContents.put(file.getFileName().toString(), fileContent);  // Store the file content with the file name as the key
                } else {
                    log.error("File not found or not readable: " + pathFile);
                    logger.Log(loggerId,"SwiftIn - File Read Error", "File not found or not readable", "ERROR");
                }
            }

        } catch (IOException e) {
//            e.printStackTrace();
            logger.Log(loggerId,"SwiftIn - Reading Swift File","Reading swift file from local","ERROR",e.getMessage());
        }

        return fileContents;
    }

    public void moveToBackup(long loggerId){
        try {
            String localpath = repo.findValueByPrmKey("SwiftInLocalPath");
            String backuppath = repo.findValueByPrmKey("SwiftInBackupPath");

            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formattedDate = today.format(formatter);

            String completeBackupPath = backuppath+formattedDate+File.separator;

            File folder = new File(completeBackupPath);
            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    System.out.println("Folder created successfully.");
                }
            }
            Path sourceDir = Paths.get(localpath);
            Path targetDir = Paths.get(completeBackupPath);

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir)) {
                for (Path file : stream) {
                    if (Files.isRegularFile(file)) { // Check if it is a file (not a directory)
                        Path targetPath = targetDir.resolve(file.getFileName());
                        Files.move(file, targetPath, StandardCopyOption.REPLACE_EXISTING);
                        logger.Log(loggerId,"SwiftIn - Backup Files","File Moved to backup folder","BACKUP",file.getFileName().toString());

                        System.out.println("Moved: " + file.getFileName() + " to " + targetPath);
                    }
                }
            } catch (IOException e) {
//                System.err.println("Error moving files: " + e.getMessage());
                logger.Log(loggerId,"SwiftIn - Backup Files","File Moved to backup folder","ERROR",e.getMessage());

            }

        }
        catch (Exception e){
//            e.printStackTrace();
            logger.Log(loggerId,"SwiftIn - Backup Files","File Moved to backup folder","ERROR",e.getMessage());
        }
    }

    public void duplicateSwift(long loggerId){
        try {

            // Sftp Config
            String sftpHost = repo.findValueByPrmKey("TempSwiftInSftpAddress");
            String sftpUsername = repo.findValueByPrmKey("TempSwiftInSftpUsername");
            String sftpPassword = repo.findValueByPrmKey("TempSwiftInSftpPassword");
            String sftpPath = repo.findValueByPrmKey("TempSwiftInSftpPath");

            String BTsftpHost = repo.findValueByPrmKey("BTSwiftInSftpAddress");
            String BTsftpUsername = repo.findValueByPrmKey("BTSwiftInSftpUsername");
            String BTsftpPassword = repo.findValueByPrmKey("BTSwiftInSftpPassword");
            String BTsftpPath = repo.findValueByPrmKey("BTSwiftInSftpPath");
            String BTsftpPort = repo.findValueByPrmKey("BTSwiftInSftpPort");

            String FTIsftpHost = repo.findValueByPrmKey("FTISwiftInSftpAddress");
            String FTIsftpUsername = repo.findValueByPrmKey("FTISwiftInSftpUsername");
            String FTIsftpPassword = repo.findValueByPrmKey("FTISwiftInSftpPassword");
            String FTIsftpPath = repo.findValueByPrmKey("FTISwiftInSftpPath");
            String FTIsftpPort = repo.findValueByPrmKey("FTISwiftInSftpPort");

            SftpFileTransfer sftp = new SftpFileTransfer(sftpHost,sftpUsername,sftpPassword,sftpPath);
            sftp.forwardSwiftFileNew(
                    BTsftpHost,
                    Integer.parseInt(BTsftpPort),
                    BTsftpUsername,
                    BTsftpPassword,
                    BTsftpPath,
                    FTIsftpHost,
                    Integer.parseInt(FTIsftpPort),
                    FTIsftpUsername,
                    FTIsftpPassword,
                    FTIsftpPath,
                    logger,loggerId
                    );
//            sftp.forwardSwiftFile(
//                    BTsftpHost,
//                    BTsftpUsername,
//                    BTsftpPassword,
//                    BTsftpPath,
//                    FTIsftpHost,
//                    FTIsftpUsername,
//                    FTIsftpPassword,
//                    FTIsftpPath,
//                    logger,loggerId
//                    );

        } catch (Exception e) {
//            e.printStackTrace();
            logger.Log(loggerId,"SwiftIn - Reading Swift File","Reading swift file from local","ERROR",e.getMessage());
        }
    }
}
