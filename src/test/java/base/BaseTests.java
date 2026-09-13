package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utilities.ConfigReader;
import utilities.DriverFactory;
import utilities.VideoRecorder;
import java.lang.reflect.Method;
import java.time.Duration;
import org.testng.annotations.Listeners;
import listeners.AllureListener;
import utils.Assertions;

import java.awt.AWTException;


@Listeners(AllureListener.class)
public class BaseTests {

    protected WebDriver driver;
    public VideoRecorder recorder;

    @BeforeMethod
    public void setUp(Method method) throws AWTException {

        System.out.println("Starting Test: " + method.getName());

        // Start recording
        recorder = new VideoRecorder();
        recorder.startRecording(method.getName());

        // Create driver from configuration
        driver = DriverFactory.createDriver(
                ConfigReader.get("browser"));

        Assertions.setDriver(driver);

        // Maximize window
        driver.manage().window().maximize();

        // Implicit wait
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }

    protected void openSystem() {
        driver.get(ConfigReader.get("baseUrl"));
    }

    @AfterMethod
    public void tearDown() {

        recorder.stopRecording();

        System.out.println("Recording stopped");
//                    driver.quit();
    }
}