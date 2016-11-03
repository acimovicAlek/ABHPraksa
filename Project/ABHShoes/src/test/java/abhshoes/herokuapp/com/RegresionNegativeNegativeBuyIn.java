package abhshoes.herokuapp.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by infloop on 11/3/16.
 */

@Test(groups = "Regression")
public class RegresionNegativeNegativeBuyIn {

    WebDriver driver;
    WebDriverWait wait;
    boolean failed = false;

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
    public void navigateToCategory(){
        driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul[1]/li[4]/a")).click();

        Assert.assertEquals(driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/h4")).getText(), "Sportska obuća");
    }

    @Test(priority = 2)
    public  void clickOnArtifact(){
        driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/a")).click();

        Assert.assertTrue(driver.findElement(By.id("size")).isDisplayed());
    }

    @Test(priority = 3)
    public void negativeQuantity(){
        new Select(driver.findElement(By.xpath(".//*[@id='size']"))).selectByValue("34");
        driver.findElement(By.xpath(".//*[@id='quantity']")).clear();
        driver.findElement(By.xpath(".//*[@id='quantity']")).sendKeys("-10");
        new Select(driver.findElement(By.xpath(".//*[@id='color']"))).selectByValue("Plava");

        Assert.assertTrue(driver.findElement(By.xpath("html/body/div[1]/div[1]/div[3]/form/input[3]"))
        .getCssValue("disabled").equals(""));
    }
}
