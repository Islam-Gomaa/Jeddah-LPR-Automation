package pages.admin;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductsPage extends BasePage<ProductsPage> {

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By editButton = By.xpath("//button[.='Edit']");
    private final By saveButton = By.xpath("//button[.='Save']");
    private final By deleteButton = By.xpath("//button[.='Delete']");
    private final By confirmDeleteButton = By.xpath("//button[.='confirm']");
    private final By uploadedImageName = By.cssSelector("[placeholder='Enter image'] .file-name");
    private final By uploadedIconName = By.cssSelector("[placeholder='Enter icon'] .file-name");
    // product
    private final By productTitleArabic = By.xpath("(//input[@placeholder='Enter title in arabic'])[1]");
    private final By productTitleEnglish = By.xpath("(//input[@placeholder='Enter title in english'])[1]");
    private final By categoryDDL = By.cssSelector("[updatekey='category.id'] .v-input__control [aria-haspopup='listbox']");
    private final By descriptionAR = By.cssSelector("textarea[placeholder='Enter Description (Arabic)']");
    private final By descriptionEN = By.cssSelector("textarea[placeholder='Enter Description (English)']");
    private final By imageUploadInput = By.cssSelector("[placeholder='Enter image'] input[type='file']");
    // features
    private final By featuresTitleArabic = By.cssSelector("input[placeholder='Enter  title in arabic']");
    private final By featuresTitleEnglish = By.cssSelector("input[placeholder='Enter title in English']");
    private final By groupDDL = By.cssSelector("[updatekey='group.id'] .v-input__control [aria-haspopup='listbox']");
    private final By iconUploadInput = By.cssSelector("[placeholder='Enter icon'] input[type='file']");
    // SEO
    private final By seoTitleArabic = By.cssSelector(".field-group .content-start input[placeholder='Enter title in arabic']");
    private final By seoTitleEnglish = By.cssSelector(".field-group .content-start input[placeholder='Enter title in english']");
    private final By seoDescriptionArabic = By.cssSelector(".field-group .content-start input[placeholder='Enter Description (Arabic)']");
    private final By seoDescriptionEnglish = By.cssSelector(".field-group .content-start input[placeholder='Enter Description (English)']");
    private final By keywords = By.cssSelector("input[placeholder='Enter keywords']");

    // Fluent setters — each returns `this` so calls can be chained

    @Step("Enter Product Arabic Title")
    public ProductsPage enterProductTitleArabic(String name) {
        sendKeys(productTitleArabic, name);
        return this;
    }

    @Step("Enter Product English Title")
    public ProductsPage enterProductTitleEnglish(String name) {
        sendKeys(productTitleEnglish,name);
        return this;
    }
    @Step("Enter Product Description Arabic")
    public ProductsPage enterProductDescriptionArabic(String name) {
        sendKeys(descriptionAR,name);
        return this;
    }

    @Step("Enter Product Description English")
    public ProductsPage enterProductDescriptionEnglish(String name) {
        sendKeys(descriptionEN,name);
        return this;
    }

    @Step("Select Category DDL")
    public ProductsPage selectCategoryDDL(String value) {
        click(categoryDDL);
        Actions actions = new Actions(driver);
        actions.sendKeys(value)
                .pause(Duration.ofMillis(500))
                .sendKeys(Keys.ESCAPE)
                .perform();
        return this;
    }

    @Step("Upload Image")
    public ProductsPage uploadImage(String filePath) {
        uploadFile(imageUploadInput, filePath);
        return this;
    }

    @Step("Enter Feature Title Arabic")
    public ProductsPage enterFeaturesTitleArabic(String name) {
        sendKeys(featuresTitleArabic,name);
        return this;
    }

    @Step("Enter Feature Title English")
    public ProductsPage enterFeaturesTitleEnglish(String name) {
        sendKeys(featuresTitleEnglish,name);
        return this;
    }

    @Step("Select Group DDL")
    public ProductsPage selectGroupDDL(String value) {
        click(groupDDL);
        Actions actions = new Actions(driver);
        actions.sendKeys(value)
                .pause(Duration.ofMillis(500))
                .sendKeys(Keys.ESCAPE)
                .perform();
        return this;
    }

    @Step("Upload Icon")
    public ProductsPage uploadIcon(String filePath) {
        uploadFile(iconUploadInput, filePath);
        return this;
    }

    @Step("Enter SEO Title Arabic")
    public ProductsPage enterSeoTitleArabic(String name) {
        sendKeys(seoTitleArabic,name);
        return this;
    }

    @Step("Enter SEO Title English")
    public ProductsPage enterSeoTitleEnglish(String name) {
        sendKeys(seoTitleEnglish,name);
        return this;
    }
    @Step("Enter SEO Description Arabic")
    public ProductsPage enterSeoDescriptionArabic(String name) {
        sendKeys(seoDescriptionArabic,name);
        return this;
    }

    @Step("Enter SEO Description English")
    public ProductsPage enterSeoDescriptionEnglish(String name) {
        sendKeys(seoDescriptionEnglish,name);
        return this;
    }

    @Step("Enter Keywords")
    public ProductsPage enterKeywords(String name) {
        sendKeys(keywords,name, Keys.ENTER);
        return this;
    }

    @Step("Click Edit Blog")
    public ProductsPage clickEditProduct() {
        click(editButton);
        return this;
    }

    @Step("Click Save Blog")
    public ProductsPage clickSaveProduct() {
        click(saveButton);
        return this;
    }

    @Step("Click Delete Blog")
    public ProductsPage clickDeleteProduct() {
        click(deleteButton);
        return this;
    }

    @Step("Click Confirm  Delete Blog")
    public ProductsPage clickConfirmDeleteProduct() {
        click(confirmDeleteButton);
        return this;
    }

    @Step("Get Logo Src")
    public String getUploadedLogoSrc(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        return wait.until(driver -> {
            try {
                WebElement el = driver.findElement(locator);
                String text = el.getText();
                return (text != null && !text.trim().isEmpty()) ? text.trim() : null;
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }

    @Step("Open product and get Image")
    public String openProductAndGetImage() {
        clickSearchResult();
        return getUploadedLogoSrc(uploadedImageName);
    }

    @Step("Open product and get Icon")
    public String openProductAndGetIcon() {
        return getUploadedLogoSrc(uploadedIconName);
    }

}
