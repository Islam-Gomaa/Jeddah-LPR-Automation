package tests;

import api.LprApiClient;
import base.BaseTests;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BasePage;
import pages.GatesPage;
import utilities.AuthHelper;
import utils.Assertions;

import java.nio.file.Path;

public class Gates extends BaseTests {

    BasePage basePage;
    GatesPage gatespage;

    // General Vehicle Test Data

    private Path carImage = Path.of("src/test/resources/images/car.png");
    private Path plateImage = Path.of("src/test/resources/images/plate.png");

    private LprApiClient client;
    private Response response;

    private String plateEn;
    private String plateAr;

    private String plateNumber;
    private String plateLettersEn;
    private String plateLettersAr;

    // Generate Vehicle

    @BeforeMethod
    public void createVehiclePassage() {

        client = new LprApiClient();
        response = client.sendVehiclePassage(carImage, plateImage);

        System.out.println("==============================");
        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.asPrettyString());

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Vehicle passage request failed: " + response.asPrettyString());

        // Generated Plate
        plateEn = client.getGeneratedPlateEn();
        plateAr = client.getGeneratedPlateAr();
        plateNumber = plateEn.replaceAll("[^0-9]", "");
        plateLettersEn = plateEn.replaceAll("[^A-Za-z]", "");
        plateLettersAr = plateAr.replaceAll("[^\\u0600-\\u06FF]", "");

        // Debug
        System.out.println("Plate EN: " + plateEn);
        System.out.println("Plate AR: " + plateAr);
        System.out.println("Expected Number: " + plateNumber);
        System.out.println("Expected Letters EN: " + plateLettersEn);
        System.out.println("Expected Letters AR: " + plateLettersAr);
        System.out.println("Date: " + client.getGeneratedDate());
        System.out.println("==============================");
    }

    // Admin Login

    @BeforeMethod
    public void setupAdminSession() {
        openSystem();
        basePage = AuthHelper.login(driver);
    }

//    @Test(priority = 1)
//    public void sendVehiclePassageEvent() {
//
//        gatespage = basePage.openGates();
//
//        Assertions.myAssertTrue(gatespage.isPlateNumberDisplayed(plateNumber),
//                "Plate number did not appear successfully");
//        gatespage.clickPermitStatusButtonInTable(plateNumber);
//
//        Assertions.myAssertTrue(
//                gatespage.isPlateLetterArDisplayed(),
//                "Plate letter Ar did not appear successfully");
//
//        Assertions.myAssertEqualsIgnoreSpaces(
//                gatespage.getPlateLetterArElement(),
//                gatespage.getPlateLetterAr(),
//                plateLettersAr,
//                "Incorrect Arabic plate letters");
//
//        Assertions.myAssertTrue(
//                gatespage.isPlateLetterEnDisplayed(),
//                "Plate letter En did not appear successfully");
//
//        Assertions.myAssertEqualsIgnoreSpaces(
//                gatespage.getPlateLetterEnElement(),
//                gatespage.getPlateLetterEn(),
//                plateLettersEn,
//                "Incorrect English plate letter");
//
//        Assertions.myAssertEquals(
//                gatespage.getVehicleIsNotPermittedTextElement(),
//                gatespage.getVehicleIsNotPermittedText(),
//                "This vehicle is not permitted to enter",
//                "Vehicle is permitted");
//
//        gatespage
//                .clickCreatePermit()
//                .selectVehicleTypeDDL()
//                .selectDriverDDL()
//                .selectMainWasteDDL()
//                .selectSubWasteDDL()
//                .selectContractorDDL()
//                .clickSaveButton();
//    }

//    @Test(priority = 2)
//    public void changeVehicleStatusInTable(){
//        gatespage = basePage.openGates();
//
//        gatespage
//                .searchInputs(plateEn)
//                .clickPermitStatusButtonInTable(plateNumber)
//                .clickChangeStatusButton()
//                .selectSetStatusDDL()
//                .enterStatusNotes("note")
//                .clickSaveButton();
//
//        Assertions.myAssertEquals(
//                gatespage.getSuccessMessage(),
//                gatespage.getSuccessMessageText(),
//                "Status updated successfully",
//                "Status not updated");
//
//    }
//
//    @Test(priority = 3)
//    public void changeVehicleStatusByIcon() {
//        gatespage = basePage.openGates();
//
//        gatespage
//                .searchInputs(plateEn)
//                .clickChangeStatusIcon(plateNumber)
//                .selectSetStatusDDL()
//                .enterStatusNotes("test note")
//                .clickSaveButton();
//
//        Assertions.myAssertEquals(
//                gatespage.getSuccessMessage(),
//                gatespage.getSuccessMessageText(),
//                "Status updated successfully",
//                "Status not updated");
//    }


//    @Test(priority = 4)
//    public void createVehicleReportInTable() {
//        gatespage = basePage.openGates();
//
//        gatespage
//                .searchInputs(plateEn)
//                .clickReportButtonIcon(plateNumber)
//                .enterNameEnglish("test name name")
//                .enterNameArabic("بلاغ جديد جدا")
//                .selectAssignedUserDDL()
//                .selectSelectStageDDL()
//                .selectPriorityDDL()
//                .enterDetailsEnglish("test descriptions")
//                .enterDetailsArabic("تفاصيل جديدة")
//                .clickSaveButton();
//
//        Assertions.myAssertEquals(
//                gatespage.getSuccessMessage(),
//                gatespage.getSuccessMessageText(),
//                "Status updated successfully",
//                "Status not updated"
//        );
//    }

    @Test(priority = 1)
    public void filterByLocations(){
        gatespage = basePage.openGates();
        gatespage.searchAndSelectLocation("Gates 2");
    }

}