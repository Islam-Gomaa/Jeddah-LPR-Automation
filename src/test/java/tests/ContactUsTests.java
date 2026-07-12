package tests;

import base.BaseTests;
import org.testng.annotations.Test;
import pages.admin.BasePage;
import pages.admin.ContactUsPage;
import pages.website.ContactUsWebPage;
import pages.website.HomePage;
import utilities.AuthHelper;
import utils.Assertions;

import static dataReader.ReadDataFromJson.dataModel;

public class ContactUsTests extends BaseTests {

    BasePage basePage;
    ContactUsWebPage contactUsWebPage;
    ContactUsPage contactUsPage;
    HomePage homePage;

    @Test(priority = 1)
    public void addContactUsTest() {

        // ====== Website ======

        openWebsite();
        homePage = new HomePage(driver);
        contactUsWebPage = homePage.openContactUsWebsite();
        contactUsWebPage
                .enterContactName(dataModel().ContactUs.name)
                .enterContactEmail(dataModel().ContactUs.email)
                .enterContactPhone(dataModel().ContactUs.phone)
                .selectRequestDDL(dataModel().ContactUs.requestDDL)
                .enterContactSubject(dataModel().ContactUs.subject)
                .selectRequestTypeDDL(dataModel().ContactUs.requestTypeDDL)
                .enterContactMessage(dataModel().ContactUs.message)
                .clickSubmitButton();

        openAdmin();
        basePage = AuthHelper.login(driver);

        contactUsPage = basePage.openContacts();
        contactUsPage.clickResult();


//        Assertions.myAssertTrue(
//                contactUsPage.isContactDetailsDisplayed(contactUsPage.contactDetailsName && dataModel().ContactUs.name,
//                "Product is not displayed on website");

//        contactUsWebPage
//                .searchInputs(dataModel().Subscribes.email)
//                .clickSearchResult();
//
//        Assertions.myAssertEquals(
//                contactUsWebPage.getActualEmail(),dataModel().Subscribes.email,"Actual email is not displayed");
//
//        contactUsWebPage
//                .deleteSubscriber();
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
    }
}


