package tests;

import base.BaseTests;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;
import pages.website.HomePage;
import utilities.Waits;
import utils.Assertions;

public class HomeTests extends BaseTests {

    private HomePage homePage;

    @Test(priority = 1)
    public void verifyHeaderNavigationLinks() {

        openWebsite();
        homePage = new HomePage(driver);

        // Header Home
        homePage.clickHeaderHomeLink();

        Assertions.myAssertTrue(
                homePage.getCurrentUrl().contains("/"),
                "Header Home URL is incorrect");

        // Header Solutions and Services
        homePage.clickHeaderSolutionsAndServicesLink();

        Assertions.myAssertTrue(
                homePage.getCurrentUrl().contains("/services-and-solutions"),
                "Header Solutions and Services URL is incorrect");

        // Header About Us
        homePage.clickHeaderAboutUsLink();

        Assertions.myAssertTrue(
                homePage.getCurrentUrl().contains("/about-us"),
                "Header About Us URL is incorrect");

        // Header Use Case
        homePage.clickHeaderUseCaseLink();

        Assertions.myAssertTrue(
                homePage.getCurrentUrl().contains("/use-case"),
                "Header Use Case URL is incorrect");


        // Header Blogs
        homePage.clickHeaderBlogsLink();

        Assertions.myAssertTrue(
                homePage.getCurrentUrl().contains("/blogs"),
                "Header Blogs URL is incorrect");
//
//        openWebsite();
//
//        // Header Careers
//        homePage.clickHeaderCareersLink();
//
        String homeTab = homePage.switchToCareersTab();

//        Assertions.myAssertTrue(
//                homePage.waitUntilUrlContains("career.wakeb.tech/jobs"),
//                "Header Careers URL is incorrect");

        // Close Careers tab and return to Home tab
        driver.close();
        driver.switchTo().window(homeTab);

        Assertions.myAssertTrue(
                homePage.getCurrentUrl().contains("staging.wakeb.tech"),
                "Failed to return to Home tab"
        );
    }

    @Test(priority = 2)
    public void verifyFooterBrandSection() {

        openWebsite();
        homePage = new HomePage(driver);

        homePage.scrollToFooter();

        Assertions.myAssertTrue(
                homePage.isFooterLogoDisplayed(),
                "Footer logo is not displayed");

        Assertions.myAssertTrue(
                homePage.isFooterDescriptionDisplayed(),
                "Footer description is not displayed");
    }

    @Test(priority = 3)
    public void verifyFooterCompanySectionLinks() {

        openWebsite();
        homePage = new HomePage(driver);

        homePage.scrollToFooter();

        Assertions.myAssertTrue(
                homePage.isCompanySectionTitleDisplayed(),
                "Company section title is not displayed");

        // Footer Home
        Assertions.myAssertTrue(
                homePage.isHomeLinkDisplayed(),
                "Footer Home link is not displayed");

        homePage.clickHomeLink();

        Assertions.myAssertTrue(
                homePage.getCurrentUrl().contains("/"),
                "Footer Home URL is incorrect");

        openWebsite();
        homePage.scrollToFooter();

        // Footer Careers
        Assertions.myAssertTrue(
                homePage.isCareersLinkDisplayed(),
                "Footer Careers link is not displayed");

//        homePage.clickCareersLink();
//
//        Assertions.myAssertTrue(
//                homePage.getCurrentUrl().contains("/careers"),
//                "Footer Careers URL is incorrect");
//
        openWebsite();
        homePage.scrollToFooter();


        // Footer Services
        Assertions.myAssertTrue(
                homePage.isServicesLinkDisplayed(),
                "Footer Services link is not displayed");

        homePage.clickServicesLink();

        Assertions.myAssertTrue(
        homePage.getCurrentUrl().contains("/services-and-solutions"),
                "Footer Services URL is incorrect");

        openWebsite();
        homePage.scrollToFooter();

        // Footer About Us
        Assertions.myAssertTrue(
                homePage.isAboutUsLinkDisplayed(),
                "Footer About Us link is not displayed");

        homePage.clickAboutUsLink();

        Assertions.myAssertTrue(
                homePage.getCurrentUrl().contains("/about-us"),
                "Footer About Us URL is incorrect");

        openWebsite();
        homePage.scrollToFooter();

        // Footer Blogs
        Assertions.myAssertTrue(
                homePage.isBlogsLinkDisplayed(),
                "Footer Blogs link is not displayed");

        homePage.clickBlogsLink();

        Assertions.myAssertTrue(
                homePage.getCurrentUrl().contains("/blogs"),
                "Footer Blogs URL is incorrect");

        openWebsite();
        homePage.scrollToFooter();

        // Footer Contact Us
        Assertions.myAssertTrue(
                homePage.isContactUsLinkDisplayed(),
                "Footer Contact Us link is not displayed");

        homePage.clickContactUsLink();

        Assertions.myAssertTrue(
                homePage.getCurrentUrl().contains("/contact-us"),
                "Footer Contact Us URL is incorrect");

        openWebsite();
        homePage.scrollToFooter();

        // Footer Become a Partner
        Assertions.myAssertTrue(
                homePage.isBecomePartnerLinkDisplayed(),
                "Footer Become a Partner link is not displayed");

//        homePage.clickBecomePartnerLink();
//
//        Assertions.myAssertTrue(
//                homePage.getCurrentUrl().contains("/become-partner"),
//                "Footer Become a Partner URL is incorrect");

        openWebsite();
    }

    @Test(priority = 4)
    public void verifyFooterSolutionsSectionLinks() {

        openWebsite();
        homePage = new HomePage(driver);

        homePage.scrollToFooter();

        Assertions.myAssertTrue(
                homePage.isSolutionsSectionTitleDisplayed(),
                "Solutions section title is not displayed");

        Assertions.myAssertTrue(
                homePage.isCloudServiceLinkDisplayed(),
                "Cloud Service link is not displayed");

        Assertions.myAssertTrue(
                homePage.isCustomizedServicesLinkDisplayed(),
                "Customized Services link is not displayed");

        Assertions.myAssertTrue(
                homePage.isDigitalInfrastructureLinkDisplayed(),
                "Digital Infrastructure link is not displayed");

        Assertions.myAssertTrue(
                homePage.isWebSolutionsLinkDisplayed(),
                "Web Solutions link is not displayed");

        Assertions.myAssertTrue(
                homePage.isUavsLinkDisplayed(),
                "UAVs link is not displayed");

        Assertions.myAssertTrue(
                homePage.isDataServiceLinkDisplayed(),
                "Data Service link is not displayed");

        Assertions.myAssertTrue(
                homePage.isImmersiveTechnologyLinkDisplayed(),
                "Immersive Technology link is not displayed");

        Assertions.myAssertTrue(
                homePage.isGisLinkDisplayed(),
                "GIS link is not displayed");

        Assertions.myAssertTrue(
                homePage.isSimulationLinkDisplayed(),
                "Simulation link is not displayed");

        Assertions.myAssertTrue(
                homePage.isCommandAndControlLinkDisplayed(),
                "Command and Control link is not displayed");

        Assertions.myAssertTrue(
                homePage.isOthersLinkDisplayed(),
                "Others link is not displayed");

        Assertions.myAssertTrue(
                homePage.isArtificialIntelligenceLinkDisplayed(),
                "Artificial Intelligence link is not displayed");
    }

    @Test(priority = 5)
    public void verifyFooterContactSectionData() {

        openWebsite();
        homePage = new HomePage(driver);

        homePage.scrollToFooter();

        Assertions.myAssertTrue(
                homePage.isContactSectionTitleDisplayed(),
                "Contact section title is not displayed");

        Assertions.myAssertTrue(
                homePage.isAddressDisplayed(),
                "Address is not displayed");

        Assertions.myAssertTrue(
                homePage.isPhoneNumberOneDisplayed(),
                "First phone number is not displayed");

        Assertions.myAssertTrue(
                homePage.isPhoneNumberTwoDisplayed(),
                "Second phone number is not displayed");

        Assertions.myAssertTrue(
                homePage.isEmailAddressDisplayed(),
                "Email address is not displayed");
    }

    @Test(priority = 6)
    public void verifyFooterSocialMediaIcons() {

        openWebsite();
        homePage = new HomePage(driver);

        homePage.scrollToFooter();

        Assertions.myAssertTrue(
                homePage.isFacebookIconDisplayed(),
                "Facebook icon is not displayed");

        Assertions.myAssertTrue(
                homePage.isTwitterIconDisplayed(),
                "Twitter/X icon is not displayed");

        Assertions.myAssertTrue(
                homePage.isInstagramIconDisplayed(),
                "Instagram icon is not displayed");

        Assertions.myAssertTrue(
                homePage.isYoutubeIconDisplayed(),
                "YouTube icon is not displayed");

        Assertions.myAssertTrue(
                homePage.isLinkedinIconDisplayed(),
                "LinkedIn icon is not displayed");
    }
}