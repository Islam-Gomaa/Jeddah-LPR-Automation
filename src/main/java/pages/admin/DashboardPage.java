package pages.admin;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.ElementActions;

public class DashboardPage extends ElementActions {

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    // Dashboard Count Locators
    private final By articlesCountValue = By.xpath("//h5[normalize-space()='Articles count']/following-sibling::h4");
    private final By newsCountValue = By.xpath("//*[normalize-space()='News count']/following::h4[1]");
    private final By productsCountValue = By.xpath("//*[normalize-space()='Products count']/following::h4[1]");
    private final By clientsCountValue = By.xpath("//*[normalize-space()='Clients count']/following::h4[1]");

    // Dashboard Tabs Locators
    private final By blogsTab = By.cssSelector("button[role='tab'][value='blog']");
    private final By productsTab = By.cssSelector("button[role='tab'][value='product']");
    private final By clientsTab = By.cssSelector("button[role='tab'][value='client']");

    // Blogs Dashboard Locators
    private final By articlesCountCard = By.xpath("//h5[contains(text(),'Articles count')]");
    private final By newsCountCard = By.xpath("//h5[contains(text(),'News count')]");
    private final By articlesByDateChart = By.xpath("//h5[contains(text(),'Articles by date')]");
    private final By newsByDateChart = By.xpath("//h5[contains(normalize-space(),'news by date')]");
    private final By blogCategoriesChart = By.xpath("//h5[contains(text(),'Blog categories')]");

    // Products Dashboard Locators
    private final By productsCountCard = By.xpath("//*[contains(text(),'Products count')]");
    private final By productCategoriesChart = By.xpath("//*[contains(text(),'Product categories')]");
    private final By productsByDateChart = By.xpath("//*[contains(text(),'Products by date')]");

    // Clients Dashboard Locators
    private final By clientsCountCard = By.xpath("//*[contains(text(),'Clients count')]");


    // Dashboard Tabs Selection Methods
    private boolean isTabSelected(By locator) {
        return "true".equals(getElement(locator).getAttribute("aria-selected"));
    }

    @Step("Verify Blogs tab selected")
    public boolean isBlogsTabSelected() {
        return isTabSelected(blogsTab);
    }

    @Step("Verify Products tab selected")
    public boolean isProductsTabSelected() {
        return isTabSelected(productsTab);
    }

    @Step("Verify Clients tab selected")
    public boolean isClientsTabSelected() {
        return isTabSelected(clientsTab);
    }

    // Dashboard Tabs Display Methods
    @Step("Verify Blogs tab displayed")
    public boolean isBlogsTabDisplayed() {
        return isDisplayed(blogsTab);
    }

    @Step("Verify Products tab displayed")
    public boolean isProductsTabDisplayed() {
        return isDisplayed(productsTab);
    }

    @Step("Verify Clients tab displayed")
    public boolean isClientsTabDisplayed() {
        return isDisplayed(clientsTab);
    }

    // Dashboard Navigation Methods
    @Step("Click Blogs tab")
    public DashboardPage openBlogsDashboard() {
        click(blogsTab);
        return this;
    }

    @Step("Click Products tab")
    public DashboardPage openProductsDashboard() {
        click(productsTab);
        return this;
    }

    @Step("Click Clients tab")
    public DashboardPage openClientsDashboard() {
        click(clientsTab);
        return this;
    }

    // Blogs Dashboard Display Methods
    @Step("Verify Articles count card displayed")
    public boolean isArticlesCountDisplayed() {
        return isDisplayed(articlesCountCard);
    }

    @Step("Verify News count card displayed")
    public boolean isNewsCountDisplayed() {
        return isDisplayed(newsCountCard);
    }

    @Step("Verify Articles by date chart displayed")
    public boolean isArticlesByDateChartDisplayed() {
        return isDisplayed(articlesByDateChart);
    }

    @Step("Verify News by date chart displayed")
    public boolean isNewsByDateChartDisplayed() {
        return isDisplayed(newsByDateChart);
    }

    @Step("Verify Blog categories chart displayed")
    public boolean isBlogCategoriesChartDisplayed() {
        return isDisplayed(blogCategoriesChart);
    }

    // Products Dashboard Display Methods
    @Step("Verify Products count card displayed")
    public boolean isProductsCountDisplayed() {
        return isDisplayed(productsCountCard);
    }

    @Step("Verify Product categories chart displayed")
    public boolean isProductCategoriesChartDisplayed() {
        return isDisplayed(productCategoriesChart);
    }

    @Step("Verify Products by date chart displayed")
    public boolean isProductsByDateChartDisplayed() {
        return isDisplayed(productsByDateChart);
    }

    // Clients Dashboard Display Methods
    @Step("Verify Clients count card displayed")
    public boolean isClientsCountDisplayed() {
        return isDisplayed(clientsCountCard);
    }


    // Dashboard Count Getters
    @Step("Get Articles count")
    public String getArticlesCount() {
        return getText(articlesCountValue);
    }

    @Step("Get News count")
    public String getNewsCount() {
        return getText(newsCountValue);
    }

    @Step("Get Products count")
    public String getProductsCount() {
        return getText(productsCountValue);
    }

    @Step("Get Clients count")
    public String getClientsCount() {
        return getText(clientsCountValue);
    }
}


