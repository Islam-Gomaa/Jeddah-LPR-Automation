package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.*;

import static org.testng.Assert.*;

public class Assertions {

  private static WebDriver driver;

  public static void setDriver(WebDriver webDriver) {
    driver = webDriver;
  }

  private static void highlightElement(WebElement element) {

    if (element == null) {
      return;
    }
    try {
      JavascriptExecutor js = (JavascriptExecutor) driver;

      js.executeScript(
          "arguments[0].style.outline='4px solid #FFD700';"
              + "arguments[0].style.outlineOffset='2px';"
              + "arguments[0].style.boxShadow='0 0 12px #FFD700';",
          element);
      Thread.sleep(1000);

      js.executeScript(
          "arguments[0].style.outline='';"
              + "arguments[0].style.outlineOffset='';"
              + "arguments[0].style.boxShadow='';",
          element);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } catch (StaleElementReferenceException | JavascriptException ignored) {
    }
  }


  // ================ EQUALS ================

//  @Step("Assert equals")
//  public static void myAssertEquals(Object actual, Object expected) {
//    Allure.addAttachment("Actual Result", String.valueOf(actual));
//    Allure.addAttachment("Expected Result", String.valueOf(expected));
//
//    assertEquals(actual, expected);
//  }

  @Step("Assert equals with element")
  public static void myAssertEquals(WebElement element, Object actual, Object expected) {
    highlightElement(element);
    Allure.addAttachment("Actual Result", String.valueOf(actual));
    Allure.addAttachment("Expected Result", String.valueOf(expected));
    assertEquals(actual, expected);
  }

//  @Step("Assert equals with message")
//  public static void myAssertEquals(Object actual, Object expected, String message) {
//    Allure.addAttachment("Actual Result", String.valueOf(actual));
//    Allure.addAttachment("Expected Result", String.valueOf(expected));
//    assertEquals(actual, expected, message);
//  }

  @Step("Assert equals with message and element")
  public static void myAssertEquals(WebElement element, Object actual, Object expected, String message) {
    highlightElement(element);
    Allure.addAttachment("Actual Result", String.valueOf(actual));
    Allure.addAttachment("Expected Result", String.valueOf(expected));
    assertEquals(actual, expected, message);
  }

  // ================ PLATE LETTERS ================

  @Step("Assert equals ignoring spaces")
  public static void myAssertEqualsIgnoreSpaces(
      WebElement element, String actual, String expected, String message) {

    // Highlight the element
    highlightElement(element);

    Allure.addAttachment("Actual Result", actual);
    Allure.addAttachment("Expected Result", expected);

    assertEquals(actual.replaceAll("\\s+", ""), expected.replaceAll("\\s+", ""), message);
  }

  @Step("Assert not equals")
  public static void myAssertNotEquals(Object actual, Object unexpected) {
    Allure.addAttachment("Actual Result", String.valueOf(actual));
    Allure.addAttachment("Unexpected Result", String.valueOf(unexpected));
    assertNotEquals(actual, unexpected);
  }

  // ================= BOOLEAN =================

  @Step("Assert true")
  public static void myAssertTrue(boolean condition, String message) {
    Allure.addAttachment("Assertion", message);
    assertTrue(condition, message);
  }

  @Step("Assert false")
  public static void myAssertFalse(boolean condition, String message) {
    Allure.addAttachment("Assertion", message);
    assertFalse(condition, message);
  }

  // ================= NULL =================

  @Step("Assert null")
  public static void myAssertNull(Object object, String message) {
    Allure.addAttachment("Assertion", message);
    assertNull(object, message);
  }

  @Step("Assert not null")
  public static void myAssertNotNull(Object object, String message) {
    Allure.addAttachment("Assertion", message);
    assertNotNull(object, message);
  }

  // ================= STRING =================

  @Step("Assert contains")
  public static void myAssertContains(String actual, String expected) {
    Allure.addAttachment("Actual Text", actual);
    Allure.addAttachment("Expected Text", expected);
    assertTrue(
        actual.contains(expected), "Expected [" + actual + "] to contain [" + expected + "]");
  }

  @Step("Assert not contains")
  public static void myAssertNotContains(String actual, String unexpected) {
    Allure.addAttachment("Actual Text", actual);
    Allure.addAttachment("Unexpected Text", unexpected);
    assertFalse(
        actual.contains(unexpected),
        "Expected [" + actual + "] NOT to contain [" + unexpected + "]");
  }

  @Step("Assert starts with")
  public static void myAssertStartsWith(String actual, String prefix) {
    Allure.addAttachment("Actual Text", actual);
    Allure.addAttachment("Expected Prefix", prefix);
    assertTrue(
        actual.startsWith(prefix), "Expected [" + actual + "] to start with [" + prefix + "]");
  }

  @Step("Assert ends with")
  public static void myAssertEndsWith(String actual, String suffix) {
    Allure.addAttachment("Actual Text", actual);
    Allure.addAttachment("Expected Suffix", suffix);
    assertTrue(actual.endsWith(suffix), "Expected [" + actual + "] to end with [" + suffix + "]");
  }

  @Step("Assert empty")
  public static void myAssertEmpty(String actual, String message) {
    Allure.addAttachment("Assertion", message);
    assertTrue(actual == null || actual.isEmpty(), message);
  }

  @Step("Assert not empty")
  public static void myAssertNotEmpty(String actual, String message) {
    Allure.addAttachment("Assertion", message);
    assertFalse(actual == null || actual.isEmpty(), message);
  }

  // ================= URL / TITLE =================

  @Step("Assert URL equals")
  public static void myAssertUrlEquals(String actualUrl, String expectedUrl) {
    Allure.addAttachment("Actual URL", actualUrl);
    Allure.addAttachment("Expected URL", expectedUrl);
    assertEquals(actualUrl, expectedUrl);
  }

  @Step("Assert URL contains")
  public static void myAssertUrlContains(String actualUrl, String expectedPart) {
    Allure.addAttachment("Actual URL", actualUrl);
    Allure.addAttachment("Expected Part", expectedPart);
    assertTrue(
        actualUrl.contains(expectedPart),
        "Expected URL [" + actualUrl + "] to contain [" + expectedPart + "]");
  }

  @Step("Assert title equals")
  public static void myAssertTitleEquals(String actualTitle, String expectedTitle) {
    Allure.addAttachment("Actual Title", actualTitle);
    Allure.addAttachment("Expected Title", expectedTitle);
    assertEquals(actualTitle, expectedTitle);
  }

  @Step("Assert title contains")
  public static void myAssertTitleContains(String actualTitle, String expectedPart) {
    Allure.addAttachment("Actual Title", actualTitle);
    Allure.addAttachment("Expected Part", expectedPart);
    assertTrue(
        actualTitle.contains(expectedPart),
        "Expected title [" + actualTitle + "] to contain [" + expectedPart + "]");
  }
}
