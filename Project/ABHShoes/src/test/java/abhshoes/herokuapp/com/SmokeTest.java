package test.java.abhshoes.herokuapp.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by infloop on 10/21/16.
 */
public class SmokeTest {

    WebDriver driver;
    WebDriverWait wait;

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
            String Node = (param=="1"?"http://192.168.56.102:5555/wd/hub":"http://192.168.56.1:6666/wd/hub");
            driver = new RemoteWebDriver(new URL(Node), cap);
        }
        else throw new Exception("Invalid parameters!");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.navigate().to(URL);
        driver.manage().window().maximize();
    }

    @AfterTest
    public void afterTest() {
        driver.close();
        driver.quit();
    }

    @Test(priority = 1)
    public void goToLogin() {
        driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul[2]/li[1]/a")).click();

        Assert.assertTrue(
                driver.findElement(By.xpath("html/body/div[1]/form/div[2]/input")).isDisplayed()
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

        Assert.assertTrue(
                driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/h4"))
                        .getText().equals("SPORTS")
                        && driver.getCurrentUrl().contains("categories")
        );
    }

    @Test(priority = 4)
    public void clickOnArtifact() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("html/body/div[1]/div[2]/div[2]/a")));
        driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/a")).click();

        Assert.assertTrue(
                driver.findElement(By.xpath("html/body/div[1]/div[1]/div[3]/form/input[4]")).isDisplayed()
                        && driver.getCurrentUrl().contains("products")
        );
    }

    @Test(priority = 5)
    public void goToCart() {

        new Select(driver.findElement(By.xpath(".//*[@id='size']"))).selectByValue("30");
        driver.findElement(By.xpath(".//*[@id='quantity']")).sendKeys("\b2");
        new Select(driver.findElement(By.xpath(".//*[@id='color']"))).selectByValue("Plava");
        driver.findElement(By.xpath("html/body/div[1]/div[1]/div[3]/form/input[4]")).click();

        boolean criterium = false;

        List<WebElement> elements = driver.findElements(By.xpath("html/body/div[1]/div[2]/div"));

        for (WebElement i :
                elements) {

            if (
                    i.findElement(By.xpath(".//div/h4[2]")).getText().equals("Size: 30")
                            && i.findElement(By.xpath(".//div/h4[3]")).getText().equals("Color: Plava")
                    ) {
                criterium = true;
                break;
            }
        }

        Assert.assertTrue(criterium);

    }

    @Test(priority = 6)
    public void clickContinue() throws InterruptedException {
        driver.findElement(By.xpath("html/body/div[1]/div[3]/a[1]")).click();

        Thread.sleep(1000);

        Assert.assertTrue(
                driver.findElement(By.xpath("html/body/div[1]/div[1]/h3")).getText().equals("Select shipping address")
                        && driver.getCurrentUrl().contains("cart/checkout")
        );
    }

    @Test(priority = 7)
    public void clickCont() {
        driver.findElement(By.xpath("html/body/div[1]/div[3]/button")).click();

        Assert.assertTrue(
                driver.findElement(By.xpath("html/body/div[1]/div[1]/h3")).getText().equals("Payment")
        );
    }


    @Test(priority = 9)
    public void clickToPay() throws InterruptedException {
        driver.findElement(By.xpath("html/body/div[1]/div[3]/form/div[2]/button")).click();

        boolean condition = true;


        driver.switchTo().frame(driver.findElement(By.xpath("html/body/iframe[2]")));


        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@placeholder='Email']")));
            condition = true;
        } catch (Exception e) {
            condition = false;
        }

        Assert.assertTrue(condition);
    }

    @Test(priority = 10)
    public void enterCreditCard() throws InterruptedException {


        driver.findElement(By.xpath(".//*[@placeholder='Email']")).sendKeys("acimovic.alek@gmail.com");

        String input = "4242424242424242";
        for (int i = 0; i < input.length(); i++)
            driver.findElement(By.xpath(".//*[@placeholder='Card number']")).sendKeys(input.substring(i, i + 1));

        input = "130";
        for (int i = 0; i < input.length(); i++)
            driver.findElement(By.xpath(".//*[@placeholder='MM / YY']")).sendKeys(input.substring(i, i + 1));

        driver.findElement(By.xpath(".//*[@placeholder='CVC']")).sendKeys("1111");
        driver.findElement(By.xpath(".//button")).click();
        boolean condition = true;

        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("html/body/nav/div/div/div[1]/a")));
        } catch (Exception e) {
            condition = false;
        }
        Assert.assertTrue(
                driver.findElement(By.xpath("html/body/div[1]/h3[1]")).getText().equals("Thanks!")
                        && condition
        );
    }

}
