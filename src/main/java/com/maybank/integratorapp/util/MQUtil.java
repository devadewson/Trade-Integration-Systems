package com.maybank.integratorapp.util;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static String findTransRef(String queueName,String message){
        String relatedTransaction = "";
        if(message !=null){

            if(message.contains("<reference>")){
                Pattern pattern = Pattern.compile("<reference>(.*?)</reference>");
                Matcher matcher = pattern.matcher(message);

                if (matcher.find()) {
                    relatedTransaction = matcher.group(1);
                }

            }else if(message.contains("MasterReference>")){
                Pattern pattern = Pattern.compile("<[^:>]+:MasterReference>(.*?)</[^:>]+:MasterReference>");
                Matcher matcher = pattern.matcher(message);

                if (matcher.find()) {
                    relatedTransaction = matcher.group(1);
                }

            }
            else if(queueName.contains("swiftOutgoing")){
                String text = message;
                int startIndex = text.indexOf(":20:") + 4; // `+4` to skip `:20:`
                int endIndex = text.indexOf("\n", startIndex); // Find the next newline

                if (startIndex >= 4 && endIndex != -1) {
                    relatedTransaction = text.substring(startIndex, endIndex).trim();

                }

            }

        }
        return relatedTransaction;
    }
}
