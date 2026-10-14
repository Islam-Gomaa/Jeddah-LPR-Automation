package tests;
import org.testng.Assert;
import api.LprApiClient;
import base.BaseTests;
import io.restassured.response.Response;
import org.testng.annotations.*;
import pages.BasePage;
import pages.GatesPage;
import pages.ScalePage;
import utilities.AuthHelper;
import utilities.ConfigReader;
import utils.Assertions;
import java.nio.file.Path;
import static dataReader.ReadDataFromJson.dataModel;


public class Scale extends BaseTests {

    BasePage basePage;
    ScalePage scalepage;

    // General Vehicle Test Data

    private Path carImage = Path.of("src/test/resources/images/car.png");
    private Path plateImage = Path.of("src/test/resources/images/plate.png");

    private String X_TOKEN = ConfigReader.get("X_TOKEN");
    private String ENDPOINT = ConfigReader.get("scaleEndPoint");


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

        scalepage = basePage.openScale();

        Assertions.myAssertTrue(
                scalepage.isPlateNumberDisplayed(plateNumber),
                "Plate number did not appear successfully");
        scalepage.clickPermitStatusButtonInTable(plateNumber);

        Assertions.myAssertTrue(
                scalepage.isPlateLetterArDisplayed(),
                "Plate letter Ar did not appear successfully");

        Assertions.myAssertEqualsIgnoreSpaces(
                scalepage.getPlateLetterArElement(),
                scalepage.getPlateLetterAr(),
                plateLettersAr,
                "Incorrect Arabic plate letters");

        Assertions.myAssertTrue(
                scalepage.isPlateLetterEnDisplayed(),
                "Plate letter En did not appear successfully");

        Assertions.myAssertEqualsIgnoreSpaces(
                scalepage.getPlateLetterEnElement(),
                scalepage.getPlateLetterEn(),
                plateLettersEn,
                "Incorrect English plate letter");

        Assertions.myAssertEquals(
                scalepage.getVehicleIsNotPermittedTextElement(),
                scalepage.getVehicleIsNotPermittedText(),
                "This vehicle is not permitted to enter",
                "Vehicle is permitted");

        scalepage
                .clickCreatePermit()
                .selectVehicleTypeDDL()
                .selectDriverDDL()
                .selectMainWasteDDL()
                .selectSubWasteDDL()
                .selectContractorDDL()
                .clickSaveButton()
                .waitUntilVehicleIsPermitted();

        Assertions.myAssertEquals(
                scalepage.getVehicleIsPermittedTextElement(),
                scalepage.getVehicleIsPermittedText(),
                "This vehicle is permitted to enter",
                "Vehicle is not permitted");
    }

    @Test(priority = 2)
    public void changeVehicleStatusInPopUp() throws InterruptedException {
        scalepage = basePage.openScale();

        scalepage
                .searchInputs(plateEn)
                .waitForVehicleAppear()
                .clickPermitStatusButtonInTable(plateNumber)
                .clickChangeStatusButton()
                .selectSetStatusDDL(dataModel().Gates.status)
                .enterStatusNotes(dataModel().Gates.notes)
                .clickSaveButton();

        Assertions.myAssertEquals(
                scalepage.getSuccessMessage(),
                scalepage.getSuccessMessageText(),
                "Status updated successfully",
                "Status not updated");
    }

    @Test(priority = 3)
    public void changeVehicleStatusByIcon() {
        scalepage = basePage.openScale();

        scalepage
                .searchInputs(plateEn)
                .clickChangeStatusIcon(plateNumber)
                .selectSetStatusDDL(dataModel().Gates.status)
                .enterStatusNotes(dataModel().Gates.notes)
                .clickSaveButton()
                .waitForSuccessMessage("Status updated successfully");


        Assertions.myAssertEquals(
                scalepage.getSuccessMessage(),
                scalepage.getSuccessMessageText(),
                "Status updated successfully",
                "Status not updated");
    }

    @Test(priority = 4)
    public void createVehicleReportInTable() {
        scalepage = basePage.openScale();

        scalepage
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
                scalepage.getSuccessMessage(),
                scalepage.getSuccessMessageText(),
                "Created successfully",
                "Status not Created"
        );
    }

    @Test(priority = 5)
    public void filterByLocations(){
        scalepage = basePage.openScale();
        scalepage.filterByLocationAndSelect(dataModel().Gates.locationGate);
        scalepage.clickPermitStatusButtonInTable(plateNumber);

        Assertions.myAssertEquals(
                scalepage.getLocationsGate(),
                scalepage.getLocationsGateText(),
                dataModel().Gates.locationGate,
                "Location Gate not appeared");
    }

    @Test(priority = 6)
    public void filterByContractType(){
        scalepage = basePage.openScale();
        scalepage.filterByContractTypeAndSelect(dataModel().Gates.contract);
        scalepage.clickPermitStatusButtonInTable(plateNumber);
        scalepage.clickVehicleInformationTab();

        Assertions.myAssertEquals(
                scalepage.getContractType(),
                scalepage.getContractTypeText(),
                dataModel().Gates.contract,
                "Contract Type not appeared");
    }

    @Test(priority = 7)
    public void deleteRestoreAndForceDeleteVehicleInTable() {

        // Delete Vehicle In Table() {

        scalepage = basePage.openScale();

        scalepage
                .searchInputs(plateEn)
                .clickDeleteIcon();

        Assertions.myAssertEquals(
                scalepage.getPopUpAction(),
                scalepage.getPopUpActionText(),
                "Delete",
                "Expected delete confirmation popup to be displayed"
        );

        Assertions.myAssertEqualsNormalizedNumbers(
                scalepage.getPopUpPlateNumber(),
                scalepage.getPopUpPlateNumberText(),
                "Are you sure you want to delete " + plateAr + "؟",
                "Delete confirmation message is incorrect"
        );

        scalepage.clickOkButton()
                .waitForSuccessMessage("Deleted successfully");

        Assertions.myAssertEquals(
                scalepage.getSuccessMessage(),
                scalepage.getSuccessMessageText(),
                "Deleted successfully",
                "Vehicle not Deleted"
        );

        // Restore Vehicle In Table

        scalepage
                .clickArchiveIcon()
                .searchInputs(plateEn)
                .clickRestoreIcon();

        Assertions.myAssertEquals(
                scalepage.getPopUpAction(),
                scalepage.getPopUpActionText(),
                "Restore",
                "Expected Restore confirmation popup to be displayed"
        );

        Assertions.myAssertEqualsNormalizedNumbers(
                scalepage.getPopUpPlateNumber(),
                scalepage.getPopUpPlateNumberText(),
                "Restore " + plateAr + "؟",
                "Restore confirmation message is incorrect"
        );

        scalepage.clickOkButton()
                .waitForSuccessMessage("Restored successfully");

        Assertions.myAssertEquals(
                scalepage.getSuccessMessage(),
                scalepage.getSuccessMessageText(),
                "Restored successfully",
                "Vehicle not Restored"
        );

        // Force Delete Vehicle In Table
        // Step 1

        scalepage
                .clickArchiveIcon()
                .searchInputs(plateEn)
                .clickDeleteIcon();

        Assertions.myAssertEquals(
                scalepage.getPopUpAction(),
                scalepage.getPopUpActionText(),
                "Delete",
                "Expected delete confirmation popup to be displayed"
        );

        Assertions.myAssertEqualsNormalizedNumbers(
                scalepage.getPopUpPlateNumber(),
                scalepage.getPopUpPlateNumberText(),
                "Are you sure you want to delete " + plateAr + "؟",
                "Delete confirmation message is incorrect"
        );

        scalepage.clickOkButton()
                .waitForSuccessMessage("Deleted successfully");

        Assertions.myAssertEquals(
                scalepage.getSuccessMessage(),
                scalepage.getSuccessMessageText(),
                "Deleted successfully",
                "Vehicle not Deleted"
        );
        // Step 2
        scalepage
                .clickArchiveIcon()
                .searchInputs(plateEn)
                .clickForceDeleteIcon();

        Assertions.myAssertEquals(
                scalepage.getPopUpAction(),
                scalepage.getPopUpActionText(),
                "Permanent delete",
                "Expected Permanent delete confirmation popup to be displayed"
        );

        Assertions.myAssertEqualsNormalizedNumbers(
                scalepage.getPopUpPlateNumber(),
                scalepage.getPopUpPlateNumberText(),
                "Delete permanently? This action cannot be undone.",
                "Delete permanently confirmation message is incorrect");


        scalepage.clickOkButton()
                .waitForSuccessMessage("Deleted successfully");

        Assertions.myAssertEquals(
                scalepage.getSuccessMessage(),
                scalepage.getSuccessMessageText(),
                "Deleted successfully",
                "Vehicle not Deleted"
        );

    }

}