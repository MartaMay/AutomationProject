package AutomationProject1;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AutomationProject1 {

    @Test
    public void testCase(){

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("http://duotify.us-east-2.elasticbeanstalk.com/register.php");
        String title = driver.getTitle();
        Assert.assertEquals(title, "Welcome to Duotify!");

        driver.findElement(By.id("hideLogin")).click();

        Faker faker = new Faker();
        String username = faker.name().username();
        String first = faker.address().firstName();
        String last = faker.address().lastName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys(username, Keys.TAB,
              first, Keys.TAB, last, Keys.TAB, email, Keys.TAB, email, Keys.TAB, password, Keys.TAB, password, Keys.ENTER);

        Assert.assertEquals(driver.getCurrentUrl(), "http://duotify.us-east-2.elasticbeanstalk.com/browse.php?");

        String actual = String.valueOf(driver.findElement(By.id("nameFirstAndLast")).getText());
        String expected = first + " " + last;

        Assert.assertEquals(actual, expected );

        driver.findElement(By.id("nameFirstAndLast")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String actual1 = driver.findElement(By.xpath("//div[@class='userInfo']//h1")).getText();
        Assert.assertEquals(actual1, expected);

        driver.findElement(By.xpath("//button[@onclick='logout()']")).click();

        driver.navigate().refresh();
        Assert.assertEquals(driver.getCurrentUrl(), "http://duotify.us-east-2.elasticbeanstalk.com/register.php");

        driver.findElement(By.cssSelector("input[name='loginUsername']")).sendKeys(username, Keys.TAB, password, Keys.ENTER);

        Assert.assertTrue((driver.findElement(By.cssSelector("h1[class='pageHeadingBig']")).getText()).contains("You Might Also Like"));

        driver.findElement(By.xpath(" //span[@id='nameFirstAndLast']")).click();

        driver.findElement(By.xpath("//button[@onclick='logout()']")).click();

        driver.navigate().refresh();
        Assert.assertEquals(driver.getCurrentUrl(), "http://duotify.us-east-2.elasticbeanstalk.com/register.php");








    }
}
