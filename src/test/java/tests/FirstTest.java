package tests;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.ScreenshotManager;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FirstTest extends BaseTest {
    @Before
    public void openHomePage() {
        driver.get("http://ditadelivery01.ams.dev/1420746/user-guide");
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".image")));
    }

    @Test
    public void landingTopicTest() throws IOException {
        int diff = ScreenshotManager.compareWithExpected("landing_topic");
        assertEquals("Images are no the same", 0, diff);
    }

    @Test
    public void tocTest() throws IOException {
        WebElement secondTocEntry = driver.findElement(By.xpath("//div[text() = 'For your safety']"));

        actions.moveToElement(secondTocEntry).build().perform();
        int diff = ScreenshotManager.compareWithExpected("toc_hover_over_entry");
        assertEquals("Images are no the same", 0, diff);
    }

    @Test
    public void topBarTest() throws IOException {
        WebElement topBar = driver.findElement(By.cssSelector(".sdl-dita-delivery-topbar"));

        int diff = ScreenshotManager.compareWithExpected("top_bar", topBar);
        assertEquals("Images are no the same", 0, diff);
    }

    @Test
    public void imageTest() throws IOException {
        driver.get("http://ditadelivery01.ams.dev/1420746/164290/");
        /*WebElement spinnerPage = driver.findElement(By.cssSelector(".sdl-dita-delivery-page .sdl-activityindicator"));
        WebElement spinnerToc = driver.findElement(By.cssSelector(".sdl-dita-delivery-toc .sdl-activityindicator"));
        wait.until(ExpectedConditions.stalenessOf(spinnerPage));
        wait.until(ExpectedConditions.stalenessOf(spinnerToc));*/
        WebElement breadcrumbs = driver.findElement(By.cssSelector(".sdl-dita-delivery-breadcrumbs"));
        WebElement pageTitle = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".title")));
        wait.until(ExpectedConditions.textToBePresentInElement(breadcrumbs, pageTitle.getText()));

        int diff = ScreenshotManager.compareWithExpected("image");
        assertEquals("Images are no the same", 0, diff);
    }

    @Test
    public void pubTest() throws IOException {
        WebElement breadcrumbs = driver.findElement(By.cssSelector(".sdl-dita-delivery-breadcrumbs"));
        WebElement pageTitle = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".title")));
        wait.until(ExpectedConditions.textToBePresentInElement(breadcrumbs, pageTitle.getText()));
        WebElement thirdTocEntry = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text() = 'Your phone']/../i[@class = 'expand-collapse']")));
        thirdTocEntry.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text() = 'Your phone']/../../div[@class='children']")));
        int diff = ScreenshotManager.compareWithExpected("expand");
        assertEquals("Images are no the same", 0, diff);
    }
}
