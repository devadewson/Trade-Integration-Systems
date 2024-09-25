package com.maybank.integratorapp.component.coresystem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessSwiftIn {

    public Map<String, List<String>> getFileContent(){
        Path folderPath = Paths.get("D:\\Agung\\Projects\\BankTrade Trade Transformation\\IntegrationList");
        Map<String, List<String>> fileContents = new HashMap<>();

        try {

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
}
