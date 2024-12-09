package tests.api;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import static io.restassured.RestAssured.given;

public class APITest extends APIBase {


    @Test
    public void testGetSingleObjectWithDynamicId() {
        int objectId = 2;

        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/objects/" + objectId);

        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println(response.asPrettyString());
        System.out.println("ID: " + response.jsonPath().getInt("id"));
        System.out.println("Name: " + response.jsonPath().getString("name"));
        System.out.println("Description: " + response.jsonPath().getString("description"));
        Assert.assertEquals(response.jsonPath().getInt("id"), objectId, "ID mismatch");


        List<HashMap<String, Object>> allObjects = response.jsonPath().getList("$");
        int totalObjects = allObjects.size();

        System.out.println("Total number of objects: " + totalObjects);
        String nameAtIndex3 = response.jsonPath().getString("[2].name"); // 3. eleman
        System.out.println("Name of object at index 3: " + nameAtIndex3);
    }

    @Test
    public void testUsingHamcrestMatchers() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/objects")
                .then()
                .statusCode(200)
                .body("id[0]", equalTo("1"))
                .body("name[2]", equalTo("Apple iPhone 12 Pro Max"))
                .body("$", hasSize(13))
                .log().all();
    }

    @Test
    public void testGetObjectsById() {
        Response response = given()
                .header("Content-Type", "application/json")
                .queryParam("id", "1,2,3")
                .when()
                .get("https://api.restful-api.dev/objects");

        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch");
        response.jsonPath().getList("id").forEach(id -> {
            Assert.assertTrue(id.equals(1) || id.equals(2) || id.equals(3),
                    "Unexpected ID found: " + id);
        });
        System.out.println("Response: " + response.getBody().asPrettyString());
    }

    @Test(dependsOnMethods = {"testGetObjectsById"})
    void PostRequestWithHamcrest() {
        String patchId= "";
        String jsonBody = """
                    {
                        "name": "Apple MacBook Pro 17",
                        "data": {
                            "year": 2019,
                            "price": 1849.92,
                            "CPU model": "Intel Core i9",
                            "Hard disk size": "2 TB"
                        }
                    }
                """;

//        given()
//                .header("Content-Type", "application/json")
//                .body(jsonBody)
//                .when()
//                .post("https://api.restful-api.dev/objects")
//                .then()
//                .statusCode(200)
//                .body("name", equalTo("Apple MacBook Pro 17"))
//                .body("data.year", equalTo(2019))
//                .body("data.price", equalTo(1849.92f));
////                .body("data.CPU model", equalTo("Intel Core i9"))
////                .body("data.Hard disk size", equalTo("1 TB"));


        Response postResponse = given()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"name\": \"Apple MacBook Pro 16\",\n" +
                        "  \"data\": {\n" +
                        "    \"year\": 2019,\n" +
                        "    \"price\": 1849.99,\n" +
                        "    \"CPU model\": \"Intel Core i9\",\n" +
                        "    \"Hard disk size\": \"1 TB\"\n" +
                        "  }\n" +
                        "}")
                .when()
                .post("/objects");


        postResponse.then().statusCode(200);
        patchId = postResponse.jsonPath().getString("id");
        System.out.println("ID: " + patchId);


        Response patchResponse = given()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"name\": \"Apple MacBook Pro 16 (Updated Name)\"\n" +
                        "}")
                .when()
                .patch("/objects/"+ patchId);

        patchResponse .then()
                .log().body() // Yanıtı logla
                .statusCode(200);

    }
    @Test
    void testOneMorePost() throws IOException {
        String body = "{\"name\": \"Apple iPad Air\", \"data\": { \"Generation\": \"4th\", \"Price\": \"519.99\", \"Capacity\": \"256 GB\" }}";
        URL url = new URL("https://api.restful-api.dev/objects");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
            dos.writeBytes(body);
        }

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}






