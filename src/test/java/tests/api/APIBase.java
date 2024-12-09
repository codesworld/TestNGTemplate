package tests.api;

import io.restassured.RestAssured;
import utils.ConfigReader;

public class APIBase {
    static {
        RestAssured.baseURI = ConfigReader.getProperty("apiUrl");
    }
}
