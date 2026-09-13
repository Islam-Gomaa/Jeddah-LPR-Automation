//package tests;
//
//import base.BaseTests;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//import pages.BasePage;
//import utilities.AuthHelper;
//import utils.Assertions;
//
//import static dataReader.ReadDataFromJson.dataModel;
//
//public class FeatureGroupTests extends BaseTests {
//
//    BasePage basePage;
////    FeatureGroupPage featureGroupPage;
//
//    @BeforeMethod
//    public void setupAdminSession() {
//        openSystem();
//        basePage = AuthHelper.login(driver);
//    }
//
//    @Test(priority = 1)
//    public void createFeatureGroupTest() {

        // ====== Control Panel ======




//        featureGroupPage = basePage.openFeatureGroup();
//        featureGroupPage
//                .clickAddButton()
//                .enterArabicName(dataModel().FeatureGroup.nameAR)
//                .enterEnglishName(dataModel().FeatureGroup.nameEN)
//                .clickSubmit();
//
//        Assertions.myAssertTrue(
//                basePage.isSuccessIconDisplayed()
//                        && basePage.isSuccessMessageDisplayed(),
//                "Success popup is not displayed correctly");
//
//        Assertions.myAssertEquals(
//                basePage.getSuccessMessage(),
//                "Created successfully");
//
//        featureGroupPage
//                .closePopUpIcon()
//                .searchInputs(dataModel().FeatureGroup.nameEN);
//
//        Assertions.myAssertEquals(
//                basePage.getTableSearchResult(),
//                dataModel().FeatureGroup.nameEN);
//
//        featureGroupPage
//                .searchInputs(dataModel().FeatureGroup.nameEN)
//                .clickSearchResult()
//                .clickEdit()
//                .enterArabicName(dataModel().FeatureGroup.editNameAR)
//                .enterEnglishName(dataModel().FeatureGroup.editNameEN)
//                .clickSubmit();
//
//        Assertions.myAssertTrue(
//                basePage.isSuccessIconDisplayed()
//                        && basePage.isSuccessMessageDisplayed(),
//                "Success popup is not displayed correctly");
//
//        Assertions.myAssertEquals(
//                basePage.getSuccessMessage(),
//                "Updated successfully");
//
//        featureGroupPage
//                .closePopUpIcon()
//                .searchInputs(dataModel().FeatureGroup.editNameEN);
//
//        Assertions.myAssertEquals(
//                basePage.getTableSearchResult(),
//                dataModel().FeatureGroup.editNameEN);
//
//        featureGroupPage
//                .searchInputs(dataModel().FeatureGroup.editNameEN)
//                .clickSearchResult()
//                .clickDelete();
//
//        Assertions.myAssertTrue(
//                basePage.isSuccessIconDisplayed()
//                        && basePage.isSuccessMessageDisplayed(),
//                "Success popup is not displayed correctly"
//        );
//        Assertions.myAssertEquals(
//                basePage.getSuccessMessage(),
//                "Deleted successfully"
//        );
//
//        featureGroupPage
//                .closePopUpIcon()
//                .clearSearchInputs()
//                .searchInputs(dataModel().FeatureGroup.editNameEN);
//
//        Assertions.myAssertTrue(
//                basePage.isNoDataMessageCorrect(),
//                "No data message is not displayed after search"
//        );
//    }
//}
//

