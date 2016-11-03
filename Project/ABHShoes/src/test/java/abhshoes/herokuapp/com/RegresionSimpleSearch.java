package abhshoes.herokuapp.com;
import org.omg.CORBA.TIMEOUT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
 * Created by infloop on 11/2/16.
 */
@Test(groups = "Regression")
public class RegresionSimpleSearch {

    WebDriver driver;
    WebDriverWait wait;
    int numberOfArticlesInCart = 0;
    int id = -1;

    @BeforeTest
    @Parameters("driverSelected")
    public void beforeTest(String driverSelected) throws Exception {
        if (driverSelected.equals("Firefox")) driver = new FirefoxDriver();
        else if (driverSelected.equals("Chrome")) driver = new ChromeDriver();
        else throw new Exception("Invalid parameters!");
        wait = new WebDriverWait(driver, 10);
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
    public void goToLogin() {
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul[2]/li[1]/a")));
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
    public void navigateToShoes() {
        driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul[1]/li[3]/a")).click();

        numberOfArticlesInCart = Integer.parseInt(driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul[2]/li[4]/a/span[2]")).
                        getText());

        Assert.assertTrue(
                driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/h4"))
                        .getText().equals("Dječija obuća")
                        && driver.getCurrentUrl().contains("categories")
        );
    }

    @Test(priority = 4)
    public void searchForArticle(){
        new Select(driver.findElement(By.name("brand_id"))).selectByVisibleText("Puma");
        new Select(driver.findElement(By.name("size_id"))).selectByVisibleText("34");
        new Select(driver.findElement(By.name("color_id"))).selectByVisibleText("Zuta");
        driver.findElement(By.id("min_price")).sendKeys("100");
        driver.findElement(By.id("max_price")).sendKeys("130");
        driver.findElement(By.xpath("html/body/div[1]/div[1]/div/form/input[5]")).click();

        List<WebElement> list = driver.findElements(By.xpath("html/body/div[1]/div[2]/div"));

        boolean condition = false;
        for(int i = 1; i < list.size(); i++)
            if(list.get(i).findElement(By.xpath(".//p")).getText().equals("Puma - Starke Converse"))
            {condition = true; id = i;}

        Assert.assertTrue(condition);

    }

    @Test(priority = 5)
    public void clickOnArtifact(){
        driver.findElement(By.xpath("html/body/div[1]/div[2]/div["+(id+1)+"]/a")).click();

        Assert.assertTrue(
                driver.findElement(By.xpath("html/body/div[1]/div[1]/div[3]/form/input[4]")).isDisplayed()
                        && driver.getCurrentUrl().contains("products")
        );
    }

    @Test(priority = 6)
    public void addToCart(){
        new Select(driver.findElement(By.xpath(".//*[@id='size']"))).selectByValue("34");
        driver.findElement(By.xpath(".//*[@id='quantity']")).sendKeys("\b5");
        new Select(driver.findElement(By.xpath(".//*[@id='color']"))).selectByValue("Zuta");
        driver.findElement(By.xpath("html/body/div[1]/div[1]/div[3]/form/input[3]")).click();

        int newCartValue = Integer.parseInt(driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul[2]/li[4]/a/span[2]")).
                getText());

        Assert.assertEquals(newCartValue, numberOfArticlesInCart+5);

    }

}
