package tests;

import base.BaseTests;
import data.VehicleTestData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.VehiclesPage;
import utilities.AuthHelper;
import utils.Assertions;

import static dataReader.ReadDataFromJson.dataModel;

public class Vehicles extends BaseTests {

    private BasePage basePage;
    private VehiclesPage vehiclesPage;

    private String plateEn;
    private String plateAr;
    private String plateNumber;
    private String plateLettersEn;
    private String plateLettersAr;

    @BeforeClass(alwaysRun = true)
    public void loadVehicleTestData() {

        plateEn = VehicleTestData.getPlateEn();
        plateAr = VehicleTestData.getPlateAr();
        plateNumber = VehicleTestData.getPlateNumber();
        plateLettersEn = VehicleTestData.getPlateLettersEn();
        plateLettersAr = VehicleTestData.getPlateLettersAr();

        Assert.assertNotNull(
                plateEn,
                "Vehicle data is missing. Gates must generate the vehicle first."
        );

        Assert.assertNotNull(
                plateNumber,
                "Vehicle plate number is missing."
        );

        // Debug
        System.out.println("==============================");
        System.out.println("==============================");
        System.out.println("==============================");
        System.out.println("Plate EN: " + plateEn);
        System.out.println("Plate AR: " + plateAr);
        System.out.println("Expected Number: " + plateNumber);
        System.out.println("Expected Letters EN: " + plateLettersEn);
        System.out.println("Expected Letters AR: " + plateLettersAr);
        System.out.println("==============================");
        System.out.println("==============================");
        System.out.println("==============================");
    }

    // Admin Login
    @BeforeMethod
    public void setupAdminSession() {
        openSystem();
        basePage = AuthHelper.login(driver);
    }

    // Verify the existing vehicle in Vehicles module
    @Test(priority = 1)
    public void verifyAndEditVehicle() {

        vehiclesPage = basePage.openVehicles();

        vehiclesPage
                .searchInputs(plateNumber);

        Assertions.myAssertTrue(
                vehiclesPage.isPlateNumberDisplayed(plateNumber),
                "Plate number did not appear successfully"
        );

        Assertions.myAssertTrue(
                vehiclesPage.iscurrentContract(dataModel().Vehicles.contract),
                "Current Contract did not appear successfully"
        );

        Assertions.myAssertEquals(
                vehiclesPage.getCarStatusTextElement(plateNumber),
                vehiclesPage.getCarStatusText(plateNumber),
                "Active",
                "Vehicle status not Active");

        // Edit Vehicle

        vehiclesPage
                .clickEditVehiclesIcon(plateNumber)
                .clickSaveButton();

        Assertions.myAssertEquals(
                vehiclesPage.getSuccessMessage(),
                vehiclesPage.getSuccessMessageText(),
                "Updated successfully",
                "Vehicle not Updated"
        );
    }

    // Change Vehicle Status to be Blocked

    @Test(priority = 2)
    public void changeVehicleStatusToBeBlocked() {
        vehiclesPage = basePage.openVehicles();

        vehiclesPage
                .searchInputs(plateNumber);

        Assertions.myAssertEquals(
                vehiclesPage.getCarStatusTextElement(plateNumber),
                vehiclesPage.getCarStatusText(plateNumber),
                "Active",
                "Vehicle status not Active");

        vehiclesPage
                .clickChangeStatusIcon(plateNumber);

        Assertions.myAssertEquals(
                vehiclesPage.getPopUpAction(),
                vehiclesPage.getPopUpActionText(),
                "Change Status",
                "Expected Change Status confirmation popup to be displayed"
        );

        Assertions.myAssertEqualsNormalizedNumbers(
                vehiclesPage.getPopUpPlateNumber(),
                vehiclesPage.getPopUpPlateNumberText(),
                "Are you sure you want to change the car status to Inactive?",
                "Change Status confirmation message is incorrect"
        );

        vehiclesPage.clickOkButton()
                .waitForSuccessMessage("The car has been blocked successfully");

        // The car has been unblocked successfully
        Assertions.myAssertEquals(
                vehiclesPage.getSuccessMessage(),
                vehiclesPage.getSuccessMessageText(),
                "The car has been blocked successfully",
                "The car still Active and not Blocked"
        );
    }
    // Change Vehicle Status to be Active
    @Test(priority = 3)
    public void changeVehicleStatusToBeActive() {
        vehiclesPage = basePage.openVehicles();

        vehiclesPage
                .searchInputs(plateNumber);

        Assertions.myAssertEquals(
                vehiclesPage.getCarStatusTextElement(plateNumber),
                vehiclesPage.getCarStatusText(plateNumber),
                "Blocked",
                "Vehicle status not Blocked");

        vehiclesPage
                .clickChangeStatusIcon(plateNumber);


        Assertions.myAssertEquals(
                vehiclesPage.getPopUpAction(),
                vehiclesPage.getPopUpActionText(),
                "Change Status",
                "Expected Change Status confirmation popup to be displayed"
        );

        Assertions.myAssertEqualsNormalizedNumbers(
                vehiclesPage.getPopUpPlateNumber(),
                vehiclesPage.getPopUpPlateNumberText(),
                "Are you sure you want to change the car status to Active?",
                "Change Status confirmation message is incorrect"
        );

        vehiclesPage.clickOkButton()
                .waitForSuccessMessage("The car has been unblocked successfully");

        Assertions.myAssertEquals(
                vehiclesPage.getSuccessMessage(),
                vehiclesPage.getSuccessMessageText(),
                "The car has been unblocked successfully",
                "The car still Blocked and not Active"
        );
    }

    @Test(priority = 4)
    public void deleteRestoreAndForceDeleteVehicleInTable() {

        // Delete Vehicle In Table() {

        vehiclesPage = basePage.openVehicles();

        vehiclesPage
                .searchInputs(plateEn)
                .clickDeleteIcon();

        Assertions.myAssertEquals(
                vehiclesPage.getPopUpAction(),
                vehiclesPage.getPopUpActionText(),
                "Delete",
                "Expected delete confirmation popup to be displayed"
        );

        Assertions.myAssertEqualsNormalizedNumbers(
                vehiclesPage.getPopUpPlateNumber(),
                vehiclesPage.getPopUpPlateNumberText(),
                "Are you sure you want to delete " + plateNumber + plateLettersEn + "?",
                "Delete confirmation message is incorrect"
        );

        vehiclesPage.clickOkButton()
                .waitForSuccessMessage("Deleted successfully");

        Assertions.myAssertEquals(
                vehiclesPage.getSuccessMessage(),
                vehiclesPage.getSuccessMessageText(),
                "Deleted successfully",
                "Vehicle not Deleted"
        );

        // Restore Vehicle In Table

        vehiclesPage
                .clickArchiveIcon()
                .searchInputs(plateEn)
                .clickRestoreIcon();

        Assertions.myAssertEquals(
                vehiclesPage.getPopUpAction(),
                vehiclesPage.getPopUpActionText(),
                "Restore",
                "Expected Restore confirmation popup to be displayed"
        );

        Assertions.myAssertEqualsNormalizedNumbers(
                vehiclesPage.getPopUpPlateNumber(),
                vehiclesPage.getPopUpPlateNumberText(),
                "Restore " + plateNumber + plateLettersEn + "?",
                "Restore confirmation message is incorrect"
        );

        vehiclesPage.clickOkButton()
                .waitForSuccessMessage("Restored successfully");

        Assertions.myAssertEquals(
                vehiclesPage.getSuccessMessage(),
                vehiclesPage.getSuccessMessageText(),
                "Restored successfully",
                "Vehicle not Restored"
        );

        // Force Delete Vehicle In Table
        // Step 1

        vehiclesPage
                .clickArchiveIcon()
                .searchInputs(plateEn)
                .clickDeleteIcon();

        Assertions.myAssertEquals(
                vehiclesPage.getPopUpAction(),
                vehiclesPage.getPopUpActionText(),
                "Delete",
                "Expected delete confirmation popup to be displayed"
        );

        Assertions.myAssertEqualsNormalizedNumbers(
                vehiclesPage.getPopUpPlateNumber(),
                vehiclesPage.getPopUpPlateNumberText(),
                "Are you sure you want to delete " + plateNumber + plateLettersEn + "?",
                "Delete confirmation message is incorrect"
        );

        vehiclesPage.clickOkButton()
                .waitForSuccessMessage("Deleted successfully");

        Assertions.myAssertEquals(
                vehiclesPage.getSuccessMessage(),
                vehiclesPage.getSuccessMessageText(),
                "Deleted successfully",
                "Vehicle not Deleted"
        );
        // Step 2
        vehiclesPage
                .clickArchiveIcon()
                .searchInputs(plateEn)
                .clickForceDeleteIcon();

        Assertions.myAssertEquals(
                vehiclesPage.getPopUpAction(),
                vehiclesPage.getPopUpActionText(),
                "Permanent delete",
                "Expected Permanent delete confirmation popup to be displayed"
        );

        Assertions.myAssertEqualsNormalizedNumbers(
                vehiclesPage.getPopUpPlateNumber(),
                vehiclesPage.getPopUpPlateNumberText(),
                "Delete permanently? This action cannot be undone.",
                "Delete permanently confirmation message is incorrect");


        vehiclesPage.clickOkButton()
                .waitForSuccessMessage("Deleted successfully");

        Assertions.myAssertEquals(
                vehiclesPage.getSuccessMessage(),
                vehiclesPage.getSuccessMessageText(),
                "Deleted successfully",
                "Vehicle not Deleted"
        );

    }
}





//        Assertions.myAssertEqualsIgnoreSpaces(
//                vehiclesPage.getPlateLetterArElement(),
//                vehiclesPage.getPlateLetterAr(),
//                plateLettersAr,
//                "Incorrect Arabic plate letters"
//        );
//
//        Assertions.myAssertTrue(
//                vehiclesPage.isPlateLetterEnDisplayed(),
//                "Plate letter En did not appear successfully"
//        );
//
//        Assertions.myAssertEqualsIgnoreSpaces(
//                vehiclesPage.getPlateLetterEnElement(),
//                vehiclesPage.getPlateLetterEn(),
//                plateLettersEn,
//                "Incorrect English plate letters"
//        );

