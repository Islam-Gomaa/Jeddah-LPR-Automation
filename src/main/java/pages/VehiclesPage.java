package pages;

import io.qameta.allure.Step;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static data.VehicleTestData.plateNumber;

public class VehiclesPage extends BasePage<VehiclesPage> {

  public VehiclesPage(WebDriver driver) {
    super(driver);
  }

  // Locators
  private String getRowByPlate(String plateNumber) {
    return "//tbody//tr[.//*[contains(normalize-space(.),'" + plateNumber + "')]]";
  }

  public By plateNumberEn(String plateNumber) {
    return By.xpath(getRowByPlate(plateNumber) + "//td[2]//span[3]");
  }

  public By plateNumberAr(String plateNumber) {
    return By.xpath(getRowByPlate(plateNumber) + "//td[6]//span[1]");
  }

  public By permitStatusBtn(String plateNumber) {
    return By.xpath(getRowByPlate(plateNumber) + "//td[8]");
  }

  public By currentContract(String contract) {return By.xpath(getRowByPlate(contract) + "//td[3]//span[1]");
  }

  public By carStatus(String plateNumber) {return By.xpath(getRowByPlate(plateNumber) + "//td[6] //div[(@class='v-chip__content')]");}


  private final By plateLetterArInPopup = By.xpath("(//div[contains(@class,'plate__letter__ar')]//span)[2]");

  private final By plateLetterEnInPopup = By.xpath("(//div[contains(@class,'plate__letter__en')]//span)[4]");

  private final By vehicleIsNotPermittedTXT =
          By.xpath("(//div[contains(@class,'no_permit permit_status')]//span)[2]");

  private final By vehicleIsPermittedTXT =
          By.xpath("(//div[contains(@class,'permit permit_status')]//span)[2]");

  // Change Status
  public By changeStatusIcon(String plateNumber) {return By.xpath("("+ getRowByPlate(plateNumber) + "//button[1])[2]");}

  private final By changeStatusBtn = By.cssSelector("[class='d-flex gap-2'] button[type='button'][class*='text-primary']");
  private final By setStatusField = By.xpath("//div[contains(@class,'v-field')][.//input[@placeholder='Set Status']]");
  private final By setStatusInput = By.cssSelector(".v-field__input > input[placeholder='Set Status']");
  private final By notes = By.cssSelector(".v-field__field> textarea[placeholder='Notes']");

  // Filters
  private final By statusFilter = By.xpath("(//div[contains(@class,'v-field') and @role='combobox'][.//input[@placeholder='Status']])");
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
        try {
            return driver.findElement(plateNumberEn(number)).isDisplayed();
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            return driver.findElement(plateNumberEn(number)).isDisplayed();
        }
    }
  @Step("Check plate number EN")
  public boolean iscurrentContract(String contract) {
      return isDisplayed(currentContract(contract));
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


  @Step("Get vehicle Car Status text")
  public String getCarStatusText(String plateNumber) {
    return getText(carStatus(String plateNumber));
  }

  @Step("Get vehicle Car Status text")
  public WebElement getCarStatusTextElement(String plateNumber) {
        return getText(carStatus(String plateNumber));
  }

  @Step("Wait until vehicle is permitted")
  public VehiclesPage waitUntilVehicleIsPermitted() {

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
  public VehiclesPage clickPermitStatusButtonInTable(String plateNumber) {
   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
   By permitButton = permitStatusBtn(plateNumber);
   wait.until(ExpectedConditions.elementToBeClickable(permitButton));
   click(permitButton);
  return this;
 }


  // Change Status

  @Step("Click Change Status button In Pop-Up")
  public VehiclesPage clickChangeStatusButton() {
    click(changeStatusBtn);
    return this;
  }

  @Step("Click Change Status Icon In table")
  public VehiclesPage clickChangeStatusIcon(String plateNumber) {
    click(changeStatusIcon(plateNumber));
    return this;
  }

  @Step("Select Set Status DDL")
  public VehiclesPage selectSetStatusDDL(String status) {

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
  public VehiclesPage enterStatusNotes(String note) {
    sendKeys(notes,note);
    return this;
  }

  @Step("Wait for vehicle to be ready")
  public VehiclesPage waitForVehicleAppear() {

    try {
      Thread.sleep(300);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Interrupted while waiting for vehicle", e);
    }
    return this;
  }

  // Filter by status

  @Step("Filter by status")
  public VehiclesPage filterByStatusAndSelect(String value) {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    WebElement dropdown =
            wait.until(ExpectedConditions.elementToBeClickable(statusFilter));

    // Open Status dropdown
    dropdown.click();

    wait.until(driver ->
            "true".equals(
                    driver.findElement(statusFilter)
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
  public WebElement getStatus(String plateNumber) {
    return driver.findElement(carStatus(plateNumber));
  }


    @Step("Check plate number EN")
    public boolean isCarStatus(String plateNumber) {
        return isDisplayed(carStatus(plateNumber));
    }


    // Delete Vehicle

  @Step("Get success location")
  public WebElement getContractType() {
    return driver.findElement(contractTypeField);
  }

  @Step("Get vehicle is not permitted text")
  public String getContractTypeText() {
    return getText(contractTypeField);
  }


  @Step("Click Delete Vehicle")
  public VehiclesPage clickDeleteIcon() {
    click(deleteIcon);
    return this;
  }

  @Step("Click Archive Page")
  public VehiclesPage clickArchiveIcon() {
    click(archiveIcon);
    return this;
  }

  @Step("Click Restore Vehicle")
  public VehiclesPage clickRestoreIcon() {
    click(restoreIcon);
    return this;
  }

  @Step("Click Force Delete Vehicle")
  public VehiclesPage clickForceDeleteIcon() {
    click(forceDeleteIcon);
    return this;
  }

  @Step("Click Ok Button")
  public VehiclesPage clickOkButton() {
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
