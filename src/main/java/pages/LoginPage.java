package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage<LoginPage> {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By logo = By.cssSelector("[class='mb-3'][src='/images/svg/logo_lg.svg']");
    private final By loginEmail = By.cssSelector("input[id*='input'][type='text']");
    private final By password = By.cssSelector("input[id*='input'][type='password']");
    private final By loginButton = By.cssSelector("button[type='submit'][class*='v-btn']");

    // Fluent setters — each returns `this` so calls can be chained
    @Step("Enter Email")
    public LoginPage enterEmail(String mail) {
        sendKeys(loginEmail, mail);
        return this;
    }

    @Step("Enter Password")
    public LoginPage enterPassword(String passwordText) {
        sendKeys(password, passwordText);
        return this;
    }

    @Step("Click Login Button")
    public BasePage clickLoginButton() {
        click(loginButton);
        return new BasePage(driver);
    }

    @Step("Verify Logo")
    public boolean isLogoDisplayed() {
        return isElementPresent(logo);
    }

}
