package tests;

import base.BaseTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.admin.BasePage;
import pages.admin.UsersPage;
import utilities.AuthHelper;
import utils.Assertions;

import static dataReader.ReadDataFromJson.dataModel;

public class UsersTests extends BaseTests {

    BasePage basePage;
    UsersPage usersPage;

    @BeforeMethod
    public void setupAdminSession() {
        openAdmin();
        basePage = AuthHelper.login(driver);
    }

    @Test(priority = 1)
    public void shouldAddEditAndDeleteUsersSuccessfully(){

        // ====== Control Panel ======
        usersPage = basePage.openUsers();
        usersPage
                .clickAddUserButton()
                .enterName(dataModel().Users.name)
                .enterPhoneNumber(dataModel().Users.code,dataModel().Users.phone)
                .selectRole(dataModel().Users.role)
                .enterEmail(dataModel().Users.email)
                .selectGender(dataModel().Users.gender)
                .enterPassword(dataModel().Users.password)
                .enterConfirmPassword(dataModel().Users.confirmPassword)
                .clickAddButton();

        Assertions.myAssertTrue(
                basePage.isSuccessIconDisplayed()
                        && basePage.isSuccessMessageDisplayed(),
                "Success popup is not displayed correctly"
        );

        Assertions.myAssertEquals(
                basePage.getSuccessMessage(),
                "Created successfully");

        usersPage
                .closePopUpIcon()
                .searchInputs(dataModel().Users.name);

        Assertions.myAssertEquals(
                basePage.getTableSearchResult(),
                dataModel().Users.name);

        usersPage
                .searchInputs(dataModel().Users.name)
                .clickSearchResult()
                .clickEdit()
                .enterName(dataModel().Users.editName)
                .enterEmail(dataModel().Users.editEmail)
                .enterPassword(dataModel().Users.editPassword)
                .enterConfirmPassword(dataModel().Users.editConfirmPassword)
                .clickSubmit();


        Assertions.myAssertTrue(
                basePage.isSuccessIconDisplayed()
                        && basePage.isSuccessMessageDisplayed(),
                "Success popup is not displayed correctly"
        );

        Assertions.myAssertEquals(
                basePage.getSuccessMessage(),
                "Updated successfully"
        );

        usersPage
                .closePopUpIcon()
                .searchInputs(dataModel().Users.editName);

        Assertions.myAssertEquals(
                basePage.getTableSearchResult(),
                dataModel().Users.editName);

        usersPage

                .searchInputs(dataModel().Users.editName)
                .clickSearchResult()
                .clickDelete()
                .clickConfirmDelete();

        Assertions.myAssertTrue(
                basePage.isSuccessIconDisplayed()
                        && basePage.isSuccessMessageDisplayed(),
                "Success popup is not displayed correctly");

        Assertions.myAssertEquals(
                basePage.getSuccessMessage(),
                "Deleted successfully");

        usersPage
                .closePopUpIcon()
                .clearSearchInputs()
                .searchInputs(dataModel().Users.editName);

        Assertions.myAssertTrue(
                basePage.isNoDataMessageCorrect(),
                "No data message is not displayed after search"
        );

    }
}


