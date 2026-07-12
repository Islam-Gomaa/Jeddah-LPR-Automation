package pages.admin;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UsersPage extends BasePage<UsersPage> {

    public UsersPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By addUserBtn = By.cssSelector("#teleported-items .flex .v-btn");
    private final By userName = By.cssSelector("input[placeholder='Enter Name']");
    private final By phoneCode = By.cssSelector("input[placeholder='Select Phone Code']");
    private final By phoneNumber = By.cssSelector("input[placeholder='Select Phone Number']");
    private final By userEmail = By.cssSelector("input[placeholder='Enter Email']");
    private final By gender = By.xpath("//input[@placeholder='Select Gender']/ancestor::div[@class='v-input__control']/ancestor::div[contains(@class,'v-input')]");
    private final By roles = By.xpath("//input[@placeholder='Select Roles']/ancestor::div[@class='v-input__control']/ancestor::div[contains(@class,'v-input')]");
    private final By password = By.cssSelector("input[placeholder='Enter Password']");
    private final By confirmPassword = By.cssSelector("input[placeholder='Enter Confirm Password']");
    private final By cancelBtn = By.xpath("//button[.='Cancel']");
    private final By addBtn = By.cssSelector("button[type='submit']");


    // Fluent setters — each returns `this` so calls can be chained

    @Step("Click Add User Button")
    public UsersPage clickAddUserButton() {
        click(addUserBtn);
        return this;
    }

    @Step("Enter User Name")
    public UsersPage enterName(String name) {
        sendKeys(userName, name);
        return this;
    }

    @Step("Enter Phone Code and Number")
    public UsersPage enterPhoneNumber(String code, String num) {
        click(phoneCode);
        Actions actions = new Actions(driver);
        actions.sendKeys(code).perform();

        WebElement option = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class,'v-overlay--active')]//*[contains(.,'" + code + "')]")
                ));
        option.click();
        sendKeys(phoneNumber, num);
        return this;
    }

    @Step("Select Role")
    public UsersPage selectRole(String role) {
        click(roles);
        WebElement option = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class,'v-overlay--active')]//*[normalize-space()='" + role + "']")
                ));
        option.click();
        return this;
    }

    @Step("Enter User Email")
    public UsersPage enterEmail(String mail) {
        sendKeys(userEmail, mail);
        return this;
    }

    @Step("Select Gender")
    public UsersPage selectGender(String value) {
        click(gender);
        Actions actions = new Actions(driver);
        actions.sendKeys(value)
                .pause(Duration.ofMillis(500))
                .sendKeys(Keys.ESCAPE)
                .perform();
        return this;
    }

    @Step("Enter Password")
    public UsersPage enterPassword(String passwordTxt) {
        sendKeys(password, passwordTxt);
        return this;
    }

    @Step("Enter Confirm Password")
    public UsersPage enterConfirmPassword(String confirmPasswordTxt) {
        sendKeys(confirmPassword, confirmPasswordTxt);
        return this;
    }

    @Step("Click Add Button")
    public UsersPage clickAddButton() {
        click(addBtn);
        return this;
    }

    @Step("Click Cancel Button")
    public UsersPage clickCancelButton() {
        click(cancelBtn);
        return this;
    }


//    @Step("Verify Logo")
//    public boolean isLogoDisplayed() {
//        return isElementPresent(logo);
//    }

}
