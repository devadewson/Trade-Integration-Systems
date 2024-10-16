package com.maybank.integratorapp.component;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class SftpFileTransfer {

    private String sftpHost = "";
    private int sftpPort = 22;
    private String sftpUser;
    private String sftpPassword;
    private String sftpKey;
    private String remoteDirectoryPath;

    public SftpFileTransfer(String address,String user,String password,String key,String remotePath){
        this.sftpHost = address;
        this.sftpUser = user;
        this.sftpPassword = password;
        this.sftpKey = key;
        this.remoteDirectoryPath = remotePath;
    }

    public void getSwiftFile(String localDirectoryPath) {
//        String remoteFilePath = "/path/on/remote/server/file.txt";

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
                        System.out.println("Downloaded SWIFT file: " + entry.getFilename());
                        channelSftp.rm(remoteFilePath);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
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
    public void putSwiftFile(String localDirectoryPath) {
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

            // Upload all files from the local directory to the remote directory
            File localDirectory = new File(localDirectoryPath);
            if (localDirectory.isDirectory()) {
                for (File file : localDirectory.listFiles()) {
                    if (file.isFile()) { // Only process files, skip directories
                        try (InputStream inputStream = new FileInputStream(file)) {
                            channelSftp.put(inputStream, remoteDirectoryPath + file.getName());
                            System.out.println("Uploaded SWIFT file: " + file.getName());
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
            e.printStackTrace();
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

