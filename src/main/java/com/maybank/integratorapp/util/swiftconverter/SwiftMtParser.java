package com.maybank.integratorapp.util.swiftconverter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a raw SWIFT MT message into its individual tags.
 * Expects a message in the standard SWIFT FIN format with blocks.
 */
@Component
@Slf4j
public class SwiftMtParser {

    // Regex to find lines that start with a colon, followed by 2-3 digits, another colon, and then any content.
    // Example: ":20:REF123" or ":59F:/DE123\nJane Doe"
    private static final Pattern TAG_LINE_PATTERN = Pattern.compile("^(:([0-9]{2,3}[A-Z]?):)(.*)$", Pattern.MULTILINE);

    /**
     * Parses the raw SWIFT message and extracts all tags from the text block (Block 4).
     *
     * @param rawMessage The complete SWIFT MT message as a single string.
     * @return A list of ParsedTag objects containing tagId and tagValue.
     */
    public static List<ParsedTag> parse(String rawMessage) {
        List<ParsedTag> parsedTags = new ArrayList<>();

        // 1. Find the start of Block 4 ({4: ... })
        String block4Content = extractBlock4Content(rawMessage);
        if (block4Content == null) {
            log.warn("No Block 4 found in message.");
            return parsedTags;
        }

        // 2. Use Matcher to find all tag lines within the block content
        Matcher matcher = TAG_LINE_PATTERN.matcher(block4Content);

        while (matcher.find()) {
            // group(2) is the tag ID (e.g., "20", "32A")
            String tagId = matcher.group(2);
            // group(3) is everything after the second colon, which is the tag's value
            String tagValue = matcher.group(3);

            // Basic cleanup: Remove any trailing hyphen from line continuation (older formats)
            if (tagValue != null && tagValue.endsWith("-")) {
                tagValue = tagValue.substring(0, tagValue.length() - 1);
            }

            // Handle multi-line values: If the next line doesn't start with ':', it's a continuation of the previous value.
            tagValue = handleMultiLineValue(block4Content, matcher, tagValue);

            ParsedTag tag = new ParsedTag(tagId, tagValue);
            parsedTags.add(tag);
            log.debug("Parsed tag: {} -> {}", tagId, tagValue);
        }
        return parsedTags;
    }

    /**
     * Extracts the content of SWIFT Block 4 (the Text Block).
     * The block starts with "{4:" and ends with a closing brace "-}".
     *
     * @param rawMessage The complete SWIFT message.
     * @return The content inside Block 4, or null if not found.
     */
//    private static String extractBlock4Content(String rawMessage) {
//        // Regex to find the content of Block 4: {4:\n[content]\n-}
//        Pattern block4Pattern = Pattern.compile("\\{4:[^\\}]*\\}-\\}", Pattern.DOTALL);
//        Matcher block4Matcher = block4Pattern.matcher(rawMessage);
//
//        if (block4Matcher.find()) {
//            String fullBlock = block4Matcher.group();
//            // Strip off the block identifiers "{4:" and "-}" to get just the content.
//            // The length of "{4:" is 3, and we also remove the trailing "-}".
//            int startIndex = 3;
//            int endIndex = fullBlock.length() - 2;
//            if (endIndex > startIndex) {
//                return fullBlock.substring(startIndex, endIndex);
//            }
//        }
//        return null;
//    }
    private static String extractBlock4Content(String rawMessage) {
        // More specific pattern for SWIFT block 4
        Pattern block4Pattern = Pattern.compile("\\{4:\\s*\\n((?:.*\\n)*?)\\s*-\\}", Pattern.DOTALL);
        Matcher block4Matcher = block4Pattern.matcher(rawMessage);

        if (block4Matcher.find()) {
            return block4Matcher.group(1).trim();
        }
        return null;
    }

    /**
     * Handles multi-line values for a tag. If the line after the current match does not start
     * with a colon, it is considered a continuation line and is appended to the value.
     *
     * @param fullBlockContent The entire content of Block 4.
     * @param currentMatcher   The matcher that found the current tag.
     * @param currentValue     The value extracted for the current tag so far.
     * @return The complete value, with any continuation lines appended.
     */
    private static String handleMultiLineValue(String fullBlockContent, Matcher currentMatcher, String currentValue) {
        int currentMatchEnd = currentMatcher.end();
        int nextLineStart = findStartOfNextLine(fullBlockContent, currentMatchEnd);

        // While there are more lines and the next line doesn't start a new tag...
        while (nextLineStart < fullBlockContent.length() && fullBlockContent.charAt(nextLineStart) != ':') {
            int endOfLine = fullBlockContent.indexOf('\n', nextLineStart);
            if (endOfLine == -1) {
                endOfLine = fullBlockContent.length();
            }

            // Append the continuation line to the value
            String continuationLine = fullBlockContent.substring(nextLineStart, endOfLine).trim();
            // Replace the Swift line break character (if present) with a space or newline
            continuationLine = continuationLine.replaceAll("\\r?\\n", " ");
            currentValue = currentValue + " " + continuationLine;

            // Move the pointer to the next line
            nextLineStart = (endOfLine < fullBlockContent.length()) ? endOfLine + 1 : fullBlockContent.length();
        }
        return currentValue;
    }

    private static int findStartOfNextLine(String text, int currentPosition) {
        int nextNewLine = text.indexOf('\n', currentPosition);
        if (nextNewLine == -1) {
            return text.length();
        }
        // Return the position after the newline character
        return nextNewLine + 1;
    }

    /**
     * Simple data holder class for a parsed tag.
     */
    public static class ParsedTag {
        private final String tagId;
        private final String tagValue;

        public ParsedTag(String tagId, String tagValue) {
            this.tagId = tagId;
            this.tagValue = tagValue;
        }
        // Getters
        public String getTagId() { return tagId; }
        public String getTagValue() { return tagValue; }
    }
}
