package com.maybank.integratorapp.component.coresystem;

import com.maybank.integratorapp.component.SftpFileTransfer;
import com.maybank.integratorapp.data.service.LogInterfaceProcessService;
import com.maybank.integratorapp.data.service.MsParameterService;
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
    @Autowired
    MsParameterService repo;
    public Map<String, List<String>> getFileContent(LogInterfaceProcessService logger){
//        Path folderPath = Paths.get("D:\\Agung\\Projects\\BankTrade Trade Transformation\\IntegrationList");
        Map<String, List<String>> fileContents = new HashMap<>();

        try {

            // Sftp Config
            String sftpHost = repo.findValueByPrmKey("SwiftInSftpAddress");
            String sftpUsername = repo.findValueByPrmKey("SwiftInSftpUsername");
            String sftpPassword = repo.findValueByPrmKey("SwiftInSftpPassword");
            String sftpPath = repo.findValueByPrmKey("SwiftInSftpPath");
            String localpath = repo.findValueByPrmKey("SwiftInLocalPath");

            SftpFileTransfer sftp = new SftpFileTransfer(sftpHost,sftpUsername,sftpPassword,sftpPath);
            List<String> listFileProcessed = sftp.getSwiftFile(localpath,logger);
            Path folderPath = Paths.get(localpath);

            for (String pathFile:listFileProcessed) {
                Path file = Paths.get(pathFile);  // Convert the String path into a Path object

                // Check if the file exists before attempting to read it
                if (Files.exists(file) && Files.isReadable(file)) {
                    System.out.println("Reading file: " + file.getFileName());
                    logger.Log("SwiftIn - Reading Swift File", "Reading swift file", "READ-LOCAL",file.getFileName().toString());

                    List<String> fileContent = Files.readAllLines(file, StandardCharsets.UTF_8);  // Read the file content
                    fileContents.put(file.getFileName().toString(), fileContent);  // Store the file content with the file name as the key
                } else {
                    System.err.println("File not found or not readable: " + pathFile);
                    logger.Log("SwiftIn - File Read Error", "File not found or not readable", "ERROR");
                }
            }

        } catch (IOException e) {
//            e.printStackTrace();
            logger.Log("SwiftIn - Reading Swift File","Reading swift file from local","ERROR",e.getMessage());
        }

        return fileContents;
    }

    public void moveToBackup(LogInterfaceProcessService logger){
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
                        logger.Log("SwiftIn - Backup Files","File Moved to backup folder","BACKUP",file.getFileName().toString());

                        System.out.println("Moved: " + file.getFileName() + " to " + targetPath);
                    }
                }
            } catch (IOException e) {
//                System.err.println("Error moving files: " + e.getMessage());
                logger.Log("SwiftIn - Backup Files","File Moved to backup folder","ERROR",e.getMessage());

            }

        }
        catch (Exception e){
//            e.printStackTrace();
            logger.Log("SwiftIn - Backup Files","File Moved to backup folder","ERROR",e.getMessage());
        }
    }

    public void duplicateSwift(LogInterfaceProcessService logger){
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

            String FTIsftpHost = repo.findValueByPrmKey("FTISwiftInSftpAddress");
            String FTIsftpUsername = repo.findValueByPrmKey("FTISwiftInSftpUsername");
            String FTIsftpPassword = repo.findValueByPrmKey("FTISwiftInSftpPassword");
            String FTIsftpPath = repo.findValueByPrmKey("FTISwiftInSftpPath");

            SftpFileTransfer sftp = new SftpFileTransfer(sftpHost,sftpUsername,sftpPassword,sftpPath);
            sftp.forwardSwiftFile(
                    BTsftpHost,
                    BTsftpUsername,
                    BTsftpPassword,
                    BTsftpPath,
                    FTIsftpHost,
                    FTIsftpUsername,
                    FTIsftpPassword,
                    FTIsftpPath,
                    logger
                    );

        } catch (Exception e) {
//            e.printStackTrace();
            logger.Log("SwiftIn - Reading Swift File","Reading swift file from local","ERROR",e.getMessage());
        }
    }
}
