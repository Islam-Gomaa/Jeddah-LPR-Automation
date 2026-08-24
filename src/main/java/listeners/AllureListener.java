package listeners;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AllureListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("========== Test Suite Started ==========");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("========== Test Suite Finished ==========");
    }

    @Override
    public void onTestStart(ITestResult result) {
        Allure.step("Starting Test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Allure.step("Test Passed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Allure.step("Test Skipped");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        Throwable throwable = result.getThrowable();

        if (throwable != null) {

            StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw));

            Allure.addAttachment(
                    "Exception",
                    "text/plain",
                    sw.toString()
            );
        }
    }
}