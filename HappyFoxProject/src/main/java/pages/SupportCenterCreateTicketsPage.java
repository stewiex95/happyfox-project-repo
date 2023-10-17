package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * Author - Harish, Purpose - Class contains objects and methods for Support center page where we create tickets to agents. Here we handle the form fields.
 */
public class SupportCenterCreateTicketsPage {
    public WebDriver driver;

    public SupportCenterCreateTicketsPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement supportCenterCreateTicketsPage() {
        return driver.findElement(By.xpath("//section[@class='hf-new-ticket-page ']"));
    }

    public WebElement subjectField() {
        return driver.findElement(By.xpath("//input[@id='id_subject']"));
    }

    public WebElement messageField() {
        return driver.findElement(By.xpath("//div[@id='cke_id_html']//div[@role='textbox']"));
    }

    public WebElement contactNameField() {
        return driver.findElement(By.xpath("//div[@class='hf-contact-fields']//input[@id='id_name']"));
    }

    public WebElement contactEmailField() {
        return driver.findElement(By.xpath("//div[@class='hf-contact-fields']//input[@id='id_email']"));
    }

    public WebElement contactPhoneField() {
        return driver.findElement(By.xpath("//div[@class='hf-contact-fields']//input[@id='id_phone']"));
    }

    public WebElement createTicketButton() {
        return driver.findElement(By.xpath("//button[@id='submit']"));
    }

    public WebElement attachFileInput() {
        return driver.findElement(By.xpath("//input[@id='attach-file-input']"));
    }

    public boolean isSupportCenterCreateTicketsPageDisplayed() {
        return supportCenterCreateTicketsPage().isDisplayed();
    }

    public void fillCreateTicketFormFields(String subject, String message, String filePath, String contactName, String contactEmail, String contactPhoneNumber) {
        subjectField().sendKeys(subject);
        messageField().sendKeys(message);
        attachFileInput().sendKeys(filePath);
        contactNameField().sendKeys(contactName);
        contactEmailField().sendKeys(contactEmail);
        contactPhoneField().sendKeys(contactPhoneNumber);
        createTicketButton().click();
    }

}
