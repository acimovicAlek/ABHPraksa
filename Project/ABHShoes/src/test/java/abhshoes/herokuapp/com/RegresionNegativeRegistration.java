package test.java.abhshoes.herokuapp.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by infloop on 11/2/16.
 */

@Test(groups = "Regression")
public class RegresionNegativeRegistration {

    WebDriver driver;
    Boolean failed = false;

    @BeforeTest
    @Parameters({"driverSelected", "param"})
    public void beforeTest(String driverSelected, @Optional() String param) throws Exception {

        String URL = "http://atlant-bh-shoes.herokuapp.com";

        if (driverSelected.equalsIgnoreCase("firefox"))
        {
            System.out.println(" Executing on FireFox");
            String Node = (param=="1"?"http://192.168.56.102:6667/wd/hub":"http://192.168.56.1:6666/wd/hub");
            DesiredCapabilities cap = DesiredCapabilities.firefox();
            cap.setBrowserName("firefox");

            driver = new RemoteWebDriver(new URL(Node), cap);
        }
        else if (driverSelected.equalsIgnoreCase("chrome"))
        {
            System.out.println(" Executing on CHROME");
            DesiredCapabilities cap = DesiredCapabilities.chrome();
            cap.setBrowserName("chrome");
            String Node = (param=="1"?"http://192.168.56.1:5555/wd/hub":"http://192.168.56.1:6666/wd/hub");
            driver = new RemoteWebDriver(new URL(Node), cap);
        }
        else throw new Exception("Invalid parameters!");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.navigate().to(URL);
        driver.manage().window().maximize();
    }

    @AfterTest
    public void afterTest(){
        driver.close();
        driver.quit();
    }

    @Test(priority = 1)
    public void navigateToRegistration(){
        driver.findElement(By.partialLinkText("Register")).click();

        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='new_user']/div[1]/div")).isDisplayed());
    }

    @Test(priority = 2)
    public void blankRegistration(){
        driver.findElement(By.xpath(".//*[@id='new_user']/div[2]/input")).click();

        try{
            driver.findElement(By.xpath(".//*[@id='new_user']/div[1]/div"));
        }catch (Exception e){
            failed = true;
        }

        Assert.assertFalse(failed);
    }

    @Test(priority = 3)
    public void invalidEmail(){
        if(failed){
            navigateToRegistration();
            failed = false;
        }
        driver.findElement(By.id("user_name")).sendKeys("NegativeRegresion");
        driver.findElement(By.id("user_surname")).sendKeys("NegativeRegresion");
        driver.findElement(By.id("user_email")).sendKeys("FAIL");
        driver.findElement(By.id("user_password")).sendKeys("pass123");
        driver.findElement(By.id("user_password_confirmation")).sendKeys("pass123");

        driver.findElement(By.xpath(".//*[@id='new_user']/div[2]/input")).click();

        try{
            driver.findElement(By.xpath(".//*[@id='new_user']/div[1]/div"));
        }catch (Exception e){
            failed = true;
        }

        Assert.assertFalse(failed);
    }

    @Test(priority = 4)
    public void usedEmail(){
        if(failed){
            navigateToRegistration();
            failed = false;
        }
        driver.findElement(By.id("user_name")).sendKeys("NegativeRegresion");
        driver.findElement(By.id("user_surname")).sendKeys("NegativeRegresion");
        driver.findElement(By.id("user_email")).sendKeys("acimovic.alek@gmail.com");
        driver.findElement(By.id("user_password")).sendKeys("pass123");
        driver.findElement(By.id("user_password_confirmation")).sendKeys("pass123");

        driver.findElement(By.xpath(".//*[@id='new_user']/div[2]/input")).click();

        try{
            driver.findElement(By.xpath(".//*[@id='new_user']/div[1]/div"));
        }catch (Exception e){
            failed = true;
        }

        Assert.assertFalse(failed);
    }

    @Test(priority = 5)
    public void whiteSpacePassword() throws InterruptedException {
        Thread.sleep(5000);
        if(failed){
            navigateToRegistration();
            failed = false;
        }
        driver.findElement(By.id("user_name")).sendKeys("NegativeRegresion");
        driver.findElement(By.id("user_surname")).sendKeys("NegativeRegresion");
        driver.findElement(By.id("user_email")).sendKeys("invalidepass@test.com");
        driver.findElement(By.id("user_password")).sendKeys("     ");
        driver.findElement(By.id("user_password_confirmation")).sendKeys("     ");

        driver.findElement(By.xpath(".//*[@id='new_user']/div[2]/input")).click();

        try{
            driver.findElement(By.xpath(".//*[@id='new_user']/div[1]/div"));
        }catch (Exception e){
            failed = true;
        }

        Assert.assertFalse(failed);
    }

    @Test(priority = 6)
    public void almostBlankPassword(){
        if(failed){
            navigateToRegistration();
            failed = false;
        }
        driver.findElement(By.id("user_name")).sendKeys("NegativeRegresion");
        driver.findElement(By.id("user_surname")).sendKeys("NegativeRegresion");
        driver.findElement(By.id("user_email")).sendKeys("almostblank@test.com");
        driver.findElement(By.id("user_password")).sendKeys("     !");
        driver.findElement(By.id("user_password_confirmation")).sendKeys("     !");

        driver.findElement(By.xpath(".//*[@id='new_user']/div[2]/input")).click();

        try{
            driver.findElement(By.xpath(".//*[@id='new_user']/div[1]/div"));
        }catch (Exception e){
            failed = true;
        }

        Assert.assertFalse(failed);
    }
}
