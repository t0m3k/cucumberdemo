package org.example.utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
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

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

//        WebDriverManager.firefoxdriver().setup();
//        driver = new FirefoxDriver();
        dict.addDict("driver", driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

    }

    @After
    public void tearDown() throws InterruptedException {
        driver.quit();
    }
}
