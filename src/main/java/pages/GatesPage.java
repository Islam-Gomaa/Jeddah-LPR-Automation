package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class GatesPage extends BasePage<GatesPage> {

  public GatesPage(WebDriver driver) {
    super(driver);
  }

  // Locators
  private String getRowByPlate(String plateNumber) {
    return "//tbody//tr[.//*[contains(normalize-space(.),'" + plateNumber + "')]]";
  }

  public By plateNumberEn(String plateNumber) {
    return By.xpath(getRowByPlate(plateNumber) + "//td[5]//span[3]");
  }

  public By plateNumberAr(String plateNumber) {
    return By.xpath(getRowByPlate(plateNumber) + "//td[6]");
  }

  public By permitStatusBtn(String plateNumber) {
    return By.xpath(getRowByPlate(plateNumber) + "//td[8]");
  }

  public By changeStatusIcon(String plateNumber) {
    return By.xpath(getRowByPlate(plateNumber) + "//button[1][1]");
  }

  public By addComplaintIcon(String plateNumber) {
    return By.xpath(getRowByPlate(plateNumber) + "//button[1][2]");
  }

  public By deleteGateIcon(String plateNumber) {
    return By.xpath(getRowByPlate(plateNumber) + "//button[1][3]");
  }

  private final By plateLetterArInPopup =
      By.xpath("(//div[contains(@class,'plate__letter__ar')]//span)[2]");
  private final By plateLetterEnInPopup =
      By.xpath("(//div[contains(@class,'plate__letter__en')]//span)[4]");
  private final By vehicleIsNotPermittedTXT =
      By.xpath("(//div[contains(@class,'no_permit permit_status')]//span)[2]");

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
  private final By uHFTag = By.xpath("");
  private final By notes = By.xpath("");

  @Step("Check plate number EN")
  public boolean isPlateNumberDisplayed(String number) {
    return isDisplayed(plateNumberEn(number));
  }

  // Button Actions
  @Step("Click Permit button")
  public GatesPage clickPermitStatusButton(String plateNumber) {
    click(permitStatusBtn(plateNumber));
    return this;
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

  @Step("Get vehicle is not permitted text")
  public String getVehicleIsNotPermittedText() {
    return getText(vehicleIsNotPermittedTXT);
  }

  public WebElement getVehicleIsNotPermittedTextElement() {
    return driver.findElement(vehicleIsNotPermittedTXT);
  }

  @Step("Click Create Permit button")
  public GatesPage clickCreatePermit() {
    click(createPermitBtn);
    return this;
  }

  @Step("Select Vehicle Type DDL")
  public GatesPage selectVehicleTypeDDL() {
    click(vehicleType);
    Actions actions = new Actions(driver);
    actions.sendKeys(Keys.ARROW_DOWN).pause(Duration.ofMillis(500)).sendKeys(Keys.ENTER).perform();
    return this;
  }

  @Step("Select Vehicle Type DDL")
  public GatesPage selectDriverDDL() {
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

  @Step("Select Vehicle Type DDL")
  public GatesPage selectMainWasteDDL() {
    click(mainWaste);
    Actions actions = new Actions(driver);
    actions.sendKeys(Keys.ARROW_DOWN).pause(Duration.ofMillis(500)).sendKeys(Keys.ENTER).perform();
    return this;
  }

  @Step("Select Vehicle Type DDL")
  public GatesPage selectSubWasteDDL() {
    click(subWaste);
    Actions actions = new Actions(driver);
    actions.sendKeys(Keys.ARROW_DOWN).pause(Duration.ofMillis(500)).sendKeys(Keys.ENTER).perform();
    return this;
  }

  @Step("Select Vehicle Type DDL")
  public GatesPage selectContractorDDL() {
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

  @Step("Select Vehicle Type DDL")
  public GatesPage selectContracDDL() {
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

  //  @Step("Enter Arabic display name")
  //  public GatesPage enterDisplayNameArabic(String value) {
  //    sendKeys(displayNameAr, value);
  //    return this;
  //  }
  //
  //  @Step("Enter English display name")
  //  public GatesPage enterDisplayNameEnglish(String value) {
  //    sendKeys(displayNameEn, value);
  //    return this;
  //  }
  //
  //  @Step("Enter Arabic description")
  //  public GatesPage enterDescriptionArabic(String value) {
  //    sendKeys(descriptionAR, value);
  //    return this;
  //  }
  //
  //  @Step("Enter English description")
  //  public GatesPage enterDescriptionEnglish(String value) {
  //    sendKeys(descriptionEN, value);
  //    return this;
  //  }
  //
  //  // Active Status Methods
  //  @Step("Enable role")
  //  public GatesPage enableRole() {
  //    if (!getElement(isActiveRole).isSelected()) {
  //      jsClick(isActiveRole);
  //    }
  //    return this;
  //  }
  //
  //  @Step("Disable role")
  //  public GatesPage disableRole() {
  //    if (getElement(isActiveRole).isSelected()) {
  //      jsClick(isActiveRole);
  //    }
  //    return this;
  //  }
  //
  //  // Permission Methods
  //  @Step("Search permission: {permission}")
  //  public GatesPage searchPermission(String permission) {
  //    sendKeys(permissionSearch, permission);
  //    return this;
  //  }
  //
  //  @Step("Open permission category: {category}")
  //  public GatesPage openPermissionCategory(String category) {
  //    click(permissionCategory(category));
  //    return this;
  //  }
  //
  //  @Step("Select permission: {permission}")
  //  public GatesPage selectPermission(String permission) {
  //    jsClick(permissionCheckbox(permission));
  //    return this;
  //  }
  //
  //  @Step("Unselect permission: {permission}")
  //  public GatesPage unselectPermission(String permission) {
  //    if (getElement(permissionCheckbox(permission)).isSelected()) {
  //      click(permissionCheckbox(permission));
  //    }
  //    return this;
  //  }

  //  // Button Actions
  //  @Step("Click Add button")
  //  public GatesPage clickAdd() {
  //    click(addButton);
  //    return this;
  //  }
  //
  //  @Step("Click Cancel button")
  //  public GatesPage clickCancel() {
  //    click(cancelButton);
  //    return this;
  //  }
  //
  //  @Step("Click Edit button")
  //  public GatesPage clickEditRole() {
  //    click(editButton);
  //    return this;
  //  }
  //
  //  @Step("Click Delete button")
  //  public GatesPage clickDeleteRole() {
  //    click(deleteButton);
  //    return this;
  //  }
  //
  //  @Step("Click Confirm Delete button")
  //  public GatesPage clickConfirmDeleteRole() {
  //    click(confirmDeleteButton);
  //    return this;
  //  }

  //  // Verification Methods
  //  @Step("Verify permission category displayed")
  //  public boolean isPermissionCategoryDisplayed(String category) {
  //    Waits.waitForVisible(driver, permissionCategory(category));
  //    return isDisplayed(permissionCategory(category));
  //  }

  //  @Step("Fill role information")
  //  public GatesPage fillRoleData(
  //      String name, String displayAr, String displayEn, String descAr, String descEn) {
  //
  //    return enterRoleName(name)
  //        .enterDisplayNameArabic(displayAr)
  //        .enterDisplayNameEnglish(displayEn)
  //        .enterDescriptionArabic(descAr)
  //        .enterDescriptionEnglish(descEn);
  //  }

  //  @Step("Assign permissions in category {category}")
  //  public GatesPage assignPermissions(String category, String... permissions) {
  //
  //    openPermissionCategory(category);
  //
  //    for (String permission : permissions) {
  //      selectPermission(permission);
  //    }
  //
  //    return this;
  //  }

  //  @Step("Verify role is not displayed")
  //  public boolean isRoleNotDisplayed(String roleName) {
  //    return !isElementPresent(roleCard(roleName));
  //  }
  //
  //  @Step("Open role: {roleName}")
  //  public GatesPage openRole(String roleName) {
  //    click(roleCard(roleName));
  //    return this;
  //  }
  //
  //  @Step("Scroll Permissions List To Bottom")
  //  public GatesPage scrollPermissionsListToBottom() {
  //    WebElement list = getElement(permissionsList);
  //
  //    ((JavascriptExecutor) driver)
  //        .executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", list);
  //    return this;
  //  }
}
