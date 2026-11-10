package tests;

import base.BaseTests;
import data.VehicleTestData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.GatesPage;
import pages.VehiclesPage;
import utilities.AuthHelper;
import utils.Assertions;

import static dataReader.ReadDataFromJson.dataModel;

public class Vehicles extends BaseTests {

    private BasePage basePage;
    private VehiclesPage vehiclesPage;
    private GatesPage gatesPage;


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
        System.out.println("Plate EN: " + plateEn);
        System.out.println("Plate AR: " + plateAr);
        System.out.println("Expected Number: " + plateNumber);
        System.out.println("Expected Letters EN: " + plateLettersEn);
        System.out.println("Expected Letters AR: " + plateLettersAr);
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

    // Verify the existing vehicle in Vehicles module
    @Test(priority = 2)
    public void verifyAndEditVehicle() {

        vehiclesPage = basePage.openVehicles();

        vehiclesPage
                .searchInputs(plateNumber);

        Assertions.myAssertTrue(
                vehiclesPage.isPlateNumberDisplayed(plateNumber),
                "Plate number did not appear successfully"
        );

        Assertions.myAssertTrue(
                vehiclesPage.isCurrentContractDisplayed(
                        plateNumber,
                        dataModel().Vehicles.contract
                ),
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
    @Test(priority = 3)
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

    @Test(priority = 4)
    public void filterByBlockedStatus(){
        vehiclesPage = basePage.openVehicles();
        vehiclesPage
                .searchInputs(plateNumber)
                .filterByStatusAndSelect(dataModel().Vehicles.blocked);

        Assertions.myAssertEquals(
                vehiclesPage.getCarStatusTextElement(plateNumber),
                vehiclesPage.getCarStatusText(plateNumber),
                "Blocked",
                "Vehicle status is Active");
    }

    // Change Vehicle Status to be Active
    @Test(priority = 5)
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

    @Test(priority = 6)
    public void filterByActiveStatus(){
        vehiclesPage = basePage.openVehicles();
        vehiclesPage
                .searchInputs(plateNumber)
                .filterByStatusAndSelect(dataModel().Vehicles.active);

        Assertions.myAssertEquals(
                vehiclesPage.getCarStatusTextElement(plateNumber),
                vehiclesPage.getCarStatusText(plateNumber),
                "Active",
                "Vehicle status is Blocked");
    }

    // Delete Vehicle in Table
    @Test(priority = 7)
    public void deleteRestoreAndForceDeleteVehicleInTable() {

        vehiclesPage = basePage.openVehicles();

        vehiclesPage
                .searchInputs(plateNumber)
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
                "Are you sure you want to delete " + plateNumber  + " " +  plateLettersEn + "?",
                "Delete confirmation message is incorrect"
        );

                vehiclesPage.clickOkButton()
                            .waitForVehicleAppear();

        Assertions.myAssertEquals(
                vehiclesPage.getPopUpAction(),
                vehiclesPage.getPopUpActionText(),
        "The car cannot be deleted because it is linked to contracts in the system",
        "The car is linked to contracts in the system and deleted");

        vehiclesPage.clickOkButton();

        Assertions.myAssertTrue(
                vehiclesPage.isPlateNumberDisplayed(plateNumber),
                "Plate number did not appear successfully"
        );

}
}

