package abhapp.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by infloop on 10/7/16.
 */
public class SmokeTest {

    WebDriver driver = new FirefoxDriver();
    WebDriverWait wait = new WebDriverWait(driver,10);

    @Test(priority = 1)
    public void step1() throws Exception {
        try{
            driver.manage().window().maximize();
        driver.get("http://ah-test.abhapp.com");

        //Login as company

            driver.findElement(By.xpath(".//*[@class='login-container']/div[2]/div[2]/form[1]/input[1]"))
                    .sendKeys("abhint@test.com");
            driver.findElement(By.xpath(".//*[@class='login-container']/div[2]/div[2]/form[1]/input[2]"))
                    .sendKeys("test123");
            driver.findElement(By.xpath(".//*[@class='ember-application']/div[2]/div/div[2]/div[2]/form/div/button"))
                   .click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='ember-application']/div[2]/nav/div/div/div/a/img")));

            //driver.findElement();
        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }

    }

    //Go to responisiblities page
    @Test(priority = 2)
    public void step2() throws Exception {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='ember-application']/div[2]/nav/div/div[2]/ul/li[3]/a")));
            driver.findElement(By.xpath(".//*[@class='ember-application']/div[2]/nav/div/div[2]/ul/li[3]/a")).click();
            wait.until(ExpectedConditions.urlToBe("http://ah-test.abhapp.com/responsibilities"));
            //String URL = driver.getCurrentUrl();
            //if(!URL.equals(new String("http://ah-test.abhapp.com/projects"))) throw new Exception("FAIL");
        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    //Go to create new responsiblity page
    @Test(priority = 3)
    public void step3() throws Exception {
        try{
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@href='/responsibilities/new']")));
            driver.findElement(By.xpath(".//*[@href='/responsibilities/new']")).click();
            wait.until(ExpectedConditions.urlToBe("http://ah-test.abhapp.com/responsibilities/new"));
        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    //Input data for new responisibility
    @Test(priority = 4)
    public void step4() throws Exception {
        try{
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='buttons']/button")));
            driver.findElement(By.xpath(".//*[@class='responsibility-form']/div/input")).sendKeys("ResponsibilitySMOKE");
            driver.findElement(By.xpath(".//*[@id='description']")).sendKeys("TEST");
            driver.findElement(By.xpath(".//*[@class='buttons']/button")).click();
            wait.until(ExpectedConditions.urlToBe("http://ah-test.abhapp.com/responsibilities"));
            wait.until(ExpectedConditions.textToBePresentInElement(By.xpath(".//*[@class='content']"), "ResponsibilitySMOKE"));
        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    //Go to projects page
    @Test(priority = 5)
    public void step5() throws Exception {
        try{
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='ember-application']/div[2]/nav/div/div[2]/ul/li[2]/a")));
            driver.findElement(By.xpath(".//*[@class='ember-application']/div[2]/nav/div/div[2]/ul/li[2]/a")).click();
            wait.until(ExpectedConditions.urlToBe("http://ah-test.abhapp.com/projects"));
        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    //Create new project
    @Test(priority = 6)
    public void step6() throws Exception {
        try{
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='heading']/a")));
            driver.findElement(By.xpath(".//*[@class='heading']/a")).click();
            wait.until(ExpectedConditions.urlToBe("http://ah-test.abhapp.com/projects/new"));
        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    //Select new Existing project
    @Test(priority = 7)
    public void step7() throws Exception {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='project-cards']/a[1]")));
            driver.findElement(By.xpath(".//*[@class='project-cards']/a[1]")).click();
            wait.until(ExpectedConditions.urlToBe("http://ah-test.abhapp.com/projects/new/active"));
        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    //Fill form
    @Test(priority = 8)
    public void step8() throws Exception {
        try{
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='buttons']/button")));
            driver.findElement(By.xpath(".//*[@id='name']")).sendKeys("TestProject1SMOKE");
            driver.findElement(By.xpath(".//*[@id='description']")).sendKeys("TEST");

            //Get date
            SimpleDateFormat dateFromat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 15);

            driver.findElement(By.xpath(".//*[@id='startDateActive']")).sendKeys(dateFromat.format(calendar.getTime()));
            driver.findElement(By.xpath(".//*[@class='buttons']/button")).click();
            wait.until(ExpectedConditions.urlContains("/edit/responsibilities"));
        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    //Add responsibility
    @Test(priority = 9)
    public void step9() throws Exception {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='buttons']/button")));
            driver.findElement(By.xpath(".//*[@class='typeahead-wrap']/input")).sendKeys("SMOKE");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='tt-dataset-states']/span/div[1]")));
            List<WebElement> elements = driver.findElements(By.xpath(".//*[@class='tt-dataset-states']/span/div"));

            for (WebElement i:
                 elements) {
                if(i.getText().equals("ResponsibilitySMOKE")) {i.click();break;}
            }

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='project-responsibility-container']/a")));

            //driver.findElement(By.xpath(".//*[@class='buttons']/button")).click();
            //wait.until(ExpectedConditions.urlContains("/edit/attachments"));
        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }


    @Test(priority = 10)
    public void step10() throws Exception {
        try {
            driver.findElement(By.xpath(".//*[@class='project-responsibility-container']/a")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='typeahead-wrap']/input")));

        }catch (Exception e)
        {
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    @Test(priority = 11)
    public void step11() throws Exception {
        try {

            driver.findElement(By.xpath(".//*[@class='typeahead-wrap']/input")).sendKeys("Employee");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='tt-dataset-states'][1]")));
            List<WebElement> elements = driver.findElements(By.xpath(".//*[@class='tt-dataset-states']/span/div"));

            for (WebElement i:
                 elements) {
                if(i.getText().equals("Employee0 Employee")) i.click();
            }

            driver.findElement(By.xpath(".//*[@class='buttons']/button")).click();
            wait.until(ExpectedConditions.urlContains("/edit/responsibilities"));

        }catch (Exception e)
        {
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    @Test(priority = 12)
    public void step12() throws Exception {
        try{

            driver.findElement(By.xpath(".//*[@class='buttons']/button")).click();
            wait.until(ExpectedConditions.urlContains("/edit/attachments"));

        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    @Test(priority = 13)
    public void step13() throws Exception {
        try{
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='buttons']/a")));
            driver.findElement(By.xpath(".//*[@class='buttons']/a")).click();
            wait.until(ExpectedConditions.textToBePresentInElement(By.xpath(".//*[@class='heading project-heading']"),
                    "TestProject1SMOKE"));
        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    //Logout
    @Test(priority = 14)
    public void step14() throws Exception {
        try {
            driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[5]/div/img")).click();
            wait.until(ExpectedConditions.
                    elementToBeClickable(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[5]/div[2]/div/div[2]/button")));
            driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[5]/div[2]/div/div[2]/button")).click();
            wait.until(ExpectedConditions.urlToBe("http://ah-test.abhapp.com/login"));
        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    //Login as user
    @Test(priority = 15)
    public void step15() throws Exception {
        try{
            driver.findElement(By.xpath(".//*[@class='login-container']/div[2]/div[2]/form[1]/input[1]"))
                    .sendKeys("employee@test.com");
            driver.findElement(By.xpath(".//*[@class='login-container']/div[2]/div[2]/form[1]/input[2]"))
                    .sendKeys("test123");
            driver.findElement(By.xpath(".//*[@class='ember-application']/div[2]/div/div[2]/div[2]/form/div/button"))
                    .click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='ember-application']/div[2]/nav/div/div/div/a/img")));



        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    @Test(priority = 16)
    public void step16() throws Exception {
        try
        {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[6]/div/img")));
            driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[6]/div/img")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[6]/div[2]/div[2]")));
        }catch (Exception e)
        {
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    @Test(priority = 17)
    public void step17() throws Exception {
        try
        {

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='notifications-container']/div[2]/div[1]")));

            List<WebElement> elements = driver.findElements(By.xpath(".//*[@class='notification']/div/div[2]"));

            for (WebElement i:
                 elements) {
                String typeOfNotification = "You've been assigned to a responsibility of";
                if(i.findElement(By.xpath(".//div[1]")).getText().equals(typeOfNotification))
                    if (i.findElement(By.xpath(".//div[2]/span[1]")).getText().equals("ResponsibilitySMOKE")) {
                        if (i.findElement(By.xpath(".//div[2]/span[2]")).getText().equals("TestProject1SMOKE"))
                        i.click();
                    }
            }

            wait.until(ExpectedConditions.textToBePresentInElement(By.xpath(".//*[@class='heading project-heading']"),
                    "TestProject1SMOKE"));

        }catch (Exception e)
        {
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    @Test(priority = 18)
    public void step18() throws Exception {
        try
        {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='assigned-responsibility']/a")));
            driver.findElement(By.xpath(".//*[@class='assigned-responsibility']/a")).click();
            wait.until(ExpectedConditions.urlContains("/responsibilities/see-position"));

        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    @Test(priority = 19)
    public void step19() throws Exception {
        try
        {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='actions']/div[2]/a")));
            driver.findElement(By.xpath(".//*[@class='actions']/div[2]/a")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@class='set-engagement-container']/div[5]")));
        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }

    @Test(priority = 20)
    public void step20() throws Exception {
        try
        {

            WebElement slider = driver.findElement(By.xpath(".//*[@class='project-container'][1]/div[3]/div[1]/div[1]/span"));

            Actions move = new Actions(driver);
            Action action = move.dragAndDropBy(slider,20, 0).build();
            action.perform();

            driver.findElement(By.xpath(".//*[@class='buttons']/button")).click();
            wait.until(ExpectedConditions.textToBePresentInElement(By.xpath(".//*[@class='heading project-heading']"),
                    "TestProject1SMOKE"));

            driver.close();
            driver.quit();

        }catch (Exception e){
            driver.close();
            driver.quit();
            throw new Exception(e);
        }
    }
}
