//شوف الكود ده واستني عالطلب الطلب بتاعي لحد ما اكملك كلامي package tests;
//
//import api.LprApiClient;
//import base.BaseTests;
//import io.restassured.response.Response;
//import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//import pages.BasePage;
//import pages.GatesPage;
//import utilities.AuthHelper;
//import utils.Assertions;
//import org.openqa.selenium.WebElement;
//
//import java.nio.file.Path;
//
//public class Gates extends BaseTests {
//
//    BasePage basePage;
//    GatesPage gatespage;
//
//    @BeforeMethod
//    public void setupAdminSession() {
//        openSystem();
//        basePage = AuthHelper.login(driver);
//    }
//
//    @Test
//    public void sendVehiclePassageEvent() {
//
//        Path carImage = Path.of("src/test/resources/images/car.png");
//
//        Path plateImage = Path.of("src/test/resources/images/plate.png");
//
//        LprApiClient client = new LprApiClient();
//
//        Response response = client.sendVehiclePassage(carImage, plateImage);
//
//        System.out.println("Status: " + response.statusCode());
//        System.out.println("Response: " + response.asPrettyString());
//        System.out.println("Plate EN: " + client.getGeneratedPlateEn());
//        System.out.println("Plate AR: " + client.getGeneratedPlateAr());
//        System.out.println("Date: " + client.getGeneratedDate());
//
//        Assert.assertEquals(
//                response.statusCode(), 200, "Vehicle passage request failed: " + response.asPrettyString());
//
//        // Use plate number in process cycle
//
//        String plateEn = client.getGeneratedPlateEn();
//        String plateAr = client.getGeneratedPlateAr();
//
//        String plateNumber = plateEn.replaceAll("[^0-9]", "");
//        String plateLettersEn = plateEn.replaceAll("[^A-Za-z]", "");
//        String plateLettersAr = plateAr.replaceAll("[^\\u0600-\\u06FF]", "");
//
//        System.out.println("Plate EN: " + plateEn);
//        System.out.println("Plate AR: " + plateAr);
//
//        System.out.println("Expected Number: " + plateNumber);
//        System.out.println("Expected Letters EN: " + plateLettersEn);
//        System.out.println("Expected Letters AR: " + plateLettersAr);
//
//        // Gates Page
//        gatespage = basePage.openGates();
//
//        Assertions.myAssertTrue(
//                gatespage.isPlateNumberDisplayed(plateNumber), "Plate number did not appear successfully");
//
//        // Create Permit for vehicle
//        gatespage.clickPermitStatusButtonInTable(plateNumber);
//
//        Assertions.myAssertTrue(
//                gatespage.isPlateLetterArDisplayed(), "Plate letter Ar did not appear successfully");
//
//        Assertions.myAssertEqualsIgnoreSpaces(
//                gatespage.getPlateLetterArElement(),
//                gatespage.getPlateLetterAr(),
//                plateLettersAr,
//                "Incorrect Arabic plate letters");
//
//        Assertions.myAssertTrue(
//                gatespage.isPlateLetterEnDisplayed(), "Plate letter En did not appear successfully");
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
//
////        Assertions.myAssertEquals(
////                gatespage.getVehicleIsNotPermittedTextElement(),
////                gatespage.getVehicleIsNotPermittedText(),
////                "This vehicle is permitted to enter",
////                "Vehicle does not permitted");
//
//        // Change Status
//
//
//        gatespage
////            .searchInputs(plateEn)
////            .clickPermitStatusButtonInTable(plateNumber)
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
//
//
//        // Report
//
//        gatespage
//                .searchInputs(plateEn)
//                .clickReportButtonIcon(plateNumber)
//                .enterNameEnglish("test name ")
//                .enterNameArabic("بلاغ جديد")
//                .selectAssignedUserDDL()
//                .selectSelectStageDDL()
//                .selectPriorityDDL()
//                .enterDetailsEnglish("test des ")
//                .enterDetailsArabic("تفاصيل")
//                .clickSaveButton();
//
//        Assertions.myAssertEquals(
//                gatespage.getSuccessMessage(),
//                gatespage.getSuccessMessageText(),
//                "Status updated successfully",
//                "Status not updated");
//
//
//    }
//}
