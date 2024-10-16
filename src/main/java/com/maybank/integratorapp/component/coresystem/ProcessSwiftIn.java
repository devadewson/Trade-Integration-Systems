package com.maybank.integratorapp.component.coresystem;

import com.maybank.integratorapp.component.SftpFileTransfer;
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
    public Map<String, List<String>> getFileContent(){
//        Path folderPath = Paths.get("D:\\Agung\\Projects\\BankTrade Trade Transformation\\IntegrationList");
        Map<String, List<String>> fileContents = new HashMap<>();

        try {

            // Sftp Config
            String sftpHost = repo.findValueByPrmKey("SwiftInSftpAddress");
            String sftpUsername = repo.findValueByPrmKey("SwiftInSftpUsername");
            String sftpPassword = repo.findValueByPrmKey("SwiftInSftpPassword");
            String sftpKey = repo.findValueByPrmKey("SwiftInSftpKey");
            String sftpPath = repo.findValueByPrmKey("SwiftInSftpPath");
            String localpath = repo.findValueByPrmKey("SwiftInLocalPath");

            SftpFileTransfer sftp = new SftpFileTransfer(sftpHost,sftpUsername,sftpPassword,sftpKey,sftpPath);
            sftp.getSwiftFile(localpath);
            Path folderPath = Paths.get(localpath);

            Files.walkFileTree(folderPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".txt")) {
                        System.out.println("Reading file: " + file.getFileName());
                        List<String> fileContent = Files.readAllLines(file, StandardCharsets.UTF_8);
                        fileContents.put(file.getFileName().toString(), fileContent);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContents;
    }

    public void moveToBackup(){
        try {
            String localpath = repo.findValueByPrmKey("SwiftInLocalPath");
            String backuppath = repo.findValueByPrmKey("SwiftInBackupPath");

            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formattedDate = today.format(formatter);

            String completeBackupPath = backuppath+formattedDate+"\\";

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
                        System.out.println("Moved: " + file.getFileName() + " to " + targetPath);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error moving files: " + e.getMessage());
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
