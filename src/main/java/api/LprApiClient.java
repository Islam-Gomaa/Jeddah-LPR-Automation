package api;

import io.restassured.response.Response;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

public class LprApiClient {

    private static final String ENDPOINT =
            "https://jeddah-lpr-api.wakeb.dev/api/ai/car-gates";

    private static final String X_TOKEN =
            "srjg2ao9UDYYIJ2cseoWZfyRXyjBOROJsxMLP7";

    /*
     * Saudi License Plate Letters
     *
     * English -> Arabic
     *
     * A -> ا
     * B -> ب
     * J -> ح
     * D -> د
     * R -> ر
     * S -> س
     * X -> ص
     * T -> ط
     * E -> ع
     * G -> ق
     * K -> ك
     * L -> ل
     * Z -> م
     * N -> ن
     * H -> ه
     * U -> و
     * V -> ي
     */

    private static final char[] ENGLISH_PLATE_LETTERS = {
            'A',
            'B',
            'J',
            'D',
            'R',
            'S',
            'X',
            'T',
            'E',
            'G',
            'K',
            'L',
            'Z',
            'N',
            'H',
            'U',
            'V'
    };

    private static final char[] ARABIC_PLATE_LETTERS = {
            'ا',
            'ب',
            'ح',
            'د',
            'ر',
            'س',
            'ص',
            'ط',
            'ع',
            'ق',
            'ك',
            'ل',
            'م',
            'ن',
            'ه',
            'و',
            'ي'
    };

    private String generatedPlateEn;
    private String generatedPlateAr;
    private String generatedDate;

    public Response sendVehiclePassage(
            Path carImage,
            Path plateImage) {

        int plateNumber = generatePlateNumber();

        /*
         * Generate English plate first.
         *
         * Example:
         * 3977 JDS
         */
        generatedPlateEn =
                generateEnglishPlate(plateNumber);

        /*
         * Generate Arabic plate from the
         * exact same English letters.
         */
        generatedPlateAr =
                generateArabicPlate(
                        plateNumber,
                        generatedPlateEn
                );

        generatedDate =
                LocalDateTime.now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyy-MM-dd HH:mm"
                                )
                        );

        /*
         * Debug information
         */
        System.out.println("==============================");
        System.out.println(
                "PLATE EN : [" + generatedPlateEn + "]"
        );
        System.out.println(
                "PLATE AR : [" + generatedPlateAr + "]"
        );
        System.out.println(
                "DATE     : [" + generatedDate + "]"
        );
        System.out.println("==============================");

        try {

            String carImageBase64 =
                    java.util.Base64.getEncoder()
                            .encodeToString(
                                    Files.readAllBytes(carImage)
                            );

            String plateImageBase64 =
                    java.util.Base64.getEncoder()
                            .encodeToString(
                                    Files.readAllBytes(plateImage)
                            );

            return given()

                    .header(
                            "Accept",
                            "application/json"
                    )

                    .header(
                            "X-Token",
                            X_TOKEN
                    )

                    .multiPart(
                            "location_id",
                            "2"
                    )

                    .multiPart(
                            "car_image",
                            carImageBase64
                    )

                    .multiPart(
                            "plate_image",
                            plateImageBase64
                    )

                    .multiPart(
                            "type",
                            "1"
                    )

                    .multiPart(
                            "date",
                            generatedDate
                    )

                    .multiPart(
                            "plate_ar",
                            generatedPlateAr,
                            "text/plain; charset=UTF-8"
                    )

                    .multiPart(
                            "plate_en",
                            generatedPlateEn
                    )

                    .when()

                    .post(ENDPOINT);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to send vehicle passage request",
                    e
            );
        }
    }

    private int generatePlateNumber() {

        return ThreadLocalRandom.current()
                .nextInt(
                        1000,
                        10000
                );
    }

    private String generateEnglishPlate(
            int number) {

        StringBuilder letters =
                new StringBuilder();

        for (int i = 0; i < 3; i++) {

            char letter =
                    ENGLISH_PLATE_LETTERS[
                            ThreadLocalRandom.current()
                                    .nextInt(
                                            ENGLISH_PLATE_LETTERS.length
                                    )
                            ];

            letters.append(letter);
        }

        return number
                + " "
                + letters;
    }

    private String generateArabicPlate(
            int number,
            String englishPlate) {

        /*
         * Extract the English letters.
         *
         * Example:
         *
         * 3977 JDS
         *
         * becomes:
         *
         * JDS
         */
        String englishLetters =
                englishPlate.substring(
                        englishPlate.indexOf(" ") + 1
                );

        StringBuilder arabicLetters =
                new StringBuilder();

        /*
         * IMPORTANT:
         *
         * Arabic is RTL.
         *
         * The English plate:
         *
         * J D S
         *
         * maps to:
         *
         * ح د س
         *
         * But to make the Arabic characters
         * appear in the correct physical
         * positions in the UI, we send them
         * in reverse order:
         *
         * س د ح
         *
         * The UI then displays:
         *
         * ح د س
         *
         * So:
         *
         * J <-> ح
         * D <-> د
         * S <-> س
         */

        for (int i = englishLetters.length() - 1;
             i >= 0;
             i--) {

            char englishLetter =
                    englishLetters.charAt(i);

            char arabicLetter =
                    getArabicEquivalent(
                            englishLetter
                    );

            arabicLetters.append(
                    arabicLetter
            );
        }

        /*
         * No spaces between Arabic letters.
         *
         * Example:
         *
         * س د ح
         *
         * is NOT sent with spaces.
         *
         * Actual value:
         *
         * س د ح 3977
         *
         * Wait:
         *
         * Since the UI handles RTL,
         * the actual string must remain
         * a continuous Arabic sequence.
         *
         * Example:
         *
         * س د ح 3977
         */

        return arabicLetters
                + " "
                + number;
    }

    private char getArabicEquivalent(
            char englishLetter) {

        for (int i = 0;
             i < ENGLISH_PLATE_LETTERS.length;
             i++) {

            if (ENGLISH_PLATE_LETTERS[i]
                    == englishLetter) {

                return ARABIC_PLATE_LETTERS[i];
            }
        }

        throw new IllegalArgumentException(
                "Unsupported Saudi plate letter: "
                        + englishLetter
        );
    }

    public String getGeneratedPlateEn() {

        return generatedPlateEn;
    }

    public String getGeneratedPlateAr() {

        return generatedPlateAr;
    }

    public String getGeneratedDate() {

        return generatedDate;
    }
}