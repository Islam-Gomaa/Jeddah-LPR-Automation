package pages.website;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.admin.BasePage;
import utilities.Waits;

import java.time.Duration;


public class HomePage extends BasePage<HomePage> {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private By clientLogo(String logoSrc) {
        return By.cssSelector("[aria-label='Our Clients'] img[src*='" + logoSrc + "']");
    }
    private By partnerLogo(String logoSrc) {
        return By.cssSelector(".position-relative [class='v-card-text py-0'] img[src*='" + logoSrc + "']");
    }

    // Contact us
    private final By contactUsBtn = By.xpath("//div[contains(@class,'contact-us-container')] //a[@href='/contact-us'][.='Contact Us']");

    // Locators of subscribes
    private final By subscribeIcon  = By.xpath("//span[@class='v-btn__content']//span[.='Subscribe']");
    private final By subscribeBtn = By.cssSelector(".v-footer .btn-container button[type='submit']");
    private final By subscribeInput = By.cssSelector(".v-footer input[placeholder='Enter your email address']");
    private final By subscribeTitle = By.cssSelector(".v-footer .v-container .w-full h2");
    private final By subscribeDescription = By.cssSelector(".v-footer .v-container .w-full span[class*='text']");
    private final By subscribedSuccessfullyMessage = By.cssSelector("span[class=snackbar-message]");


    // Header Tabs Locators
    private final By homePageTitle = By.xpath("//div[contains(@class,'header-container')]//a[contains(@title,'Home')]");
    private final By solutionsAndServicesPageTitle = By.xpath("//div[contains(@class,'header-container')]//a[contains(@title,'Solutions and Services')]");
    private final By aboutUsPageTitle = By.xpath("//div[contains(@class,'header-container')]//a[contains(@title,'About Us')]");
    private final By useCasePageTitle = By.xpath("//div[contains(@class,'header-container')]//a[contains(@title,'Use Case')]");
    private final By blogsPageTitle = By.xpath("//div[contains(@class,'header-container')]//a[contains(@title,'Blogs')]");
    private final By careersPageTitle = By.xpath("//div[contains(@class,'header-container')]//a[contains(@title,'Careers')]");

    // Footer

    // Company Section Locators
    private final By companySectionTitle = By.xpath("//div[contains(@class,'v-row')]//h2[normalize-space()='Company']");
    private final By homeLink = By.xpath("//div[contains(@class,'v-row')]//a[contains(@title,'Home')]");
    private final By careersLink = By.xpath("//div[contains(@class,'v-row')]//a[contains(@title,'Careers')]");
    private final By servicesLink = By.xpath("//div[contains(@class,'v-row')]//a[contains(@title,'Services')]");
    private final By aboutUsLink = By.xpath("//div[contains(@class,'v-row')]//a[contains(@title,'About Us')]");
    private final By blogsLink = By.xpath("//div[contains(@class,'v-row')]//a[contains(@title,'Blogs')]");
    private final By contactUsLink = By.xpath("//div[contains(@class,'v-row')]//a[contains(@title,'Contact Us')]");
    private final By becomePartnerLink = By.xpath("//div[contains(@class,'v-row')]//a[contains(@title,'Become a partner')]");

    // Solutions Section Locators
    private final By solutionsSectionTitle = By.xpath("//div[contains(@class,'v-row')]//h2[normalize-space()='Solutions']");
    private final By cloudServiceLink = By.xpath("//div[contains(@class,'v-row')]//a[normalize-space()='Cloud Service']");
    private final By customizedServicesLink = By.xpath("//div[contains(@class,'v-row')]//a[normalize-space()='Customized Services']");
    private final By digitalInfrastructureLink = By.xpath("//div[contains(@class,'v-row')]//a[normalize-space()='Digital Infrastructure']");
    private final By webSolutionsLink = By.xpath("//div[contains(@class,'v-row')]//a[normalize-space()='Web Solutions']");
    private final By uavsLink = By.xpath("//div[contains(@class,'v-row')]//a[contains(.,'UAV')]");
    private final By dataServiceLink = By.xpath("//div[contains(@class,'v-row')]//a[normalize-space()='Data Service']");
    private final By immersiveTechnologyLink = By.xpath("//div[contains(@class,'v-row')]//a[normalize-space()='Immersive Technology']");
    private final By gisLink = By.xpath("//div[contains(@class,'v-row')]//a[normalize-space()='GIS']");
    private final By simulationLink = By.xpath("//div[contains(@class,'v-row')]//a[normalize-space()='Simulation']");
    private final By commandAndControlLink = By.xpath("//div[contains(@class,'v-row')]//a[contains(.,'Command and Control')]");
    private final By othersLink = By.xpath("//div[contains(@class,'v-row')]//a[normalize-space()='Others']");
    private final By artificialIntelligenceLink = By.xpath("//div[contains(@class,'v-row')]//a[contains(normalize-space(),'Artificial Intelligence')]");

    // Contact Section Locators
    private final By contactSectionTitle = By.xpath("//div[contains(@class,'v-row')]//h2[normalize-space()='Contact']");
    private final By addressText = By.xpath("//div[contains(@class,'v-row')]//*[contains(text(),'King Abdulaziz')]");
    private final By phoneNumberOne = By.xpath("//div[contains(@class,'v-row')]//*[contains(text(),'+966 9200 35445')]");
    private final By phoneNumberTwo = By.xpath("//*[contains(text(),'+966 53 596 1010')]");
    private final By emailAddress = By.xpath("//*[contains(text(),'Info@wakeb.tech')]");

    // Social Media Locators
    private final By facebookIcon = By.xpath("//div[contains(@class,'v-row')]//a[contains(@href,'facebook')]");
    private final By twitterIcon = By.xpath("//div[contains(@class,'v-row')]//a[contains(@href,'twitter') or contains(@href,'x.com')]");
    private final By instagramIcon = By.xpath("//div[contains(@class,'v-row')]//a[contains(@href,'instagram')]");
    private final By youtubeIcon = By.xpath("//div[contains(@class,'v-row')]//a[contains(@href,'youtube')]");
    private final By linkedinIcon = By.xpath("//div[contains(@class,'v-row')]//a[contains(@href,'linkedin')]");

    // Footer Left Brand Section
    private final By footerLogo = By.cssSelector("a[aria-label='Go to homepage'] svg[class='nuxt-icon logo d-flex mb-0']");
    private final By footerDescription = By.cssSelector("[class*='v-col'] p[class='description']");

    // Advertisements
    private final By closeIcon = By.cssSelector("div[class='advertisment']  button[type='button']");




    // Fluent setters — each returns `this` so calls can be chained
    @Step("Open ContactUs Website Page")
    public ContactUsWebPage openContactUsWebsite() {
        click(contactUsBtn);
        return new ContactUsWebPage(driver);
    }

    // Logo appeared
    @Step("Verify logo appeared")
    public boolean isImageDisplayed(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            return wait.until(d -> !d.findElements(locator).isEmpty());
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Verify client logo appeared")
    public boolean isClientLogoDisplayed(String logoSrc) {
        return isImageDisplayed(clientLogo(logoSrc));
    }

    @Step("Verify partner logo appeared")
    public boolean isPartnerImageDisplayed(String logoSrc) {
        return isImageDisplayed(partnerLogo(logoSrc));
    }

    // Logo not appeared
    @Step("Verify image not appeared")
    public boolean isImageNotDisplayed(By locator) {
        return driver.findElements(locator).isEmpty();
    }

    @Step("Verify client logo not appeared")
    public boolean isClientLogoNotDisplayed(String logoSrc) {
        return isImageNotDisplayed(clientLogo(logoSrc));
    }

    @Step("Verify partner logo not appeared")
    public boolean isPartnerLogoNotDisplayed(String logoSrc) {
        return isImageNotDisplayed(partnerLogo(logoSrc));
    }

    @Step("Verify Subscribe Icon of Button appeared")
    public boolean isSubscribeIconOfButtonDisplayed() {
        return getElement(subscribeIcon).isDisplayed();
    }

    @Step("Verify Subscribe to the Newsletter in footer appeared")
    public boolean isSubscribeTitleDisplayed() {
        return getElement(subscribeTitle).isDisplayed();
    }

    @Step("Verify subscribe Description in footer appeared")
    public boolean isSubscribeDescriptionDisplayed() {
        return getElement(subscribeDescription).isDisplayed();
    }

    @Step("Verify subscribe Description in footer appeared")
    public boolean isSubscribedSuccessfullyMessageDisplayed() {
        return getElement(subscribedSuccessfullyMessage).isDisplayed();
    }

    @Step("Enter Email Address for subscribers ")
    public HomePage enterSubscribeEmail(String name) {
        sendKeys(subscribeInput,name);
        return this;
    }

    @Step("Click subscribe Button ")
    public HomePage clickSubscribeBtn() {
        click(subscribeBtn);
        return this;
    }


// Header Navigation Click Methods

    @Step("Click Header Home link")
    public HomePage clickHeaderHomeLink() {
        click(homePageTitle);
        Waits.waitUntilUrlContains(driver, "/");
        return this;
    }

    @Step("Click Header Solutions and Services link")
    public HomePage clickHeaderSolutionsAndServicesLink() {
        click(solutionsAndServicesPageTitle);
        Waits.waitUntilUrlContains(driver, "/services-and-solutions");
        return this;
    }

    @Step("Click Header About Us link")
    public HomePage clickHeaderAboutUsLink() {
        click(aboutUsPageTitle);
        Waits.waitUntilUrlContains(driver, "/about-us");
        return this;
    }

    @Step("Click Header Use Case link")
    public HomePage clickHeaderUseCaseLink() {
        click(useCasePageTitle);
        Waits.waitUntilUrlContains(driver, "/use-cases");
        return this;
    }

    @Step("Click Header Blogs link")
    public HomePage clickHeaderBlogsLink() {
        click(blogsPageTitle);
        Waits.waitUntilUrlContains(driver, "/blogs");
        return this;
    }

    @Step("Click Header Careers link")
    public HomePage clickHeaderCareersLink() {
        click(careersPageTitle);
        Waits.waitUntilUrlContains(driver, "/careers");
        return this;
    }

// Footer Company Section Display Methods


    @Step("Verify Company section title appeared")
    public boolean isCompanySectionTitleDisplayed() {
        return isDisplayed(companySectionTitle);
    }

    @Step("Verify Footer Home link appeared")
    public boolean isHomeLinkDisplayed() {
        return isDisplayed(homeLink);
    }

    @Step("Verify Footer Careers link appeared")
    public boolean isCareersLinkDisplayed() {
        return isDisplayed(careersLink);
    }

    @Step("Verify Footer Services link appeared")
    public boolean isServicesLinkDisplayed() {
        return isDisplayed(servicesLink);
    }

    @Step("Verify Footer About Us link appeared")
    public boolean isAboutUsLinkDisplayed() {
        return isDisplayed(aboutUsLink);
    }

    @Step("Verify Footer Blogs link appeared")
    public boolean isBlogsLinkDisplayed() {
        return isDisplayed(blogsLink);
    }

    @Step("Verify Footer Contact Us link appeared")
    public boolean isContactUsLinkDisplayed() {
        return isDisplayed(contactUsLink);
    }

    @Step("Verify Footer Become a Partner link appeared")
    public boolean isBecomePartnerLinkDisplayed() {
        return isDisplayed(becomePartnerLink);
    }



// Footer Solutions Section Display Methods


    @Step("Verify Solutions section title appeared")
    public boolean isSolutionsSectionTitleDisplayed() {
        return isDisplayed(solutionsSectionTitle);
    }

    @Step("Verify Cloud Service link appeared")
    public boolean isCloudServiceLinkDisplayed() {
        return isDisplayed(cloudServiceLink);
    }

    @Step("Verify Customized Services link appeared")
    public boolean isCustomizedServicesLinkDisplayed() {
        return isDisplayed(customizedServicesLink);
    }

    @Step("Verify Digital Infrastructure link appeared")
    public boolean isDigitalInfrastructureLinkDisplayed() {
        return isDisplayed(digitalInfrastructureLink);
    }

    @Step("Verify Web Solutions link appeared")
    public boolean isWebSolutionsLinkDisplayed() {
        return isDisplayed(webSolutionsLink);
    }

    @Step("Verify UAVs link appeared")
    public boolean isUavsLinkDisplayed() {
        return isDisplayed(uavsLink);
    }

    @Step("Verify Data Service link appeared")
    public boolean isDataServiceLinkDisplayed() {
        return isDisplayed(dataServiceLink);
    }

    @Step("Verify Immersive Technology link appeared")
    public boolean isImmersiveTechnologyLinkDisplayed() {
        return isDisplayed(immersiveTechnologyLink);
    }

    @Step("Verify GIS link appeared")
    public boolean isGisLinkDisplayed() {
        return isDisplayed(gisLink);
    }

    @Step("Verify Simulation link appeared")
    public boolean isSimulationLinkDisplayed() {
        return isDisplayed(simulationLink);
    }

    @Step("Verify Command and Control link appeared")
    public boolean isCommandAndControlLinkDisplayed() {
        return isDisplayed(commandAndControlLink);
    }

    @Step("Verify Others link appeared")
    public boolean isOthersLinkDisplayed() {
        return isDisplayed(othersLink);
    }

    @Step("Verify Artificial Intelligence link appeared")
    public boolean isArtificialIntelligenceLinkDisplayed() {
        return isDisplayed(artificialIntelligenceLink);
    }


// Additional Solutions Click Methods

    @Step("Click Others link")
    public HomePage clickOthersLink() {
        click(othersLink);
        return this;
    }

    @Step("Click Artificial Intelligence link")
    public HomePage clickArtificialIntelligenceLink() {
        click(artificialIntelligenceLink);
        return this;
    }


// Footer Contact Section Display Methods


    @Step("Verify Contact section title appeared")
    public boolean isContactSectionTitleDisplayed() {
        return getElement(contactSectionTitle).isDisplayed();
    }

    @Step("Verify Address appeared")
    public boolean isAddressDisplayed() {
        return isDisplayed(addressText);
    }

    @Step("Verify First phone number appeared")
    public boolean isPhoneNumberOneDisplayed() {
        return isDisplayed(phoneNumberOne);
    }

    @Step("Verify Second phone number appeared")
    public boolean isPhoneNumberTwoDisplayed() {
        return isDisplayed(phoneNumberTwo);
    }

    @Step("Verify Email address appeared")
    public boolean isEmailAddressDisplayed() {
        return isDisplayed(emailAddress);
    }


// Social Media Display Methods


    @Step("Verify Facebook icon appeared")
    public boolean isFacebookIconDisplayed() {
        return isDisplayed(facebookIcon);
    }

    @Step("Verify Twitter/X icon appeared")
    public boolean isTwitterIconDisplayed() {
        return isDisplayed(twitterIcon);
    }

    @Step("Verify Instagram icon appeared")
    public boolean isInstagramIconDisplayed() {
        return isDisplayed(instagramIcon);
    }

    @Step("Verify YouTube icon appeared")
    public boolean isYoutubeIconDisplayed() {
        return isDisplayed(youtubeIcon);
    }

    @Step("Verify LinkedIn icon appeared")
    public boolean isLinkedinIconDisplayed() {
        return isDisplayed(linkedinIcon);
    }


// Footer Brand Section Display Methods

    @Step("Verify footer logo appeared")
    public boolean isFooterLogoDisplayed() {
        return isDisplayed(footerLogo);
    }

    @Step("Verify footer description appeared")
    public boolean isFooterDescriptionDisplayed() {
        return isDisplayed(footerDescription);
    }


    // Footer Navigation Click Methods


    @Step("Click Home link")
    public HomePage clickHomeLink() {
        click(homeLink);
        Waits.waitUntilUrlContains(driver, "/");
        return this;
    }

    @Step("Click Careers link")
    public HomePage clickCareersLink() {
        click(careersLink);
        Waits.waitUntilUrlContains(driver, "/careers");
        return this;
    }

    @Step("Click Services link")
    public HomePage clickServicesLink() {
        click(servicesLink);
        Waits.waitUntilUrlContains(driver, "/services-and-solutions");
        return this;
    }

    @Step("Click About Us link")
    public HomePage clickAboutUsLink() {
        click(aboutUsLink);
        Waits.waitUntilUrlContains(driver, "/about-us");
        return this;
    }

    @Step("Click Blogs link")
    public HomePage clickBlogsLink() {
        click(blogsLink);
        Waits.waitUntilUrlContains(driver, "/blogs");
        return this;
    }

    @Step("Click Contact Us link")
    public HomePage clickContactUsLink() {
        click(contactUsLink);
        Waits.waitUntilUrlContains(driver, "/contact-us");
        return this;
    }

    @Step("Click Become A Partner link")
    public HomePage clickBecomePartnerLink() {
        click(becomePartnerLink);
        Waits.waitUntilUrlContains(driver, "/become-partner");
        return this;
    }


// Solutions Navigation Click Methods


    @Step("Click Cloud Service link")
    public HomePage clickCloudServiceLink() {
        click(cloudServiceLink);
        return this;
    }

    @Step("Click Customized Services link")
    public HomePage clickCustomizedServicesLink() {
        click(customizedServicesLink);
        return this;
    }

    @Step("Click Digital Infrastructure link")
    public HomePage clickDigitalInfrastructureLink() {
        click(digitalInfrastructureLink);
        return this;
    }

    @Step("Click Web Solutions link")
    public HomePage clickWebSolutionsLink() {
        click(webSolutionsLink);
        return this;
    }

    @Step("Click UAVs link")
    public HomePage clickUavsLink() {
        click(uavsLink);
        return this;
    }

    @Step("Click Data Service link")
    public HomePage clickDataServiceLink() {
        click(dataServiceLink);
        return this;
    }

    @Step("Click Immersive Technology link")
    public HomePage clickImmersiveTechnologyLink() {
        click(immersiveTechnologyLink);
        return this;
    }

    @Step("Click GIS link")
    public HomePage clickGisLink() {
        click(gisLink);
        return this;
    }

    @Step("Click Simulation link")
    public HomePage clickSimulationLink() {
        click(simulationLink);
        return this;
    }

    @Step("Click Command And Control link")
    public HomePage clickCommandAndControlLink() {
        click(commandAndControlLink);
        return this;
    }


// Social Media Get Attribute Methods

    @Step("Get Facebook link URL")
    public String getFacebookHref() {
        return getElement(facebookIcon).getAttribute("href");
    }

    @Step("Get Twitter/X link URL")
    public String getTwitterHref() {
        return getElement(twitterIcon).getAttribute("href");
    }

    @Step("Get Instagram link URL")
    public String getInstagramHref() {
        return getElement(instagramIcon).getAttribute("href");
    }

    @Step("Get YouTube link URL")
    public String getYoutubeHref() {
        return getElement(youtubeIcon).getAttribute("href");
    }

    @Step("Get LinkedIn link URL")
    public String getLinkedInHref() {
        return getElement(linkedinIcon).getAttribute("href");
    }



// =============================
// Common Helpers
// =============================

    @Step("Get current URL")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Get current page title")
    public String getPageTitle() {
        return driver.getTitle();
    }

    @Step("Switch to Careers tab")
    public String switchToCareersTab() {
        return switchToNewTab();
    }

    //  Advertisements

    @Step("Verify Advertisement image appeared")
    public boolean isAdvertisementImgDisplayed(String advertisementName) {

        By advertisementImg =
                By.cssSelector("img[alt='" + advertisementName + "']");

        return isDisplayed(advertisementImg);
    }

    @Step("Click close icon of Advertisement image")
    public HomePage clickCloseIconOfAdvertisementImage() {
        click(closeIcon);
        return this;
    }
}
