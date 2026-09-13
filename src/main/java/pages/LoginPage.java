package pages.admin;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.ElementActions;

public class LoginPage extends BasePage<LoginPage> {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By logo = By.cssSelector("img[src='/assets/logo_light.D0guCldj.svg'][class*='v-img__img']");
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
