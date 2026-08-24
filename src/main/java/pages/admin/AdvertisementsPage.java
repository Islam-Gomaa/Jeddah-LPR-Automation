package pages.admin.DataEntry;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pages.admin.BasePage;

public class CategoryPage extends BasePage<CategoryPage> {

    public CategoryPage (WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By titleArabic = By.cssSelector("input[placeholder='Enter  title in arabic']");
    private final By titleEnglish = By.cssSelector("input[placeholder='Enter title in English']");
    private final By descriptionArabic = By.cssSelector("textarea[class='v-field__input'][placeholder='Enter description in AR']");
    private final By descriptionEnglish = By.cssSelector("textarea[class='v-field__input'][placeholder='Enter description in EN']");
    private final By mainCategoryDDL = By.cssSelector("[type='select'][updatekey='parent.id'] .v-input .v-input__control");
    private final By mainCategoryInput = By.cssSelector("[updatekey='parent.id'] .v-input__control [role='combobox'] .v-field__input");
    private By ddlOption(String value) {
        return By.xpath("//div[normalize-space()='"+ value +"']");
    }
    private final By icon = By.cssSelector("[type='imageUploader'] input[type='file']");
    private final By titleArabicOfSEO = By.cssSelector("input[placeholder='Enter title in arabic']");
    private final By titleEnglishOfSEO = By.cssSelector("input[placeholder='Enter title in english']");
    private final By descriptionSEOArabic = By.cssSelector("input[class='v-field__input'][placeholder='Enter Description (Arabic)']");
    private final By descriptionSEOEnglish = By.cssSelector("input[class='v-field__input'][placeholder='Enter Description (English)']");
    private final By keyWords = By.cssSelector("input[placeholder='Enter keywords']");

    // Fluent setters — each returns `this` so calls can be chained

    @Step("Enter Arabic Title")
    public CategoryPage enterArabicTitle(String name) {
        getElement(titleArabic)
                .sendKeys(Keys.chord(Keys.CONTROL,"a"), Keys.DELETE);
        sendKeys(titleArabic,name);
        return this;
    }

    @Step("Enter English Title")
    public CategoryPage enterEnglishTitle(String name) {
        sendKeys(titleEnglish,name);
        return this;
    }
    @Step("Enter Arabic description")
    public CategoryPage enterArabicDescription(String name) {
        sendKeys(descriptionArabic,name);
        return this;
    }

    @Step("Enter English description")
    public CategoryPage enterEnglishDescription(String name) {
        sendKeys(descriptionEnglish,name);
        return this;
    }

    @Step("Select Main Category DDL")
    public CategoryPage selectMainCategoryDDL(String value) {
        click(mainCategoryDDL);
        sendKeys(ddlOption(value),  Keys.ESCAPE);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    @Step("Upload Icon")
    public CategoryPage uploadIcon(String filePath) {
        uploadFile(icon, filePath);
        return this;
    }

    // SEO section
    @Step("Enter SEO Arabic Title")
    public CategoryPage enterSEOArabicTitle(String name) {
        sendKeys(titleArabicOfSEO,name);
        return this;
    }

    @Step("Enter SEO English Title")
    public CategoryPage enterSEOEnglishTitle(String name) {
        sendKeys(titleEnglishOfSEO,name);
        return this;
    }

    @Step("Enter SEO Arabic description")
    public CategoryPage enterSEOArabicDescription(String name) {
        sendKeys(descriptionSEOArabic,name);
        return this;
    }

    @Step("Enter SEO English description")
    public CategoryPage enterSEOEnglishDescription(String name) {
        sendKeys(descriptionSEOEnglish,name);
        return this;
    }

    @Step("Enter keywords")
    public CategoryPage enterKeyWords(String value) {
            sendKeys(keyWords,  value);
            sendKeys(keyWords,  Keys.ENTER);
        return this;
    }

}
