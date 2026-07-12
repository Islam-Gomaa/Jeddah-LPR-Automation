package pages.admin;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.Waits;

public class ContactUsPage extends BasePage<ContactUsPage> {

    public ContactUsPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By deleteButton = By.cssSelector(".v-card-actions .flex button");
    private final By emailField = By.cssSelector("#myForm .v-row .field[value*='.com']");
    private final By dataTableSearchResult = By.xpath("//tbody[@class='v-data-table__tbody']/tr[1]/td[2]");
    public final By contactDetailsName = By.xpath("(//div[contains(@class,'viewMode')])[1]");
    private final By contactDetailsEmail = By.xpath("(//div[contains(@class,'viewMode')])[1]");
    private final By contactDetailsSubject = By.xpath("(//div[contains(@class,'viewMode')])[1]");
    private final By contactDetailsMessage = By.xpath("(//div[contains(@class,'viewMode')])[1]");
    private final By contactDetailsPhoneNumber = By.xpath("(//div[contains(@class,'viewMode')])[1]");
    private final By contactDetailsType = By.xpath("(//div[contains(@class,'viewMode')])[1]");
    private final By contactDetailsSubType = By.xpath("(//div[contains(@class,'viewMode')])[1]");

    // Fluent setters —
    @Step("Delete Subscriber user")
    public ContactUsPage deleteSubscriber() {
        click(deleteButton);
        return this;
    }

    @Step("Get Actual Email ")
    public String getActualEmail() {
        return getAttribute(emailField, "value");
    }


    @Step("Click on search result")
    public ContactUsPage clickResult() {
        Waits.waitForClickable(driver, dataTableSearchResult).click();
        return this;
    }

    @Step("Verify Contact Us data is appeared")
    public boolean isContactDetailsDisplayed(By locator) {

        return getElement(locator).isDisplayed();
    }

}

