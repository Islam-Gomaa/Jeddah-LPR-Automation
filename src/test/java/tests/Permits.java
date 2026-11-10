package tests;
import static dataReader.ReadDataFromJson.dataModel;

import api.LprApiClient;
import api.SaudiPlate;
import api.SaudiPlateGenerator;
import base.BaseTests;
import data.VehicleTestData;
import io.restassured.response.Response;
import java.nio.file.Path;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BasePage;
import pages.GatesPage;
import pages.PermitsPage;
import utilities.AuthHelper;
import utilities.ConfigReader;
import utils.Assertions;

public class Permits extends BaseTests {

    BasePage basePage;
    PermitsPage permitsPage;
    GatesPage gatesPage;

    // Admin Login

    @BeforeMethod
    public void setupAdminSession() {
        openSystem();
        basePage = AuthHelper.login(driver);
    }

    // Generate Vehicle
    @Test(priority = 1)
    public void verifyAddPermitWithGeneratedSaudiPlate() {

        SaudiPlate plate = SaudiPlateGenerator.generateRandomPlate();

        System.out.println("Generated Plate: " + plate);

        permitsPage = basePage.openPermits();

        permitsPage.clickAddPermitBtn();

        permitsPage.enterPlateNumber(plate);

        // Continue filling the remaining permit fields

        // Save permit

        // Verify permit creation using generated plate
    }


//        Assertions.myAssertTrue(permitsPage.isPlateNumberDisplayed(plateNumber),
//                "Plate number did not appear successfully");
//        permitsPage.clickPermitStatusButtonInTable(plateNumber);
//
//        Assertions.myAssertTrue(
//                permitsPage.isPlateLetterArDisplayed(),
//                "Plate letter Ar did not appear successfully");
//
//        Assertions.myAssertEqualsIgnoreSpaces(
//                permitsPage.getPlateLetterArElement(),
//                permitsPage.getPlateLetterAr(),
//                plateLettersAr,
//                "Incorrect Arabic plate letters");
//
//        Assertions.myAssertTrue(
//                permitsPage.isPlateLetterEnDisplayed(),
//                "Plate letter En did not appear successfully");
//
//        Assertions.myAssertEqualsIgnoreSpaces(
//                permitsPage.getPlateLetterEnElement(),
//                permitsPage.getPlateLetterEn(),
//                plateLettersEn,
//                "Incorrect English plate letter");

//        Assertions.myAssertEquals(
//                permitsPage.getVehicleIsNotPermittedTextElement(),
//                permitsPage.getVehicleIsNotPermittedText(),
//                "This vehicle is not permitted to enter",
//                "Vehicle is permitted");
//
//        permitsPage
//                .clickCreatePermit()
//                .selectVehicleTypeDDL()
//                .selectDriverDDL()
//                .selectMainWasteDDL()
//                .selectSubWasteDDL()
//                .selectContractorDDL()
//                .clickSaveButton()
//                .waitUntilVehicleIsPermitted();
//
//
//        Assertions.myAssertEquals(
//                gatespage.getVehicleIsPermittedTextElement(),
//                gatespage.getVehicleIsPermittedText(),
//                "This vehicle is permitted to enter",
//                "Vehicle is not permitted");
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
//    // Delete Vehicle In Table
//    @Test(priority = 7)
//    public void deleteRestoreAndForceDeleteVehicleInTable() {
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
//}

//}