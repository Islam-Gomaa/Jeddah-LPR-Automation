package api;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AnprXmlBuilder {

    private static final String XML_FILE =
            "TestData/anpr.tmp";
    private static final String LETTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public static String build() {

        String xml = loadTemplate();

        String currentDateTime = getCurrentDateTime();
        String licensePlate = generateLicensePlate();

        xml = xml.replaceFirst(
                "<dateTime>.*?</dateTime>",
                "<dateTime>" + currentDateTime + "</dateTime>"
        );

        xml = xml.replaceFirst(
                "<licensePlate>.*?</licensePlate>",
                "<licensePlate>" + licensePlate + "</licensePlate>"
        );

        return xml;
    }

    private static String loadTemplate() {

        try (InputStream inputStream =
                     AnprXmlBuilder.class
                             .getClassLoader()
                             .getResourceAsStream(XML_FILE)) {

            if (inputStream == null) {
                throw new RuntimeException(
                        "ANPR XML template not found: " + XML_FILE
                );
            }

            return new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load ANPR XML template",
                    e
            );
        }
    }

    private static String getCurrentDateTime() {

        return ZonedDateTime
                .now(ZoneId.of("Africa/Cairo"))
                .format(DATE_TIME_FORMATTER);
    }

    private static String generateLicensePlate() {

        StringBuilder plate = new StringBuilder();

        // 4 digits
        for (int i = 0; i < 4; i++) {
            plate.append(RANDOM.nextInt(10));
        }

        plate.append(" ");

        // 3 letters
        for (int i = 0; i < 3; i++) {
            plate.append(
                    LETTERS.charAt(
                            RANDOM.nextInt(LETTERS.length())
                    )
            );
        }

        return plate.toString();
    }
}