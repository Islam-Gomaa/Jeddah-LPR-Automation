package reporting;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class AllureAttachments {

    private AllureAttachments() {
    }

    /**
     * Attach Screenshot
     */
    public static void attachScreenshot(WebDriver driver, String name) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(
                    name,
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    ".png"
            );
        } catch (Exception ignored) {
        }
    }

    /**
     * Attach Text
     */
    public static void attachText(String title, String text) {
        if (text == null) return;

        Allure.addAttachment(
                title,
                "text/plain",
                new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)),
                ".txt"
        );
    }

    /**
     * Attach HTML
     */
    public static void attachPageSource(WebDriver driver) {
        try {
            attachText("Page Source", driver.getPageSource());
        } catch (Exception ignored) {
        }
    }

    /**
     * Attach Current URL
     */
    public static void attachCurrentUrl(WebDriver driver) {
        try {
            attachText("Current URL", driver.getCurrentUrl());
        } catch (Exception ignored) {
        }
    }

    /**
     * Attach Video
     */
    public static void attachVideo(File video) {

        if (video == null || !video.exists())
            return;

        try (FileInputStream stream = new FileInputStream(video)) {

            Allure.addAttachment(
                    "Execution Video",
                    "video/mp4",
                    stream,
                    ".mp4"
            );

        } catch (IOException ignored) {
        }
    }

    /**
     * Attach Any File
     */
    public static void attachFile(String title, File file) {

        if (file == null || !file.exists())
            return;

        try (FileInputStream stream = new FileInputStream(file)) {

            Allure.addAttachment(
                    title,
                    stream
            );

        } catch (IOException ignored) {
        }
    }
}