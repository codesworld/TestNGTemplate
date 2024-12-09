package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.DriverManager;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));

    }

    protected List<WebElement> findElements(By locator) {
        return wait.until(driver -> driver.findElements(locator));
    }


    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        findElement(locator).click();
    }

    protected void sendKeys(By locator, String text) {
        findElement(locator).sendKeys(text);
    }

    protected String getText(By locator) {
        return findElement(locator).getText();
    }
}
