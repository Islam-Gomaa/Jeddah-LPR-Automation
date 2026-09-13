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

    private String generatedPlateEn;
    private String generatedPlateAr;
    private String generatedDate;

    public Response sendVehiclePassage(
            Path carImage,
            Path plateImage) {

        int plateNumber = generatePlateNumber();

        generatedPlateEn = generateEnglishPlate(plateNumber);
        generatedPlateAr = generateArabicPlate(plateNumber);

        generatedDate = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

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
                    .header("Accept", "application/json")
                    .header("X-Token", X_TOKEN)

                    .multiPart("location_id", "2")
                    .multiPart("car_image", carImageBase64)
                    .multiPart("plate_image", plateImageBase64)
                    .multiPart("type", "1")
                    .multiPart("date", generatedDate)
                    .multiPart("plate_ar", generatedPlateAr, "text/plain; charset=UTF-8")
                    .multiPart("plate_en", generatedPlateEn)

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
        return ThreadLocalRandom.current().nextInt(1000, 10000);
    }

    private String generateEnglishPlate(int number) {

        String letters = "";

        for (int i = 0; i < 3; i++) {
            letters += (char) ThreadLocalRandom.current()
                    .nextInt('A', 'Z' + 1);
        }
        return number + " " + letters;
    }

    private String generateArabicPlate(int number) {

        String arabicLetters =
                "اب ح د رس ص ط ع ق ك ل م ن ه و ي"
                        .replace(" ", "");

        String letters = "";

        for (int i = 0; i < 3; i++) {

            letters += arabicLetters.charAt(
                    ThreadLocalRandom.current()
                            .nextInt(arabicLetters.length())
            );

            if (i < 2) {
                letters += " ";
            }
        }
        return letters + " " + number;
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