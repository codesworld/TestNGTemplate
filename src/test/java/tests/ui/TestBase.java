package tests.ui;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigReader;
import utils.DriverManager;

import java.io.File;
import java.io.IOException;

public class TestBase {
    protected static WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setupClass() {

        driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        String url = ConfigReader.getProperty("url");
        driver.get(url);
    }

    @AfterMethod
    public void tearDownClass(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File srcFile = ts.getScreenshotAs(OutputType.FILE);
            String fileName = "target/screenshots/" + result.getName() + ".png";
            File destFile = new File(fileName);
            try {
                FileHandler.copy(srcFile, destFile);
                System.out.println("Screenshot saved at: " + fileName);
            } catch (IOException e) {
                System.out.println("Failed to save screenshot: " + e.getMessage());
            }
        }
        DriverManager.quitDriver();
    }
}
