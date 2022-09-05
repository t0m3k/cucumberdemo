package org.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.utils.SharedDictionary;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;

public class GoogleSteps {

    private final SharedDictionary dict;
    private WebDriver driver;

    public GoogleSteps(SharedDictionary dict) {
        this.dict = dict;

        this.driver = (WebDriver)dict.readDict("driver");
    }

    @Given("I am on the Google Homepage")
    public void i_am_on_the_google_homepage() {
        driver.get("https://www.google.co.uk");
        driver.findElement(By.cssSelector("#L2AGLb > div")).click();
    }

    @Then("Edgewords is the top result")
    public void edgewords_is_the_top_result() {
        var url = driver.findElement(By.cssSelector("#rso > div:nth-child(1) > div > div > div > div > div > div > div.yuRUbf > a > div > cite")).getText();

        MatcherAssert.assertThat(url, containsString("https://www.edgewordstraining.co.uk"));
    }

    @When("I search for {string}")
    public void iSearchFor(String searchTerm) {
        driver.findElement(By.name("q")).sendKeys(searchTerm + Keys.ENTER);

    }

    @Then("{string} is the top result")
    public void isTheTopResult(String url) {
        var resultUrl = driver.findElement(By.cssSelector("#rso > div:first-child")).getText();

        MatcherAssert.assertThat(resultUrl, containsString(url));

    }

    @Then("I see in the results")
    public void i_see_in_the_results(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.

        var searchResults = driver.findElement(By.id("rso")).getText();

        List<Map<String, String>> table = dataTable.asMaps();

        table.forEach(row -> {
            MatcherAssert.assertThat(searchResults, containsString(row.get("url")));
            MatcherAssert.assertThat(searchResults, containsString(row.get("title")));
        });
    }
}
