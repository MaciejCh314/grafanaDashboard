package org.example.recorder;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class TestCase {

    ChromeDriver driver;
    WebDriverWait wait;

//    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        wait=new WebDriverWait(driver, Duration.ofSeconds(30));

    }
    @Test
    public void example() throws Exception {
        ScreenRecorderUtil.startRecord("example");
//        driver.get("https://www.google.com");
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Google']")));
        Thread.sleep(5000);
        ScreenRecorderUtil.stopRecord();
    }

//    @AfterTest
    public void closeDown() {
        driver.quit();
    }
}
