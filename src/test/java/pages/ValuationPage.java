package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ValuationPage extends BasePage{

    private static final By CAR_REGISTRATION_FIELD = By.id("registration");
    private static final By CAR_MILEAGE_FIELD = By.id("Mileage");
    private static final By CAR_VALUATION_AMOUNT = By.className("vehicle-value");
    private static final By CAR_COOKIE = By.id("onetrust-accept-btn-handler");
    private static final By CAR_REGISTRATION = By.id("vehicleReg");
    private static final By GO_BTN = By.id("btn-go");
    private static final By CAR_DETAILS = By.cssSelector(".d-table-row.details-vehicle-row");
    private static final By SORRY_MESSAGE = By.cssSelector(".text-focus.ng-star-inserted");
    public ValuationPage() {
        super();
    }

    public void searchCarByRegistration(String registration, String mileage) {
        click(CAR_REGISTRATION);
        sendKeys(CAR_REGISTRATION, registration);
        click(CAR_MILEAGE_FIELD);
        sendKeys(CAR_MILEAGE_FIELD, mileage);
        click(GO_BTN);
    }
    public void acceptCookies() {
        click(CAR_COOKIE);
    }

    public WebElement getSorryMessage() {
        return findElement(SORRY_MESSAGE);
    }

    public void findRegistration(String registration) throws InterruptedException {
        Thread.sleep(5000);
        click(CAR_REGISTRATION);
        sendKeys(CAR_REGISTRATION,registration);
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
