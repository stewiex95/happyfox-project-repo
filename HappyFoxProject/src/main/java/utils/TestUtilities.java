package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;

/**
 * Author - Harish, Purpose - Class contains objects and methods to be used on a global scale for classes. It contains common methods.
 */
public class TestUtilities {
    public static void mouseHoverAndClickOnElement(WebDriver driver, WebElement hoverElement, WebElement clickElement) throws InterruptedException {
        Actions actions = new Actions(driver);
        actions.moveToElement(hoverElement);
        Thread.sleep(1000);
        actions.moveToElement(clickElement);
        actions.click().build().perform();
    }

    public static void takeScreenshotAndStoreInLocation(WebDriver webdriver, String fileWithPath) throws Exception {
        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(SrcFile, DestFile);
    }

    public static String captureExtentReportsFailure(WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File destination = new File("./src/test/reports/ExtentReports/FailureScreenshots/failure_ss.png");
        FileUtils.copyFile(source, destination);
        return destination.getAbsolutePath();
    }

}
