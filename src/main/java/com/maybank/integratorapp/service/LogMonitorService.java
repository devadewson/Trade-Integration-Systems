package com.maybank.integratorapp.service;

import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.data.service.MsParameterService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LogMonitorService {
    @Autowired
    private MsParameterService parameterService;
    @Autowired
    private MsParameterRepository msParameterRepository;
    private final Map<Integer, LogFile> logFiles = new ConcurrentHashMap<>();


    @PostConstruct
    public void init() {
        // Initialize with IDs instead of exposing paths
        // Add your 4 log files
        String logPath = parameterService.findValueByPrmKey("IntegratorCronLogPath");
        String logFile = parameterService.findValueByPrmKey("IntegratorCronLogFiles");
        List<String> _files = List.of(logFile.split(";"));

        int i = 1;
        for (String file:
                _files) {

            String _logName = file.split("\\.")[0];
            registerLogFile(i, logPath+file, _logName);
            i++;
        }
        
        
        registerLogFile(i,logPath.replace("jobs/logs/","")+"app.log","INTG_App");
    }

    private void registerLogFile(int id, String path, String name) {
        LogFile logFile = new LogFile();
        logFile.setId(id);
        logFile.setName(name);
        logFile.setPath(path); // Internal use only

        try{
            File file = new File(path);
            logFile.setLastModified(LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(file.lastModified()),
                    ZoneId.systemDefault()));
            logFile.setSize(file.length());
            logFile.setExists(true);
        }catch (Exception e){
            logFile.setExists(false);
        }


        logFiles.put(id, logFile);
    }

    public List<LogFile> getAllLogFiles() {
        return new ArrayList<>(logFiles.values());
    }

    public Optional<LogFile> getLogFileById(int id) {
        return Optional.ofNullable(logFiles.get(id));
    }

    public Optional<String> getLogContent(int id) throws IOException {
        return getLogFileById(id)
                .map(logFile -> new File(logFile.getPath()))
                .filter(File::exists)
                .map(file -> {
                    try {
                        return Files.readString(file.toPath());
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
    }

    public static class LogFile {
        private int id;
        private String name;
        private String path;
        private LocalDateTime lastModified;
        private long size;
        private boolean exists;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isExists() {
            return exists;
        }

        public void setExists(boolean exists) {
            this.exists = exists;
        }
        // constructor, getters, setters

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public LocalDateTime getLastModified() {
            return lastModified;
        }

        public void setLastModified(LocalDateTime lastModified) {
            this.lastModified = lastModified;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }
    }
}
