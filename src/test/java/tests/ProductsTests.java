package tests;

import base.BaseTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.admin.BasePage;
import pages.admin.ProductsPage;
import pages.website.ServicesAndSolutionsPage;
import utilities.AuthHelper;
import utils.Assertions;

import static dataReader.ReadDataFromJson.dataModel;

public class ProductsTests extends BaseTests {

    BasePage basePage;
    ProductsPage productsPage;
    ServicesAndSolutionsPage servicesAndSolutionsPage;
    private String imageSrc;
    private String iconSrc;


    @BeforeMethod
    public void setupAdminSession() {
        openAdmin();
        basePage = AuthHelper.login(driver);
    }

    @Test(priority = 1)
    public void shouldAddEditAndDeleteProductSuccessfully(){

        // ====== Control Panel ======
        productsPage = basePage.openProducts();
        productsPage
                .clickAddButton()
                .enterProductTitleArabic(dataModel().Products.titleArabic)
                .enterProductTitleEnglish(dataModel().Products.titleEnglish)
                .enterProductDescriptionArabic(dataModel().Products.descriptionAR)
                .enterProductDescriptionEnglish(dataModel().Products.descriptionEN)
                .selectCategoryDDL(dataModel().Products.category)
                .uploadImage(System.getProperty("user.dir")+ "/src/test/resources/images/" + dataModel().Products.image)
                .enterFeaturesTitleArabic(dataModel().Products.featureTitleArabic)
                .enterFeaturesTitleEnglish(dataModel().Products.featureTitleEnglish)
                .selectGroupDDL(dataModel().Products.group)
                .uploadIcon(System.getProperty("user.dir")+ "/src/test/resources/images/" + dataModel().Products.icon)
                .enterSeoTitleArabic(dataModel().Products.seoTitleArabic)
                .enterSeoTitleEnglish(dataModel().Products.seoTitleEnglish)
                .enterSeoDescriptionArabic(dataModel().Products.seoDescriptionArabic)
                .enterSeoDescriptionEnglish(dataModel().Products.seoDescriptionEnglish)
                .enterKeywords(dataModel().Products.keywords)
                .clickAddPopUp();

        Assertions.myAssertTrue(
                basePage.isSuccessIconDisplayed()
                        && basePage.isSuccessMessageDisplayed(),
                "Success popup is not displayed correctly"
        );
        Assertions.myAssertEquals(
                basePage.getSuccessMessage(),
                "Created successfully"
        );

        productsPage
                .closePopUpIcon()
                .searchInputs(dataModel().Products.titleEnglish);

        Assertions.myAssertEquals(
                basePage.getTableSearchResult(),
                dataModel().Products.titleEnglish);

        imageSrc = productsPage.openProductAndGetImage();
        iconSrc = productsPage.openProductAndGetIcon();

        System.out.println("Image SRC = " + imageSrc);
        System.out.println("Icon SRC = " + iconSrc);

        // ====== Website ======

        openWebsite();
        servicesAndSolutionsPage = basePage.openServicesAndSolutions();

        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isElementDisplayed(servicesAndSolutionsPage::categoryCard,dataModel().Products.category),
                "Category is not displayed on website");

        servicesAndSolutionsPage
                .openCategory(dataModel().Products.category);

        // Product
        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isProductLogoDisplayed(imageSrc,dataModel().Products.titleEnglish),
                "Product is not displayed on website");

        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isElementDisplayed(servicesAndSolutionsPage::productCard,dataModel().Products.titleEnglish),
                "Product is not displayed on website");

        servicesAndSolutionsPage
                .openProduct(dataModel().Products.titleEnglish);

        // Inside product
        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isElementDisplayed(servicesAndSolutionsPage::productTitle,dataModel().Products.titleEnglish),
                "Product is not displayed on website");

        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isProductLogoDisplayed(imageSrc,dataModel().Products.titleEnglish),
                "Product is not displayed on website");

        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isProductLogoDisplayed(iconSrc,dataModel().Products.featureTitleEnglish),
                "Product is not displayed on website");

        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isElementDisplayed(servicesAndSolutionsPage::productDescription,dataModel().Products.descriptionEN),
                "Product description not displayed on website");

        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isElementDisplayed(servicesAndSolutionsPage::groupTitle,dataModel().Products.group),
                "Product group is not displayed on website");

        // ====== Control Panel ======

        openAdmin();

     productsPage = basePage.openProducts();
        productsPage
                .searchInputs(dataModel().Products.titleEnglish)
                .clickSearchResult()
                .clickEditProduct()
                .enterProductTitleArabic(dataModel().Products.editTitleArabic)
                .enterProductTitleEnglish(dataModel().Products.editTitleEnglish)
                .enterProductDescriptionArabic(dataModel().Products.editDescriptionAR)
                .enterProductDescriptionEnglish(dataModel().Products.editDescriptionEN)
                .selectCategoryDDL(dataModel().Products.editCategory)
                .uploadImage(System.getProperty("user.dir")+ "/src/test/resources/images/" + dataModel().Products.image)
                .enterFeaturesTitleArabic(dataModel().Products.editFeatureTitleArabic)
                .enterFeaturesTitleEnglish(dataModel().Products.editFeatureTitleEnglish)
                .selectGroupDDL(dataModel().Products.editGroup)
                .uploadIcon(System.getProperty("user.dir")+ "/src/test/resources/images/" + dataModel().Products.icon)
                .enterSeoTitleArabic(dataModel().Products.editSeoTitleArabic)
                .enterSeoTitleEnglish(dataModel().Products.editSeoTitleEnglish)
                .enterSeoDescriptionArabic(dataModel().Products.editSeoDescriptionArabic)
                .enterSeoDescriptionEnglish(dataModel().Products.editSeoDescriptionEnglish)
                .clickAddPopUp();


        Assertions.myAssertTrue(
                basePage.isSuccessIconDisplayed()
                        && basePage.isSuccessMessageDisplayed(),
                "Success popup is not displayed correctly"
        );
        Assertions.myAssertEquals(
                basePage.getSuccessMessage(),
                "Updated successfully"
        );

        productsPage
                .closePopUpIcon()
                .searchInputs(dataModel().Products.editTitleEnglish);

        Assertions.myAssertEquals(
                basePage.getTableSearchResult(),
                dataModel().Products.editTitleEnglish);

        imageSrc = productsPage.openProductAndGetImage();
        iconSrc = productsPage.openProductAndGetIcon();

        System.out.println("Image SRC = " + imageSrc);
        System.out.println("Icon SRC = " + iconSrc);

        // ====== Website ======

        openWebsite();
        servicesAndSolutionsPage = basePage.openServicesAndSolutions();

        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isElementDisplayed(servicesAndSolutionsPage::categoryCard,dataModel().Products.editCategory),
                "Category is not displayed on website");

        servicesAndSolutionsPage
                .openCategory(dataModel().Products.editCategory);

        // Product
        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isProductLogoDisplayed(imageSrc,dataModel().Products.editTitleEnglish),
                "Product is not displayed on website");

        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isElementDisplayed(servicesAndSolutionsPage::productCard,dataModel().Products.editTitleEnglish),
                "Product is not displayed on website");

        servicesAndSolutionsPage
                .openProduct(dataModel().Products.editTitleEnglish);

        // Inside product
        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isElementDisplayed(servicesAndSolutionsPage::productTitle,dataModel().Products.editTitleEnglish),
                "Product is not displayed on website");

        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isProductLogoDisplayed(imageSrc,dataModel().Products.editTitleEnglish),
                "Product is not displayed on website");

        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isProductLogoDisplayed(iconSrc,dataModel().Products.editFeatureTitleEnglish),
                "Product is not displayed on website");

        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isElementDisplayed(servicesAndSolutionsPage::productDescription,dataModel().Products.editDescriptionEN),
                "Product description not displayed on website");

        Assertions.myAssertTrue(
                servicesAndSolutionsPage.isElementDisplayed(servicesAndSolutionsPage::groupTitle,dataModel().Products.editGroup),
                "Product group is not displayed on website");

        // ====== Control Panel ======

        openAdmin();

        productsPage = basePage.openProducts();
        productsPage
                .searchInputs(dataModel().Products.editTitleEnglish)
                .clickSearchResult()
                .clickEditProduct()
                .clickDeleteProduct()
                .clickConfirmDeleteProduct();

        Assertions.myAssertEquals(
                basePage.getSuccessMessage(),
                "Deleted successfully"
        );

//        productsPage
//                .closePopUpIcon()
//                .clearSearchInputs()
//                .searchInputs(dataModel().Products.editTitleEnglish);
//
//        Assertions.myAssertTrue(
//                basePage.isNoDataMessageCorrect(),
//                "No data message is not displayed after search"
//        );

        // ====== Website ======
//
//        openWebsite();
//        servicesAndSolutionsPage = basePage.openServicesAndSolutions();
//        servicesAndSolutionsPage
//                .openCategory(dataModel().Products.editCategory);
//
//        Assertions.myAssertTrue(
//                servicesAndSolutionsPage.isProductLogoNotDisplayed(imageSrc , dataModel().Products.editTitleEnglish),
//                "Product is still displayed on website ❌");

    }
}