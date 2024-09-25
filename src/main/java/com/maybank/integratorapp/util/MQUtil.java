package com.maybank.integratorapp.util;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class MQUtil {
    public String getMessageUID(){
        String _ret = "";
        String _random = java.util.UUID.randomUUID().toString();
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        // Define the formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        // Format the current date and time
        String _date = now.format(formatter);

        _ret = _random+"##@@##"+_date;

        return _ret;
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
