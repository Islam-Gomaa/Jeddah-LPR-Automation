package pages.admin;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utilities.ElementActions;

public class AdvertisementsPage extends BasePage<AdvertisementsPage> {

    public AdvertisementsPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By nameArabic = By.cssSelector("input[placeholder='Enter Name (Arabic)']");
    private final By nameEnglish = By.cssSelector("input[placeholder='Enter Name (English)']");
    private final By descriptionArabic = By.cssSelector("textarea[class='v-field__input'][placeholder='Enter Description (Arabic)']");
    private final By descriptionEnglish = By.cssSelector("textarea[class='v-field__input'][placeholder='Enter Description (English)']");
    private final By image = By.cssSelector("[type='imageUploader'] input[type='file']");
    private final By isActiveCheckBox = By.cssSelector("input[aria-label='Is Active?']");

    public WebElement getActiveToggle(String name) {

        By autoAdvertisementRow =
                By.xpath("//tr[.//td[normalize-space()='" + name + "']]");

        WebElement row = driver.findElement(autoAdvertisementRow);

        return row.findElement(
                By.xpath(".//div[contains(@class,'switch__track')]")
        );
    }
    // Fluent setters — each returns `this` so calls can be chained

    @Step("Enter Arabic Title")
    public AdvertisementsPage enterArabicName(String name) {
        getElement(nameArabic)
                .sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.DELETE);
        sendKeys(nameArabic,name);
        return this;
    }

    @Step("Enter English Title")
    public AdvertisementsPage enterEnglishName(String name) {
        sendKeys(nameEnglish,name);
        return this;
    }
    @Step("Enter Arabic description")
    public AdvertisementsPage enterArabicDescription(String name) {
        sendKeys(descriptionArabic,name);
        return this;
    }

    @Step("Enter English description")
    public AdvertisementsPage enterEnglishDescription(String name) {
        sendKeys(descriptionEnglish,name);
        return this;
    }

    @Step("Upload Icon")
    public AdvertisementsPage uploadImage(String filePath) {
        uploadFile(image, filePath);
        return this;
    }

    @Step("Click IsActive")
    public AdvertisementsPage clickIsActive() {
        jsClick(isActiveCheckBox);
        return this;
    }

}
