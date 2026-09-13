package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import utilities.ElementActions;
import utilities.Waits;

import java.util.function.Function;

public class BasePage<T extends BasePage<T>> extends ElementActions {

    public BasePage(WebDriver driver){super(driver);}

    // Locators
    private final By changeLanguage = By.cssSelector(".navbar-actions-cont i[class*='v-icon notranslate v-theme--light v-icon--size-default']");
    private final By englishBtn = By.xpath("(//div[contains(@class,'language_wrapper')] //span[contains(@class,'language_option mx-2')])[2]");
    private final By submitBtn = By.cssSelector("button[form='myForm'][type='submit']");
    private final By successIcon = By.cssSelector(".swal2-icon-content img");
    private final By successMessage = By.cssSelector(".swal2-title");
    private final By editBtn = By.cssSelector(".v-card-actions .flex button");
    private final By saveEditBtn = By.xpath("//button[.='Edit']");
    private final By deleteBtn = By.xpath("//button[.='Delete']");
    private final By noDataAvailableMessage = By.xpath("//td//div[normalize-space()='No data available']");
    private final By noDataRow = By.cssSelector("tr.v-data-table-rows-no-data");
    private final By confirmDeleteBtn = By.xpath("//button[.='confirm']");
    private final By popUpMessage = By.cssSelector(".swal2-title");
    private final By recoveryBtn = By.xpath("//button[.='recovery']");


    // Page's locators
    // Control Panel
    private final By dashboard  = By.xpath("//span[normalize-space()='Dashboard']");
    private final By gates = By.xpath("//span[normalize-space()='Gates']");
    private final By scale = By.xpath("//span[normalize-space()='Scale']");
    private final By incinerator = By.xpath("//span[normalize-space()='Incinerator']");
    private final By washStation = By.xpath("//span[normalize-space()='Wash Station']");
    private final By vehicles = By.xpath("//span[normalize-space()='Vehicles']");
    private final By vehiclesContracts = By.xpath("//span[normalize-space()='Vehicles Contracts']");
    private final By visits = By.xpath("//span[normalize-space()='Visits']");
    private final By visitorCars = By.xpath("//span[normalize-space()='Visitor Cars']");
    private final By permits = By.xpath("//span[normalize-space()='permits']");
    private final By visitorsLog = By.xpath("//span[normalize-space()='Visitors Log']");
    private final By settings = By.xpath("//span[normalize-space()='Settings']");
    private final By locations = By.xpath("//span[normalize-space()='Locations']");
    private final By vehicleTypes =  By.xpath("//span[normalize-space()='Vehicle Types']");
    private final By drivers =  By.xpath("//span[normalize-space()='D']");
    private final By contractors =  By.xpath("//span[normalize-space()='Contacts']");


    // Fluent setters

    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }

    @Step("Change Language")
    public T changeLanguage() {
        Waits.waitForClickable(driver, changeLanguage).click();
        Waits.waitForClickable(driver, englishBtn).click();
        return self();
    }

//    @Step("Clear Search Inputs")
//    public T clearSearchInputs() {
//        WebElement element = Waits.waitForVisible(driver, searchInputs);
//
//        element.click();
//        element.sendKeys(Keys.CONTROL + "a");
//        element.sendKeys(Keys.DELETE);
//
//        return self();
//    }
//
//    @Step("Search Inputs")
//    public T searchInputs(String searchText) {
//        Waits.waitForVisible(driver, searchInputs);
//
//        clearSearchInputs();
//        sendKeys(searchInputs, searchText);
//
//        Waits.waitForTableResultOrNoData(driver, dataTableSearchResult, noDataRow);
//
//        return self();
//    }
//
//    @Step("Close PopUp")
//    public T closePopUpIcon() {
//        Waits.waitForClickable(driver, closePopUp).click();
//        return self();
//    }
//
//    @Step("Click on search result")
//    public T clickSearchResult() {
//        Waits.waitForVisible(driver, dataTableSearchResult);
//        Waits.waitForClickable(driver, dataTableSearchResult).click();
//        return self();
//    }
//
//    @Step("Click Add Button")
//    public T clickAddButton() {
//        Waits.waitForClickable(driver, addBtn).click();
//        return self();
//    }
//
//    @Step("Click Add In Pup Up")
//    public T clickAddPopUp() {
//        click(addButtonInPopUp);
//        return self();
//    }
//
//    @SuppressWarnings("UnusedReturnValue")
//    @Step("Click Submit Btn")
//    public T clickSubmit() {
//        Waits.waitForClickable(driver, submitBtn).click();
//        return self();
//    }
//
//    @SuppressWarnings("UnusedReturnValue")
//    @Step("Click Edit Btn")
//    public T clickEdit() {
//        Waits.waitForClickable(driver, editBtn).click();
//        return self();
//    }
//
//    @Step("Click Edit Btn and Save")
//    public T clickEditAndSave() {
//        Waits.waitForClickable(driver, saveEditBtn).click();
//        return self();
//    }
//
//    @Step("Get Item update")
//    public String getTableSearchResult() {
//        return getText(dataTableSearchResult);
//    }
//
//
//    @Step("Click Delete Button")
//    public T clickDelete() {
//        click(deleteBtn);
//        return self();
//    }
//
//
//    @Step("Click Confirm Delete Button")
//    public T clickConfirmDelete() {
//        click(confirmDeleteBtn);
//        return self();
//    }
//
//    @Step("Verify success icon appeared")
//    public boolean isSuccessIconDisplayed() {
//        return getElement(successIcon).isDisplayed();
//    }
//
//    @Step("Verify success message appeared")
//    public boolean isSuccessMessageDisplayed() {
//        return getElement(successIcon).isDisplayed();
//    }
//
//    @Step("Get success message")
//    public String getSuccessMessage() {
//        return getText(successMessage);
//    }
//
//    @Step("Get no data available message")
//    public boolean isNoDataMessageCorrect() {
//        return Waits.waitForTextToBe(driver, noDataAvailableMessage, "No data available");
//    }
//
//    @Step("Verify main Element appeared")
//    public boolean isElementDisplayed(Function<String, By> locatorFunction, String name) {
//
//        int maxScrolls = 10;
//
//        for (int i = 0; i < maxScrolls; i++) {
//
//            By locator = locatorFunction.apply(name);
//
//            if (isElementPresent(locator)) {
//
//                WebElement element = getElement(locator);
//
//                scrollToElement(locator);
//
//                highlightElement(element);
//
//                return true;
//            }
//
//            scrollBy(0, 500);
//        }
//
//        return false;
//    }
//
//    @Step("Open Dashboard Page")
//    public DashboardPage openDashboard() {
//        click(dashboard);
//        return new DashboardPage(driver);
//    }
//
    @Step("Open Gates Page")
    public GatesPage openGates() {
        click(gates);
        return new GatesPage(driver);
    }
//
//    @Step("Open UseCases Page")
//    public UseCasesPage openUseCases() {
//        click(dataEntry);
//        click(useCases);
//        return new UseCasesPage(driver);
//    }
//
//    @Step("Open Category Page")
//    public CategoryPage openCategory() {
//        click(dataEntry);
//        click(category);
//        return new CategoryPage(driver);
//    }
//
//    @Step("Open Clients Page")
//    public ClientsPage openClients() {
//        click(dataEntry);
//        click(clients);
//        return new ClientsPage(driver);
//    }
//
//    @Step("Open Partners Page")
//    public PartnersPage openPartners() {
//        click(dataEntry);
//        click(partners);
//        return new PartnersPage(driver);
//    }
//
//    @Step("Open Blogs Page")
//    public BlogsPage openBlogs() {
//        click(blogs);
//        click(subBlogs);
//        return new BlogsPage(driver);
//    }
//
//    @Step("Open Blog Categories Page")
//    public BlogCategoriesPage openBlogCategories() {
//        click(blogs);
//        click(blogCategories);
//        return new BlogCategoriesPage(driver);
//    }
//
//    @Step("Open Products Page")
//    public ProductsPage openProducts() {
//        click(products);
//        return new ProductsPage(driver);
//    }
//
//    @Step("Open Advertisements Page")
//    public AdvertisementsPage openAdvertisements() {
//        click(advertisements);
//        return new AdvertisementsPage(driver);
//    }
//
//    @Step("Open Users Page")
//    public UsersPage openUsers() {
//        click(users);
//        return new UsersPage(driver);
//    }
//
//    @Step("Open Roles Page")
//    public RolesPage openRoles() {
//        click(roles);
//        return new RolesPage(driver);
//    }
//
//    @Step("Open Subscribers Page")
//    public SubscribesPage openSubscribers() {
//        click(subscribers);
//        return new SubscribesPage(driver);
//    }
//
//    @Step("Open Contacts Page")
//    public ContactUsPage openContacts() {
//        click(contacts);
//        return new ContactUsPage(driver);
//    }
//
//    // Website pages
//
//    @Step("Open Services And Solutions Page")
//    public ServicesAndSolutionsPage openServicesAndSolutions() {
//        click(solutionsAndServices);
//        return new ServicesAndSolutionsPage(driver);
//    }
//
//    @Step("Open Blogs Page")
//    public BlogsWebPage openBlogsWebsite() {
//        click(blogsTab);
//        return new BlogsWebPage(driver);
//    }

    public void scrollToFooter() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        long lastHeight = (Long) js.executeScript("return document.body.scrollHeight");

        while (true) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}

            long newHeight = (Long) js.executeScript("return document.body.scrollHeight");

            if (newHeight == lastHeight)
                break;

            lastHeight = newHeight;
        }
    }

}