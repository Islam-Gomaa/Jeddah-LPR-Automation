package utilities;

import org.openqa.selenium.WebDriver;
import pages.BasePage;
import pages.LoginPage;

import static dataReader.ReadDataFromJson.dataModel;

public class AuthHelper {

    public static BasePage login(WebDriver driver) {

        LoginPage loginPage = new LoginPage(driver);

        return loginPage
                .enterEmail(dataModel().Login.validCredentials.Email)
                .enterPassword(dataModel().Login.validCredentials.Password)
                .clickLoginButton()
                .changeLanguage();
    }
}
