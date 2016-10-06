package abhapp.com;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    @Test
    public void initTest(){
        WebDriver driver = new ChromeDriver();
        driver.get("http://ah-test.abhapp.com");
        driver.close();
        driver.quit();
    }

}
