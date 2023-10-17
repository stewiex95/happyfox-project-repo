package web_app_tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import components.StickyHeader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import pages.*;
import utils.TestUtilities;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HappyFoxTestCases {

    private WebDriver driver;
    private ExtentReports extent;
    private ExtentSparkReporter spark;

    private ExtentTest extentTest;

    // here we setup extent reporter
    @BeforeSuite
    public void setUpExtentHtmlReporter() {
        extent = new ExtentReports();
        spark = new ExtentSparkReporter("src/test/reports/ExtentReports/extentTestReport.html");
        extent.attachReporter(spark);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
    }

    // chrome driver is automatically picked up using WebDriverManager
    @BeforeClass
    public void setUpDriver() {
        WebDriverManager.chromedriver().setup();
    }

    // initialize ChromeDriver, set implicit wait time, navigate to agents portal
    @BeforeMethod
    public void initializeDriverAndFetchUrl(ITestContext context) {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://interview.supporthive.com/staff/login");
    }

    // login to agent portal, do assertions and validations based on landing page
    private void login(String userName, String password) {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page is not displayed.");
        loginPage.enterDetailsAndSignIn(userName, password);
        StaffTicketsPage staffTicketsPage = new StaffTicketsPage(driver);
        Assert.assertTrue(staffTicketsPage.isTicketsPageDisplayed(), "Tickets page is not displayed.");
    }

    // logout of app and land on login page, make assertions and validations
    private void logout() {
        StickyHeader stickyHeader = new StickyHeader(driver);
        stickyHeader.clickOnLogout();
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.loggedOutConfirmation().isDisplayed(), "Logout confirmation message is not displayed.");

    }

    @DataProvider(name = "TC01_Data")
    public Object[][] getTC01_Data() {
        return new Object[][]{
                {"Agent", "Agent@123"}
        };
    }

    @Test(description = "Login to Agent portal, create a new status and priority", priority = 1, dataProvider = "TC01_Data")
    public void loginToAgentPortalToCreateNewStatusAndPriority(String userName, String password) {
        extentTest = extent.createTest("HappyFoxTestCases.loginToAgentPortalToCreateNewStatusAndPriority", "Login to Agent portal, create a new status and priority");
        // Test data for creating status
        String statusName = "Issue created";
        String color = "#ff6300";
        String behaviorOption = "Pending";
        String statusDescription = "Status when a new ticket is created in HappyFox";
        extentTest.info("Status Data used -> statusName - " + statusName + ", color - " + color + ", behaviorOption -" + behaviorOption + ", statusDescription - " + statusDescription);

        // Test data for creating priority
        String priorityName = "Assistance required";
        String priorityDescription = "Priority of the newly created tickets";
        extentTest.info("Priority Data used -> priorityName - " + priorityName + ", priorityDescription - " + priorityDescription);

        // Login
        extentTest.info("loginUserName - " + userName + ", loginUserPassword - " + password);
        login(userName, password);
        extentTest.info("Login successful.");

        StickyHeader stickyHeader = new StickyHeader(driver);

        // Create new status by filling up status form fields
        stickyHeader.navigateToManageModuleSwitcherLink("Statuses");
        StatusesPage statusesPage = new StatusesPage(driver);
        Assert.assertTrue(statusesPage.isStatusesPageIsDisplayed(), "Statuses page is not displayed.");
        statusesPage.fillNewStatusFormFields(statusName, color, behaviorOption, statusDescription);

        // Create new priority by filling up priority form fields
        stickyHeader.navigateToManageModuleSwitcherLink("Priorities");
        PrioritiesPage prioritiesPage = new PrioritiesPage(driver);
        Assert.assertTrue(prioritiesPage.isPrioritiesPageIsDisplayed(), "Priorities page is not displayed.");
        prioritiesPage.fillNewPriorityFormFields(priorityName, priorityDescription, "-");

        extentTest.info("Added new status and priority in agent portal.");
        // Logout
        logout();
        extentTest.pass("HappyFoxTestCases.loginToAgentPortalToCreateNewStatusAndPriority - Test passed.");
    }

    @Test(description = "Verify ticket property changes after setting newly created status and priority as default", priority = 2, dataProvider = "TC01_Data")
    public void verifyTicketChangesBasedOnDefaultStatusAndPriority(String userName, String password) throws Exception {
        extentTest = extent.createTest("HappyFoxTestCases.verifyTicketChangesBasedOnDefaultStatusAndPriority", "Verify ticket property changes after setting newly created status and priority as default");
        // Test data for newly added status
        String statusName = "Issue created";
        extentTest.info("Status Data used -> statusName - " + statusName);

        // Test data for newly added priority
        String priorityName = "Assistance required";
        extentTest.info("Priority Data used -> priorityName - " + priorityName);

        // Test data for support center create ticket form fields
        String subject = "Ticket for automation testing - 123";
        String message = "This is a ticket creation testing message";
        File file = new File("./src/test/reports/Screenshots/Screen.png");
        String filePath = file.getAbsolutePath();
        String contactName = "TesterQA123";
        String contactEmail = "harishs98+qatest@gmail.com";
        String contactPhoneNumber = "1234567890";

        // Test data to verify ticket details and canned action assertions based on newly added status and priority
        String cannedActionText = "Reply to Customer Query";
        extentTest.info("Canned action label -> cannedActionText - " + cannedActionText);
        String statusOfCannedAction = "shipment";
        String priorityOfCannedAction = "Critical";
        String tabsOfCannedAction = "3 Tags";
        extentTest.info("Canned action data -> statusOfCannedAction - " + statusOfCannedAction + ", priorityOfCannedAction - " + priorityOfCannedAction + ", tabsOfCannedAction -" + tabsOfCannedAction);

        // Login
        extentTest.info("loginUserName - " + userName + ", loginUserPassword - " + password);
        login(userName, password);
        extentTest.info("Login successful.");

        StickyHeader stickyHeader = new StickyHeader(driver);

        // Set new Status as default
        stickyHeader.navigateToManageModuleSwitcherLink("Statuses");
        StatusesPage statusesPage = new StatusesPage(driver);
        statusesPage.makeStatusDefault(statusName);

        // Set new Priority as default
        stickyHeader.navigateToManageModuleSwitcherLink("Priorities");
        PrioritiesPage prioritiesPage = new PrioritiesPage(driver);
        prioritiesPage.makePriorityDefault(priorityName);

        extentTest.info("Set newly created status and priority as default.");
        // logout of agent portal
        logout();

        // Create a ticket on Support center
        driver.get("https://interview.supporthive.com/new");
        SupportCenterCreateTicketsPage supportCenterCreateTicketsPage = new SupportCenterCreateTicketsPage(driver);
        Assert.assertTrue(supportCenterCreateTicketsPage.isSupportCenterCreateTicketsPageDisplayed(), "Support center create tickets page is not displayed.");
        // take screenshot of form, store in location - reports/Screenshots
        TestUtilities.takeScreenshotAndStoreInLocation(driver, filePath);
        extentTest.info("Screenshot of form is saved in project.");
        // Fill create ticket form, attach screenshot
        supportCenterCreateTicketsPage.fillCreateTicketFormFields(subject, message, filePath, contactName, contactEmail, contactPhoneNumber);
        extentTest.info("Support center ticket is created along with a screenshot attachment.");

        // Open newly created ticket details
        driver.get("https://interview.supporthive.com/staff/login");
        login(userName, password);
        StaffTicketsPage staffTicketsPage = new StaffTicketsPage(driver);
        staffTicketsPage.navigateToTicketDetails(subject);

        // Verify ticket details - property changes like status, priority and tags
        staffTicketsPage.verifyStatusAndPriorityOfTicketInDetails(statusName, priorityName);
        // reply to ticket using canned action and verify changes, make assertions and validations
        staffTicketsPage.replyToTicketAndUseCannedActionInTicketDetails(statusOfCannedAction, priorityOfCannedAction, tabsOfCannedAction);

        // logout of agent portal
        logout();
        extentTest.pass("HappyFoxTestCases.verifyTicketChangesBasedOnDefaultStatusAndPriority - Test passed.");
    }

    @Test(description = "Delete newly created status and priority and logout", priority = 3, dataProvider = "TC01_Data")
    public void deleteNewlyCreatedStatusAndPriority(String userName, String password) {
        extentTest = extent.createTest("HappyFoxTestCases.deleteNewlyCreatedStatusAndPriority", "Delete newly created status and priority and logout");
        // Test data for newly added status
        String statusName = "Issue created";
        extentTest.info("Status Data used -> statusName - " + statusName);

        // Test data for newly added priority
        String priorityName = "Assistance required";
        extentTest.info("Priority Data used -> priorityName - " + priorityName);

        // Login
        extentTest.info("loginUserName - " + userName + ", loginUserPassword - " + password);
        login(userName, password);
        extentTest.info("Login successful.");

        StickyHeader stickyHeader = new StickyHeader(driver);

        // Delete newly status, assign the tickets under the status to alternate status
        stickyHeader.navigateToManageModuleSwitcherLink("Statuses");
        StatusesPage statusesPage = new StatusesPage(driver);
        statusesPage.deleteStatus(statusName);

        // Delete newly priority, assign the tickets under the status to alternate priority
        stickyHeader.navigateToManageModuleSwitcherLink("Priorities");
        PrioritiesPage prioritiesPage = new PrioritiesPage(driver);
        prioritiesPage.deletePriority(priorityName);

        extentTest.info("Deleted new status and priority in agent portal.");
        // Logout
        logout();
        extentTest.pass("HappyFoxTestCases.deleteNewlyCreatedStatusAndPriority - Test passed.");
    }

    // Quit driver after test method has run.
    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if(result.getStatus() == ITestResult.FAILURE)
        {
            String path = TestUtilities.captureExtentReportsFailure(driver);
            extentTest.addScreenCaptureFromPath(path);
        }

        if (driver != null) {
            driver.quit();
        }
    }

    // Write extentReports
    @AfterSuite
    public void generateExtentReports() {
        extent.flush();
    }

}