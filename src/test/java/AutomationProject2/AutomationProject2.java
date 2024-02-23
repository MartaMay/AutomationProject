package AutomationProject2;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AutomationProject2 {

    @Test
    public void testSpotify() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        //1. Login:
        driver.get("https://open.spotify.com/?");

        driver.findElement(By.xpath("//button[@data-testid='login-button']")).click();
        driver.findElement(By.xpath("//input[@id='login-username']")).sendKeys("mhalushko@gmail.com", Keys.TAB, "dotruj-syqvu3-pyrfIx", Keys.ENTER);

        Assert.assertTrue((driver.findElement(By.xpath("//button[@data-testid='user-widget-link']")).isDisplayed()),"Profile icon is not displayed");

        //2. Music Search:

        driver.findElement(By.xpath("//span[text()='Search']")).click();
        driver.findElement(By.xpath("//input[@class='Text__TextElement-sc-if376j-0 gYdBJW encore-text-body-small QO9loc33XC50mMRUCIvf']")).sendKeys("Dancing on my own", Keys.ENTER);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",(driver.findElement(By.xpath("//button[@data-testid='play-button']"))));
        Thread.sleep(10000);

        WebElement song = driver.findElement(By.xpath("//div[@data-testid='now-playing-widget']"));
        Assert.assertTrue(song.isDisplayed());
        WebElement artist = driver.findElement(By.xpath("//a[@data-testid='context-item-info-artist'][1]"));
        Assert.assertTrue(artist.isDisplayed());

        //Logout:
        driver.findElement(By.xpath("//button[@data-testid='user-widget-link']")).click();
        driver.findElement(By.xpath("//span[text()='Log out']")).click();
        Assert.assertTrue((driver.findElement(By.xpath("//button[@data-testid='login-button']"))).isDisplayed());





    }
}
