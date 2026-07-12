package tests;

import base.BaseTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.admin.BasePage;
import pages.admin.RolesPage;
import utilities.AuthHelper;

import static dataReader.ReadDataFromJson.dataModel;
import static utils.Assertions.*;

public class RolesTests extends BaseTests {

     BasePage basePage;
     RolesPage rolesPage;

    @BeforeMethod
    public void setupAdminSession() {
        openAdmin();
        basePage = AuthHelper.login(driver);
    }

//    @Test(priority = 1)
//    public void verifyRolesPageUI() {
//
//
//        // ====== Control Panel ======
//        rolesPage = basePage.openRoles();
//
//
//        // Verify Roles page opened successfully
//        myAssertTrue(
//                rolesPage.isAddButtonDisplayed(),
//                "Add button should be displayed");
//
//        // Open Add Role dialog
//        rolesPage.clickAdd();
//
//        rolesPage.scrollToFooter();
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.advertisement),
//                "Advertisement category should be displayed");
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.blog),
//                "Blog category should be displayed");
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.blogCategory),
//                "Blog Category should be displayed");
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.business),
//                "Business category should be displayed");
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.category),
//                "Category should be displayed");
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.client),
//                "Client category should be displayed");
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.contact),
//                "Contact category should be displayed");
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.featureGroup),
//                "Feature Group should be displayed");
//
//        rolesPage.scrollPermissionsListToBottom();
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.partner),
//                "Partner category should be displayed");
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.permission),
//                "Permission category should be displayed");
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.product),
//                "Product category should be displayed");
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.role),
//                "Role category should be displayed");
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.setting),
//                "Setting category should be displayed");
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.subscribe),
//                "Subscribe category should be displayed");
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.useCase),
//                "Use Case category should be displayed");
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.user),
//                "User category should be displayed");
//
//        myAssertTrue(
//                rolesPage.isPermissionCategoryDisplayed(dataModel().Roles.dashboard),
//                "Dashboard category should be displayed");
//    }


    @Test(priority = 2, description = "Verify Add, Edit and Delete Role")
    public void verifyAddEditDeleteRole() {

        // ===== Open Roles Module =====
        rolesPage = basePage.openRoles();

        // Add Role

        rolesPage

                .clickAdd()
                .fillRoleData(
                        dataModel().Roles.roleName,
                        dataModel().Roles.displayNameAr,
                        dataModel().Roles.displayNameEn,
                        dataModel().Roles.descriptionAr,
                        dataModel().Roles.descriptionEn)
                .enableRole()

                // Category Permissions
                .assignPermissions(
                        dataModel().Roles.category,
                        dataModel().Roles.categoryCreate,
                        dataModel().Roles.categoryRead,
                        dataModel().Roles.categoryUpdate,
                        dataModel().Roles.categoryDelete)

                // Blog Permissions
                .assignPermissions(
                        dataModel().Roles.blog,
                        dataModel().Roles.blogCreate,
                        dataModel().Roles.blogRead,
                        dataModel().Roles.blogUpdate,
                        dataModel().Roles.blogDelete)

                // Feature Group Permissions
                .assignPermissions(
                        dataModel().Roles.featureGroup,
                        dataModel().Roles.featureGroupCreate,
                        dataModel().Roles.featureGroupUpdate,
                        dataModel().Roles.featureGroupDelete)

                .scrollPermissionsListToBottom()

                // Client Permissions
                .assignPermissions(
                        dataModel().Roles.client,
                        dataModel().Roles.clientRead)

                // User Permissions
                .assignPermissions(
                        dataModel().Roles.user,
                        dataModel().Roles.userCreate,
                        dataModel().Roles.userUpdate,
                        dataModel().Roles.userDelete)

                // Dashboard Permission
                .assignPermissions(
                        dataModel().Roles.dashboard,
                        dataModel().Roles.dashboardReport)

                .clickAdd();

        myAssertTrue(
                basePage.isSuccessIconDisplayed(),
                "Success icon should be displayed.");

        myAssertTrue(
                basePage.isSuccessMessageDisplayed(),
                "Success message should be displayed.");

        myAssertEquals(
                basePage.getSuccessMessage(),
                "Created successfully");

        myAssertTrue(
                rolesPage.isRoleDisplayed(dataModel().Roles.roleName),
                "Created role should be displayed.");

        // Edit Role

        rolesPage
                .openRole(dataModel().Roles.roleName)
                .clickEditRole()
                .clearRoleData()
                .fillRoleData(
                        dataModel().Roles.editRoleName,
                        dataModel().Roles.editDisplayNameAr,
                        dataModel().Roles.editDisplayNameEn,
                        dataModel().Roles.editDescriptionAr,
                        dataModel().Roles.editDescriptionEn)
                .clickSave();

        myAssertTrue(
                basePage.isSuccessIconDisplayed(),
                "Success icon should be displayed.");

        myAssertTrue(
                basePage.isSuccessMessageDisplayed(),
                "Success message should be displayed.");

        myAssertEquals(
                basePage.getSuccessMessage(),
                "Updated successfully");

        myAssertTrue(
                rolesPage.isRoleDisplayed(dataModel().Roles.editRoleName),
                "Updated role should be displayed.");

        // Delete Role

        rolesPage
                .openRole(dataModel().Roles.editRoleName)
                .clickDeleteRole()
                .clickConfirmDeleteRole();

        myAssertTrue(
                basePage.isSuccessIconDisplayed(),
                "Success icon should be displayed.");

        myAssertTrue(
                basePage.isSuccessMessageDisplayed(),
                "Success message should be displayed.");

        myAssertEquals(
                basePage.getSuccessMessage(),
                "Deleted successfully");

        myAssertTrue(
                rolesPage.isRoleNotDisplayed(dataModel().Roles.editRoleName),
                "Role should be deleted successfully.");
    }
//
//    @Test(priority = 3)
//    public void verifyCancelEditRole() {
//
//        rolesPage = basePage.openRoles();
//
//        rolesPage
//                .openRole(dataModel().Roles.editRoleName)
//                .clickEditRole()
//                .clickCancel();
//
//        myAssertTrue(
//                rolesPage.isRoleDisplayed(dataModel().Roles.editRoleName),
//                "Role should still exist");
//    }

}