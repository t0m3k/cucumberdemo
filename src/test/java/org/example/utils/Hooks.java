package org.example.utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class Hooks {
    private WebDriver driver;

    private final SharedDictionary dict;

    public Hooks(SharedDictionary dict) {
        this.dict = dict;
    }

    @Before("@GUI")
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

//        WebDriverManager.firefoxdriver().setup();
//        driver = new FirefoxDriver();
        dict.addDict("driver", driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

    }

    @Before("@API")
    public void APISetup(){
        var req = RestAssured.given().baseUri("http://localhost:2002");
        dict.addDict("apirequest", req);
    }

    @After("@GUI")
    public void tearDown() throws InterruptedException {
        driver.quit();
    }
}
