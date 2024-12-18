package tests.ui;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.ValuationPage;
import utils.ConfigReader;
import utils.DriverManager;
import utils.FileUtils;
import utils.TestResult;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

import static org.testng.Assert.assertTrue;

@Test
public class ValuationTest extends TestBase {

    @Test
    public static void getTitle() throws InterruptedException {
        String title = DriverManager.getDriver().getTitle();
        assertTrue(title.contains("Sell your car in under an hour"));
        Thread.sleep(5000);
    }

    @Test
    public static void registrationList() throws IOException, InterruptedException {

        ValuationPage valuationPage = new ValuationPage();
        valuationPage.acceptCookies();
        TestResult passedRegistration = new TestResult("src/test/resources/passed_regs.txt");
        TestResult failedRegistration = new TestResult("src/test/resources/failed_regs.txt");

        String inputPath = "src/test/resources/car_input.txt";
        List<String> registrationList = FileUtils.readCarRegistrations(inputPath);

        String outputPath = "src/test/resources/car_output.txt";
        Map<String, List<String>> carData = FileUtils.parseCarData(outputPath);

        for (String registration : registrationList) {

            String trimmedRegNumber = registration.replaceAll("\\s+", "");

            if (carData.containsKey(trimmedRegNumber)) {
                List<String> expectedValues = carData.get(trimmedRegNumber);
                valuationPage.searchCarByRegistration(registration, "32000");
                Thread.sleep(5000);
                Map<String, String> carDetails = valuationPage.getCarDetailsFromWebsite();

                boolean allMatch = expectedValues.stream().allMatch(carDetails.values()::contains);

                if (allMatch) {
                    assertTrue(allMatch);
                    passedRegistration.logRegistration(registration);

                } else {
                    assertTrue(valuationPage.getSorryMessage().isDisplayed());
                    failedRegistration.logRegistration(trimmedRegNumber);

                }
            } else {
                System.out.println("No expected data found for registration: " + registration);
                failedRegistration.logRegistration(registration + "can't be found");

            }

            driver.navigate().to(ConfigReader.getProperty("url"));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(20));

        }
            passedRegistration.close();
            failedRegistration.close();

        }

    }




