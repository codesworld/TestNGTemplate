package tests.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.ValuationPage;
import utils.ConfigReader;
import utils.CookieManager;
import utils.DriverManager;
import java.time.Duration;

public class TestBase {
    protected static WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setupClass() throws InterruptedException {

        driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        String url = ConfigReader.getProperty("url");
        DriverManager.getDriver().get(url);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(20));

        //CookieManager.addCookies(driver);
    }

    @AfterMethod
    public void tearDownClass() {
        DriverManager.quitDriver();
    }
}
