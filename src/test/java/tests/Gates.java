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
    GatesPage gatesPage;

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

        gatesPage = basePage.openGates();

        Assertions.myAssertTrue(gatesPage.isPlateNumberDisplayed(plateNumber),
                "Plate number did not appear successfully");
        gatesPage.clickPermitStatusButtonInTable(plateNumber);

        Assertions.myAssertTrue(
                gatesPage.isPlateLetterArDisplayed(),
                "Plate letter Ar did not appear successfully");

        Assertions.myAssertEqualsIgnoreSpaces(
                gatesPage.getPlateLetterArElement(),
                gatesPage.getPlateLetterAr(),
                plateLettersAr,
                "Incorrect Arabic plate letters");

        Assertions.myAssertTrue(
                gatesPage.isPlateLetterEnDisplayed(),
                "Plate letter En did not appear successfully");

        Assertions.myAssertEqualsIgnoreSpaces(
                gatesPage.getPlateLetterEnElement(),
                gatesPage.getPlateLetterEn(),
                plateLettersEn,
                "Incorrect English plate letter");

        Assertions.myAssertEquals(
                gatesPage.getVehicleIsNotPermittedTextElement(),
                gatesPage.getVehicleIsNotPermittedText(),
                "This vehicle is not permitted to enter",
                "Vehicle is permitted");

        gatesPage
                .clickCreatePermit()
                .selectVehicleTypeDDL()
                .selectDriverDDL()
                .selectMainWasteDDL()
                .selectSubWasteDDL()
                .selectContractorDDL()
                .clickSaveButton()
                .waitUntilVehicleIsPermitted();


        Assertions.myAssertEquals(
                gatesPage.getVehicleIsPermittedTextElement(),
                gatesPage.getVehicleIsPermittedText(),
                "This vehicle is permitted to enter",
                "Vehicle is not permitted");
    }

    @Test(priority = 2)
    public void changeVehicleStatusInPopUp() throws InterruptedException {
        gatesPage = basePage.openGates();

        gatesPage
        .searchInputs(plateEn)
        .waitForVehicleAppear()
        .clickPermitStatusButtonInTable(plateNumber)
        .clickChangeStatusButton()
        .selectSetStatusDDL(dataModel().Gates.status)
        .enterStatusNotes(dataModel().Gates.notes)
        .clickSaveButton();


        Assertions.myAssertEquals(
                gatesPage.getSuccessMessage(),
                gatesPage.getSuccessMessageText(),
                "Status updated successfully",
                "Status not updated");
    }

    @Test(priority = 3)
    public void changeVehicleStatusByIcon() {
        gatesPage = basePage.openGates();

        gatesPage
                .searchInputs(plateEn)
                .clickChangeStatusIcon(plateNumber)
                .selectSetStatusDDL(dataModel().Gates.status)
                .enterStatusNotes(dataModel().Gates.notes)
                .clickSaveButton()
                .waitForSuccessMessage("Status updated successfully");


        Assertions.myAssertEquals(
                gatesPage.getSuccessMessage(),
                gatesPage.getSuccessMessageText(),
                "Status updated successfully",
                "Status not updated");
    }

    @Test(priority = 4)
    public void createVehicleReportInTable() {
        gatesPage = basePage.openGates();

        gatesPage
                .searchInputs(plateEn)
                .clickReportButtonIcon(plateNumber)
                .enterNameEnglish(dataModel().Gates.nameEnglish)
                .enterNameArabic(dataModel().Gates.nameArabic)
                .selectAssignedUserDDL()
                .selectSelectStageDDL()
                .selectPriorityDDL()
                .enterDetailsEnglish(dataModel().Gates.descriptionEN)
                .enterDetailsArabic(dataModel().Gates.descriptionAR)
                .clickSaveButton()
                .waitForSuccessMessage("Created successfully");


        Assertions.myAssertEquals(
                gatesPage.getSuccessMessage(),
                gatesPage.getSuccessMessageText(),
                "Created successfully",
                "Status not Created"
        );
    }

    @Test(priority = 5)
    public void filterByLocations(){
        gatesPage = basePage.openGates();
        gatesPage.filterByLocationAndSelect(dataModel().Gates.locationGate)
                 .clickPermitStatusButtonInTable(plateNumber);

        Assertions.myAssertEquals(
                gatesPage.getLocationsGate(),
                gatesPage.getLocationsGateText(),
                dataModel().Gates.locationGate,
                "Location Gate not appeared");
    }

    @Test(priority = 6)
    public void filterByContractType(){
        gatesPage = basePage.openGates();
        gatesPage
                .filterByContractTypeAndSelect(dataModel().Gates.contract)
                .clickPermitStatusButtonInTable(plateNumber)
                .clickVehicleInformationTab();

        Assertions.myAssertEquals(
                gatesPage.getContractType(),
                gatesPage.getContractTypeText(),
                dataModel().Gates.contract,
                "Contract Type not appeared");
    }

    // Delete Vehicle In Table
    @Test(priority = 7)
    public void deleteRestoreAndForceDeleteVehicleInTable() {

        gatesPage = basePage.openGates();

        gatesPage
                .searchInputs(plateEn)
                .clickDeleteIcon();

        Assertions.myAssertEquals(
                gatesPage.getPopUpAction(),
                gatesPage.getPopUpActionText(),
                "Delete",
                "Expected delete confirmation popup to be displayed"
        );

        Assertions.myAssertEqualsNormalizedNumbers(
                gatesPage.getPopUpPlateNumber(),
                gatesPage.getPopUpPlateNumberText(),
                "Are you sure you want to delete " + plateAr + "؟",
                "Delete confirmation message is incorrect"
        );

        gatesPage.clickOkButton()
                 .waitForSuccessMessage("Deleted successfully");

        Assertions.myAssertEquals(
                gatesPage.getSuccessMessage(),
                gatesPage.getSuccessMessageText(),
                "Deleted successfully",
                "Vehicle not Deleted"
        );

    // Restore Vehicle In Table

        gatesPage
                .clickArchiveIcon()
                .searchInputs(plateEn)
                .clickRestoreIcon();

        Assertions.myAssertEquals(
                gatesPage.getPopUpAction(),
                gatesPage.getPopUpActionText(),
                "Restore",
                "Expected Restore confirmation popup to be displayed"
        );

        Assertions.myAssertEqualsNormalizedNumbers(
                gatesPage.getPopUpPlateNumber(),
                gatesPage.getPopUpPlateNumberText(),
                "Restore " + plateAr + "؟",
                "Restore confirmation message is incorrect"
        );

        gatesPage.clickOkButton()
                .waitForSuccessMessage("Restored successfully");

        Assertions.myAssertEquals(
                gatesPage.getSuccessMessage(),
                gatesPage.getSuccessMessageText(),
                "Restored successfully",
                "Vehicle not Restored"
        );

        // Force Delete Vehicle In Table
        // Step 1

        gatesPage
                .clickArchiveIcon()
                .searchInputs(plateEn)
                .clickDeleteIcon();

        Assertions.myAssertEquals(
                gatesPage.getPopUpAction(),
                gatesPage.getPopUpActionText(),
                "Delete",
                "Expected delete confirmation popup to be displayed"
        );

        Assertions.myAssertEqualsNormalizedNumbers(
                gatesPage.getPopUpPlateNumber(),
                gatesPage.getPopUpPlateNumberText(),
                "Are you sure you want to delete " + plateAr + "؟",
                "Delete confirmation message is incorrect"
        );

        gatesPage.clickOkButton()
                .waitForSuccessMessage("Deleted successfully");

        Assertions.myAssertEquals(
                gatesPage.getSuccessMessage(),
                gatesPage.getSuccessMessageText(),
                "Deleted successfully",
                "Vehicle not Deleted"
        );
         // Step 2
        gatesPage
                .clickArchiveIcon()
                .searchInputs(plateEn)
                .clickForceDeleteIcon();

        Assertions.myAssertEquals(
                gatesPage.getPopUpAction(),
                gatesPage.getPopUpActionText(),
                "Permanent delete",
                "Expected Permanent delete confirmation popup to be displayed"
        );

         Assertions.myAssertEqualsNormalizedNumbers(
                 gatesPage.getPopUpPlateNumber(),
                 gatesPage.getPopUpPlateNumberText(),
        "Delete permanently? This action cannot be undone.",
        "Delete permanently confirmation message is incorrect");


        gatesPage.clickOkButton()
                .waitForSuccessMessage("Deleted successfully");

        Assertions.myAssertEquals(
                gatesPage.getSuccessMessage(),
                gatesPage.getSuccessMessageText(),
                "Deleted successfully",
                "Vehicle not Deleted"
        );
}

}