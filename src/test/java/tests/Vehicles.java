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
    public void verifyVehicle() {

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

        Assertions.myAssertTrue(
                vehiclesPage.isCarStatus(plateNumber),
                "Car Status not  Contract did not appear successfully"
        );

        Assertions.myAssertEquals(
                vehiclesPage.getCarStatusTextElement(plateNumber),
                vehiclesPage.getCarStatusText(plateNumber),
                "Active",
                "Vehicle status not Active");






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
    }
}