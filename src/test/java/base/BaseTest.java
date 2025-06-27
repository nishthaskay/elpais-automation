package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class BaseTest {
    protected WebDriver driver;

    @Parameters({ "browser", "useBrowserStack" })
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional("chrome") String browser, @Optional("false") boolean useBrowserStack) throws Exception {

        if (useBrowserStack) {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            switch (browser.toLowerCase()) {
                case "firefox":
                    capabilities.setCapability("browserName", "Firefox");
                    capabilities.setCapability("browserVersion", "latest");
                    break;
                case "edge":
                    capabilities.setCapability("browserName", "Edge");
                    capabilities.setCapability("browserVersion", "latest");
                    break;
                case "iphone":
                    capabilities.setCapability("browserName", "iPhone");
                    capabilities.setCapability("device", "iPhone 14");
                    capabilities.setCapability("realMobile", "true");
                    capabilities.setCapability("os_version", "16");
                    break;
                case "pixel":
                    capabilities.setCapability("browserName", "Android");
                    capabilities.setCapability("device", "Google Pixel 7");
                    capabilities.setCapability("realMobile", "true");
                    capabilities.setCapability("os_version", "13.0");
                    break;
                default:
                    capabilities.setCapability("browserName", "Chrome");
                    capabilities.setCapability("browserVersion", "latest");
                    break;
            }

            HashMap<String, Object> bstackOptions = new HashMap<>();
            bstackOptions.put("os", "Windows");
            bstackOptions.put("osVersion", "10");
            bstackOptions.put("projectName", "ElPais Automation");
            bstackOptions.put("buildName", "Build 1.0");
            bstackOptions.put("sessionName", "ElPais Test");

            capabilities.setCapability("bstack:options", bstackOptions);

            String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
            String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
            String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";

            driver = new RemoteWebDriver(new URL(URL), capabilities);
        } else {
            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = (WebDriver) new EdgeDriver();
                    break;
                case "safari":
                    driver = new SafariDriver();
                    break;
                default:
                    WebDriverManager.chromedriver().setup();
                    driver = (WebDriver) new ChromeDriver();
                    break;
            }
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
