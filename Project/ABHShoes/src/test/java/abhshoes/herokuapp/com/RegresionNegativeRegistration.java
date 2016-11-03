package abhshoes.herokuapp.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by infloop on 11/2/16.
 */

@Test(groups = "Regression")
public class RegresionNegativeRegistration {

    WebDriver driver;
    WebDriverWait wait;
    Boolean failed = false;

    @BeforeTest
    @Parameters("driverSelected")
    public void beforeTest(String driverSelected) throws Exception {
        if (driverSelected.equals("Firefox")) driver = new FirefoxDriver();
        else if (driverSelected.equals("Chrome")) driver = new ChromeDriver();
        else throw new Exception("Invalid parameters!");
        driver.get("http://atlant-bh-shoes.herokuapp.com");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
