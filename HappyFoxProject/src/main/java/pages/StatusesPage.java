package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.TestUtilities;

/**
 * Author - Harish, Purpose - Class contains objects and methods for the statuses page where we can view the statuses for tickets and create new if needed
 */
public class StatusesPage {

    public WebDriver driver;

    public StatusesPage(WebDriver driver) {
        this.driver = driver;
    }

    String colorFieldPlaceholder = "Enter any valid color code format.";
    String formBehaviorFieldXpath = "//div[@data-test-id='form-field-behavior']";
    String formColorFieldCommonXpath = "//div[@data-test-id='form-field-color']";
    String alternateStatusToChoose = "Completed";
    String tableXpath = "//div[@data-test-id='statuses-table']";

    public WebElement statusesTable() {
        return driver.findElement(By.xpath(tableXpath));
    }

    public WebElement addStatusLink() {
        return driver.findElement(By.xpath("//button[@data-test-id='new-status']"));
    }

    public WebElement formFieldName() {
        return driver.findElement(By.xpath("//input[@data-test-id='form-field-name']"));
    }

    public WebElement formColorField() {
        return driver.findElement(By.xpath(formColorFieldCommonXpath + "//div[contains(@class,'sp-replacer sp-light')]"));
    }

    public WebElement colorFieldInputBox() {
        return driver.findElement(By.xpath(formColorFieldCommonXpath + "//input[@placeholder='" + colorFieldPlaceholder + "']"));
    }

    public WebElement formBehaviorField() {
        return driver.findElement(By.xpath(formBehaviorFieldXpath));
    }

    public WebElement formBehaviorFieldOption(String optionLabel) {
        return driver.findElement(By.xpath(formBehaviorFieldXpath + "//li[@role='option'][text()='" + optionLabel + "']"));
    }

    public WebElement formDescriptionTextArea() {
        return driver.findElement(By.xpath("//textarea[@data-test-id='form-field-description']"));
    }

    public WebElement formAddStatusButton() {
        return driver.findElement(By.xpath("//button[@data-test-id='add-status']"));
    }

    public String statusNameInRowXpath(String statusName) {
        return tableXpath + "//div[normalize-space()='" + statusName + "']//parent::td";
    }

    public WebElement statusNameInRow(String statusName) {
        return driver.findElement(By.xpath(statusNameInRowXpath(statusName)));
    }

    public WebElement makeDefaultLinkForStatusInRow(String statusName) {
        return driver.findElement(By.xpath(statusNameInRowXpath(statusName) + "//parent::tr[contains(@data-test-id,'table-row-id')]//a[contains(@data-test-id,'make-default')]"));
    }

    public WebElement defaultStatusTickIcon(String statusName) {
        return driver.findElement(By.xpath(statusNameInRowXpath(statusName) + "//parent::tr[contains(@data-test-id,'table-row-id')]//div[@data-test-id='default-status']"));
    }

    public WebElement deleteStatusLink() {
        return driver.findElement(By.xpath("//header[@data-test-id='view-status-header']//a[@data-test-id='status-delete-trigger']"));
    }

    public WebElement deleteLinkInConfirmationBox() {
        return driver.findElement(By.xpath("//button[@data-test-id='delete-dependants-primary-action']"));
    }

    public WebElement searchAlternateStatus() {
        return driver.findElement(By.xpath("//div[@data-test-id='form-field-alternateEntity']//div[@role='button']"));
    }


    public WebElement searchInputBoxForAlternateStatus() {
        return driver.findElement(By.xpath("//div[@data-test-id='form-field-alternateEntity']//input[@type='search']"));
    }

    public WebElement alternateStatusInDropdownOptions(String option) {
        return driver.findElement(By.xpath("//div[@data-test-id='form-field-alternateEntity']//li[@role='option'][normalize-space()='" + option + "']"));
    }

    public WebElement deletionToast(String statusLabel) {
        return driver.findElement(By.xpath("//div[@data-test-id='toast-message'][normalize-space()='Status \"" + statusLabel + "\" is deleted successfully.']"));
    }

    public WebElement closeToastMessage() {
        return driver.findElement(By.xpath(" //span[@data-test-id='toast-message-close']"));
    }

    public boolean isStatusesPageIsDisplayed() {
        return statusesTable().isDisplayed();
    }

    public void fillNewStatusFormFields(String statusName, String statusColor, String behaviourOptionLabel, String description) {
        addStatusLink().click();
        formFieldName().sendKeys(statusName);
        formColorField().click();
        colorFieldInputBox().clear();
        colorFieldInputBox().sendKeys(statusColor);
        formColorField().click();
        formBehaviorField().click();
        formBehaviorFieldOption(behaviourOptionLabel).click();
        formDescriptionTextArea().sendKeys(description);
        formAddStatusButton().click();
        Assert.assertTrue(statusNameInRow(statusName).isDisplayed(), "Newly added status is not visible in the table.");
    }

    public void makeStatusDefault(String statusName) throws InterruptedException {
        TestUtilities.mouseHoverAndClickOnElement(driver, statusNameInRow(statusName), makeDefaultLinkForStatusInRow(statusName));
        Assert.assertTrue(defaultStatusTickIcon(statusName).isDisplayed(), statusName + " -> status is not set as default in Statuses page.");
    }

    public void verifyToastMessageAfterStatusDeletion(String statusLabel) {
        boolean toast = deletionToast(statusLabel).isDisplayed();
        Assert.assertTrue(toast, "Deletion toast message is not displayed.");
    }

    public void deleteStatus(String statusName) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", statusNameInRow(statusName));
        statusNameInRow(statusName).click();
        deleteStatusLink().click();
        searchAlternateStatus().click();
        searchInputBoxForAlternateStatus().click();
        alternateStatusInDropdownOptions(alternateStatusToChoose).click();
        deleteLinkInConfirmationBox().click();
        verifyToastMessageAfterStatusDeletion(statusName);
        closeToastMessage().click();
    }
}
