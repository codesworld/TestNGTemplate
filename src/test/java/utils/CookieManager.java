package utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class CookieManager {
    public static void addCookies(WebDriver driver) {

        Cookie cookie = new Cookie("OptanonConsent", "isGpcEnabled=0&datestamp=Wed+Nov+20+2024+17%3A45%3A29+GMT%2B0000+(Greenwich+Mean+Time)&version=202410.1.0&browserGpcFlag=0&isIABGlobal=false&hosts=&consentId=f038eff8-f774-461f-920c-742960f29ac6&interactionCount=1&isAnonUser=1&landingPath=https%3A%2F%2Fwww.webuyanycar.com%2F&groups=C0001%3A1%2CC0002%3A0%2CC0003%3A0%2CC0004%3A0");
        driver.manage().addCookie(cookie);
    }
}
