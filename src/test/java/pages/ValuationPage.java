package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ValuationPage extends BasePage {

    private static final By CAR_MILEAGE_FIELD = By.id("Mileage");
    private static final By CAR_COOKIE = By.xpath("//button[contains(text(), 'Accept all')]");
    private static final By CAR_REGISTRATION = By.xpath("//input[@id='vehicleReg']");
    private static final By GO_BTN = By.id("btn-go");
    private static final By CAR_DETAILS = By.cssSelector(".d-table-row.details-vehicle-row");
    private static final By SORRY_MESSAGE = By.cssSelector(".text-focus.ng-star-inserted");

    public ValuationPage() {
        super();
    }

    public void searchCarByRegistration(String registration, String mileage) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(CAR_REGISTRATION));
        button.click();
        sendKeys(CAR_REGISTRATION, registration);
        click(CAR_MILEAGE_FIELD);
        sendKeys(CAR_MILEAGE_FIELD, mileage);
        click(GO_BTN);
    }

    public void acceptCookies() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(CAR_COOKIE));
            cookieButton.click();
        } catch (TimeoutException e) {
            System.out.println("Cookie banner not found or not clickable: " + e.getMessage());
        }
    }

    public WebElement getSorryMessage() {
        return findElement(SORRY_MESSAGE);
    }

    public void findRegistration(String registration) throws InterruptedException {
        Thread.sleep(5000);
        click(CAR_REGISTRATION);
        sendKeys(CAR_REGISTRATION, registration);
        click(GO_BTN);
    }

    public Map<String, String> getCarDetailsFromWebsite() {

        Map<String, String> details = new HashMap<>();

        List<WebElement> rows = findElements(CAR_DETAILS);
        for (WebElement row : rows) {
            String key = row.findElement(By.xpath(".//div[contains(@class,'heading')]")).getText();
            String value = row.findElement(By.xpath(".//div[contains(@class,'value')]")).getText();
            if (!key.isEmpty() && !value.isEmpty()) {
                details.put(key, value);
            }
        }
        return details;
    }
}
