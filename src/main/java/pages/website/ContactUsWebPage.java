package pages.website;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.admin.BasePage;
import utilities.Waits;

import java.time.Duration;
import java.util.List;

public class ContactUsWebPage extends BasePage<ContactUsWebPage> {

    public ContactUsWebPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By contactUsPageTitle = By.cssSelector(".content-header h1[class*='page-title']");
    private final By contactUsPageDescription = By.cssSelector(".content-header p[class*='page-description']");
    private final By nameInput = By.xpath("//input[@autocomplete='name' and contains(@class,'v-field__input')]");
    private final By emailInput = By.xpath("//input[@autocomplete='email' and contains(@class,'v-field__input')]");
    private final By phoneInput = By.cssSelector("input[autocomplete='tel']");
    private final By requestDDL = By.xpath("(//div[@aria-haspopup='listbox'] //div[@class='v-field__input'])[1]");
    private final By requestTypeDDL = By.xpath("(//div[@aria-haspopup='listbox'] //div[@class='v-field__input'])[2]");
    private final By subjectInput = By.xpath("(//div[contains(@class,'v-row')] //input[@type='text'][@class='v-field__input'])[2]");
    private final By messageTextarea = By.cssSelector(".v-row textarea[class='v-field__input']");
    private final By submitBtn = By.cssSelector(".v-col button[type='submit']");
    private final By phoneTitle = By.xpath("//div[contains(@class,'v-container')] //p[.='Phone']");
    private final By phoneNum = By.xpath("//div[contains(@class,'v-container')]  //a[contains(@title,'(+966)')]");
    private final By mailTitle = By.xpath("//div[contains(@class,'v-container')] //p[.='Email']");
    private final By mailContent = By.xpath("//div[contains(@class,'v-container')]  //a[contains(@title,'@')]");
    private final By address = By.xpath("//div[contains(@class,'v-container')] //p[.='Address']");
    private final By addressContent = By.xpath("//div[contains(@class,'v-container')]  //a[contains(@title,'Riyadh, Saudi Arabia')]");
    private final By contactHeaderTitle  = By.xpath("//div[contains(@class,'v-container')]  //h3[.//span[text()='Get in Touch']]");
    private final By contactHeaderDescription = By.xpath("//div[contains(@class,'v-container')]  //p[contains(.,'Please fill out the form')]");
    private final By map = By.cssSelector(".maplibregl-map");

    // Fluent setters —

    @Step("Enter Contact Name")
    public ContactUsWebPage enterContactName(String name) {
        clickAndSendKeys(nameInput,name);
        return this;
    }

    @Step("Enter Contact Email")
    public ContactUsWebPage enterContactEmail(String name) {
        clickAndSendKeys(emailInput,name);
        return this;
    }

    @Step("Enter Contact Phone")
    public ContactUsWebPage enterContactPhone(String name) {
        clickAndSendKeys(phoneInput,name);
        return this;
    }


    public void selectFromDDL(
            By dropdownLocator,
            String value
    ) {
        // Open dropdown
        click(dropdownLocator);
        Actions actions = new Actions(driver);

        // Wait for options
        List<WebElement> allOptions =
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(driver ->
                                driver.findElements(
                                        By.xpath("//div[contains(@class,'v-list-item')]")
                                )
                        );
        // Start from first option
        actions.sendKeys(Keys.HOME)
                .perform();

        for (int i = 0; i < allOptions.size(); i++) {

            WebElement currentOption = allOptions.get(i);
            String currentText =
                    currentOption.getDomProperty("innerText").trim();

            if (currentText.equalsIgnoreCase(value.trim())) {

                actions.sendKeys(Keys.ENTER)
                        .perform();
                actions.sendKeys(Keys.ESCAPE)
                        .perform();

                return;
            }
            actions.sendKeys(Keys.ARROW_DOWN).perform();

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException(
                "Value not found in dropdown: " + value);
    }

    @Step("Select Request DDL")
    public ContactUsWebPage selectRequestDDL(
            String value
    ) {
        selectFromDDL(
                requestDDL,
                value
        );
        return this;
    }

    @Step("Select Request Type DDL")
    public ContactUsWebPage selectRequestTypeDDL(
            String value
    ) {
        selectFromDDL(
                requestTypeDDL,
                value
        );
        return this;
    }

    @Step("Enter Contact Subject")
    public ContactUsWebPage enterContactSubject(String name) {
        clickAndSendKeys(subjectInput,name);
        return this;
    }

    @Step("Enter Contact Message")
    public ContactUsWebPage enterContactMessage(String name) {
        clickAndSendKeys(messageTextarea,name);
        return this;
    }

    @Step("Click Contact submit Button")
    public ContactUsWebPage clickSubmitButton() {
        click(submitBtn);
        return this;
    }


}

