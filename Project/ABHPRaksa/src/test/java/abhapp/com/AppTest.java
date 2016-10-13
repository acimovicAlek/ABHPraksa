package abhapp.com;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    @Test
    public void initTest() throws Exception {

        //Set up Driver for Firefox
        WebDriver driver = new FirefoxDriver();
        driver.get("http://ah-test.abhapp.com");
        driver.wait(1000);
        //Login as company
        driver.findElement(By.xpath(".//*[@id='ember592']")).sendKeys("abhint@test.com");
        driver.findElement(By.xpath(".//*[@id='ember593']")).sendKeys("test123");
        driver.findElement(By.xpath(".//*[@id='ember572']/div/div[2]/div[2]/form/div/button")).click();
        try {
            driver.wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(driver.findElement(By.id("ember743")) == null){
            throw new Exception("FAIL");
        }

        driver.close();
        driver.quit();
    }

}
