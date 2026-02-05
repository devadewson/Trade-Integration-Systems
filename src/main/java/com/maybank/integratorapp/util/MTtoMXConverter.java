package com.maybank.integratorapp.util;

//import com.maybank.integratorapp.util.swiftconverter.MtToMxConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MTtoMXConverter {

    @Autowired
//    private MtToMxConversionService mtToMxConversionService;

    // MT message types to MX message types mapping
    private static final Map<String, String> MT_TO_MX_MAPPING = new HashMap<>();
    static {
        MT_TO_MX_MAPPING.put("103", "pacs.008.001.08"); // Single Customer Credit Transfer
        MT_TO_MX_MAPPING.put("202", "pacs.009.001.08"); // Financial Institution Transfer
        MT_TO_MX_MAPPING.put("910", "pacs.002.001.10"); // Confirmation
    }
    public String extractMTType(String mtMessage) {
        // MT message format: {1:...}{2:XXX}{3:...}{4:...}
        // The MT type is in the {2:XXX} block where XXX is the message type
        Pattern pattern = Pattern.compile("\\{2:I([0-9]{3})[A-Z0-9]{12}");
        Matcher matcher = pattern.matcher(mtMessage);
//        Pattern pattern = Pattern.compile("\\{2:I(\\d{3})\\}");
//        Matcher matcher = pattern.matcher(mtMessage);

        if (matcher.find()) {
            return matcher.group(1); // Returns the 3-digit MT type
        }

        throw new IllegalArgumentException("Could not find MT type in message");
    }

    public String convertMTtoMX(String mtMessage) {
        if (mtMessage == null || mtMessage.length() < 3) {
            throw new IllegalArgumentException("Invalid MT message format");
        }

        String mtType = extractMTType(mtMessage); // Extract MT type (e.g., 103, 202)
        String mxMessageType = MT_TO_MX_MAPPING.get(mtType);

        if (mxMessageType == null) {
            throw new UnsupportedOperationException("MT type " + mtType + " not supported");
        }
//        mtToMxConversionService.convertIncomingMessage(mtMessage,mtType,"2024");

        return generateMXMessage(mtMessage, mxMessageType);
    }

    private String generateMXMessage(String mtMessage, String mxMessageType) {
        // Parse MT message fields (simplified parsing)
        Map<String, String> mtFields = parseMTFields(mtMessage);

        switch (mxMessageType) {
            case "pacs.008.001.08":
                return generatePacs008(mtFields,mtMessage);
            case "pacs.009.001.08":
                return generatePacs009(mtFields);
            case "pacs.002.001.10":
                return generatePacs002(mtFields);
            default:
                throw new UnsupportedOperationException("MX type not implemented");
        }
    }

    private Map<String, String> parseMTFields(String mtMessage) {
        Map<String, String> fields = new HashMap<>();

        // Find the start of the body (after {4:)
        int bodyStart = mtMessage.indexOf("{4:");
        if (bodyStart == -1) {
            return fields;
        }

        // Extract the body content
        String body = mtMessage.substring(bodyStart + 3);
        int bodyEnd = body.indexOf("-}");
        if (bodyEnd != -1) {
            body = body.substring(0, bodyEnd);
        }

        // Parse each field line
        String[] lines = body.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith(":")) {
                String[] parts = line.split(":", 3);
                if (parts.length >= 3) {
                    String fieldTag = parts[1];
                    String fieldValue = parts[2].trim();
                    fields.put(fieldTag, fieldValue);
                }
            }
        }

        return fields;
    }
    private String generatePacs008(Map<String, String> mtFields, String mtMessage) {
        // Extract BIC codes from MT message
        String senderBIC = extractSenderBIC(mtMessage);
        String receiverBIC = extractReceiverBIC(mtMessage);

        String messageId = generateMessageId();
        String creationDateTime = getCurrentDateTime();
        String businessMessageId = "I" + System.currentTimeMillis();

        // Parse amount and currency from field 32A
        String amountField = mtFields.getOrDefault("32A", "000000USD0,00");
        String valueDate = amountField.substring(0, 6); // YYMMDD
        String currency = amountField.substring(6, 9); // Currency code
        String amount = amountField.substring(9).replace(",", "."); // Amount with decimal point

        // Format value date to YYYY-MM-DD
        String settlementDate = "20" + valueDate.substring(0, 2) + "-" +
                valueDate.substring(2, 4) + "-" +
                valueDate.substring(4, 6);

        String beneficiary = mtFields.getOrDefault("59A", "");
        String debtor = mtFields.getOrDefault("50A", "");
        String endToEndId = mtFields.getOrDefault("20", businessMessageId);
        String remittanceInfo = mtFields.getOrDefault("70", "");

        // Build the SWIFT Alliance envelope with pacs.008 inside
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Saa:DataPDU xmlns:Saa=\"urn:swift:saa:xsd:saa.2.0\" xmlns:Sw=\"urn:swift:snl:ns.Sw\" xmlns:SwInt=\"urn:swift:snl:ns.SwInt\" xmlns:SwGbl=\"urn:swift:snl:ns.SwGbl\" xmlns:SwSec=\"urn:swift:snl:ns.SwSec\">\n" +
                "  <Saa:Revision>2.0.13</Saa:Revision>\n" +
                "  <Saa:Header>\n" +
                "    <Saa:Message>\n" +
                "      <Saa:SenderReference>" + generateSenderReference(senderBIC) + "</Saa:SenderReference>\n" +
                "      <Saa:MessageIdentifier>pacs.008.001.08</Saa:MessageIdentifier>\n" +
                "      <Saa:Format>MX</Saa:Format>\n" +
                "      <Saa:SubFormat>Input</Saa:SubFormat>\n" +
                "      <Saa:Sender>\n" +
                "        <Saa:DN>ou=xxx,o=" + senderBIC.toLowerCase() + ",o=swift</Saa:DN>\n" +
                "      </Saa:Sender>\n" +
                "      <Saa:Receiver>\n" +
                "        <Saa:DN>ou=xxx,o=" + receiverBIC.toLowerCase() + ",o=swift</Saa:DN>\n" +
                "      </Saa:Receiver>\n" +
                "      <Saa:InterfaceInfo>\n" +
                "        <Saa:UserReference>" + businessMessageId + "</Saa:UserReference>\n" +
                "      </Saa:InterfaceInfo>\n" +
                "      <Saa:NetworkInfo>\n" +
                "        <Saa:Service>swift.finplus!pc</Saa:Service>\n" +
                "        <Saa:SWIFTNetNetworkInfo>\n" +
                "          <Saa:RequestType>pacs.008.001.08</Saa:RequestType>\n" +
                "          <Saa:RequestSubtype>swift.cbprplus.02</Saa:RequestSubtype>\n" +
                "          <Saa:IsCopyRequested>false</Saa:IsCopyRequested>\n" +
                "        </Saa:SWIFTNetNetworkInfo>\n" +
                "      </Saa:NetworkInfo>\n" +
                "    </Saa:Message>\n" +
                "  </Saa:Header>\n" +
                "  <Saa:Body>\n" +
                "    <AppHdr xmlns=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.02\">\n" +
                "      <Fr>\n" +
                "        <FIId>\n" +
                "          <FinInstnId>\n" +
                "            <BICFI>" + senderBIC + "</BICFI>\n" +
                "          </FinInstnId>\n" +
                "        </FIId>\n" +
                "      </Fr>\n" +
                "      <To>\n" +
                "        <FIId>\n" +
                "          <FinInstnId>\n" +
                "            <BICFI>" + receiverBIC + "</BICFI>\n" +
                "          </FinInstnId>\n" +
                "        </FIId>\n" +
                "      </To>\n" +
                "      <BizMsgIdr>" + businessMessageId + "</BizMsgIdr>\n" +
                "      <MsgDefIdr>pacs.008.001.08</MsgDefIdr>\n" +
                "      <BizSvc>swift.cbprplus.02</BizSvc>\n" +
                "      <CreDt>" + creationDateTime + "</CreDt>\n" +
                "    </AppHdr>\n" +

                "  </Saa:Body>\n" +
                "</Saa:DataPDU>";
    }

    private String extractSenderBIC(String mtMessage) {
        // Extract sender BIC from {1:F01BANKBEBBAXXX...}
        Pattern pattern = Pattern.compile("\\{1:F01([A-Z0-9]{12})");
        Matcher matcher = pattern.matcher(mtMessage);
        if (matcher.find()) {
            String bic = matcher.group(1);
            return bic.length() == 12 ? bic : bic + "XXX"; // Ensure proper BIC format
        }
        return "XXXXXXXXXXXX"; // Default
    }

    private String extractReceiverBIC(String mtMessage) {
        // Extract receiver BIC from {2:I103MBBEUS33XXXXN}
        Pattern pattern = Pattern.compile("\\{2:I[0-9]{3}([A-Z0-9]{12})");
        Matcher matcher = pattern.matcher(mtMessage);
        if (matcher.find()) {
            String bic = matcher.group(1);
            return bic.length() == 12 ? bic : bic + "XXX"; // Ensure proper BIC format
        }
        return "XXXXXXXXXXXX"; // Default from your sample
    }

    private String generateSenderReference(String senderBIC) {
        return senderBIC + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssSSS"));
    }
    private String generatePacs009(Map<String, String> mtFields) {
        // Similar implementation for pacs.009
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\">\n" +
                "  <!-- Financial Institution Transfer content -->\n" +
                "</Document>";
    }

    private String generatePacs002(Map<String, String> mtFields) {
        // Similar implementation for pacs.002
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.10\">\n" +
                "  <!-- Payment Status Report content -->\n" +
                "</Document>";
    }

    private String generateMessageId() {
        return "MSG" + System.currentTimeMillis();
    }

    private String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}