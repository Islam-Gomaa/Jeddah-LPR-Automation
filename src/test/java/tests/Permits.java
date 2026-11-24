package tests;

import static dataReader.ReadDataFromJson.dataModel;

import api.SaudiPlate;
import api.SaudiPlateGenerator;
import base.BaseTests;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.*;
import pages.BasePage;
import pages.PermitsPage;
import utilities.AuthHelper;
import utils.Assertions;
import utils.TextUtils;

public class Permits extends BaseTests {

    // Test data not yet available in testData.json
    private static final String VEHICLE_TYPE = "Sedan";
    private static final String PROJECT = "test new project";
    private static final String DRIVER = "Automation Driver";
    private static final String VISIT_REASON = "Automation visit";

    // Messages observed in the application
    private static final String CREATED_MESSAGE = "Created successfully";
    private static final String REQUIRED_FIELD_MESSAGE = "This field is required";
    private static final String INCOMPLETE_PLATE_MESSAGE = "Please enter both letters and numbers";
    private static final String TYPE_REQUIRED_MESSAGE = "The type field is required.";

    private static final LocalTime START_TIME = LocalTime.of(8, 30);
    private static final LocalTime END_TIME = LocalTime.of(23, 45);

    enum RequiredField { PLATE, VEHICLE_TYPE, PROJECT, START_DATE, END_DATE }

    private BasePage basePage;
    private PermitsPage permitsPage;
    private final List<SaudiPlate> createdPermits = new ArrayList<>();

    // Admin Login
    @BeforeMethod
    public void setupAdminSession() {
        openSystem();
        basePage = AuthHelper.login(driver);
        permitsPage = basePage.openPermits();
    }

    // Delete permits created by the test so each test stays independent
    @AfterMethod(alwaysRun = true)
    public void deleteCreatedPermits() {
        for (SaudiPlate plate : createdPermits) {
            try {
                driver.navigate().refresh();
                permitsPage = new PermitsPage(driver);
                permitsPage.searchInputs(String.valueOf(plate.getPlateNumber()));
                if (permitsPage.isPermitDisplayedInTable(plate)) {
                    permitsPage.deletePermit(plate);
                }
            } catch (RuntimeException e) {
                System.out.println("Cleanup failed for permit " + plate + ": " + e.getMessage());
            }
        }
        createdPermits.clear();
    }

    // ==================== Helpers ====================

    private static LocalDate today() {
        return LocalDate.now();
    }

    private static LocalDate lastDayOfCurrentMonth() {
        return today().withDayOfMonth(today().lengthOfMonth());
    }

    private static String plateLettersEn(SaudiPlate plate) {
        return plate.getPlateEn().split(" ")[1];
    }

    private static String plateLettersAr(SaudiPlate plate) {
        return plate.getPlateAr().split(" ")[0];
    }

    // Fills a valid Employee permit, leaving out the given required field
    private void fillEmployeePermitExcept(SaudiPlate plate, RequiredField omitted) {
        permitsPage.clickAddPermitBtn();

        if (omitted != RequiredField.PLATE) {
            permitsPage.enterPlateNumber(plate);
        }
        permitsPage.selectPermitType(dataModel().Permits.employeeType);
        if (omitted != RequiredField.VEHICLE_TYPE) {
            permitsPage.enterVehicleType(VEHICLE_TYPE);
        }
        if (omitted != RequiredField.PROJECT) {
            permitsPage.selectProject(PROJECT);
        }
        permitsPage.enterDriver(DRIVER);
        if (omitted != RequiredField.START_DATE) {
            permitsPage.selectStartDateTime(today(), START_TIME);
        }
        if (omitted != RequiredField.END_DATE) {
            permitsPage.selectEndDateTime(lastDayOfCurrentMonth(), END_TIME);
        }
    }

    private void verifyPermitCreated(SaudiPlate plate, String expectedType) {
        Assertions.myAssertTrue(permitsPage.isPermitFormClosed(),
                "Permit form did not close after saving");

        permitsPage.waitForSuccessMessage(CREATED_MESSAGE);
        Assertions.myAssertEquals(
                permitsPage.getSuccessMessage(),
                permitsPage.getSuccessMessageText(),
                CREATED_MESSAGE,
                "Permit was not created");

        permitsPage.waitForPopupToClose()
                .searchInputs(String.valueOf(plate.getPlateNumber()));

        Assertions.myAssertTrue(permitsPage.isPermitDisplayedInTable(plate),
                "Created permit is not displayed in the table: " + plate);

        Assertions.myAssertEqualsIgnoreSpaces(
                permitsPage.getPermitRow(plate),
                permitsPage.getPlateEnglishInTable(plate),
                plateLettersEn(plate) + "-" + plate.getPlateNumber(),
                "Incorrect English plate in table");

        Assertions.myAssertEqualsIgnoreSpaces(
                permitsPage.getPermitRow(plate),
                TextUtils.normalizeArabicNumbers(permitsPage.getPlateArabicInTable(plate)),
                plateLettersAr(plate) + "-" + plate.getPlateNumber(),
                "Incorrect Arabic plate in table");

        Assertions.myAssertEquals(
                permitsPage.getPermitRow(plate),
                permitsPage.getPermitTypeInTable(plate),
                expectedType.toUpperCase(),
                "Incorrect permit type in table");
    }

    // ==================== Positive Scenarios ====================

    @Test(description = "Add an Employee permit with a generated Saudi plate, "
            + "today as Start Date and the last day of the month as End Date")
    public void addEmployeePermitWithGeneratedSaudiPlate() {
        SaudiPlate plate = SaudiPlateGenerator.generateRandomPlate();
        System.out.println("Generated Plate: " + plate);

        fillEmployeePermitExcept(plate, null);

        Assertions.myAssertEquals(null, permitsPage.getStartDateValue(),
                today() + " 08:30", "Start Date was not set to today");
        Assertions.myAssertEquals(null, permitsPage.getEndDateValue(),
                lastDayOfCurrentMonth() + " 23:45", "End Date was not set to the last day of the month");

        createdPermits.add(plate);
        permitsPage.clickSavePermit();

        verifyPermitCreated(plate, dataModel().Permits.employeeType);

        Assertions.myAssertEquals(permitsPage.getPermitRow(plate),
                permitsPage.getProjectInTable(plate), PROJECT, "Incorrect project in table");
        Assertions.myAssertEquals(permitsPage.getPermitRow(plate),
                permitsPage.getDriverInTable(plate), DRIVER, "Incorrect driver in table");
    }

    @Test(description = "Add a Visitor permit with a reason for visit")
    public void addVisitorPermitWithVisitReason() {
        SaudiPlate plate = SaudiPlateGenerator.generateRandomPlate();
        System.out.println("Generated Plate: " + plate);

        permitsPage.clickAddPermitBtn()
                .enterPlateNumber(plate)
                .selectPermitType(dataModel().Permits.visitorType)
                .enterVehicleType(VEHICLE_TYPE)
                .enterDriver(DRIVER)
                .enterVisitReason(VISIT_REASON)
                .selectStartDateTime(today(), START_TIME)
                .selectEndDateTime(lastDayOfCurrentMonth(), END_TIME);

        createdPermits.add(plate);
        permitsPage.clickSavePermit();

        verifyPermitCreated(plate, dataModel().Permits.visitorType);
    }

    @Test(description = "Arabic and English plate inputs hold the generated plate characters")
    public void enterGeneratedPlateFillsArabicAndEnglishCharacters() {
        SaudiPlate plate = SaudiPlateGenerator.generateRandomPlate();
        System.out.println("Generated Plate: " + plate);

        permitsPage.clickAddPermitBtn()
                .enterPlateNumber(plate);

        Assertions.myAssertEquals(null,
                permitsPage.getEnteredPlateEnglish(),
                plateLettersEn(plate) + " " + plate.getPlateNumber(),
                "Incorrect English plate characters");

        // Inputs are left-to-right; plateAr letters are stored in right-to-left display order
        String arabicLettersInInputOrder = new StringBuilder(plateLettersAr(plate)).reverse().toString();
        Assertions.myAssertEquals(null,
                TextUtils.normalizeArabicNumbers(permitsPage.getEnteredPlateArabic()),
                arabicLettersInInputOrder + " " + plate.getPlateNumber(),
                "Incorrect Arabic plate characters");
    }

    @Test(description = "Select Start and End date/time in a different month than the current one")
    public void selectStartAndEndDateTimeInDifferentMonth() {
        LocalDate startDate = today().plusMonths(1).withDayOfMonth(15);
        LocalDate endDate = startDate.plusMonths(1).withDayOfMonth(1);
        LocalTime startTime = LocalTime.of(10, 15);
        LocalTime endTime = LocalTime.of(18, 5);

        permitsPage.clickAddPermitBtn()
                .selectStartDateTime(startDate, startTime)
                .selectEndDateTime(endDate, endTime);

        Assertions.myAssertEquals(null, permitsPage.getStartDateValue(),
                startDate + " 10:15", "Incorrect Start Date/Time");
        Assertions.myAssertEquals(null, permitsPage.getEndDateValue(),
                endDate + " 18:05", "Incorrect End Date/Time");
    }

    // ==================== Negative & Validation Scenarios ====================

    @DataProvider(name = "requiredFields")
    public Object[][] requiredFields() {
        return new Object[][]{
                {RequiredField.PLATE, new String[]{PermitsPage.PLATE_ARABIC_FIELD, PermitsPage.PLATE_ENGLISH_FIELD}},
                {RequiredField.VEHICLE_TYPE, new String[]{PermitsPage.VEHICLE_TYPE_FIELD}},
                {RequiredField.PROJECT, new String[]{PermitsPage.PROJECT_FIELD}},
                {RequiredField.START_DATE, new String[]{PermitsPage.START_DATE_FIELD}},
                {RequiredField.END_DATE, new String[]{PermitsPage.END_DATE_FIELD}},
        };
    }

    @Test(dataProvider = "requiredFields",
            description = "Saving an Employee permit without a required field shows a required-field message")
    public void saveEmployeePermitWithoutRequiredFieldShowsValidation(RequiredField omitted, String[] fieldLabels) {
        SaudiPlate plate = SaudiPlateGenerator.generateRandomPlate();

        fillEmployeePermitExcept(plate, omitted);
        permitsPage.clickSavePermit();

        for (String fieldLabel : fieldLabels) {
            Assertions.myAssertEquals(null,
                    permitsPage.getValidationMessage(fieldLabel),
                    REQUIRED_FIELD_MESSAGE,
                    "Missing required message for " + fieldLabel);
        }
        Assertions.myAssertTrue(permitsPage.isPermitFormOpen(),
                "Permit form closed although " + omitted + " is missing");
    }

    @Test(description = "Saving a permit without Type shows the server error pop-up")
    public void savePermitWithoutTypeShowsError() {
        SaudiPlate plate = SaudiPlateGenerator.generateRandomPlate();

        permitsPage.clickAddPermitBtn()
                .enterPlateNumber(plate)
                .enterVehicleType(VEHICLE_TYPE)
                .selectStartDateTime(today(), START_TIME)
                .selectEndDateTime(lastDayOfCurrentMonth(), END_TIME)
                .clickSavePermit()
                .waitForSuccessMessage(TYPE_REQUIRED_MESSAGE);

        Assertions.myAssertTrue(permitsPage.isErrorPopupDisplayed(),
                "Error pop-up is not displayed");
        Assertions.myAssertEquals(
                permitsPage.getSuccessMessage(),
                permitsPage.getSuccessMessageText(),
                TYPE_REQUIRED_MESSAGE,
                "Incorrect error message");
        Assertions.myAssertTrue(permitsPage.isPermitFormOpen(),
                "Permit form closed although Type is missing");
    }

    @Test(description = "Saving a plate with numbers but no letters shows an incomplete plate message")
    public void savePermitWithIncompletePlateShowsValidation() {
        SaudiPlate plate = SaudiPlateGenerator.generateRandomPlate();

        permitsPage.clickAddPermitBtn()
                .enterPlateNumberWithoutLetters(plate)
                .clickSavePermit();

        Assertions.myAssertEquals(null,
                permitsPage.getValidationMessage(PermitsPage.PLATE_ARABIC_FIELD),
                INCOMPLETE_PLATE_MESSAGE,
                "Incorrect Arabic plate validation");
        Assertions.myAssertEquals(null,
                permitsPage.getValidationMessage(PermitsPage.PLATE_ENGLISH_FIELD),
                INCOMPLETE_PLATE_MESSAGE,
                "Incorrect English plate validation");
        Assertions.myAssertTrue(permitsPage.isPermitFormOpen(),
                "Permit form closed with an incomplete plate");
    }

    @Test(description = "Past dates cannot be selected as Start Date")
    public void pastDateIsNotSelectableAsStartDate() {
        permitsPage.clickAddPermitBtn()
                .openStartDatePicker();

        Assertions.myAssertFalse(permitsPage.isDaySelectableInPicker(today().minusDays(1)),
                "Yesterday is selectable as Start Date");
        Assertions.myAssertTrue(permitsPage.isDaySelectableInPicker(today()),
                "Today is not selectable as Start Date");

        permitsPage.cancelDatePicker();
    }

    @Test(description = "Hours above 23 are limited to 23 in the time picker")
    public void hoursAboveMaximumAreLimitedInTimePicker() {
        permitsPage.clickAddPermitBtn()
                .openStartDatePicker()
                .selectDayInPicker(today())
                .typeHoursInPicker("35");

        Assertions.myAssertEquals(null, permitsPage.getHoursInPicker(), "23",
                "Hours accepted a value above 23");

        permitsPage.cancelDatePicker();
    }

    @Test(description = "End Date picker does not allow dates earlier than the Start Date")
    public void endDateEarlierThanStartDateIsNotSelectable() {
        LocalDate startDate = today().plusDays(10);

        permitsPage.clickAddPermitBtn()
                .selectStartDateTime(startDate, START_TIME)
                .openEndDatePicker();

        Assertions.myAssertFalse(permitsPage.isDaySelectableInPicker(startDate.minusDays(1)),
                "A day before the Start Date is selectable as End Date");
        Assertions.myAssertTrue(permitsPage.isDaySelectableInPicker(startDate),
                "The Start Date itself is not selectable as End Date");

        permitsPage.cancelDatePicker();
    }

    @Test(description = "Moving the Start Date after the End Date clears the End Date")
    public void movingStartDateAfterEndDateClearsEndDate() {
        permitsPage.clickAddPermitBtn()
                .selectStartDateTime(today(), START_TIME)
                .selectEndDateTime(today().plusDays(3), END_TIME)
                .selectStartDateTime(today().plusDays(10), START_TIME);

        Assertions.myAssertEmpty(permitsPage.getEndDateValue(),
                "End Date was not cleared after moving Start Date after it");
    }

    @Test(description = "Closing the form without saving does not create a permit")
    public void closePermitFormWithoutSaving() {
        SaudiPlate plate = SaudiPlateGenerator.generateRandomPlate();

        permitsPage.clickAddPermitBtn()
                .enterPlateNumber(plate)
                .selectPermitType(dataModel().Permits.employeeType)
                .enterVehicleType(VEHICLE_TYPE)
                .clickClosePermitForm();

        Assertions.myAssertTrue(permitsPage.isPermitFormClosed(),
                "Permit form did not close");

        permitsPage.searchInputs(String.valueOf(plate.getPlateNumber()));

        Assertions.myAssertTrue(permitsPage.isNoDataDisplayedInTable(),
                "A permit was created although the form was closed: " + plate);
    }

    @Test(description = "Cancelling the date/time picker keeps the previously confirmed value")
    public void cancelDateTimeSelectionKeepsPreviousValue() {
        permitsPage.clickAddPermitBtn()
                .selectStartDateTime(today(), LocalTime.of(9, 15));

        String confirmedValue = permitsPage.getStartDateValue();

        permitsPage.openStartDatePicker()
                .selectDayInPicker(today().plusDays(1))
                .setTimeInPicker(LocalTime.of(17, 45))
                .cancelDatePicker();

        Assertions.myAssertEquals(null, permitsPage.getStartDateValue(), confirmedValue,
                "Start Date changed after cancelling the picker");
    }
}
