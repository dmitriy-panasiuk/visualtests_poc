package tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.ScreenshotManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class BaseTest {
    protected static WebDriver driver;
    protected static Wait wait;
    protected static Actions actions;

    @BeforeClass
    public static void before() throws IOException, URISyntaxException {
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver = new InternetExplorerDriver();
        ScreenshotManager.init(driver);
        //driver.manage().window().maximize();
        driver.manage().window().setSize(new Dimension(1200, 800));
        wait = new WebDriverWait(driver, 10);
        actions = new Actions(driver);
    }

    protected void removeElements(By by) {
        List<WebElement> elementsList = driver.findElements(by);
        for(WebElement element : elementsList ) {
            ((JavascriptExecutor)driver).executeScript("arguments[0].remove();", element);
        }
    }

    protected List<String> getPublicationPages(String pubId) throws IOException {
        List<String> pageUrls = new ArrayList<>();

        return pageUrls;
    }

    @AfterClass
    public static void after() {
        driver.quit();
    }
}
