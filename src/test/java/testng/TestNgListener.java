package testng;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.DriverManager;

import java.io.File;
import java.io.IOException;

public class TestNgListener implements ITestListener {
    private ExtentReports extent;
    private ExtentTest test;
   private ExtentSparkReporter htmlReporter;

    @Override
    public void onStart(ITestContext context) {
        htmlReporter = new ExtentSparkReporter("target/extentReport1.html");
        htmlReporter.config().setDocumentTitle("Automation Test Report");
        htmlReporter.config().setReportName("Test Results");
        htmlReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Tester", "Your Name");
        extent.setSystemInfo("Environment", "QA");
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.info("Test started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail("Test failed: " + result.getMethod().getMethodName());
        WebDriver driver = DriverManager.getDriver();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotPath = "target/screenshots/" + result.getMethod().getMethodName() + ".png";
            FileUtils.copyFile(screenshot, new File(screenshotPath));
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (IOException e) {
            test.fail("Failed to attach screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
