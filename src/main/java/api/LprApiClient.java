package api;

import io.restassured.response.Response;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import static io.restassured.RestAssured.given;

public class LprApiClient {

    private String generatedPlateEn;
    private String generatedPlateAr;
    private String generatedDate;

    /*
     * Send Vehicle Passage Request
     */

    public Response sendVehiclePassage(
            Path carImage,
            Path plateImage,
            String X_TOKEN,
            String ENDPOINT) {

        // Generate Saudi plate
        SaudiPlate plate = SaudiPlateGenerator.generateRandomPlate();

        generatedPlateEn = plate.getPlateEn();
        generatedPlateAr = plate.getPlateAr();

        // Generate passage date
        generatedDate = LocalDateTime.now()
                .format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                );

        // Log generated data
        System.out.println("==============================");
        System.out.println("PLATE EN : [" + generatedPlateEn + "]");
        System.out.println("PLATE AR : [" + generatedPlateAr + "]");
        System.out.println("DATE     : [" + generatedDate + "]");
        System.out.println("==============================");

        try {

            // Convert images to Base64
            String carImageBase64 = Base64.getEncoder()
                    .encodeToString(
                            Files.readAllBytes(carImage)
                    );

            String plateImageBase64 = Base64.getEncoder()
                    .encodeToString(
                            Files.readAllBytes(plateImage)
                    );

            // Send API Request
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

    /*
     * Getters
     */

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