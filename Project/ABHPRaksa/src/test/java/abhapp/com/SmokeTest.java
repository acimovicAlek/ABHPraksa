package abhapp.com;

import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by infloop on 10/7/16.
 */
public class SmokeTest {

    WebDriver driver = new FirefoxDriver();
    WebDriverWait wait = new WebDriverWait(driver,10);
    WebElement condition;

    @BeforeClass
    public void openDriver(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://ah-test.abhapp.com");
    }

    @AfterClass
    public void closeDriver(){
        driver.close();
        driver.quit();
    }


    @Test(priority = 1)
    public void logiInAsCompany() throws Exception {
        driver.findElement(By.xpath(".//*[@class='login-container']/div[2]/div[2]/form[1]/input[1]"))
                    .sendKeys("abhint@test.com");
        driver.findElement(By.xpath(".//*[@class='login-container']/div[2]/div[2]/form[1]/input[2]"))
                    .sendKeys("test123");
        driver.findElement(By.xpath(".//*[@class='ember-application']/div[2]/div/div[2]/div[2]/form/div/button"))
                    .click();
        //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//*[@class='ember-application']/div[2]/nav/div/div/div/a/img"))));

        condition = driver.findElement(By.xpath(".//*[@class='ember-application']/div[2]/nav/div/div/div/a/img"));
        Assert.assertTrue(condition.isDisplayed());

    }

    //Go to responisiblities page
    @Test(priority = 2)
    public void navigateToResponsibilities() throws Exception {
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='ember-application']/div[2]/nav/div/div[2]/ul/li[3]/a")));
        driver.findElement(By.xpath(".//*[@class='ember-application']/div[2]/nav/div/div[2]/ul/li[3]/a")).click();
        //wait.until(ExpectedConditions.urlToBe("http://ah-test.abhapp.com/responsibilities"));

        condition = driver.findElement(By.xpath(".//*[@href='/responsibilities/new']"));
        org.testng.Assert.assertTrue(condition.isDisplayed() && driver.getCurrentUrl().equals("http://ah-test.abhapp.com/responsibilities"));
    }


    //Go to create new responsiblity page
    @Test(priority = 3)
    public void navigateToNewResponsibility() throws Exception {
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@href='/responsibilities/new']")));
        driver.findElement(By.xpath(".//*[@href='/responsibilities/new']")).click();
        //wait.until(ExpectedConditions.urlToBe("http://ah-test.abhapp.com/responsibilities/new"));

        condition = driver.findElement(By.xpath(".//*[@class='buttons']/button"));
        org.testng.Assert.assertTrue(condition.isDisplayed() && driver.getCurrentUrl().equals("http://ah-test.abhapp.com/responsibilities/new"));
    }

    //Input data for new responisibility
    @Test(priority = 4)
    public void createNewResponsibility() throws Exception {
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='buttons']/button")));
        driver.findElement(By.xpath(".//*[@class='responsibility-form']/div/input")).sendKeys("ResponsibilitySMOKE");
        driver.findElement(By.xpath(".//*[@id='description']")).sendKeys("TEST");
        driver.findElement(By.xpath(".//*[@class='buttons']/button")).click();
        //wait.until(ExpectedConditions.urlToBe("http://ah-test.abhapp.com/responsibilities"));
        //wait.until(ExpectedConditions.textToBePresentInElement(By.xpath(".//*[@class='content']"), "ResponsibilitySMOKE"));

        condition = driver.findElement(By.xpath("//span[contains(text(),'ResponsibilitySMOKE')]"));
        org.testng.Assert.assertTrue(
                condition.isDisplayed()
                        && driver.getCurrentUrl().
                        equals("http://ah-test.abhapp.com/responsibilities"));
    }

    //Go to projects page
    @Test(priority = 5)
    public void navigateToProjects() throws Exception {
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='ember-application']/div[2]/nav/div/div[2]/ul/li[2]/a")));
        driver.findElement(By.xpath(".//*[@href='/projects']")).click();
        //wait.until(ExpectedConditions.urlToBe("http://ah-test.abhapp.com/projects"));
        condition = driver.findElement(By.xpath(".//*[@class='heading']/nav"));

        org.testng.Assert.assertTrue(condition.isDisplayed() && driver.getCurrentUrl().equals("http://ah-test.abhapp.com/projects"));
    }

    //Create new project
    @Test(priority = 6)
    public void navigateToNewProjectMenu() throws Exception {
        wait.until(ExpectedConditions.urlToBe("http://ah-test.abhapp.com/projects"));
        driver.findElement(By.xpath(".//*[@class='heading']/a")).click();
        //wait.until(ExpectedConditions.urlToBe("http://ah-test.abhapp.com/projects/new"));

        condition = driver.findElement(By.xpath(".//*[@class='project-cards']/a[1]"));
        org.testng.Assert.assertTrue(condition.isDisplayed() && driver.getCurrentUrl().equals("http://ah-test.abhapp.com/projects/new"));
    }

    //Select new Existing project
    @Test(priority = 7)
    public void selectExistingProject() throws Exception {
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='project-cards']/a[1]")));
        driver.findElement(By.xpath(".//*[@class='project-cards']/a[1]")).click();
        wait.until(ExpectedConditions.urlToBe("http://ah-test.abhapp.com/projects/new/active"));

        condition = driver.findElement(By.xpath(".//*[@class='buttons']/button"));
        org.testng.Assert.assertTrue(condition.isDisplayed() && driver.getCurrentUrl().equals("http://ah-test.abhapp.com/projects/new/active"));
    }

    //Fill form
    @Test(priority = 8)
    public void inputProjectData() throws Exception {
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='buttons']/button")));
        driver.findElement(By.xpath(".//*[@id='name']")).sendKeys("TestProject1SMOKE");
        driver.findElement(By.xpath(".//*[@id='description']")).sendKeys("TEST");

        //Get date
        SimpleDateFormat dateFromat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 15);

        driver.findElement(By.xpath(".//*[@id='startDateActive']")).sendKeys(dateFromat.format(calendar.getTime()));
        driver.findElement(By.xpath(".//*[@class='buttons']/button")).click();
        //wait.until(ExpectedConditions.urlContains("/edit/responsibilities"));

        condition = driver.findElement(By.xpath(".//*[@class='add-responsibilities-container']"));
        org.testng.Assert.assertTrue(condition.isDisplayed() && driver.getCurrentUrl().contains("/edit/responsibilities"));
    }

    //Add responsibility
    @Test(priority = 9)
    public void addResponsibilityToProject() throws Exception {
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='buttons']/button")));
        driver.findElement(By.xpath(".//*[@class='typeahead-wrap']/input")).sendKeys("SMOKE");
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='tt-dataset-states']/span/div[1]")));
        List<WebElement> elements = driver.findElements(By.xpath(".//*[@class='tt-dataset-states']/span/div"));

        for (WebElement i:
                elements) {
            if(i.getText().equals("ResponsibilitySMOKE")) {i.click();break;}
        }

        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='project-responsibility-container']/a")));

        condition = driver.findElement(By.xpath(".//*[@class='project-responsibility-container']/a"));
        org.testng.Assert.assertTrue(condition.isDisplayed());
    }

    //Click invite button
    @Test(priority = 10)
    public void openInviteForm() throws Exception {
        driver.findElement(By.xpath(".//*[@class='project-responsibility-container']/a")).click();
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='typeahead-wrap']/input")));
        condition = driver.findElement(By.xpath(".//*[@class='typeahead-wrap']/input"));
        org.testng.Assert.assertTrue(condition.isDisplayed());
    }

    //Select Employee0
    @Test(priority = 11)
    public void selectEmployee() throws Exception {
        driver.findElement(By.xpath(".//*[@class='typeahead-wrap']/input")).sendKeys("Employee");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='tt-dataset-states'][1]")));
        List<WebElement> elements = driver.findElements(By.xpath(".//*[@class='tt-dataset-states']/span/div"));

        for (WebElement i:
                elements) {
            if(i.getText().equals("Employee0 Employee")) i.click();
        }

        driver.findElement(By.xpath(".//*[@class='buttons']/button")).click();
        wait.until(ExpectedConditions.urlContains("/edit/responsibilities"));

        condition = driver.findElement(By.xpath(".//*[@class='buttons']/button"));
        org.testng.Assert.assertTrue(condition.isDisplayed() && driver.getCurrentUrl().contains("/edit/responsibilities"));
    }

    //Navigate to attachments tab
    @Test(priority = 12)
    public void navigateToAttachments() throws Exception {
        driver.findElement(By.xpath(".//*[@class='buttons']/button")).click();
        //wait.until(ExpectedConditions.urlContains("/edit/attachments"));
        condition = driver.findElement(By.xpath(".//*[@class='buttons']/a"));
        org.testng.Assert.assertTrue(condition.isDisplayed() && driver.getCurrentUrl().contains("/edit/attachments"));
    }

    //Finish creating project
    @Test(priority = 13)
    public void createProject() throws Exception {
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='buttons']/a")));
        driver.findElement(By.xpath(".//*[@class='buttons']/a")).click();
        //wait.until(ExpectedConditions.textToBePresentInElement(By.xpath(".//*[@class='heading project-heading']"),"TestProject1SMOKE"));
        condition = driver.findElement(By.xpath(".//span[contains(text(),'TestProject1SMOKE')]"));
        org.testng.Assert.assertTrue(condition.isDisplayed());
    }

    //Logout
    @Test(priority = 14)
    public void logout() throws Exception {
        driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[5]/div/img")).click();
        //wait.until(ExpectedConditions.
        //        elementToBeClickable(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[5]/div[2]/div/div[2]/button")));
        driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[5]/div[2]/div/div[2]/button")).click();
        //wait.until(ExpectedConditions.urlToBe("http://ah-test.abhapp.com/login"));

        condition = driver.findElement(By.xpath(".//*[@class='ember-application']/div[2]/div/div[2]/div[2]/form/div/button"));
        org.testng.Assert.assertTrue(condition.isDisplayed());
    }

    //Login as user
    @Test(priority = 15)
    public void loginAsUser() throws Exception {
        driver.findElement(By.xpath(".//*[@class='login-container']/div[2]/div[2]/form[1]/input[1]"))
                .sendKeys("employee@test.com");
        driver.findElement(By.xpath(".//*[@class='login-container']/div[2]/div[2]/form[1]/input[2]"))
                .sendKeys("test123");
        driver.findElement(By.xpath(".//*[@class='ember-application']/div[2]/div/div[2]/div[2]/form/div/button"))
                .click();
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='ember-application']/div[2]/nav/div/div/div/a/img")));

        condition = driver.findElement(By.xpath(".//*[@class='ember-application']/div[2]/nav/div/div/div/a/img"));
        Assert.assertTrue(condition.isDisplayed());
    }

    //Click on notification icon
    @Test(priority = 16)
    public void notifications() throws Exception {
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[6]/div/img")));
        driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[6]/div/img")).click();
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[6]/div[2]/div[2]")));
        condition = driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[6]/div[2]/div[2]"));
        org.testng.Assert.assertTrue(condition.isDisplayed());
    }

    //Select invite notification
    @Test(priority = 17)
    public void selectInvitation() throws Exception {
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='notifications-container']/div[2]/div[1]")));

        List<WebElement> elements = driver.findElements(By.xpath(".//*[@class='notification']/div/div[2]"));

        for (WebElement i:
                elements) {
            String typeOfNotification = "You've been assigned to a responsibility of";
            if(i.findElement(By.xpath(".//div[1]")).getText().equals(typeOfNotification))
                if (i.findElement(By.xpath(".//div[2]/span[1]")).getText().equals("ResponsibilitySMOKE")) {
                    if (i.findElement(By.xpath(".//div[2]/span[2]")).getText().equals("TestProject1SMOKE")) i.click();
                }
        }

        //wait.until(ExpectedConditions.textToBePresentInElement(By.xpath(".//*[@class='heading project-heading']"),
        //            "TestProject1SMOKE"));

        condition = driver.findElement(By.xpath(".//span[contains(text(),'TestProject1SMOKE')]"));
        org.testng.Assert.assertTrue(condition.isDisplayed());

    }

    //Navigate to invite details
    @Test(priority = 18)
    public void inviteDetails() throws Exception {
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='assigned-responsibility']/a")));
        driver.findElement(By.xpath(".//*[@class='assigned-responsibility']/a")).click();
        //wait.until(ExpectedConditions.urlContains("/responsibilities/see-position"));
        condition = driver.findElement(By.xpath(".//*[@class='actions']/div[2]/a"));
        org.testng.Assert.assertTrue(condition.isDisplayed() && driver.getCurrentUrl().contains("/responsibilities/see-position"));
    }

    //Accept invite
    @Test(priority = 19)
    public void acceptInvite() throws Exception {
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='actions']/div[2]/a")));
        driver.findElement(By.xpath(".//*[@class='actions']/div[2]/a")).click();
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='set-engagement-container']/div[5]")));
        condition = driver.findElement(By.xpath(".//*[@class='set-engagement-container']/div[5]"));
        org.testng.Assert.assertTrue(condition.isDisplayed());
    }

    //Set engagement and save
    @Test(priority = 20)
    public void setEngagementAndFinish() throws Exception {
        WebElement slider = driver.findElement(By.xpath(".//*[@class='project-container'][1]/div[3]/div[1]/div[1]/span"));

        Actions move = new Actions(driver);
        Action action = move.dragAndDropBy(slider,20, 0).build();
        action.perform();

        driver.findElement(By.xpath(".//*[@class='buttons']/button")).click();
        //wait.until(ExpectedConditions.textToBePresentInElement(By.xpath(".//*[@class='heading project-heading']"),
        //        "TestProject1SMOKE"));

        condition = driver.findElement(By.xpath(".//span[contains(text(),'TestProject1SMOKE')]"));
        org.testng.Assert.assertTrue(condition.isDisplayed());
    }
}
