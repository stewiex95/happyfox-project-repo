package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.TestUtilities;

/**
 * Author - Harish, Purpose - Class contains objects and methods for the priorities page where we can create new priority or view existing
 */
public class PrioritiesPage {

    public WebDriver driver;

    public PrioritiesPage(WebDriver driver) {
        this.driver = driver;
    }

    String alternatePriorityToChoose = "Low";
    public WebElement addPriorityLink() {
        return driver.findElement(By.xpath("//button[@data-test-id='new-priority']"));
    }

    public WebElement formFieldName() {
        return driver.findElement(By.xpath("//input[@data-test-id='form-field-name']"));
    }

    public WebElement formDescriptionTextArea() {
        return driver.findElement(By.xpath("//textarea[@data-test-id='form-field-description']"));
    }

    public WebElement formHelpAgentsTextArea() {
        return driver.findElement(By.xpath("//textarea[@data-test-id='form-field-helpText']"));
    }

    public WebElement formAddPriorityButton() {
        return driver.findElement(By.xpath("//button[@data-test-id='add-priority']"));
    }

    String tableXpath = "//div[@data-test-id='priorities-table']";

    public WebElement prioritiesTable() {
        return driver.findElement(By.xpath(tableXpath));
    }

    public String priorityNameInRowXpath(String priorityName) {
        return tableXpath + "//span[normalize-space()='" + priorityName + "']//parent::td";
    }

    public WebElement priorityNameInRow(String priorityName) {
        return driver.findElement(By.xpath(priorityNameInRowXpath(priorityName)));
    }

    public WebElement makeDefaultLinkForPriorityInRow(String priorityName) {
        return driver.findElement(By.xpath(priorityNameInRowXpath(priorityName) + "//parent::tr[contains(@data-test-id,'table-row-id')]//div[contains(@data-test-id,'make-default')]"));
    }

    public WebElement defaultPriorityTickIcon(String priorityName) {
        return driver.findElement(By.xpath(priorityNameInRowXpath(priorityName) + "//parent::tr[contains(@data-test-id,'table-row-id')]//div[@data-test-id='default-priority']"));
    }

    public WebElement deletePriorityLink() {
        return driver.findElement(By.xpath("//header[@data-test-id='view-priority-header']//a[@data-test-id='priority-delete-trigger']"));
    }

    public WebElement deleteLinkInConfirmationBox() {
        return driver.findElement(By.xpath("//button[@data-test-id='delete-dependants-primary-action']"));
    }

    public WebElement searchAlternatePriority() {
        return driver.findElement(By.xpath("//div[@data-test-id='form-field-alternateEntity']//div[@role='button']"));
    }


    public WebElement searchInputBoxForAlternatePriority() {
        return driver.findElement(By.xpath("//div[@data-test-id='form-field-alternateEntity']//input[@type='search']"));
    }


    public WebElement alternatePriorityInDropdownOptions(String option) {
        return driver.findElement(By.xpath("//div[@data-test-id='form-field-alternateEntity']//li[@role='option'][normalize-space()='" + option + "']"));
    }

    public WebElement deletionToast(String priorityLabel) {
        return driver.findElement(By.xpath("//div[@data-test-id='toast-message'][normalize-space()='Priority \"" + priorityLabel + "\" is deleted successfully.']"));
    }

    public WebElement closeToastMessage() {
        return driver.findElement(By.xpath(" //span[@data-test-id='toast-message-close']"));
    }

    public boolean isPrioritiesPageIsDisplayed() {
        return prioritiesTable().isDisplayed();
    }

    public void fillNewPriorityFormFields(String priorityName, String description, String helpAgentsText) {
        addPriorityLink().click();
        formFieldName().sendKeys(priorityName);
        formDescriptionTextArea().sendKeys(description);
        formHelpAgentsTextArea().sendKeys(helpAgentsText);
        formAddPriorityButton().click();
        Assert.assertTrue(priorityNameInRow(priorityName).isDisplayed(), "Newly added priority is not visible in the table.");
    }

    public void makePriorityDefault(String priorityName) throws InterruptedException {
        TestUtilities.mouseHoverAndClickOnElement(driver, priorityNameInRow(priorityName), makeDefaultLinkForPriorityInRow(priorityName));
        Assert.assertTrue(defaultPriorityTickIcon(priorityName).isDisplayed(), priorityName + " -> priority is not set as default in Priorities page.");
    }

    public void verifyToastMessageAfterPriorityDeletion(String priorityLabel) {
        boolean toast = deletionToast(priorityLabel).isDisplayed();
        Assert.assertTrue(toast, "Deletion toast message is not displayed.");
    }

    public void deletePriority(String priorityName) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", priorityNameInRow(priorityName));
        priorityNameInRow(priorityName).click();
        deletePriorityLink().click();
        searchAlternatePriority().click();
        searchInputBoxForAlternatePriority().click();
        alternatePriorityInDropdownOptions(alternatePriorityToChoose).click();
        deleteLinkInConfirmationBox().click();
        verifyToastMessageAfterPriorityDeletion(priorityName);
        closeToastMessage().click();
    }

}
