package test.java.abhshoes.herokuapp.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Duo on 21.12.2016..
 */
public class RegressionSizes {

    WebDriver driver;
    WebDriverWait wait;
    int numberOfArticlesInCart = 0;
    int id = -1;
    @BeforeTest
    public void beforeTest() throws Exception {

        String URL = "https://atlant-bh-shoes.herokuapp.com";

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Duo\\Desktop\\New folder\\ABH\\Project\\ABHShoes\\chromedriver");

        driver = new FirefoxDriver();
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
    public void goToLogin() {
        driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul[2]/li[1]/a")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("html/body/div[1]/form/div[1]/div")));
        Assert.assertTrue(
                driver.findElement(By.xpath("html/body/div[1]/form/div[1]/div")).isDisplayed()
                        && driver.getCurrentUrl().equals("http://atlant-bh-shoes.herokuapp.com/login")
        );
    }

    @Test(priority = 2)
    public void login() {
        driver.findElement(By.xpath(".//*[@id='session_email']")).sendKeys("acimovic.alek@gmail.com");
        driver.findElement(By.xpath(".//*[@id='session_password']")).sendKeys("novipass");
        driver.findElement(By.xpath("html/body/div[1]/form/div[2]/input")).click();

        Assert.assertTrue(driver.findElement(By.xpath("html/body/div[2]/div[1]")).getText().equals("Aleksandar, you are now logged in!"));
    }

    @Test(priority = 3)
    public void  navigateToDashboard(){
        driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul[2]/li[3]/a")).click();
        driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul[2]/li[3]/ul/li[1]/a")).click();

        Assert.assertTrue(
                driver.findElement(By.xpath("html/body/div[1]/div[2]/a[1]")).isDisplayed()
        );
    }

    @Test(priority = 4)
    public void navigateToSizeAdd(){
        driver.findElement(By.xpath("html/body/div[1]/div[3]/div[4]/a[2]")).click();

        Assert.assertTrue(
                driver.findElement(By.xpath(".//*[@id='new_size']/div/h2")).isDisplayed()
        );
    }

    @Test(priority = 5)
    public void addSize(){
        driver.findElement(By.id("size_size")).sendKeys("33");
        driver.findElement(By.xpath(".//*[@id='new_size']/div/input")).click();

        List<WebElement> sizes = driver.findElements(By.xpath("html/body/div[1]/table/tbody/tr"));
        boolean cond = false;
        for (WebElement i:
                sizes) {

            if(i.findElement(By.xpath(".//td[3]")).getText().equals("33"))
                cond = true;
        }

        Assert.assertTrue(cond);
    }

    @Test(priority = 6)
    public void deleteSize(){

        List<WebElement> sizes = driver.findElements(By.xpath("html/body/div[1]/table/tbody/tr"));
        int cond = sizes.size();

        for (WebElement i:
                sizes) {

            if(i.findElement(By.xpath(".//td[3]")).getText().equals("33"))
                i.findElement(By.xpath(".//td[4]/a[2]")).click();
        }

        driver.switchTo().alert().accept();

        sizes = driver.findElements(By.xpath("html/body/div[1]/table/tbody/tr"));

        Assert.assertNotEquals(cond, sizes.size());
    }


}
