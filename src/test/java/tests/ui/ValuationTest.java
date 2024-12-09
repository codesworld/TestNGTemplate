package tests.ui;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import pages.ValuationPage;
import utils.ConfigReader;
import utils.DriverManager;
import utils.FileUtils;
import utils.TestResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Listeners({testng.TestNgListener.class, testng.RetryListener.class})
public class ValuationTest extends TestBase {

    @Test
    public static void failTest() {
        Assert.fail("test retry");
    }

    @Test
    public static void getTitle() throws InterruptedException {
        String title = DriverManager.getDriver().getTitle();
        if (title != null) {
            Assert.assertTrue(title.contains("Sell your car in under an hour"));
        }
       driver.wait(2000);
    }

    private static final String PATTERN = "\\b[A-Z]{2}[0-9]{2}\\s?[A-Z]{3}\\b";

    @Test// (dataProvider = "carRegistrations")
    public static void registrationList() throws IOException, InterruptedException {

        ValuationPage valuationPage = new ValuationPage();
        TestResult passedRegistration = new TestResult("src/test/resources/passed_regs.txt");
        TestResult failedRegistration = new TestResult("src/test/resources/failed_regs.txt");

        String inputPath = "src/test/resources/car_input.txt";
        List<String> list = FileUtils.readCarRegistrations(inputPath);

        String outputPath = "src/test/resources/car_output.txt";
        Map<String, List<String>> carData = FileUtils.parseCarData(outputPath);

        for (String registration : list) {
            String trimmedRegNumber = registration.replaceAll("\\s+", "");

            if (carData.containsKey(trimmedRegNumber)) {
                List<String> expectedValues = carData.get(trimmedRegNumber);
                valuationPage.searchCarByRegistration(registration, "32000");
                Thread.sleep(5000);
                Map<String, String> carDetails = valuationPage.getCarDetailsFromWebsite();

                boolean allMatch = expectedValues.stream().allMatch(carDetails.values()::contains);

                if (allMatch) {
                    Assert.assertTrue(allMatch);
                    passedRegistration.logRegistration(registration);

                } else {
                    Assert.assertTrue(valuationPage.getSorryMessage().isDisplayed());
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




