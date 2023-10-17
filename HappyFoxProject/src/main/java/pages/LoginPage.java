package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.TestUtilities;

import java.time.Duration;

/**
 * Author - Harish, Purpose - Class contains objects and methods for the login page where user can sign in
 */
public class LoginPage {

    public WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    String userEmailXpath = "//input[@id='id_username']";
    String loggedOutConfirmationText = "You have logged out successfully.";
    public WebElement loginForm() {
        return driver.findElement(By.xpath("//form[@class='login-form']"));
    }
    public WebElement userNameField() {
        return driver.findElement(By.xpath(userEmailXpath));
    }

    public WebElement passwordField() {
        return driver.findElement(By.xpath("//input[@id='id_password']"));
    }

    public WebElement logInBtn() {
        return driver.findElement(By.xpath("//input[@id='btn-submit']"));
    }

    public WebElement loggedOutConfirmation() {
        return driver.findElement(By.xpath("//div[@class='confirmation'][normalize-space()='" + loggedOutConfirmationText + "']"));
    }
    public boolean isLoginPageDisplayed(){
        return loginForm().isDisplayed();
    }
    public void enterDetailsAndSignIn(String userEmail, String password) {
        userNameField().sendKeys(userEmail);
        passwordField().sendKeys(password);
        logInBtn().click();
    }

}
