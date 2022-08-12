package org.example;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class DemoAutoTest {
    WebDriver driver;
    final static String url = "http://the-internet.herokuapp.com/";
    final static String remote_AWS_EC2_Url = "http://204.236.191.158:4445";
    // The remote EC2 AWS would be different regarding instance (see Public IPv4 address + Selenium Hub port: 4445)

    @BeforeMethod
    @Parameters("browser")
    public void setup(String browser) throws MalformedURLException {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browserName", "chrome");
            driver = new RemoteWebDriver(new URL(remote_AWS_EC2_Url), caps);

        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
//            driver = new FirefoxDriver();

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browserName", "chrome");
            driver = new RemoteWebDriver(new URL(remote_AWS_EC2_Url), caps);
        }
        driver.manage().window().maximize();
        driver.get(url);
    }

    @Test
    public void demoAutoPageTitle() {
        String pageTitle = driver.getTitle();
        System.out.println(" =====> The Page Title is: " + pageTitle + " <===== ");
        Assert.assertEquals(pageTitle, "The Internet");
    }

    @Test
    public void listOfExamples() {
        List<WebElement> list = driver.findElements(By.cssSelector("div[id='content'] ul li"));
        for (WebElement s : list) {
            System.out.println(s.getText());
        }
        Assert.assertEquals(list.size(), 44);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
