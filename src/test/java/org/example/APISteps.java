package org.example;



import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.CoreMatchers.is;


import org.example.utils.SharedDictionary;

public class APISteps {
    private final SharedDictionary sharedDict;
    public APISteps(SharedDictionary sharedDict) {
        this.sharedDict = sharedDict;
    }

    @When("I request all products")
    public void i_request_all_products() {
        //Response response = given().baseUri("http://localhost:2002").when().get("/api/products");
        RequestSpecification req = (RequestSpecification)sharedDict.readDict("apirequest");
        Response response = req.when().get("/api/products");
        sharedDict.addDict("response", response);
    }
    @Then("I get a status code of {int}")
    public void i_get_a_status_code_of(Integer code) {
        Response response = (Response)sharedDict.readDict("response");
        response.then().statusCode(code);
    }

    @When("I request user number {int}")
    public void iRequestUserNumber(int userNum) {
        RequestSpecification req = (RequestSpecification)sharedDict.readDict("apirequest");
        req.auth().basic("edge","edgewords");
        Response response = req.when().get("/api/users/" + userNum);
        sharedDict.addDict("response", response);
    }

    @And("the user is {string}")
    public void theUserIs(String userName) {
        Response response = (Response)sharedDict.readDict("response");
        //using validatableResponse GPath
//        var valres = response.then();
//        valres.body("userName", is(userName));
        //using jsonPath
        //https://www.baeldung.com/guide-to-jayway-jsonpath
         var jsonUserName = response.jsonPath().get("userName").toString();
        MatcherAssert.assertThat(jsonUserName, is(userName));
    }
}
