package test.java.abhshoes.herokuapp.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by infloop on 11/3/16.
 */

@Test(groups = "Regression")
public class RegresionNegativeNegativeBuyIn {

    WebDriver driver;

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
    public void navigateToCategory(){
        driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul[1]/li[4]/a")).click();

        Assert.assertEquals(driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/h4")).getText(), "KID'S");
    }

    @Test(priority = 2)
    public  void clickOnArtifact(){
        driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/a")).click();

        Assert.assertTrue(driver.findElement(By.id("size")).isDisplayed());
    }

    @Test(priority = 3)
    public void negativeQuantity(){
        new Select(driver.findElement(By.xpath(".//*[@id='size']"))).selectByValue("36");
        driver.findElement(By.xpath(".//*[@id='quantity']")).clear();
        driver.findElement(By.xpath(".//*[@id='quantity']")).sendKeys("-10");
        new Select(driver.findElement(By.xpath(".//*[@id='color']"))).selectByValue("Plava");

        Assert.assertTrue(driver.findElement(By.xpath("html/body/div[1]/div[1]/div[3]/form/input[3]"))
        .getCssValue("disabled").equals(""));
    }
}
