package tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.ScreenshotManager;

import java.io.IOException;
import java.net.URISyntaxException;

public class BaseTest {
    protected static WebDriver driver;
    protected static Wait wait;
    protected static Actions actions;

    @BeforeClass
    public static void before() throws IOException, URISyntaxException {
        driver = new ChromeDriver();
        ScreenshotManager.init(driver);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        actions = new Actions(driver);
    }

    @AfterClass
    public static void after() {
        driver.quit();
    }
}
