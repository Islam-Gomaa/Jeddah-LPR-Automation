package pages;

import api.SaudiPlate;
import io.qameta.allure.Step;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PermitsPage extends BasePage<PermitsPage> {

  public PermitsPage(WebDriver driver) {
    super(driver);
  }

  // Locators

    private final By addPermitBtn = By.xpath("//p[.='Add']");

    private By getPlateInput(String inputMode, int character, int group) {
        return By.xpath(
                "(//input[@aria-label='Please enter OTP character "
                        + character
                        + "' and @inputmode='"
                        + inputMode
                        + "'])[" + group + "]"
        );
    }

    private void enterPlateNumbers(String number, int group) {
        for (int i = 0; i < number.length(); i++) {
            // Locate the corresponding numeric input
            // Enter one digit
        }
    }

    private void enterPlateLetters(String letters, int group) {
        for (int i = 0; i < letters.length(); i++) {
            // Locate the corresponding text input
            // Enter one letter
        }
    }

    public void enterPlateNumber(SaudiPlate plate) {

        String number = String.valueOf(plate.getPlateNumber());

        String englishLetters = plate.getPlateEn().split(" ")[1];

        String arabicLetters = plate.getPlateAr().split(" ")[0];

        // Arabic plate - Group 1
        enterPlateNumbers(number, 1);
        enterPlateLetters(arabicLetters, 1);

        // English plate - Group 2
        enterPlateNumbers(number, 2);
        enterPlateLetters(englishLetters, 2);
    }


    @Step("Click Add Permits Button")
    public PermitsPage clickAddPermitBtn() {
        jsClick(addPermitBtn);
        return this;
    }




}
