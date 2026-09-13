//package tests;
//
//import base.BaseTests;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//import pages.admin.BasePage;
//import pages.admin.DashboardPage;
//import pages.website.HomePage;
//import utilities.AuthHelper;
//import utils.Assertions;
//
//
//public class DashboardTests extends BaseTests {
//    BasePage basePage;
//    DashboardPage dashboardPage;
//
//    @BeforeMethod
//    public void setupAdminSession() {
//        openAdmin();
//        basePage = AuthHelper.login(driver);
//    }
//
//    @Test(priority = 1)
//    public void verifyDashboardTabs() {
//
//        dashboardPage = basePage.openDashboard();
//        dashboardPage = new DashboardPage(driver);
//
//        // Verify Blogs tab
//        Assertions.myAssertTrue(
//                dashboardPage.isBlogsTabDisplayed(),
//                "Blogs tab is not displayed");
//
//        dashboardPage.openBlogsDashboard();
//
//        Assertions.myAssertTrue(
//                dashboardPage.isBlogsTabSelected(),
//                "Blogs tab is not selected");
//
//        // Verify Products tab
//        Assertions.myAssertTrue(
//                dashboardPage.isProductsTabDisplayed(),
//                "Products tab is not displayed");
//
//        dashboardPage.openProductsDashboard();
//
//        Assertions.myAssertTrue(
//                dashboardPage.isProductsTabSelected(),
//                "Products tab is not selected");
//
//        // Verify Clients tab
//        Assertions.myAssertTrue(
//                dashboardPage.isClientsTabDisplayed(),
//                "Clients tab is not displayed");
//
//        dashboardPage.openClientsDashboard();
//
//        Assertions.myAssertTrue(
//                dashboardPage.isClientsTabSelected(),
//                "Clients tab is not selected");
//    }
//
//    @Test(priority = 2)
//    public void verifyBlogsDashboardContent() {
//
//        dashboardPage = basePage.openDashboard();
//
//        dashboardPage = new DashboardPage(driver);
//
//        dashboardPage.openBlogsDashboard();
//
//        Assertions.myAssertTrue(
//                dashboardPage.isArticlesCountDisplayed(),
//                "Articles count card is not displayed");
//
//        Assertions.myAssertTrue(
//                dashboardPage.isNewsCountDisplayed(),
//                "News count card is not displayed");
//
//        Assertions.myAssertTrue(
//                dashboardPage.isArticlesByDateChartDisplayed(),
//                "Articles by date chart is not displayed");
//
//        Assertions.myAssertTrue(
//                dashboardPage.isNewsByDateChartDisplayed(),
//                "News by date chart is not displayed");
//
//        Assertions.myAssertTrue(
//                dashboardPage.isBlogCategoriesChartDisplayed(),
//                "Blog categories chart is not displayed");
//    }
//
//    @Test(priority = 3)
//    public void verifyProductsDashboardContent() {
//
//        dashboardPage = basePage.openDashboard();
//
//        dashboardPage = new DashboardPage(driver);
//
//        dashboardPage.openProductsDashboard();
//
//        Assertions.myAssertTrue(
//                dashboardPage.isProductsCountDisplayed(),
//                "Products count card is not displayed");
//
//        Assertions.myAssertTrue(
//                dashboardPage.isProductCategoriesChartDisplayed(),
//                "Product categories chart is not displayed");
//
//        Assertions.myAssertTrue(
//                dashboardPage.isProductsByDateChartDisplayed(),
//                "Products by date chart is not displayed");
//    }
//
//    @Test(priority = 4)
//    public void verifyClientsDashboardContent() {
//
//        dashboardPage = basePage.openDashboard();
//
//        dashboardPage = new DashboardPage(driver);
//
//        dashboardPage.openClientsDashboard();
//
//        Assertions.myAssertTrue(
//                dashboardPage.isClientsCountDisplayed(),
//                "Clients count card is not displayed");
//    }
//
//    @Test(priority = 5)
//    public void verifyDashboardCounts() {
//
//        dashboardPage = basePage.openDashboard();
//
//        dashboardPage = new DashboardPage(driver);
//
//        dashboardPage.openBlogsDashboard();
//
//        Assertions.myAssertFalse(
//                dashboardPage.getArticlesCount().isBlank(),
//                "Articles count is empty");
//
//        Assertions.myAssertFalse(
//                dashboardPage.getNewsCount().isBlank(),
//                "News count is empty");
//
//        dashboardPage.openProductsDashboard();
//
//        Assertions.myAssertFalse(
//                dashboardPage.getProductsCount().isBlank(),
//                "Products count is empty");
//
//        dashboardPage.openClientsDashboard();
//
//        Assertions.myAssertFalse(
//                dashboardPage.getClientsCount().isBlank(),
//                "Clients count is empty");
//    }
//}