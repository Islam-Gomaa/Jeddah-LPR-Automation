package pages;

import api.SaudiPlate;
import io.qameta.allure.Step;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PermitsPage extends BasePage<PermitsPage> {

    // Field labels as displayed in the Add Permit form
    public static final String PLATE_ARABIC_FIELD = "Plate Number (Arabic)";
    public static final String PLATE_ENGLISH_FIELD = "Plate Number (English)";
    public static final String VEHICLE_TYPE_FIELD = "Vehicle Type";
    public static final String PROJECT_FIELD = "Project";
    public static final String START_DATE_FIELD = "Start Date";
    public static final String END_DATE_FIELD = "End Date";
    private static final String TYPE_FIELD = "Type";

    // Value format of the Start/End Date inputs, e.g. "2026-11-22 16:45"
    private static final DateTimeFormatter DATE_TIME_VALUE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Date part of a calendar day aria-label, e.g. "November 1, 2026"
    private static final DateTimeFormatter DAY_ARIA_LABEL_FORMAT =
            DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
    private static final Pattern DAY_ARIA_LABEL_DATE =
            Pattern.compile("[A-Za-z]+ \\d{1,2}, \\d{4}$");

    private static final int ARABIC_PLATE_GROUP = 1;
    private static final int ENGLISH_PLATE_GROUP = 2;
    private static final int MAX_MONTH_NAVIGATIONS = 240;

    private final WebDriverWait wait;
    private WebElement permitForm;

    public PermitsPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ==================== Locators: Form ====================

    private final By addPermitBtn = By.xpath("//p[.='Add']");

    private final By permitFormLocator =
            By.xpath("//div[contains(@class,'v-dialog')]//form[contains(@class,'v-form')]");

    private final By vehicleTypeInput =
            By.xpath("//div[contains(@class,'v-dialog')]//input[@placeholder='Vehicle Type']");

    private final By driverInput =
            By.xpath("//div[contains(@class,'v-dialog')]//input[@placeholder='Driver']");

    private final By visitReasonInput =
            By.xpath("//div[contains(@class,'v-dialog')]//textarea[@placeholder='Reason for visit']");

    private final By startDateInput =
            By.xpath("//input[@placeholder='Start Date' and @readonly]");

    private final By endDateInput =
            By.xpath("//input[@placeholder='End Date' and @readonly]");

    private final By closeFormBtn =
            By.xpath("//div[contains(@class,'v-dialog')]//button[normalize-space()='Close']");

    // Column that wraps a form field (label, input and validation message)
    private String fieldColumn(String fieldLabel) {
        return "//div[contains(@class,'v-dialog')]"
                + "//div[contains(@class,'field-label') and normalize-space()='" + fieldLabel + "']"
                + "/ancestor::div[contains(@class,'v-col')][1]";
    }

    private By selectField(String fieldLabel) {
        return By.xpath(fieldColumn(fieldLabel) + "//div[@role='combobox']");
    }

    private By selectedValue(String fieldLabel) {
        return By.xpath(fieldColumn(fieldLabel) + "//*[contains(@class,'v-select__selection-text')]");
    }

    private By validationMessage(String fieldLabel) {
        return By.xpath(fieldColumn(fieldLabel) + "//div[contains(@class,'v-messages__message')]");
    }

    private By dropdownOption(String optionText) {
        return By.xpath(
                "//div[contains(@class,'v-overlay--active')]"
                        + "//div[@role='option'][normalize-space()='" + optionText + "']");
    }

    // group 1 = Arabic plate, group 2 = English plate
    private By getPlateInput(String inputMode, int character, int group) {
        return By.xpath(
                "(//input[@aria-label='Please enter OTP character "
                        + character
                        + "' and @inputmode='"
                        + inputMode
                        + "'])[" + group + "]"
        );
    }

    // ==================== Locators: Date/Time Picker ====================

    private final By pickerContainer = By.cssSelector(".picker-container");

    private final By nextMonthBtn =
            By.cssSelector(".picker-container [data-testid='next-month']");

    private final By previousMonthBtn =
            By.cssSelector(".picker-container [data-testid='prev-month']");

    // Day buttons exist only for days of the displayed month (enabled or disabled)
    private final By displayedMonthDay =
            By.cssSelector(".picker-container .v-date-picker-month__day-btn[aria-label]");

    private final By confirmDateButton = By.xpath(
            "//div[contains(@class,'picker-container')]"
                    + "//div[contains(@class,'picker-actions')]"
                    + "//button[normalize-space()='Confirm']");

    private final By cancelDateButton = By.xpath(
            "//div[contains(@class,'picker-container')]"
                    + "//div[contains(@class,'picker-actions')]"
                    + "//button[normalize-space()='Cancel']");

    /*
     * Day buttons are identified by aria-label, e.g. "Sunday, November 1, 2026"
     * or "Today, Sunday, November 22, 2026". Disabled days have no data-v-date
     * attribute, so aria-label is the only attribute present on every day.
     */
    private By calendarDayButton(LocalDate date) {
        return By.xpath(
                "//div[contains(@class,'picker-container')]"
                        + "//button[contains(@class,'v-date-picker-month__day-btn')"
                        + " and contains(@aria-label, ', " + date.format(DAY_ARIA_LABEL_FORMAT) + "')]");
    }

    private By calendarDay(LocalDate date) {
        return By.xpath(
                "//div[contains(@class,'picker-container')]"
                        + "//button[contains(@class,'v-date-picker-month__day-btn')"
                        + " and contains(@aria-label, ', " + date.format(DAY_ARIA_LABEL_FORMAT) + "')]"
                        + "/parent::div");
    }

    // Exact class token match: 'time-input' must not match the 'time-inputs' wrapper
    private By timeInput(String label) {
        return By.xpath(
                "//div[contains(@class,'picker-container')]"
                        + "//div[contains(concat(' ',normalize-space(@class),' '),' time-input ')]"
                        + "[.//label[normalize-space()='" + label + "']]//input");
    }

    // ==================== Locators: Table & Pop-ups ====================

    private final By noDataRow = By.cssSelector("tr.v-data-table-rows-no-data");

    private final By popupContainer = By.cssSelector(".swal2-container");

    private final By errorPopup = By.cssSelector(".swal2-popup.swal2-icon-error");

    private final By popupConfirmBtn = By.cssSelector(".swal2-confirm");

    private String permitRow(SaudiPlate plate) {
        return "//tbody//tr[td[2]"
                + "[.//span[normalize-space()='" + englishLetters(plate) + "']]"
                + "[.//span[normalize-space()='" + plate.getPlateNumber() + "']]]";
    }

    private By permitRowLocator(SaudiPlate plate) {
        return By.xpath(permitRow(plate));
    }

    private By permitCell(SaudiPlate plate, int column) {
        return By.xpath(permitRow(plate) + "/td[" + column + "]");
    }

    private By permitDeleteBtn(SaudiPlate plate) {
        return By.xpath(permitRow(plate) + "//button[contains(@class,'delete')]");
    }

    // ==================== Plate Helpers ====================

    private static String englishLetters(SaudiPlate plate) {
        return plate.getPlateEn().split(" ")[1];
    }

    /*
     * plateAr stores the letters in right-to-left display order (as shown in the table).
     * The OTP inputs are left-to-right and the application keeps the Arabic and English
     * inputs in sync position by position, so the Arabic letters are typed reversed.
     */
    private static String arabicLettersInInputOrder(SaudiPlate plate) {
        return new StringBuilder(plate.getPlateAr().split(" ")[0]).reverse().toString();
    }

    private static boolean sameCharacter(String current, char expected) {
        if (current.length() != 1) {
            return false;
        }
        char actual = current.charAt(0);
        if (Character.isDigit(actual) && Character.isDigit(expected)) {
            // Arabic plate inputs display Arabic-Indic digits
            return Character.getNumericValue(actual) == Character.getNumericValue(expected);
        }
        return actual == expected;
    }

    // One character per OTP input. The app may already have filled the input
    // from the other plate group, in which case typing it again is skipped.
    private void typePlateCharacter(By input, char character) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(input));
        String current = element.getDomProperty("value");

        if (current != null && sameCharacter(current, character)) {
            return;
        }
        if (current != null && !current.isEmpty()) {
            element.clear();
        }
        element.sendKeys(String.valueOf(character));
    }

    private void enterPlateNumbers(String number, int group) {
        for (int i = 0; i < number.length(); i++) {
            typePlateCharacter(getPlateInput("numeric", i + 1, group), number.charAt(i));
        }
    }

    private void enterPlateLetters(String letters, int group) {
        for (int i = 0; i < letters.length(); i++) {
            typePlateCharacter(getPlateInput("text", i + 1, group), letters.charAt(i));
        }
    }

    private String readPlateInputs(String inputMode, int count, int group) {
        StringBuilder value = new StringBuilder();
        for (int i = 1; i <= count; i++) {
            String character = driver.findElement(getPlateInput(inputMode, i, group)).getDomProperty("value");
            value.append(character == null ? "" : character);
        }
        return value.toString();
    }

    private String getEnteredPlate(int group) {
        return readPlateInputs("text", 3, group) + " " + readPlateInputs("numeric", 4, group);
    }

    // ==================== Form Actions ====================

    @Step("Click Add Permits Button")
    public PermitsPage clickAddPermitBtn() {
        jsClick(addPermitBtn);
        permitForm = wait.until(ExpectedConditions.visibilityOfElementLocated(permitFormLocator));
        return this;
    }

    @Step("Enter Saudi Plate Number: {plate}")
    public PermitsPage enterPlateNumber(SaudiPlate plate) {

        String number = String.valueOf(plate.getPlateNumber());

        // Arabic plate - Group 1
        enterPlateLetters(arabicLettersInInputOrder(plate), ARABIC_PLATE_GROUP);
        enterPlateNumbers(number, ARABIC_PLATE_GROUP);

        // English plate - Group 2
        enterPlateLetters(englishLetters(plate), ENGLISH_PLATE_GROUP);
        enterPlateNumbers(number, ENGLISH_PLATE_GROUP);

        return this;
    }

    @Step("Enter plate numbers without letters: {plate}")
    public PermitsPage enterPlateNumberWithoutLetters(SaudiPlate plate) {
        String number = String.valueOf(plate.getPlateNumber());
        enterPlateNumbers(number, ARABIC_PLATE_GROUP);
        enterPlateNumbers(number, ENGLISH_PLATE_GROUP);
        return this;
    }

    @Step("Get entered Arabic plate (letters and numbers)")
    public String getEnteredPlateArabic() {
        return getEnteredPlate(ARABIC_PLATE_GROUP);
    }

    @Step("Get entered English plate (letters and numbers)")
    public String getEnteredPlateEnglish() {
        return getEnteredPlate(ENGLISH_PLATE_GROUP);
    }

    private PermitsPage selectFromDropdown(String fieldLabel, String option) {
        click(selectField(fieldLabel));
        wait.until(ExpectedConditions.elementToBeClickable(dropdownOption(option))).click();
        wait.until(ExpectedConditions.textToBe(selectedValue(fieldLabel), option));
        return this;
    }

    @Step("Select Permit Type: {type}")
    public PermitsPage selectPermitType(String type) {
        return selectFromDropdown(TYPE_FIELD, type);
    }

    @Step("Select Project: {projectName}")
    public PermitsPage selectProject(String projectName) {
        return selectFromDropdown(PROJECT_FIELD, projectName);
    }

    // Native typing: setting the value through JavaScript (ElementActions.sendKeys)
    // updates the DOM but not the form model, so the server rejects the field.
    private void typeText(By locator, String text) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(locator));
        input.clear();
        input.sendKeys(text);
        wait.until(ExpectedConditions.domPropertyToBe(input, "value", text));
    }

    @Step("Enter Vehicle Type: {vehicleType}")
    public PermitsPage enterVehicleType(String vehicleType) {
        typeText(vehicleTypeInput, vehicleType);
        return this;
    }

    @Step("Enter Driver: {driverName}")
    public PermitsPage enterDriver(String driverName) {
        typeText(driverInput, driverName);
        return this;
    }

    @Step("Enter Reason for visit: {reason}")
    public PermitsPage enterVisitReason(String reason) {
        typeText(visitReasonInput, reason);
        return this;
    }

    @Step("Click Save Permit")
    public PermitsPage clickSavePermit() {
        return clickSaveButton();
    }

    @Step("Click Close Permit form")
    public PermitsPage clickClosePermitForm() {
        click(closeFormBtn);
        return this;
    }

    @Step("Check permit form is open")
    public boolean isPermitFormOpen() {
        try {
            return permitForm != null && permitForm.isDisplayed();
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }

    @Step("Wait for permit form to close")
    public boolean isPermitFormClosed() {
        try {
            return wait.until(ExpectedConditions.invisibilityOf(permitForm));
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Get validation message of field: {fieldLabel}")
    public String getValidationMessage(String fieldLabel) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(validationMessage(fieldLabel)))
                .getText().trim();
    }

    // ==================== Calendar Actions ====================

    private void openDatePicker(By dateInput) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(dateInput));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", input);
        input.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(pickerContainer));
    }

    private YearMonth getDisplayedMonth() {
        return wait.until(d -> {
            try {
                // "Today, Sunday, November 22, 2026" -> "November 22, 2026"
                String label = d.findElement(displayedMonthDay).getDomAttribute("aria-label");
                Matcher matcher = DAY_ARIA_LABEL_DATE.matcher(label);
                return matcher.find()
                        ? YearMonth.from(LocalDate.parse(matcher.group(), DAY_ARIA_LABEL_FORMAT))
                        : null;
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }

    // Returns false when the target month cannot be reached (navigation button disabled)
    private boolean navigateToMonth(YearMonth target) {
        for (int i = 0; i < MAX_MONTH_NAVIGATIONS; i++) {
            YearMonth displayed = getDisplayedMonth();
            if (displayed.equals(target)) {
                return true;
            }

            WebElement navButton = driver.findElement(
                    target.isAfter(displayed) ? nextMonthBtn : previousMonthBtn);
            if (!navButton.isEnabled()) {
                return false;
            }
            navButton.click();
            wait.until(d -> !displayed.equals(getDisplayedMonth()));
        }
        throw new IllegalStateException("Could not navigate the date picker to " + target);
    }

    private void setTimeField(String label, int value) {
        // Locale.ROOT keeps Latin digits on machines with an Arabic default locale
        String expected = String.format(Locale.ROOT, "%02d", value);
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(timeInput(label)));

        // The field resets to "00" when cleared and shifts typed digits in from the right
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        input.sendKeys(expected);

        wait.until(ExpectedConditions.domPropertyToBe(input, "value", expected));
    }

    @Step("Open Start Date picker")
    public PermitsPage openStartDatePicker() {
        openDatePicker(startDateInput);
        return this;
    }

    @Step("Open End Date picker")
    public PermitsPage openEndDatePicker() {
        openDatePicker(endDateInput);
        return this;
    }

    @Step("Select day in date picker: {date}")
    public PermitsPage selectDayInPicker(LocalDate date) {
        if (!navigateToMonth(YearMonth.from(date))) {
            throw new IllegalArgumentException("Month of " + date + " is not reachable in the date picker");
        }

        WebElement dayButton = wait.until(ExpectedConditions.presenceOfElementLocated(calendarDayButton(date)));
        if (!dayButton.isEnabled()) {
            throw new IllegalArgumentException("Date " + date + " is disabled in the date picker");
        }
        // A native click can be intercepted by the time section or miss while the menu
        // is still animating; the selected-state check below verifies the result.
        js.executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", dayButton);

        // Confirms the requested day is selected (not just today's highlighted day)
        wait.until(ExpectedConditions.attributeContains(
                calendarDay(date), "class", "v-date-picker-month__day--selected"));
        return this;
    }

    @Step("Set time in date picker: {time}")
    public PermitsPage setTimeInPicker(LocalTime time) {
        setTimeField("Hours", time.getHour());
        setTimeField("Minutes", time.getMinute());
        return this;
    }

    @Step("Type hours in date picker: {hours}")
    public PermitsPage typeHoursInPicker(String hours) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(timeInput("Hours")));
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        input.sendKeys(hours);
        return this;
    }

    @Step("Get hours value in date picker")
    public String getHoursInPicker() {
        return driver.findElement(timeInput("Hours")).getDomProperty("value");
    }

    @Step("Check day is selectable in date picker: {date}")
    public boolean isDaySelectableInPicker(LocalDate date) {
        if (!navigateToMonth(YearMonth.from(date))) {
            return false;
        }
        return driver.findElement(calendarDayButton(date)).isEnabled();
    }

    @Step("Confirm selected date and time")
    public PermitsPage confirmDatePicker() {
        WebElement picker = driver.findElement(pickerContainer);
        wait.until(ExpectedConditions.elementToBeClickable(confirmDateButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(picker));
        return this;
    }

    @Step("Cancel date picker")
    public PermitsPage cancelDatePicker() {
        WebElement picker = driver.findElement(pickerContainer);
        wait.until(ExpectedConditions.elementToBeClickable(cancelDateButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(picker));
        return this;
    }

    private PermitsPage selectDateTime(By dateInput, LocalDate date, LocalTime time) {
        openDatePicker(dateInput);
        selectDayInPicker(date);
        setTimeInPicker(time);
        confirmDatePicker();

        String expected = date.atTime(time).format(DATE_TIME_VALUE_FORMAT);
        wait.until(ExpectedConditions.domPropertyToBe(driver.findElement(dateInput), "value", expected));
        return this;
    }

    // ==================== Public Date Methods ====================

    @Step("Select Start Date and Time: {date} {time}")
    public PermitsPage selectStartDateTime(LocalDate date, LocalTime time) {
        return selectDateTime(startDateInput, date, time);
    }

    @Step("Select End Date and Time: {date} {time}")
    public PermitsPage selectEndDateTime(LocalDate date, LocalTime time) {
        return selectDateTime(endDateInput, date, time);
    }

    @Step("Get Start Date value")
    public String getStartDateValue() {
        return driver.findElement(startDateInput).getDomProperty("value");
    }

    @Step("Get End Date value")
    public String getEndDateValue() {
        return driver.findElement(endDateInput).getDomProperty("value");
    }

    // ==================== Pop-ups ====================

    @Step("Check error pop-up is displayed")
    public boolean isErrorPopupDisplayed() {
        return isElementPresent(errorPopup);
    }

    @Step("Wait for pop-up to close")
    public PermitsPage waitForPopupToClose() {
        WebElement popup = driver.findElement(popupContainer);
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.invisibilityOf(popup));
        return this;
    }

    // ==================== Table ====================

    @Step("Check permit is displayed in table: {plate}")
    public boolean isPermitDisplayedInTable(SaudiPlate plate) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(permitRowLocator(plate)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Check table shows no data")
    public boolean isNoDataDisplayedInTable() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(noDataRow));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Get permit row")
    public WebElement getPermitRow(SaudiPlate plate) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(permitRowLocator(plate)));
    }

    @Step("Get English plate in table")
    public String getPlateEnglishInTable(SaudiPlate plate) {
        return getText(permitCell(plate, 2));
    }

    @Step("Get Arabic plate in table")
    public String getPlateArabicInTable(SaudiPlate plate) {
        return getText(permitCell(plate, 3));
    }

    @Step("Get driver in table")
    public String getDriverInTable(SaudiPlate plate) {
        return getText(permitCell(plate, 4));
    }

    @Step("Get project in table")
    public String getProjectInTable(SaudiPlate plate) {
        return getText(permitCell(plate, 5));
    }

    @Step("Get permit type in table")
    public String getPermitTypeInTable(SaudiPlate plate) {
        return getText(permitCell(plate, 6));
    }

    @Step("Delete permit: {plate}")
    public PermitsPage deletePermit(SaudiPlate plate) {
        WebElement row = getPermitRow(plate);
        click(permitDeleteBtn(plate));
        wait.until(ExpectedConditions.elementToBeClickable(popupConfirmBtn)).click();
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.invisibilityOf(row));
        return this;
    }
}
