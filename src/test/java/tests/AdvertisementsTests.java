package tests;

import base.BaseTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.admin.BasePage;
import pages.admin.DataEntry.CategoryPage;
import pages.website.ServicesAndSolutionsPage;
import utilities.AuthHelper;
import utils.Assertions;

import static dataReader.ReadDataFromJson.dataModel;

public class CategoryTests extends BaseTests {

    BasePage basePage;
    CategoryPage categoryPage;
    ServicesAndSolutionsPage servicesAndSolutionsPage;

    @BeforeMethod
    public void setupAdminSession() {
        openAdmin();
        basePage = AuthHelper.login(driver);
    }

    @Test(priority = 1)
    public void shouldAddEditAndDeleteCategorySuccessfully(){

        // ====== Control Panel ======
        categoryPage = basePage.openCategory();

        categoryPage
                .clickAddButton()
                .enterArabicTitle(dataModel().Category.titleAr)
                .enterEnglishTitle(dataModel().Category.titleEn)
                .enterArabicDescription(dataModel().Category.descriptionAr)
                .enterEnglishDescription(dataModel().Category.descriptionEn)
                .uploadIcon(System.getProperty("user.dir")+ "/src/test/resources/images/" + dataModel().Category.icon)
                .enterSEOArabicTitle(dataModel().Category.nameArSEO)
                .enterSEOEnglishTitle(dataModel().Category.nameEnSEO)
                .enterSEOArabicDescription(dataModel().Category.descriptionArSEO)
                .enterSEOEnglishDescription(dataModel().Category.descriptionEnSEO)
                .enterKeyWords(dataModel().Category.keywords)
                .clickSubmit();

        Assertions.myAssertTrue(
                basePage.isSuccessIconDisplayed()
                        && basePage.isSuccessMessageDisplayed(),
                "Success popup is not displayed correctly"
        );
        Assertions.myAssertEquals(
                basePage.getSuccessMessage(),
                "Created successfully"
        );

        // ====== Control Panel ======
        categoryPage
                .closePopUpIcon()
                .searchInputs(dataModel().Category.titleEn);

        Assertions.myAssertEquals(
                basePage.getTableSearchResult(),
                dataModel().Category.titleEn);

        // ====== Website ======
        openWebsite();
        servicesAndSolutionsPage = basePage.openServicesAndSolutions();
        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isElementDisplayed(servicesAndSolutionsPage::categoryCard,dataModel().Category.titleEn),
                "Category is not displayed on website");

        // ====== Control Panel ======

        openAdmin();
        categoryPage = basePage.openCategory();
        categoryPage
                .searchInputs(dataModel().Category.titleEn)
                .clickSearchResult()
                .clickEdit()
                .enterArabicTitle(dataModel().Category.editTitleAr)
                .enterEnglishTitle(dataModel().Category.editTitleEn)
                .enterSEOArabicTitle(dataModel().Category.editNameArSEO)
                .enterSEOEnglishTitle(dataModel().Category.editNameEnSEO)
                .enterSEOArabicDescription(dataModel().Category.editDescriptionArSEO)
                .enterSEOEnglishDescription(dataModel().Category.editDescriptionEnSEO)
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

        // ====== Control Panel ======
        categoryPage
                .closePopUpIcon()
                .searchInputs(dataModel().Category.editTitleEn);

        Assertions.myAssertEquals(
                basePage.getTableSearchResult(),
                dataModel().Category.editTitleEn);

        // ====== Website ======
        openWebsite();
        servicesAndSolutionsPage = basePage.openServicesAndSolutions();
        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isElementDisplayed(servicesAndSolutionsPage::categoryCard,dataModel().Category.editTitleEn),
                "Category is not displayed on website");

        // ====== Control Panel ======
        openAdmin();
        categoryPage = basePage.openCategory();
        categoryPage
                .searchInputs(dataModel().Category.editTitleEn)
                .clickSearchResult()
                .clickDelete();

        // Assertion on control panel
        Assertions.myAssertTrue(
                basePage.isSuccessIconDisplayed()
                        && basePage.isSuccessMessageDisplayed(),
                "Success popup is not displayed correctly"
        );
        Assertions.myAssertEquals(
                basePage.getSuccessMessage(),
                "Deleted successfully"
        );

        // ====== Control Panel ======

        categoryPage
                .closePopUpIcon()
                .clearSearchInputs()
                .searchInputs(dataModel().Category.editTitleEn);

        // assertion on control panel
        Assertions.myAssertTrue(
                basePage.isNoDataMessageCorrect(),
                "No data message is not displayed after search"
        );

    }
}


