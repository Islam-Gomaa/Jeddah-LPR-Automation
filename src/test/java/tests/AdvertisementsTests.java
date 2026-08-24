package tests;

import base.BaseTests;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.admin.AdvertisementsPage;
import pages.admin.BasePage;
import pages.website.HomePage;
import pages.website.ServicesAndSolutionsPage;
import utilities.AuthHelper;
import utils.Assertions;

import static dataReader.ReadDataFromJson.dataModel;

public class AdvertisementsTests extends BaseTests {

    BasePage basePage;
    AdvertisementsPage advertisementsPage;
    ServicesAndSolutionsPage servicesAndSolutionsPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupAdminSession() {
        openAdmin();
        basePage = AuthHelper.login(driver);
    }

    //  Advertisements - Verify Add, Activate, Edit to Inactive, Website Visibility, and Delete
//    @Test(priority = 1)
//    public void shouldAddActivateEditToInactiveVerifyWebsiteVisibilityAndDeleteAdvertisement(){
//
//        // ====== Control Panel ======
//        advertisementsPage = basePage.openAdvertisements();
//
//        advertisementsPage
//                .clickAddButton()
//                .enterArabicName(dataModel().Advertisements.titleAr)
//                .enterEnglishName(dataModel().Advertisements.titleEn)
//                .enterArabicDescription(dataModel().Advertisements.descriptionAr)
//                .enterEnglishDescription(dataModel().Advertisements.descriptionEn)
//                .uploadImage(System.getProperty("user.dir")+ "/src/test/resources/images/" + dataModel().Advertisements.image)
//                .clickIsActive()
//                .clickAddPopUp();
//
//        Assertions.myAssertTrue(
//                basePage.isSuccessIconDisplayed()
//                        && basePage.isSuccessMessageDisplayed(),
//                "Success popup is not displayed correctly"
//        );
//        Assertions.myAssertEquals(
//                basePage.getSuccessMessage(),
//                "Created successfully"
//        );
//
//        WebElement activeToggle =
//                advertisementsPage.getActiveToggle(dataModel().Advertisements.titleEn);
//
//        Assert.assertTrue(
//                activeToggle.getAttribute("class")
//                        .contains("v-switch__track bg-success"),
//                "Auto Advertisement should be Active"
//        );
//
//        // ====== Website ======
//
//        openWebsite();
//        homePage = new HomePage(driver);
//
//        Assertions.myAssertTrue(
//                homePage.isAdvertisementImgDisplayed(
//                        dataModel().Advertisements.titleEn
//                ),
//                "Active Advertisement image is not displayed"
//        );
//
//        homePage
//                .clickCloseIconOfAdvertisementImage();
//
//        // Header Solutions and Services
//        homePage.clickHeaderSolutionsAndServicesLink();
//
//        Assertions.myAssertTrue(
//                homePage.getCurrentUrl().contains("/services-and-solutions"),
//                "Header Solutions and Services URL is incorrect");
//
//        // ====== Control Panel ======
//
//        openAdmin();
//        advertisementsPage = basePage.openAdvertisements();
//        advertisementsPage
//                .searchInputs(dataModel().Advertisements.titleEn)
//                .clickSearchResult()
//                .clickEdit()
//                .enterArabicName(dataModel().Advertisements.editTitleAr)
//                .enterEnglishName(dataModel().Advertisements.editTitleEn)
//                .clickIsActive()
//                .clickEditAndSave();
//
//
//        Assertions.myAssertTrue(
//                basePage.isSuccessIconDisplayed()
//                        && basePage.isSuccessMessageDisplayed(),
//                "Success popup is not displayed correctly"
//        );
//        Assertions.myAssertEquals(
//                basePage.getSuccessMessage(),
//                "Updated successfully"
//        );
//
//        // ====== Website ======
//
//        openWebsite();
//        homePage = new HomePage(driver);
//
//
//        // Header Solutions and Services
//        homePage.clickHeaderSolutionsAndServicesLink();
//
//        Assertions.myAssertTrue(
//                homePage.getCurrentUrl().contains("/services-and-solutions"),
//                "Header Solutions and Services URL is incorrect");
//
//
//
//
//        // ====== Control Panel ======
//        openAdmin();
//        advertisementsPage = basePage.openAdvertisements();
//        advertisementsPage
//                .searchInputs(dataModel().Advertisements.editTitleEn)
//                .clickSearchResult()
//                .clickDelete();
//
//        // Assertion on control panel
//        Assertions.myAssertTrue(
//                basePage.isSuccessIconDisplayed()
//                        && basePage.isSuccessMessageDisplayed(),
//                "Success popup is not displayed correctly"
//        );
//        Assertions.myAssertEquals(
//                basePage.getSuccessMessage(),
//                "Deleted successfully"
//        );
//    }

   //  Advertisements - Verify Add, Website Visibility, and Delete
    @Test(priority = 2)
    public void shouldAddAndDeleteAdvertisementSuccessfully() {

        // ====== Control Panel ======
        advertisementsPage = basePage.openAdvertisements();

        advertisementsPage
                .clickAddButton()
                .enterArabicName(dataModel().Advertisements.titleAr)
                .enterEnglishName(dataModel().Advertisements.titleEn)
                .enterArabicDescription(dataModel().Advertisements.descriptionAr)
                .enterEnglishDescription(dataModel().Advertisements.descriptionEn)
                .uploadImage(
                        System.getProperty("user.dir")
                                + "/src/test/resources/images/"
                                + dataModel().Advertisements.image
                )
                .clickIsActive()
                .clickAddPopUp();

        // Assertion: Advertisement created successfully
        Assertions.myAssertTrue(
                basePage.isSuccessIconDisplayed()
                        && basePage.isSuccessMessageDisplayed(),
                "Success popup is not displayed correctly"
        );

        Assertions.myAssertEquals(
                basePage.getSuccessMessage(),
                "Created successfully"
        );

        // Assertion: Advertisement is Active in Control Panel
        WebElement activeToggle =
                advertisementsPage.getActiveToggle(
                        dataModel().Advertisements.titleEn
                );

        Assert.assertTrue(
                activeToggle.getAttribute("class")
                        .contains("v-switch__track bg-success"),
                "Advertisement should be Active"
        );

        // ====== Website ======
        openWebsite();
        homePage = new HomePage(driver);

        // Assertion: Active Advertisement is displayed on Website
        Assertions.myAssertTrue(
                homePage.isAdvertisementImgDisplayed(
                        dataModel().Advertisements.titleEn
                ),
                "Active Advertisement image is not displayed"
        );

        homePage.clickCloseIconOfAdvertisementImage();

        // ====== Control Panel ======
        openAdmin();
        advertisementsPage = basePage.openAdvertisements();

        advertisementsPage
                .searchInputs(dataModel().Advertisements.titleEn)
                .clickSearchResult()
                .clickDelete();

        // Assertion: Advertisement deleted successfully
        Assertions.myAssertTrue(
                basePage.isSuccessIconDisplayed()
                        && basePage.isSuccessMessageDisplayed(),
                "Success popup is not displayed correctly"
        );

        Assertions.myAssertEquals(
                basePage.getSuccessMessage(),
                "Deleted successfully"
        );
    }
}


