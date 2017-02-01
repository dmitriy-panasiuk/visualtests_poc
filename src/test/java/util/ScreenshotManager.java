package util;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScreenshotManager {
    private static String expectedDir;
    private static String actualDir;
    private static String diffDir;
    private static String gifDir;
    private static WebDriver driver;

    public static void init(WebDriver webDriver) throws IOException, URISyntaxException {
        driver = webDriver;
        URL resourcesDir = ScreenshotManager.class.getClassLoader().getResource(".");
        setRootScreenshotsDir(Paths.get(resourcesDir.toURI()).toAbsolutePath() + "/screenshots");
    }

    private static Screenshot takeScreenshot(String name) throws IOException {
        Screenshot actualScreenshot = new AShot().takeScreenshot(driver);
        File result = new File(actualDir + name);
        ImageIO.write(actualScreenshot.getImage(), "png", result);
        return actualScreenshot;
    }

    public static int compareWithExpected(String name) throws IOException {
        String imageName = getScreenShotName(name);
        Screenshot actualScreenshot = takeScreenshot(imageName);
        Screenshot expectedScreenshot = null;
        try {
            expectedScreenshot = new Screenshot(ImageIO.read(new File(expectedDir + "/" + imageName)));
        } catch (IOException e) {
            throw new IOException("expected screenshot is not found");
        }
        ImageDiff diff = new ImageDiffer().makeDiff(expectedScreenshot, actualScreenshot);

        if (diff.getDiffSize() > 0) {
            File diffFile = new File(diffDir + "/" + imageName);
            ImageIO.write(diff.getMarkedImage(), "png", diffFile);
            createGif(imageName);
        }
        return diff.getDiffSize();
    }

    private static void createGif(String imageName) throws IOException {
        File gifFile = new File(gifDir + "/" + imageName + ".gif");
        BufferedImage firstImage = ImageIO.read(new File(actualDir + "/" + imageName));
        BufferedImage secondImage = ImageIO.read(new File(expectedDir + "/" + imageName));
        BufferedImage thirdImage = ImageIO.read(new File(diffDir + "/" + imageName));

        ImageOutputStream output = new FileImageOutputStream(gifFile);
        GifSequenceWriter writer = new GifSequenceWriter(output, firstImage.getType(), 1000, true);
        writer.writeToSequence(firstImage);
        writer.writeToSequence(secondImage);
        writer.writeToSequence(thirdImage);

        writer.close();
        output.close();
    }

    private static void setRootScreenshotsDir(String absolutePath) throws IOException {
        expectedDir = absolutePath + "/expected/";
        actualDir = absolutePath + "/actual/";
        diffDir = absolutePath + "/diff/";
        gifDir = absolutePath + "/gifs/";
        createFolders();
    }

    private static void createFolders() throws IOException {
        Files.createDirectories(Paths.get(expectedDir));
        Files.createDirectories(Paths.get(actualDir));
        Files.createDirectories(Paths.get(diffDir));
        Files.createDirectories(Paths.get(gifDir));
    }

    private static String getScreenShotName(String name) {
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = cap.getBrowserName().toLowerCase();
        String resolution = driver.manage().window().getSize().toString();
        return name + "_" + browserName + "_" + resolution + ".png";
    }
}
