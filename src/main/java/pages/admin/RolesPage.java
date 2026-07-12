package pages.admin;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import utilities.Waits;

public class RolesPage extends BasePage<RolesPage> {

    public RolesPage(WebDriver driver) {
        super(driver);
    }

        // Buttons Locators
        private final By addButton = By.xpath("//button[contains(.,'Add Role')]");
        private final By editButton = By.xpath("//button[contains(.,'Edit')]");
        private final By saveButton = By.xpath("//button[contains(.,'Save')]");
        private final By deleteButton = By.xpath("//button[contains(.,'Delete')]");
        private final By confirmDeleteButton = By.xpath("//button[contains(.,'confirm')]");
        private final  By cancelButton =  By.xpath("//button[contains(.,'Cancel')]");

        // Role Information Locators
        private final By roleName = By.cssSelector("input[placeholder='Enter Name']");
        private final By displayNameAr = By.cssSelector("input[placeholder='Enter Display Name (Arabic)']");
        private final By displayNameEn = By.cssSelector("input[placeholder='Enter Display Name (English)']");
        private final By descriptionAR = By.cssSelector("textarea[placeholder='Enter Description (Arabic)']");
        private final By descriptionEN = By.cssSelector("textarea[placeholder='Enter Description (English)']");
        private final By isActiveRole = By.cssSelector("[class*='v-checkbox-btn'] input[aria-label='Is Active?']");

        // Permission Locators
        private final By permissionSearch = By.cssSelector("input[placeholder='Search']");
        private final By permissionsList = By.cssSelector("ul.overflow-y-auto");

        private By permissionCategory(String category) {
            return By.xpath("//div[contains(@class,'border-surface_hover')]//h3[normalize-space()='" + category + "']");}

        private By permissionCheckbox(String permission) {
            return By.cssSelector("[class*='v-checkbox-btn'] input[value='" + permission + "']");}

        private By roleCard(String roleName) {
            return By.xpath("//div[contains(@class,'flex-col')]//h3[normalize-space()='" + roleName + "']");}



    // Role Information Methods
        @Step("Enter role name")
        public RolesPage enterRoleName(String value) {
            sendKeys(roleName, value);
            return this;
        }

        @Step("Enter Arabic display name")
        public RolesPage enterDisplayNameArabic(String value) {
            sendKeys(displayNameAr, value);
            return this;
        }

        @Step("Enter English display name")
        public RolesPage enterDisplayNameEnglish(String value) {
            sendKeys(displayNameEn, value);
            return this;
        }

        @Step("Enter Arabic description")
        public RolesPage enterDescriptionArabic(String value) {
            sendKeys(descriptionAR, value);
            return this;
        }

        @Step("Enter English description")
        public RolesPage enterDescriptionEnglish(String value) {
            sendKeys(descriptionEN, value);
            return this;
        }

        // Active Status Methods
        @Step("Enable role")
        public RolesPage enableRole() {
            if (!getElement(isActiveRole).isSelected()) {
                jsClick(isActiveRole);
            }
            return this;
        }

        @Step("Disable role")
        public RolesPage disableRole() {
            if (getElement(isActiveRole).isSelected()) {
                jsClick(isActiveRole);
            }
            return this;
        }

        // Permission Methods
        @Step("Search permission: {permission}")
        public RolesPage searchPermission(String permission) {
            sendKeys(permissionSearch, permission);
            return this;
        }

        @Step("Open permission category: {category}")
        public RolesPage openPermissionCategory(String category) {
            click(permissionCategory(category));
            return this;
        }

        @Step("Select permission: {permission}")
        public RolesPage selectPermission(String permission) {
            jsClick(permissionCheckbox(permission));
            return this;
        }

        @Step("Unselect permission: {permission}")
        public RolesPage unselectPermission(String permission) {
            if (getElement(permissionCheckbox(permission)).isSelected()) {
                click(permissionCheckbox(permission));
            }
            return this;
        }

        // Button Actions
        @Step("Click Add button")
        public RolesPage clickAdd() {
            click(addButton);
            return this;
        }

        @Step("Click Save button")
        public RolesPage clickSave() {
            click(saveButton);
            return this;
        }

        @Step("Click Cancel button")
        public RolesPage clickCancel() {
            click(cancelButton);
            return this;
        }

        @Step("Click Edit button")
        public RolesPage clickEditRole() {
            click(editButton);
            return this;
        }

        @Step("Click Delete button")
        public RolesPage clickDeleteRole() {
            click(deleteButton);
            return this;
        }

        @Step("Click Confirm Delete button")
        public RolesPage clickConfirmDeleteRole() {
            click(confirmDeleteButton);
            return this;
        }

        // Verification Methods
        @Step("Verify permission category displayed")
        public boolean isPermissionCategoryDisplayed(String category) {
            Waits.waitForVisible(driver, permissionCategory(category));
            return isDisplayed(permissionCategory(category));
        }

        @Step("Verify permission displayed")
        public boolean isPermissionDisplayed(String permission) {
            return isDisplayed(permissionCheckbox(permission));
        }

        @Step("Verify Add button displayed")
        public boolean isAddButtonDisplayed() {
            return isDisplayed(addButton);
        }

        @Step("Verify Save button displayed")
        public boolean isSaveButtonDisplayed() {
            return isDisplayed(saveButton);
        }

        @Step("Verify role displayed")
        public boolean isRoleDisplayed(String roleName) {
            return isDisplayed(roleCard(roleName));
        }

       @Step("Verify Edit button displayed")
        public boolean isEditButtonDisplayed() {
            return isDisplayed(editButton);
        }

        @Step("Verify Delete button displayed")
        public boolean isDeleteButtonDisplayed() {
           return isDisplayed(deleteButton);
       }

        @Step("Fill role information")
        public RolesPage fillRoleData(String name,
                                      String displayAr,
                                      String displayEn,
                                      String descAr,
                                      String descEn) {

            return enterRoleName(name)
                    .enterDisplayNameArabic(displayAr)
                    .enterDisplayNameEnglish(displayEn)
                    .enterDescriptionArabic(descAr)
                    .enterDescriptionEnglish(descEn);
        }

        @Step("Clear role information")
        public RolesPage clearRoleData() {

            clear(roleName);
            clear(displayNameAr);
            clear(displayNameEn);
            clear(descriptionAR);
            clear(descriptionEN);

            return this;
        }

        @Step("Assign permissions in category {category}")
        public RolesPage assignPermissions(String category, String... permissions) {

            openPermissionCategory(category);

            for (String permission : permissions) {
                selectPermission(permission);
            }

            return this;
        }

        @Step("Verify role is not displayed")
        public boolean isRoleNotDisplayed(String roleName) {
        return !isElementPresent(roleCard(roleName));
        }

        @Step("Open role: {roleName}")
        public RolesPage openRole(String roleName) {
            click(roleCard(roleName));
            return this;
        }
        @Step("Scroll Permissions List To Bottom")
        public RolesPage scrollPermissionsListToBottom() {
            WebElement list = getElement(permissionsList);

            ((JavascriptExecutor) driver).executeScript(
                   "arguments[0].scrollTop = arguments[0].scrollHeight;",
                    list
            );
            return this;
        }

    }

