package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestUtilities;

import java.time.Duration;

/**
 * Author - Harish, Purpose - Class contains objects and methods for the sticky header which contains the navigation links, search and user profile icon
 */
public class StickyHeader {

    public WebDriver driver;

    public StickyHeader(WebDriver driver) {
        this.driver = driver;
    }

    String ticketsModuleSwitcherInHeaderXpath = "//div[@data-test-id='module-switcher_trigger']//span[@data-test-id='current-module']";
    String manageSectionInSwitcherXpath = "//div[@data-test-id='module-switcher_manage-title']//parent::div[@class='hf-module-switcher-module']";

    public WebElement tickersModuleSwitcherInHeader() {
        return driver.findElement(By.xpath(ticketsModuleSwitcherInHeaderXpath));
    }
    public WebElement userMenuInHeader() {
        return driver.findElement(By.xpath("//div[@data-test-id='staff-menu']"));
    }

    public WebElement logoutLinkInMenu() {
        return driver.findElement(By.xpath("//li[@data-test-id='staff-menu_logout']"));
    }

    public WebElement optionInManageSection(String linkLabel) {
        return driver.findElement(By.xpath(manageSectionInSwitcherXpath + "//li//a[normalize-space()='" + linkLabel + "']"));
    }

    public void navigateToManageModuleSwitcherLink(String linkLabel) {
        tickersModuleSwitcherInHeader().click();
        optionInManageSection(linkLabel).click();
    }

    public void clickOnLogout(){
        userMenuInHeader().click();
        logoutLinkInMenu().click();
    }
}
