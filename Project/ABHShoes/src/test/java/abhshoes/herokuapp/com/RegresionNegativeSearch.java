package abhshoes.herokuapp.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by infloop on 11/3/16.
 */

@Test(groups = "Regression")
public class RegresionNegativeSearch {

    WebDriver driver;
    WebDriverWait wait;
    boolean failed = false;

    @BeforeTest
    @Parameters("driverSelected")
    public void beforeTest(String driverSelected) throws Exception {
        if (driverSelected.equals("Firefox")) driver = new FirefoxDriver();
        else if (driverSelected.equals("Chrome")) driver = new ChromeDriver();
        else throw new Exception("Invalid parameters!");        driver.get("http://atlant-bh-shoes.herokuapp.com");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void afterTest(){
        driver.close();
        driver.quit();
    }

    @Test(priority = 1)
    public void navigateToShoes() {
        driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul[1]/li[3]/a")).click();

        Assert.assertTrue(
                driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/h4"))
                        .getText().equals("Dječija obuća")
                        && driver.getCurrentUrl().contains("categories")
        );
    }

    @Test(priority = 2)
    public void negativePrice(){
        new Select(driver.findElement(By.name("brand_id"))).selectByVisibleText("Puma");
        new Select(driver.findElement(By.name("size_id"))).selectByVisibleText("34");
        new Select(driver.findElement(By.name("color_id"))).selectByVisibleText("Zuta");
        driver.findElement(By.id("min_price")).sendKeys("-100");
        driver.findElement(By.id("max_price")).sendKeys("-130");

        Assert.assertTrue(driver.findElement(By.xpath("html/body/div[1]/div[1]/div/form/input[5]")).getCssValue("disabled").equals(""    ));

    }

    @Test(priority = 3)
    public void invalidePriceRange(){
        new Select(driver.findElement(By.name("brand_id"))).selectByVisibleText("Puma");
        new Select(driver.findElement(By.name("size_id"))).selectByVisibleText("34");
        new Select(driver.findElement(By.name("color_id"))).selectByVisibleText("Zuta");
        driver.findElement(By.id("min_price")).sendKeys("100");
        driver.findElement(By.id("max_price")).sendKeys("30");

        Assert.assertTrue(driver.findElement(By.xpath("html/body/div[1]/div[1]/div/form/input[5]")).getCssValue("disabled").equals(""    ));

    }

}
