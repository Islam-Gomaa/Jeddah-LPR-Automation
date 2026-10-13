package pages;

import io.qameta.allure.Step;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScalePage extends BasePage<ScalePage> {

  public ScalePage(WebDriver driver) {
    super(driver);
  }

  // Locators
  private String getRowByPlate(String plateNumber) {
    return "//tbody//tr[.//*[contains(normalize-space(.),'" + plateNumber + "')]]";
  }

  public By plateNumberEn(String plateNumber) {
    return By.xpath(getRowByPlate(plateNumber) + "//td[7]//span[3]");
  }

  public By plateNumberAr(String plateNumber) {
    return By.xpath(getRowByPlate(plateNumber) + "//td[8]");
  }

  public By permitStatusBtn(String plateNumber) {
    return By.xpath(getRowByPlate(plateNumber) + "//td[13]");
  }

  public By deleteGateIcon(String plateNumber) {
    return By.xpath("("+ getRowByPlate(plateNumber) + "//button[1])[4]");
  }

  private final By plateLetterArInPopup =
          By.xpath("(//div[contains(@class,'plate__letter__ar')]//span)[2]");
  private final By plateLetterEnInPopup =
          By.xpath("(//div[contains(@class,'plate__letter__en')]//span)[4]");


  // Create Permit
  private final By createPermitBtn = By.xpath("//button[.='Create Permit']");
  private final By vehicleType =
      By.xpath(
          "//div[@updatekey='car_type.id']//div[contains(@class,'v-input v-input--horizontal')]");
  private final By drivers =
      By.xpath(
          "//div[@updatekey='driver.id']//div[contains(@class,'v-input v-input--horizontal')]");
  private final By mainWaste =
      By.xpath(
          "//div[@updatekey='main_waste.id']//div[contains(@class,'v-input v-input--horizontal')]");
  private final By subWaste =
      By.xpath("//div[@updatekey='waste.id']//div[contains(@class,'v-input v-input--horizontal')]");
  private final By contractor =
      By.xpath(
          "//div[@updatekey='contractor.id']//div[contains(@class,'v-input v-input--horizontal')]");
  private final By vehicleIsNotPermittedTXT =
          By.xpath("(//div[contains(@class,'no_permit permit_status')]//span)[2]");

  private final By vehicleIsPermittedTXT =
          By.xpath("(//div[contains(@class,'permit permit_status')]//span)[2]");

  // Change Status
  public By changeStatusIcon(String plateNumber) {
    return By.xpath("(" + getRowByPlate(plateNumber) + "//button[1])[2]");
  }

  private final By changeStatusBtn = By.cssSelector("[class='d-flex gap-2'] button[type='button'][class*='text-primary']");
  private final By setStatusField = By.xpath("//div[contains(@class,'v-field')][.//input[@placeholder='Set Status']]");
  private final By setStatusInput = By.cssSelector(".v-field__input > input[placeholder='Set Status']");
  private final By notes = By.cssSelector(".v-field__field> textarea[placeholder='Notes']");

  // Report
  public By addComplaintIcon(String plateNumber) {
    return By.xpath("(" + getRowByPlate(plateNumber) + "//button[1])[2]");
  }
  private final By reportButton = By.xpath("//p[contains(normalize-space(.),'Report')]");
  private final By nameEnglish = By.cssSelector("[class='v-field__field'] input[placeholder='Name (English)']");
  private final By nameArabic = By.cssSelector("[class='v-field__field'] input[placeholder='Name (Arabic)']");
  private final By assignedUser = By.xpath("//div[contains(@type,'select')][.//div[contains(@class,'field-label') and normalize-space()='Assigned User']]//div[contains(@class,'v-field__input')]");
  private final By selectStage = By.xpath("//div[contains(@type,'select')][.//div[contains(@class,'field-label') and normalize-space()='Select Stage']]//div[contains(@class,'v-field__input')]");
  private final By priority = By.xpath("//div[contains(@type,'select')][.//div[contains(@class,'field-label') and normalize-space()='Priority']]//div[contains(@class,'v-field__input')]");
  private final By detailsEnglish = By.cssSelector("textarea[placeholder='Details (English)']");
  private final By detailsArabic = By.cssSelector("textarea[placeholder='Details (Arabic)']");

  // Filters
  private final By locations = By.xpath("(//div[contains(@class,'v-field') and @role='combobox'][.//input[@placeholder='Location']])");
  private final By locationsGate = By.xpath("//p[contains(normalize-space(.),'Gate 2')]");
  private final By vehicleInformationTab = By.xpath("//button[contains(@class,'v-btn') and normalize-space()='Vehicle Information']");
  private final By contractTypeDDL = By.xpath("(//div[contains(@class,'v-field') and @role='combobox'][.//input[@placeholder='Contract Type']])");
  private final By contractTypeField = By.xpath("(//div[contains(@class,'info-data')] //p[normalize-space()='Temporary Contract'])[1]");

  // Delete, Restore and Force Delete
  private final By deleteIcon = By.xpath("(//div[contains(@class,'actions')] //button[contains(@class,'bg-table-icon delete')])[2]");
  private final By archiveIcon = By.xpath("//div[contains(@class,'transition-all')] //button[contains(@class,'v-btn--elevated')]");
  private final By restoreIcon = By.xpath("//div[contains(@class,'actions')] //button[contains(@class,'elevated table-icon edit')]");
  private final By forceDeleteIcon = By.xpath("//div[contains(@class,'actions')] //button[contains(@class,'elevated table-icon delete')]");
  private final By okButton = By.xpath("//button[contains(@class,'swal2-confirm')]");
  private final By assertPopUpAction = By.xpath("//h2[contains(@id,'swal2-title')]");
  private final By assertPopUpPlateNumber = By.xpath("//div[contains(@id,'swal2-html-container')]");



  // Actions

  // Check Plate Number
  @Step("Check plate number EN")
  public boolean isPlateNumberDisplayed(String number) {
    return isDisplayed(plateNumberEn(number));
  }

  @Step("Check plate letter AR Displayed")
  public boolean isPlateLetterArDisplayed() {
    return isDisplayed(plateLetterArInPopup);
  }

  @Step("Get plate letter AR")
  public String getPlateLetterAr() {
    return getText(plateLetterArInPopup);
  }

  public WebElement getPlateLetterArElement() {
    return driver.findElement(plateLetterArInPopup);
  }

  @Step("Check plate letter EN Displayed")
  public boolean isPlateLetterEnDisplayed() {
    return isDisplayed(plateLetterEnInPopup);
  }

  @Step("Get plate letter EN")
  public String getPlateLetterEn() {
    return getText(plateLetterEnInPopup);
  }

  public WebElement getPlateLetterEnElement() {
    return driver.findElement(plateLetterEnInPopup);
  }

  // Create Permit
  @Step("Get vehicle is not permitted text")
  public String getVehicleIsNotPermittedText() {
    return getText(vehicleIsNotPermittedTXT);
  }
  @Step("Get vehicle is not permitted text")
  public WebElement getVehicleIsNotPermittedTextElement() {
    return driver.findElement(vehicleIsNotPermittedTXT);
  }

  @Step("Wait until vehicle is permitted")
  public ScalePage waitUntilVehicleIsPermitted() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    wait.until(driver -> {
      try {
        return driver.findElement(vehicleIsPermittedTXT)
                .getText()
                .trim()
                .equals("This vehicle is permitted to enter");
      } catch (Exception e) {
        return false;
      }
    });

    return this;
  }

  @Step("Get vehicle is not permitted text")
  public String getVehicleIsPermittedText() {
    return getText(vehicleIsPermittedTXT);
  }
  @Step("Get vehicle is not permitted text")
  public WebElement getVehicleIsPermittedTextElement() {
    return driver.findElement(vehicleIsPermittedTXT);
  }

 @Step("Click Permit button for plate")
 public ScalePage clickPermitStatusButtonInTable(String plateNumber) {
   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
   By permitButton = permitStatusBtn(plateNumber);
   wait.until(ExpectedConditions.elementToBeClickable(permitButton));
   click(permitButton);
  return this;
}
  @Step("Click Permit button")
  public ScalePage clickVehicleInformationTab() {
    click(vehicleInformationTab);
    return this;
  }
  @Step("Click Create Permit button")
  public ScalePage clickCreatePermit() {
    click(createPermitBtn);
    return this;
  }

  @Step("Select Vehicle Type DDL")
  public ScalePage selectVehicleTypeDDL() {
    click(vehicleType);
    Actions actions = new Actions(driver);
    actions.sendKeys(Keys.ARROW_DOWN).pause(Duration.ofMillis(500)).sendKeys(Keys.ENTER).perform();
    return this;
  }

  @Step("Select Driver DDL")
  public ScalePage selectDriverDDL() {
    click(drivers);
    Actions actions = new Actions(driver);
    actions
        .pause(Duration.ofMillis(500))
        .sendKeys(Keys.ARROW_DOWN)
        .pause(Duration.ofMillis(500))
        .sendKeys(Keys.ENTER)
        .perform();
    return this;
  }

  @Step("Select Main Waste DDL")
  public ScalePage selectMainWasteDDL() {
    click(mainWaste);
    Actions actions = new Actions(driver);
    actions.sendKeys(Keys.ARROW_DOWN).pause(Duration.ofMillis(500)).sendKeys(Keys.ENTER).perform();
    return this;
  }

  @Step("Select Sub Waste DDL")
  public ScalePage selectSubWasteDDL() {
    click(subWaste);
    Actions actions = new Actions(driver);
    actions.sendKeys(Keys.ARROW_DOWN).pause(Duration.ofMillis(500)).sendKeys(Keys.ENTER).perform();
    return this;
  }

  @Step("Select Contractor DDL")
  public ScalePage selectContractorDDL() {
    click(contractor);
    Actions actions = new Actions(driver);
    actions
        .pause(Duration.ofMillis(500))
        .sendKeys(Keys.ARROW_DOWN)
        .pause(Duration.ofMillis(500))
        .sendKeys(Keys.ENTER)
        .perform();
    return this;
  }

  // Change Status

  @Step("Click Change Status button In Pop-Up")
  public ScalePage clickChangeStatusButton() {
    click(changeStatusBtn);
    return this;
  }

  @Step("Click Change Status Icon In table")
  public ScalePage clickChangeStatusIcon(String plateNumber) {
    click(changeStatusIcon(plateNumber));
    return this;
  }

  @Step("Select Set Status DDL")
  public ScalePage selectSetStatusDDL(String status) {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    wait.until(ExpectedConditions.elementToBeClickable(setStatusField)).click();
    By statusOption = By.xpath(
            "//div[contains(@class,'v-list-item') and @role='option']" +
                    "//div[contains(@class,'v-list-item-title') and normalize-space()='" + status + "']"
    );
    wait.until(ExpectedConditions.elementToBeClickable(statusOption))
            .click();
    return this;
  }

  @Step("Enter Status Notes")
  public ScalePage enterStatusNotes(String note) {
    sendKeys(notes,note);
    return this;
  }

  @Step("Wait for vehicle to be ready: {0}")
  public ScalePage waitForVehicleAppear() {

    try {
      Thread.sleep(300);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Interrupted while waiting for vehicle", e);
    }

    return this;
  }

  // Report
  @Step("Click Complaint button")
  public ScalePage clickReportButtonIcon(String plateNumber) {
    click(permitStatusBtn(plateNumber));
    return this;
  }

  @Step("Click Complaint button")
  public ScalePage clickReportButtonInPopUp() {
    click(reportButton);
    return this;
  }

  @Step("Enter Name English")
  public ScalePage enterNameEnglish(String name) {
    Actions actions = new Actions(driver);
    sendKeys(nameEnglish, name);
     actions.pause(Duration.ofMillis(500))
             .sendKeys(Keys.ENTER).perform();

    return this;
  }
  @Step("Enter Name Arabic")
  public ScalePage enterNameArabic(String name) {
    Actions actions = new Actions(driver);
    sendKeys(nameArabic, name);
    actions.pause(Duration.ofMillis(500))
            .sendKeys(Keys.ENTER).perform();
    return this;
  }

  @Step("Select Assigned User DDL")
  public ScalePage selectAssignedUserDDL() {
    click(assignedUser);
    Actions actions = new Actions(driver);
       actions.pause(Duration.ofMillis(1500))
              .sendKeys(Keys.ARROW_DOWN)
              .sendKeys(Keys.ARROW_UP)
              .pause(Duration.ofMillis(500))
              .sendKeys(Keys.ENTER).perform();
    return this;
  }

  @Step("Select Select Stage DDL")
  public ScalePage selectSelectStageDDL() {
    click(selectStage);
    Actions actions = new Actions(driver);
    actions.pause(Duration.ofMillis(1500))
            .sendKeys(Keys.ARROW_DOWN)
            .sendKeys(Keys.ARROW_UP)
            .pause(Duration.ofMillis(500))
            .sendKeys(Keys.ENTER).perform();
    return this;
  }

  @Step("Select Priority DDL")
  public ScalePage selectPriorityDDL() {
    click(priority);
    Actions actions = new Actions(driver);
    actions.pause(Duration.ofMillis(1500))
            .sendKeys(Keys.ARROW_DOWN)
            .pause(Duration.ofMillis(500))
            .sendKeys(Keys.ENTER).perform();
    return this;
  }

  @Step("Enter Details English")
  public ScalePage enterDetailsEnglish(String name) {
    sendKeys(detailsEnglish, name);
    return this;
  }
  @Step("Enter Details Arabic")
  public ScalePage enterDetailsArabic(String name) {
    sendKeys(detailsArabic, name);
    return this;
  }

  // Filter
  // Locations

  @Step("Search and select Location ")
  public ScalePage filterByLocationAndSelect(String value) {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    WebElement dropdown =
            wait.until(ExpectedConditions.elementToBeClickable(locations));

    // Open Location dropdown
    dropdown.click();

    wait.until(driver ->
            "true".equals(
                    driver.findElement(locations)
                            .getAttribute("aria-expanded")
            )
    );

    // Select filtered option
    By option = By.xpath(
            "//div[contains(@class,'v-list-item-title') " +
                    "and normalize-space()='" + value + "']"
    );
    wait.until(ExpectedConditions.elementToBeClickable(option)).click();

    return this;
  }

  @Step("Get success location")
  public WebElement getLocationsGate() {
    return driver.findElement(locationsGate);
  }

  @Step("Get vehicle is not permitted text")
  public String getLocationsGateText() {
    return getText(locationsGate);
  }

  // Contract Type
  @Step("Search and select Contract Type ")
  public ScalePage filterByContractTypeAndSelect(String value) {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    WebElement dropdown =
            wait.until(ExpectedConditions.elementToBeClickable(contractTypeDDL));

    // Open Location dropdown
    dropdown.click();

    wait.until(driver ->
            "true".equals(
                    driver.findElement(contractTypeDDL)
                            .getAttribute("aria-expanded")
            )
    );

    // Select filtered option
    By option = By.xpath(
            "//div[contains(@class,'v-list-item-title') " +
                    "and normalize-space()='" + value + "']"
    );
    wait.until(ExpectedConditions.elementToBeClickable(option)).click();

    return this;
  }

  @Step("Get success location")
  public WebElement getContractType() {
    return driver.findElement(contractTypeField);
  }

  @Step("Get vehicle is not permitted text")
  public String getContractTypeText() {
    return getText(contractTypeField);
  }


  @Step("Click Delete Vehicle")
  public ScalePage clickDeleteIcon() {
    click(deleteIcon);
    return this;
  }

  @Step("Click Archive Page")
  public ScalePage clickArchiveIcon() {
    click(archiveIcon);
    return this;
  }

  @Step("Click Restore Vehicle")
  public ScalePage clickRestoreIcon() {
    click(restoreIcon);
    return this;
  }

  @Step("Click Force Delete Vehicle")
  public ScalePage clickForceDeleteIcon() {
    click(forceDeleteIcon);
    return this;
  }

  @Step("Click Ok Button")
  public ScalePage clickOkButton() {
    click(okButton);
    return this;
  }

  @Step("Get success Pop-up action")
  public WebElement getPopUpAction() {
    return driver.findElement(assertPopUpAction);
  }

  @Step("Get Pop-up action text")
  public String getPopUpActionText() {
    return getText(assertPopUpAction);
  }

  @Step("Get success Pop-up Plate Number")
  public WebElement getPopUpPlateNumber() {
    return driver.findElement(assertPopUpPlateNumber);
  }

  @Step("Get Pop-up Plate Number text")
  public String getPopUpPlateNumberText() {
    return getText(assertPopUpPlateNumber);
  }

}
