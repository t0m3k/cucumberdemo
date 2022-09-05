package org.example.APITests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.hamcrest.MatcherAssert;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.lessThan;

public class DefaultRequestSpec {
    @BeforeAll
    public static void setupDefaultRequest() {
        RequestSpecification spec = given();
        spec.baseUri("http://localhost");
        spec.port(2002);
        spec.contentType(ContentType.JSON);
        requestSpecification = spec; //requestSpecification is defined static in io.restassured.RestAssured
    }

    @Test
    void testGetWithSpec() {
        RequestSpecification httpRequest = given().contentType("application/json");
        Response response = httpRequest.get("/api/products/2");
        String body = response.getBody().asString();
        MatcherAssert.assertThat(body, containsString("iPhone X"));
        int statusCode = response.statusCode();
        MatcherAssert.assertThat(statusCode, is(200));
        System.out.println("Response body => " + body);
    }

    @Test
    void bddGetStaticWithSpec() {
        when().get("/api/products/2")
                .then().statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .body("name", equalTo("iPhone X"))
                .log().headers()
                .log().body();
    }

    @Test
    void testPostWithJSON() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "mouse");
        requestParams.put("price", 20);

        given().contentType("application/json").body(requestParams.toJSONString())
                .when().post("/api/products")
                .then().statusCode(201).log().body();
    }

    @Test
    void testPutWithJSON() {
        JSONObject requestParams = new JSONObject();
        var name = "Keyboard";

        requestParams.put("name", name);
        requestParams.put("price", 20);

        given().contentType("application/json").body(requestParams.toJSONString())
                .when().put("/api/products/1")
                .then()
                .statusCode(200)
                .body("name", equalTo(name))
                .time(lessThan(2000L))
                .log().body();
    }

    @Test
    void testPostWithJSON2() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "mouse");
        requestParams.put("price", 20);

        var res = given().contentType("application/json").body(requestParams.toJSONString()).when().post("/api/products");


        var id = res.jsonPath().get("id");
        System.out.println(id);
    }
}
