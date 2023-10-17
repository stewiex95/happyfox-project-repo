package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * Author - Harish, Purpose - Class contains objects and methods for the Tickets page which contains all the tickets. Ticket details can be accessed using this class and verifications are done
 */
public class StaffTicketsPage {

    public WebDriver driver;

    public StaffTicketsPage(WebDriver driver) {
        this.driver = driver;
    }

    String replyToCustomerQueryText = "Reply to Customer Query";
    String useThisButtonLabel = "Use This";
    String pageLayoutCommonXpath = "//section[@class='hf-content hf-tickets-content ']";

    public WebElement ticketsPage() {
        return driver.findElement(By.xpath(pageLayoutCommonXpath));
    }

    public WebElement ticketStatus() {
        return driver.findElement(By.xpath("//div[@data-test-id='ticket-box_status']"));
    }

    public WebElement ticketPriority() {
        return driver.findElement(By.xpath("//div[@data-test-id='ticket-box_priority']"));
    }

    public WebElement replyLink() {
        return driver.findElement(By.xpath("//a[@data-test-id='reply-link']"));
    }

    public WebElement cannedActionLink() {
        return driver.findElement(By.xpath("//span[@data-test-id='canned-action-trigger']//parent::div"));
    }

    public WebElement replyToCustomerQueryAction() {
        return driver.findElement(By.xpath("//ul[@role='listbox']//li[normalize-space()='" + replyToCustomerQueryText + "']"));
    }

    public WebElement useThisInCannedActionPopover() {
        return driver.findElement(By.xpath("//div[contains(@class,'hf-canned-action-pane')]//button[normalize-space()='" + useThisButtonLabel + "']"));
    }

    public WebElement statusInReplyPopover(String statusText) {
        return driver.findElement(By.xpath("//div[contains(@class,'hf-floating-editor_footer-container')]" +
                "//div[@data-test-id='add-update-ticket-action_change-status_trigger']//div[@data-test-id='ticket-box_status'][normalize-space()='" + statusText + "']"));
    }

    public WebElement priorityInReplyPopover(String priorityText) {
        return driver.findElement(By.xpath("//div[contains(@class,'hf-floating-editor_footer-container')]" +
                "//div[@data-test-id='ticket-action_change-priority_trigger']//div[@data-test-id='ticket-box_priority'][normalize-space()='" + priorityText + "']"));
    }

    public WebElement tagsInReplyPopover(String tagsText) {
        return driver.findElement(By.xpath("//div[contains(@class,'hf-floating-editor_footer-container')]//div[@data-test-id='editor-add-tags-trigger']//div[normalize-space()='" + tagsText + "']"));
    }

    public WebElement ticketLinkInAllTicketsView(String ticketTitle) {
        return driver.findElement(By.xpath("//article[@data-test-id='ticket-box']//a[contains(@data-test-id,'link-to-ticket-details')][@title='" + ticketTitle + "']"));
    }

    public WebElement ticketDetailsSection() {
        return driver.findElement(By.xpath("//section[@class='hf-details hf-tickets-outlet-container']"));
    }

    public boolean isTicketsPageDisplayed(){
        return ticketsPage().isDisplayed();
    }
    public void verifyStatusAndPriorityOfTicketInDetails(String expectedStatus, String expectedPriority) {
        String statusText = ticketStatus().getAttribute("textContent").trim();
        Assert.assertEquals(statusText, expectedStatus, "Status of ticket is incorrect. Actual - " + statusText + ", Expected - " + expectedStatus);
        String priorityText = ticketPriority().getAttribute("textContent").trim();
        Assert.assertEquals(priorityText, expectedPriority, "Priority of ticket is incorrect. Actual - " + priorityText + ", Expected - " + expectedPriority);
    }

    public void navigateToTicketDetails(String ticketTitle) {
        ticketLinkInAllTicketsView(ticketTitle).click();
        Assert.assertTrue(ticketDetailsSection().isDisplayed(), "Ticket details section did not open for ticket - " + ticketTitle);
    }

    public void replyToTicketAndUseCannedActionInTicketDetails(String statusText, String priorityText, String tagsText) {
        replyLink().click();
        cannedActionLink().click();
        replyToCustomerQueryAction().click();
        useThisInCannedActionPopover().click();
        Assert.assertTrue(statusInReplyPopover(statusText).isDisplayed(), "Status of ticket after canned action in reply popover is incorrect. Expected - " + statusText);
        Assert.assertTrue(priorityInReplyPopover(priorityText).isDisplayed(), "Priority of ticket after canned action in reply popover is incorrect. Expected - " + priorityText);
        Assert.assertTrue(tagsInReplyPopover(tagsText).isDisplayed(), "Tags of ticket after canned action in reply popover is incorrect. Expected - " + tagsText);
    }

}
