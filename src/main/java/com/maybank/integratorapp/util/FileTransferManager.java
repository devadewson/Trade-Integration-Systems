package com.maybank.integratorapp.util;

import com.jcraft.jsch.*;
import com.maybank.integratorapp.component.system.messageprocessor.LimitFacilitiesMessageProcessor;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileTransferManager {
    private static Logger log = LoggerFactory.getLogger(FileTransferManager.class);
    private Session sftpSession;
    private ChannelSftp sftpChannel;
    private FTPClient ftpClient;
    private String protocol;
    private final String host;
    private final int port;
    private final String username;
    private final String password;

    public FileTransferManager(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.protocol = detectProtocol();
    }

    private String detectProtocol() {
        log.info("UID:"+username
                +" | "
                +"PWD:"+password
                +" | "
                +"HOST:"+host
                +" | "
                +"PORT:"+port
        );
        // Try SFTP first
        try {
            JSch jsch = new JSch();
            Session testSession = jsch.getSession(username, host, port);
            testSession.setPassword(password);
            testSession.setConfig("StrictHostKeyChecking", "no");
            testSession.setConfig("PreferredAuthentications","publickey,keyboard-interactive,password");
            testSession.connect();
            Channel testChannel = testSession.openChannel("sftp");
            testChannel.connect();
            testChannel.disconnect();
            testSession.disconnect();
            return "sftp";
        } catch (JSchException e) {
            // SFTP failed, try FTP
            log.error(e.getMessage());
            FTPClient testFtp = new FTPClient();
            try {
                testFtp.connect(host, port);
                if (FTPReply.isPositiveCompletion(testFtp.getReplyCode())) {
                    if (testFtp.login(username, password)) {
                        testFtp.disconnect();
                        return "ftp";
                    }
                }
            } catch (IOException ex) {
                log.error(ex.getMessage());
                // Ignore, we'll return unknown
            }
        }
        return "unknown";
    }

    public void openConnection() throws Exception {
        if ("sftp".equals(protocol)) {
            JSch jsch = new JSch();
            sftpSession = jsch.getSession(username, host, port);
            sftpSession.setPassword(password);
            sftpSession.setConfig("StrictHostKeyChecking", "no");
            sftpSession.setConfig("PreferredAuthentications","publickey,keyboard-interactive,password");
            sftpSession.connect();
            sftpChannel = (ChannelSftp) sftpSession.openChannel("sftp");
            sftpChannel.connect();
        } else if ("ftp".equals(protocol)) {
            ftpClient = new FTPClient();
            ftpClient.connect(host, port);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                throw new IOException("FTP server refused connection.");
            }
            if (!ftpClient.login(username, password)) {
                throw new IOException("FTP login failed.");
            }
            ftpClient.enterLocalPassiveMode();
        } else {
            throw new Exception("Unsupported protocol: " + protocol);
        }
    }

    public void transferFile(Path localFile, String remotePath) throws Exception {
        if ("sftp".equals(protocol)) {
            sftpChannel.put(localFile.toString(), remotePath);
        } else if ("ftp".equals(protocol)) {
            try (InputStream inputStream = Files.newInputStream(localFile)) {
                ftpClient.storeFile(remotePath, inputStream);
            }
        }
    }

    public void closeConnection() {
        try {
            if (sftpChannel != null) {
                sftpChannel.disconnect();
            }
            if (sftpSession != null) {
                sftpSession.disconnect();
            }
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            // Log warning if needed
        }
    }

}
