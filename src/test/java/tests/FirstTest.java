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
        driver.get("http://ditadelivery01.ams.dev/home");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".image")));
    }

    @Test
    public void landingTopicTest() throws IOException {

        int diff = ScreenshotManager.compareWithExpected("landing_topic");
        assertEquals("Images are no the same", diff, 0);
    }

    @Test
    public void tocTest() throws IOException {
        WebElement secondTocEntry = driver.findElement(By.xpath("//div[text() = 'For your safety']"));

        //actions.moveToElement(secondTocEntry).build().perform();
        int diff = ScreenshotManager.compareWithExpected("toc_hover_over_entry");
        assertEquals("Images are no the same", diff, 0);
    }
}
