package com.maybank.integratorapp.component;

import com.jcraft.jsch.*;
import com.maybank.integratorapp.data.service.LogInterfaceProcessService;
import com.maybank.integratorapp.util.FileTransferManager;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SftpFileTransfer {

    private String sftpHost = "";
    private int sftpPort = 22;
    private String sftpUser;
    private String sftpPassword;
    private String remoteDirectoryPath;
    private String protocol;
    private Session sftpSession;
    private ChannelSftp sftpChannel;
    private FTPClient ftpClient;

    public SftpFileTransfer(String address,String user,String password,String remotePath){
        this.sftpHost = address;
        this.sftpUser = user;
        this.sftpPassword = password;
        this.remoteDirectoryPath = remotePath;
    }

    private Session connectToSftp(String host, String user, String password) throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, 22);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setConfig("PreferredAuthentications","publickey,keyboard-interactive,password");
        session.connect();
        return session;
    }
    public void forwardSwiftFileNew(
            String destination1SftpHost,
            int destination1SftpPort,
            String destination1SftpUser,
            String destination1SftpPassword,
            String destination1SftpPath,

            String destination2SftpHost,
            int destination2SftpPort,
            String destination2SftpUser,
            String destination2SftpPassword,
            String destination2SftpPath,
            LogInterfaceProcessService logger,
            long loggerId){

        List<String> fileProcessed = new ArrayList<>();
        JSch jsch = new JSch();
        Session sourceSession = null;

        ChannelSftp sourceChannelSftp = null;
        FileTransferManager dest1 = new FileTransferManager(
                destination1SftpHost, destination1SftpPort, destination1SftpUser, destination1SftpPassword);
        FileTransferManager dest2 = new FileTransferManager(
                destination2SftpHost, destination2SftpPort, destination2SftpUser, destination2SftpPassword);

        try {
            // Create session and connect to the SFTP server
            sourceSession = connectToSftp(sftpHost,sftpUser,sftpPassword);
            // Open SFTP channel
            sourceChannelSftp = (ChannelSftp) sourceSession.openChannel("sftp");
            sourceChannelSftp.connect();


            // Open connections once
            dest1.openConnection();
            dest2.openConnection();

            logger.Log(loggerId,"SwiftIn - Forward Swift File","Forward swift file from SwiftSAA","CONNECTED");

            // Download file from the SFTP server
            Vector<ChannelSftp.LsEntry> fileList = sourceChannelSftp.ls(remoteDirectoryPath);
            for (ChannelSftp.LsEntry entry : fileList) {
                if (!entry.getAttrs().isDir()) { // Only process files, skip directories
                    String fileName = entry.getFilename();
                    String sourceFilePath = remoteDirectoryPath + "/" + fileName;
                    String destination1FilePath = destination1SftpPath + "/" + fileName;
                    String destination2FilePath = destination2SftpPath + "/" + fileName;

                    File tempFile = File.createTempFile("sftp-", ".tmp");
                    try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                        sourceChannelSftp.get(sourceFilePath, fos);
                    }
                    dest1.transferFile(tempFile.getAbsoluteFile().toPath(), destination1FilePath);
                    dest2.transferFile(tempFile.getAbsoluteFile().toPath(), destination2FilePath);

                    // Delete the temporary file
                    tempFile.delete();
                    sourceChannelSftp.rm(sourceFilePath);



//                        System.out.println("Downloaded SWIFT file: " + entry.getFilename());
                    logger.Log(loggerId,"SwiftIn - Forward Swift File","Forward swift file from SwiftSAA","FORWARDED",entry.getFilename());

                }
            }

        } catch (Exception e) {
//            e.printStackTrace();
            logger.Log(loggerId,"SwiftIn - Forward Swift File","Forward swift file from SwiftSAA","ERROR",e.getMessage());

        } finally {
            // Close SFTP channel and session
            if (sourceChannelSftp != null && sourceChannelSftp.isConnected()) {
                sourceChannelSftp.disconnect();
            }
            if (sourceSession != null && sourceSession.isConnected()) {
                sourceSession.disconnect();
            }

            dest1.closeConnection();
            dest2.closeConnection();
        }
    }

    public void forwardSwiftFile(
            String destination1SftpHost,
            String destination1SftpUser,
            String destination1SftpPassword,
            String destination1SftpPath,

            String destination2SftpHost,
            String destination2SftpUser,
            String destination2SftpPassword,
            String destination2SftpPath,
            LogInterfaceProcessService logger,
            long loggerId) {
//        String remoteFilePath = "/path/on/remote/server/file.txt";

        List<String> fileProcessed = new ArrayList<>();
        JSch jsch = new JSch();
        Session sourceSession = null;
        Session destination1Session = null;
        Session destination2Session = null;
        ChannelSftp sourceChannelSftp = null;
        ChannelSftp destination1ChannelSftp = null;
        ChannelSftp destination2ChannelSftp = null;
        try {
            // Create session and connect to the SFTP server
            sourceSession = connectToSftp(sftpHost,sftpUser,sftpPassword);
            // Open SFTP channel
            sourceChannelSftp = (ChannelSftp) sourceSession.openChannel("sftp");
            sourceChannelSftp.connect();

            // Create session and connect to the SFTP server
            destination1Session = connectToSftp(destination1SftpHost,destination1SftpUser,destination1SftpPassword);
            // Open SFTP channel
            destination1ChannelSftp = (ChannelSftp) destination1Session.openChannel("ftp");
            destination1ChannelSftp.connect();

            // Create session and connect to the SFTP server
            destination2Session = connectToSftp(destination2SftpHost,destination2SftpUser,destination2SftpPassword);
            // Open SFTP channel
            destination2ChannelSftp = (ChannelSftp) destination2Session.openChannel("sftp");
            destination2ChannelSftp.connect();


            logger.Log(loggerId,"SwiftIn - Forward Swift File","Forward swift file from SwiftSAA","CONNECTED");

            // Download file from the SFTP server
            Vector<ChannelSftp.LsEntry> fileList = sourceChannelSftp.ls(remoteDirectoryPath);
            for (ChannelSftp.LsEntry entry : fileList) {
                if (!entry.getAttrs().isDir()) { // Only process files, skip directories
                    String fileName = entry.getFilename();
                    String sourceFilePath = remoteDirectoryPath + "/" + fileName;
                    String destination1FilePath = destination1SftpPath + "/" + fileName;
                    String destination2FilePath = destination2SftpPath + "/" + fileName;

                    File tempFile = File.createTempFile("sftp-", ".tmp");
                    try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                        sourceChannelSftp.get(sourceFilePath, fos);
                    }

                    // Upload the file to the destination server
                    destination1ChannelSftp.put(tempFile.getAbsolutePath(), destination1FilePath);
                    // Upload the file to the destination server
                    destination2ChannelSftp.put(tempFile.getAbsolutePath(), destination2FilePath);

                    // Delete the temporary file
                    tempFile.delete();
//                        System.out.println("Downloaded SWIFT file: " + entry.getFilename());
                    logger.Log(loggerId,"SwiftIn - Forward Swift File","Forward swift file from SwiftSAA","FORWARDED",entry.getFilename());

                }
            }

        } catch (Exception e) {
//            e.printStackTrace();
            logger.Log(loggerId,"SwiftIn - Forward Swift File","Forward swift file from SwiftSAA","ERROR",e.getMessage());

        } finally {
            // Close SFTP channel and session
            if (sourceChannelSftp != null && sourceChannelSftp.isConnected()) {
                sourceChannelSftp.disconnect();
            }
            if (sourceSession != null && sourceSession.isConnected()) {
                sourceSession.disconnect();
            }

            if (destination1ChannelSftp != null && destination1ChannelSftp.isConnected()) {
                destination1ChannelSftp.disconnect();
            }
            if (destination1Session != null && destination1Session.isConnected()) {
                destination1Session.disconnect();
            }

            if (destination2ChannelSftp != null && destination2ChannelSftp.isConnected()) {
                destination2ChannelSftp.disconnect();
            }
            if (destination2Session != null && destination2Session.isConnected()) {
                destination2Session.disconnect();
            }

        }
    }
    public List<String> getSwiftFile(String localDirectoryPath, LogInterfaceProcessService logger,long loggerId) {
//        String remoteFilePath = "/path/on/remote/server/file.txt";

        List<String> fileProcessed = new ArrayList<>();
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            // Create session and connect to the SFTP server
            session = jsch.getSession(sftpUser, sftpHost, sftpPort);
            session.setPassword(sftpPassword);

            // Configure session to avoid host key checking
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications","publickey,keyboard-interactive,password");

            session.connect();

            // Open SFTP channel
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            logger.Log(loggerId,"SwiftIn - Downloading Swift File","Download swift file from SwiftSAA","CONNECTED");

            // Upload all files from the local directory to the remote directory
//            File localDirectory = new File(localDirectoryPath);
//            if (localDirectory.isDirectory()) {
//                for (File file : localDirectory.listFiles()) {
//                    if (file.isFile()) { // Only process files, skip directories
//                        try (InputStream inputStream = new FileInputStream(file)) {
//                            channelSftp.put(inputStream, remoteDirectoryPath + file.getName());
//                            System.out.println("Uploaded SWIFT file: " + file.getName());
//                        }
//                    }
//                }
//            } else {
//                System.out.println(localDirectoryPath + " is not a directory.");
//            }

            // Download file from the SFTP server
            Vector<ChannelSftp.LsEntry> fileList = channelSftp.ls(remoteDirectoryPath);
            for (ChannelSftp.LsEntry entry : fileList) {
                if (!entry.getAttrs().isDir()) { // Only process files, skip directories
                    String remoteFilePath = remoteDirectoryPath + entry.getFilename();
                    String localFilePath = localDirectoryPath + entry.getFilename();
                    try (OutputStream outputStream = new FileOutputStream(localFilePath)) {
                        channelSftp.get(remoteFilePath, outputStream);
//                        System.out.println("Downloaded SWIFT file: " + entry.getFilename());
                        logger.Log(loggerId,"SwiftIn - Downloading Swift File","Download swift file from SwiftSAA","DOWNLOADED",entry.getFilename());

                        channelSftp.rm(remoteFilePath);
                        fileProcessed.add(localFilePath);
                    }
                }
            }

        } catch (Exception e) {
//            e.printStackTrace();
            logger.Log(loggerId,"SwiftIn - Downloading Swift File","Download swift file from SwiftSAA","ERROR",e.getMessage());

        } finally {
            // Close SFTP channel and session
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }

            return fileProcessed;
        }
    }
    public void putSwiftFileFTP(String localDirectoryPath, LogInterfaceProcessService logger,long loggerId){
        FTPClient ftpClient = new FTPClient();
        try {
            // Connect to the server
            ftpClient.connect(sftpHost);
            ftpClient.login(sftpUser, sftpPassword);

            // Set binary file type for reliable transfer
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Enable passive mode if behind firewall
            ftpClient.enterLocalPassiveMode();

            File localFile = new File(localDirectoryPath);
//            FileInputStream inputStream = new FileInputStream(localFile);

            logger.Log(loggerId,"SwiftOut - Sending Swift File","Sending swift file from local to FTP","CONNECTED");


            // Upload file to server
            File localDirectory = new File(localDirectoryPath);
            if (localDirectory.isDirectory()) {
                for (File file : localDirectory.listFiles()) {
                    if (file.isFile()) { // Only process files, skip directories
                        try (InputStream inputStream = new FileInputStream(file)) {
                            ftpClient.storeFile(remoteDirectoryPath + file.getName(), inputStream);

//                            channelSftp.put(inputStream, remoteDirectoryPath + file.getName());
//                            System.out.println("Uploaded SWIFT file: " + file.getName());
                            logger.Log(loggerId,"SwiftOut - Sending Swift File","Sending swift file from local","UPLOADED",file.getName());

                        }
                    }
                }
            } else {
                System.out.println(localDirectoryPath + " is not a directory.");
            }

        } catch (IOException e) {

            logger.Log(loggerId,"SwiftOut - Sending Swift File","Sending swift file from local","ERROR",e.getMessage());

        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {

                logger.Log(loggerId,"SwiftOut - Sending Swift File","Sending swift file from local","ERROR",e.getMessage());

            }
        }
    }
    public void putSwiftFile(String localDirectoryPath, LogInterfaceProcessService logger,long loggerId) {
//        String remoteFilePath = "/path/on/remote/server/file.txt";

        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
//            byte[] privateKeyBytes = sftpKey.getBytes(StandardCharsets.UTF_8);
//            jsch.addIdentity("privateKey", privateKeyBytes, null, null);
            // Create session and connect to the SFTP server
            session = jsch.getSession(sftpUser, sftpHost, sftpPort);
            session.setPassword(sftpPassword);

            // Configure session to avoid host key checking
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications","publickey,keyboard-interactive,password");

            session.connect();

            // Open SFTP channel
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            logger.Log(loggerId,"SwiftOut - Sending Swift File","Sending swift file from local to SFTP","CONNECTED");

            // Upload all files from the local directory to the remote directory
            File localDirectory = new File(localDirectoryPath);
            if (localDirectory.isDirectory()) {
                for (File file : localDirectory.listFiles()) {
                    if (file.isFile()) { // Only process files, skip directories
                        try (InputStream inputStream = new FileInputStream(file)) {
                            channelSftp.put(inputStream, remoteDirectoryPath + file.getName());
//                            System.out.println("Uploaded SWIFT file: " + file.getName());
                            logger.Log(loggerId,"SwiftOut - Sending Swift File","Sending swift file from local","UPLOADED",file.getName());

                        }
                    }
                }
            } else {
                System.out.println(localDirectoryPath + " is not a directory.");
            }

            // Download file from the SFTP server
//            Vector<ChannelSftp.LsEntry> fileList = channelSftp.ls(remoteDirectoryPath);
//            for (ChannelSftp.LsEntry entry : fileList) {
//                if (!entry.getAttrs().isDir()) { // Only process files, skip directories
//                    String remoteFilePath = remoteDirectoryPath + entry.getFilename();
//                    String localFilePath = localDirectoryPath + entry.getFilename();
//                    try (OutputStream outputStream = new FileOutputStream(localFilePath)) {
//                        channelSftp.get(remoteFilePath, outputStream);
//                        System.out.println("Downloaded SWIFT file: " + entry.getFilename());
//                    }
//                }
//            }

        } catch (Exception e) {
//            e.printStackTrace();
            logger.Log(loggerId,"SwiftOut - Sending Swift File","Sending swift file from local","ERROR",e.getMessage());

        } finally {
            // Close SFTP channel and session
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }
}

