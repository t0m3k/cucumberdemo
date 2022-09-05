package org.example.APITests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class FirstApiTests {
    @Test
    void firstApiTest() {
        given().baseUri("http://localhost").port(2002)
                .when().get("/api/products")
                .then().statusCode(200).log().body();
    }
}
