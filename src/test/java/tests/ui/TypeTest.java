package tests.ui;

import org.testng.annotations.Test;
import pages.ValuationPage;

public class TypeTest extends TestBase {
    @Test
    public void testValuation() {

        ValuationPage valuationPage = new ValuationPage();
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println(driver.getTitle());
    }
}
