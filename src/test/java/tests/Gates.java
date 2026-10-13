package tests;
import data.VehicleTestData;
import org.testng.Assert;
import api.LprApiClient;
import base.BaseTests;
import io.restassured.response.Response;
import org.testng.annotations.*;
import pages.BasePage;
import pages.GatesPage;
import utilities.AuthHelper;
import utilities.ConfigReader;
import utils.Assertions;
import java.nio.file.Path;
import static dataReader.ReadDataFromJson.dataModel;


public class Gates extends BaseTests {

    BasePage basePage;
    GatesPage gatespage;

    // General Vehicle Test Data

    private Path carImage = Path.of("src/test/resources/images/car.png");
    private Path plateImage = Path.of("src/test/resources/images/plate.png");

    private String X_TOKEN = ConfigReader.get("X_TOKEN");
    private String ENDPOINT = ConfigReader.get("gatesEndPoint");


    private LprApiClient client;
    private Response response;

    private String plateEn;
    private String plateAr;

    private String plateNumber;
    private String plateLettersEn;
    private String plateLettersAr;

    // Generate Vehicle

    @BeforeSuite
    public void createVehiclePassage() {

        client = new LprApiClient();
        response = client.sendVehiclePassage(carImage, plateImage,X_TOKEN, ENDPOINT);

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

        // Store vehicle data for reuse by other test classes
        VehicleTestData.setVehicle(plateEn, plateAr);

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

    @Test(priority = 1)
    public void createVehiclePermit() {

        gatespage = basePage.openGates();

        Assertions.myAssertTrue(gatespage.isPlateNumberDisplayed(plateNumber),
                "Plate number did not appear successfully");
        gatespage.clickPermitStatusButtonInTable(plateNumber);

        Assertions.myAssertTrue(
                gatespage.isPlateLetterArDisplayed(),
                "Plate letter Ar did not appear successfully");

        Assertions.myAssertEqualsIgnoreSpaces(
                gatespage.getPlateLetterArElement(),
                gatespage.getPlateLetterAr(),
                plateLettersAr,
                "Incorrect Arabic plate letters");

        Assertions.myAssertTrue(
                gatespage.isPlateLetterEnDisplayed(),
                "Plate letter En did not appear successfully");

        Assertions.myAssertEqualsIgnoreSpaces(
                gatespage.getPlateLetterEnElement(),
                gatespage.getPlateLetterEn(),
                plateLettersEn,
                "Incorrect English plate letter");

        Assertions.myAssertEquals(
                gatespage.getVehicleIsNotPermittedTextElement(),
                gatespage.getVehicleIsNotPermittedText(),
                "This vehicle is not permitted to enter",
                "Vehicle is permitted");

        gatespage
                .clickCreatePermit()
                .selectVehicleTypeDDL()
                .selectDriverDDL()
                .selectMainWasteDDL()
                .selectSubWasteDDL()
                .selectContractorDDL()
                .clickSaveButton()
                .waitUntilVehicleIsPermitted();


        Assertions.myAssertEquals(
                gatespage.getVehicleIsPermittedTextElement(),
                gatespage.getVehicleIsPermittedText(),
                "This vehicle is permitted to enter",
                "Vehicle is not permitted");
    }

//    @Test(priority = 2)
//    public void changeVehicleStatusInPopUp() throws InterruptedException {
//        gatespage = basePage.openGates();
//
//        gatespage
//        .searchInputs(plateEn)
//        .waitForVehicleAppear()
//        .clickPermitStatusButtonInTable(plateNumber)
//        .clickChangeStatusButton()
//        .selectSetStatusDDL(dataModel().Gates.status)
//        .enterStatusNotes(dataModel().Gates.notes)
//        .clickSaveButton();
//
//
//        Assertions.myAssertEquals(
//                gatespage.getSuccessMessage(),
//                gatespage.getSuccessMessageText(),
//                "Status updated successfully",
//                "Status not updated");
//    }
//
//    @Test(priority = 3)
//    public void changeVehicleStatusByIcon() {
//        gatespage = basePage.openGates();
//
//        gatespage
//                .searchInputs(plateEn)
//                .clickChangeStatusIcon(plateNumber)
//                .selectSetStatusDDL(dataModel().Gates.status)
//                .enterStatusNotes(dataModel().Gates.notes)
//                .clickSaveButton()
//                .waitForSuccessMessage("Status updated successfully");
//
//
//        Assertions.myAssertEquals(
//                gatespage.getSuccessMessage(),
//                gatespage.getSuccessMessageText(),
//                "Status updated successfully",
//                "Status not updated");
//    }
//
//    @Test(priority = 4)
//    public void createVehicleReportInTable() {
//        gatespage = basePage.openGates();
//
//        gatespage
//                .searchInputs(plateEn)
//                .clickReportButtonIcon(plateNumber)
//                .enterNameEnglish(dataModel().Gates.nameEnglish)
//                .enterNameArabic(dataModel().Gates.nameArabic)
//                .selectAssignedUserDDL()
//                .selectSelectStageDDL()
//                .selectPriorityDDL()
//                .enterDetailsEnglish(dataModel().Gates.descriptionEN)
//                .enterDetailsArabic(dataModel().Gates.descriptionAR)
//                .clickSaveButton()
//                .waitForSuccessMessage("Created successfully");
//
//
//        Assertions.myAssertEquals(
//                gatespage.getSuccessMessage(),
//                gatespage.getSuccessMessageText(),
//                "Created successfully",
//                "Status not Created"
//        );
//    }
//
//    @Test(priority = 5)
//    public void filterByLocations(){
//        gatespage = basePage.openGates();
//        gatespage.filterByLocationAndSelect(dataModel().Gates.locationGate)
//                 .clickPermitStatusButtonInTable(plateNumber);
//
//        Assertions.myAssertEquals(
//                gatespage.getLocationsGate(),
//                gatespage.getLocationsGateText(),
//                dataModel().Gates.locationGate,
//                "Location Gate not appeared");
//    }
//
//    @Test(priority = 6)
//    public void filterByContractType(){
//        gatespage = basePage.openGates();
//        gatespage
//                .filterByContractTypeAndSelect(dataModel().Gates.contract)
//                .clickPermitStatusButtonInTable(plateNumber)
//                .clickVehicleInformationTab();
//
//        Assertions.myAssertEquals(
//                gatespage.getContractType(),
//                gatespage.getContractTypeText(),
//                dataModel().Gates.contract,
//                "Contract Type not appeared");
//    }
//
//    @Test(priority = 7)
//    public void deleteRestoreAndForceDeleteVehicleInTable() {
//
//        // Delete Vehicle In Table {
//
//        gatespage = basePage.openGates();
//
//        gatespage
//                .searchInputs(plateEn)
//                .clickDeleteIcon();
//
//        Assertions.myAssertEquals(
//                gatespage.getPopUpAction(),
//                gatespage.getPopUpActionText(),
//                "Delete",
//                "Expected delete confirmation popup to be displayed"
//        );
//
//        Assertions.myAssertEqualsNormalizedNumbers(
//                gatespage.getPopUpPlateNumber(),
//                gatespage.getPopUpPlateNumberText(),
//                "Are you sure you want to delete " + plateAr + "؟",
//                "Delete confirmation message is incorrect"
//        );
//
//        gatespage.clickOkButton()
//                 .waitForSuccessMessage("Deleted successfully");
//
//        Assertions.myAssertEquals(
//                gatespage.getSuccessMessage(),
//                gatespage.getSuccessMessageText(),
//                "Deleted successfully",
//                "Vehicle not Deleted"
//        );
//
//    // Restore Vehicle In Table
//
//        gatespage
//                .clickArchiveIcon()
//                .searchInputs(plateEn)
//                .clickRestoreIcon();
//
//        Assertions.myAssertEquals(
//                gatespage.getPopUpAction(),
//                gatespage.getPopUpActionText(),
//                "Restore",
//                "Expected Restore confirmation popup to be displayed"
//        );
//
//        Assertions.myAssertEqualsNormalizedNumbers(
//                gatespage.getPopUpPlateNumber(),
//                gatespage.getPopUpPlateNumberText(),
//                "Restore " + plateAr + "؟",
//                "Restore confirmation message is incorrect"
//        );
//
//        gatespage.clickOkButton()
//                .waitForSuccessMessage("Restored successfully");
//
//        Assertions.myAssertEquals(
//                gatespage.getSuccessMessage(),
//                gatespage.getSuccessMessageText(),
//                "Restored successfully",
//                "Vehicle not Restored"
//        );
//
//        // Force Delete Vehicle In Table
//        // Step 1
//
//        gatespage
//                .clickArchiveIcon()
//                .searchInputs(plateEn)
//                .clickDeleteIcon();
//
//        Assertions.myAssertEquals(
//                gatespage.getPopUpAction(),
//                gatespage.getPopUpActionText(),
//                "Delete",
//                "Expected delete confirmation popup to be displayed"
//        );
//
//        Assertions.myAssertEqualsNormalizedNumbers(
//                gatespage.getPopUpPlateNumber(),
//                gatespage.getPopUpPlateNumberText(),
//                "Are you sure you want to delete " + plateAr + "؟",
//                "Delete confirmation message is incorrect"
//        );
//
//        gatespage.clickOkButton()
//                .waitForSuccessMessage("Deleted successfully");
//
//        Assertions.myAssertEquals(
//                gatespage.getSuccessMessage(),
//                gatespage.getSuccessMessageText(),
//                "Deleted successfully",
//                "Vehicle not Deleted"
//        );
//         // Step 2
//        gatespage
//                .clickArchiveIcon()
//                .searchInputs(plateEn)
//                .clickForceDeleteIcon();
//
//        Assertions.myAssertEquals(
//                gatespage.getPopUpAction(),
//                gatespage.getPopUpActionText(),
//                "Permanent delete",
//                "Expected Permanent delete confirmation popup to be displayed"
//        );
//
//         Assertions.myAssertEqualsNormalizedNumbers(
//                 gatespage.getPopUpPlateNumber(),
//                 gatespage.getPopUpPlateNumberText(),
//        "Delete permanently? This action cannot be undone.",
//        "Delete permanently confirmation message is incorrect");
//
//
//        gatespage.clickOkButton()
//                .waitForSuccessMessage("Deleted successfully");
//
//        Assertions.myAssertEquals(
//                gatespage.getSuccessMessage(),
//                gatespage.getSuccessMessageText(),
//                "Deleted successfully",
//                "Vehicle not Deleted"
//        );
//
//}

}