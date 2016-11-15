package test.java.abhshoes.herokuapp.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by infloop on 11/3/16.
 */

@Test(groups = "Regression")
public class RegresioNegativeNewAdress {

    WebDriver driver;
    boolean passed = true;

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
                        .getText().equals("Dječija obuća")
                        && driver.getCurrentUrl().contains("categories")
        );
    }

    @Test(priority = 4)
    public void clickOnArtifact() {
        driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/a")).click();

        Assert.assertTrue(
                driver.findElement(By.xpath("html/body/div[1]/div[1]/div[3]/form/input[4]")).isDisplayed()
                        && driver.getCurrentUrl().contains("products")
        );
    }

    @Test(priority = 5)
    public void goToCart() {

        new Select(driver.findElement(By.xpath(".//*[@id='size']"))).selectByValue("37");
        driver.findElement(By.xpath(".//*[@id='quantity']")).sendKeys("\b5");
        new Select(driver.findElement(By.xpath(".//*[@id='color']"))).selectByValue("Zelena");
        driver.findElement(By.xpath("html/body/div[1]/div[1]/div[3]/form/input[4]")).click();

        boolean criterium = false;

        List<WebElement> elements = driver.findElements(By.xpath("html/body/div[1]/div[2]/div"));

        for (WebElement i :
                elements) {

            if (
                    i.findElement(By.xpath(".//div/h4[2]")).getText().equals("Size: 37")
                            && i.findElement(By.xpath(".//div/h4[3]")).getText().equals("Color: Zelena")
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
    public void clickNewAddress() {
        driver.findElement(By.xpath(".//*[@id='show-address']")).click();

        Assert.assertTrue(
                driver.findElement(By.xpath(".//*[@id='new_address']")).isDisplayed()
        );
    }

    @Test(priority = 8)
    public void blankAdress() {
        driver.findElement(By.xpath(".//*[@id='address_full_name']")).sendKeys("");
        driver.findElement(By.xpath(".//*[@id='address_address']")).sendKeys("");
        driver.findElement(By.xpath(".//*[@id='address_city']")).sendKeys("");
        driver.findElement(By.xpath(".//*[@id='address_state']")).sendKeys("");
        driver.findElement(By.xpath(".//*[@id='address_zip']")).sendKeys("");
        driver.findElement(By.xpath(".//*[@id='address_phone_number']")).sendKeys("");
        driver.findElement(By.xpath(".//*[@id='new_address']/input[3]")).click();

        try{
            driver.findElement(By.xpath("html/body/div[1]/div[1]/h3")).getText().equals("Payment");
        }catch (Exception e){
            passed = false;
        }

        Assert.assertTrue(passed);
    }
    @Test(priority = 9)
    public void invalideZip(){

        if(!passed){
            passed = true;
            driver.findElement(By.xpath("html/body/div[1]/div[3]/div/a[2]")).click();
            Assert.assertTrue(
                    driver.findElement(By.xpath("html/body/div[1]/div[1]/h3")).getText().equals("Select shipping address")
                            && driver.getCurrentUrl().contains("cart/checkout")
            );
            clickNewAddress();
        }

        driver.findElement(By.xpath(".//*[@id='address_full_name']")).sendKeys("Regresion ");
        driver.findElement(By.xpath(".//*[@id='address_address']")).sendKeys("Test Address 1");
        driver.findElement(By.xpath(".//*[@id='address_city']")).sendKeys("TestCity");
        driver.findElement(By.xpath(".//*[@id='address_state']")).sendKeys("TestState");
        driver.findElement(By.xpath(".//*[@id='address_zip']")).sendKeys("InvalideZip");
        new Select(driver.findElement(By.xpath(".//*[@id='address_country']"))).selectByValue("BA");
        driver.findElement(By.xpath(".//*[@id='address_phone_number']")).sendKeys("033111111");
        driver.findElement(By.xpath(".//*[@id='new_address']/input[3]")).click();
    }

    @Test(priority = 10)
    public void invalidePhone(){

        if(!passed){
            passed = true;
            driver.findElement(By.xpath("html/body/div[1]/div[3]/div/a[2]")).click();
            Assert.assertTrue(
                    driver.findElement(By.xpath("html/body/div[1]/div[1]/h3")).getText().equals("Select shipping address")
                            && driver.getCurrentUrl().contains("cart/checkout")
            );
            clickNewAddress();
        }

        driver.findElement(By.xpath(".//*[@id='address_full_name']")).sendKeys("Regresion ");
        driver.findElement(By.xpath(".//*[@id='address_address']")).sendKeys("Test Address 1");
        driver.findElement(By.xpath(".//*[@id='address_city']")).sendKeys("TestCity");
        driver.findElement(By.xpath(".//*[@id='address_state']")).sendKeys("TestState");
        driver.findElement(By.xpath(".//*[@id='address_zip']")).sendKeys("71000");
        new Select(driver.findElement(By.xpath(".//*[@id='address_country']"))).selectByValue("BA");
        driver.findElement(By.xpath(".//*[@id='address_phone_number']")).sendKeys("FAILPHONE");
        driver.findElement(By.xpath(".//*[@id='new_address']/input[3]")).click();
    }

    @Test(priority = 9)
    public void whiteSpaceInput(){

        if(!passed){
            passed = true;
            driver.findElement(By.xpath("html/body/div[1]/div[3]/div/a[2]")).click();
            Assert.assertTrue(
                    driver.findElement(By.xpath("html/body/div[1]/div[1]/h3")).getText().equals("Select shipping address")
                            && driver.getCurrentUrl().contains("cart/checkout")
            );
            clickNewAddress();
        }

        driver.findElement(By.xpath(".//*[@id='address_full_name']")).sendKeys("        ");
        driver.findElement(By.xpath(".//*[@id='address_address']")).sendKeys("        ");
        driver.findElement(By.xpath(".//*[@id='address_city']")).sendKeys("        ");
        driver.findElement(By.xpath(".//*[@id='address_state']")).sendKeys("        ");
        driver.findElement(By.xpath(".//*[@id='address_zip']")).sendKeys("71000");
        new Select(driver.findElement(By.xpath(".//*[@id='address_country']"))).selectByValue("BA");
        driver.findElement(By.xpath(".//*[@id='address_phone_number']")).sendKeys("033111111");
        driver.findElement(By.xpath(".//*[@id='new_address']/input[3]")).click();
    }



}
