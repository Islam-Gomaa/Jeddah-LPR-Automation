package api;

import io.restassured.response.Response;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

public class LprApiClient {

//    private static final String ENDPOINT =
//            "https://jeddah-lpr-api.wakeb.dev/api/ai/car-gates";

//    private static final String X_TOKEN =
//            "srjg2ao9UDYYIJ2cseoWZfyRXyjBOROJsxMLP7";

    /*
     * Saudi License Plate Letters
     */

    private static final char[] ENGLISH_PLATE_LETTERS = {
            'A', 'B', 'J', 'D', 'R', 'S', 'X', 'T', 'E', 'G', 'K', 'L', 'Z', 'N', 'H', 'U', 'V'
    };

    private static final char[] ARABIC_PLATE_LETTERS = {
            'ا', 'ب', 'ح', 'د', 'ر', 'س', 'ص', 'ط', 'ع', 'ق', 'ك', 'ل', 'م', 'ن', 'ه', 'و', 'ى'
    };

    private String generatedPlateEn;
    private String generatedPlateAr;
    private String generatedDate;

    public Response sendVehiclePassage(
            Path carImage,
            Path plateImage , String X_TOKEN,String ENDPOINT) {

        int plateNumber = generatePlateNumber();

        generatedPlateEn =
                generateEnglishPlate(plateNumber);

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

        String englishLetters =
                englishPlate.substring(
                        englishPlate.indexOf(" ") + 1
                );

        StringBuilder arabicLetters =
                new StringBuilder();

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