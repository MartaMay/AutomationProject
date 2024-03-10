package AutomationProject4;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AutomationProject4 {


    @Test
    public void testAirbnb() throws InterruptedException {

        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

       //Login:
        driver.get("https://www.airbnb.com/");
        driver.findElement(By.xpath("//div[@class='_167wsvl']/button[@type='button']")).click();
        Thread.sleep(1000);
        driver.findElement(By.linkText("Log in")).click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[text()='Continue with email']")));
        driver.findElement(By.name("user[email]")).sendKeys("mhalushko@aol.com", Keys.ENTER);
        driver.findElement(By.name("user[password]")).sendKeys("JustForTesting12345!", Keys.ENTER);
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='_167wsvl']//img")).isDisplayed(), "Profile picture is displayed");

        //Property search:
        driver.findElement(By.id("bigsearch-query-location-input")).sendKeys("Ibiza, Spain");
        driver.findElement(By.xpath("//div[text()='Add dates']")).click();

        for (int i = 0; i < 7; i++) {
            Thread.sleep(500);
            driver.findElement(By.xpath("//button[@aria-label='Move forward to switch to the next month.']")).click();
        }
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@data-testid='calendar-day-11/06/2024']")).click();
        driver.findElement(By.xpath("//div[@data-testid='calendar-day-11/12/2024']")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.xpath("//button[@type='button']//div[text()='Add guests']")));
        Actions actions = new Actions(driver);
        actions.doubleClick(driver.findElement(By.xpath("//div[@id='stepper-adults']//button[@aria-label='increase value']"))).perform();
        actions.doubleClick(driver.findElement(By.xpath("//button[@data-testid='stepper-children-increase-button']"))).perform();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.xpath("//div[text()='Search']")));

        //Filtering Results:
        driver.findElement(By.xpath("//button[@data-testid='category-bar-filter-button']")).click();
        driver.findElement(By.xpath("//input[@id='price_filter_min']")).sendKeys(Keys.chord(Keys.CONTROL, "a"), "100");
        driver.findElement(By.xpath("//input[@id='price_filter_max']")).sendKeys(Keys.chord(Keys.CONTROL, "a"), "600");
        driver.findElement(By.xpath("//a[contains(text(),'Show')]")).click();

        Thread.sleep(1000);
        List<WebElement> prices = driver.findElements(By.xpath("//span[@class='_tyxjp1']")); //| //span[@class='_1y74zjx']
        List<String> stringPrice = new ArrayList<>();
        prices.forEach(s-> stringPrice.add(s.getText()));

        List<Integer> integerPrice = new ArrayList<>();
        driver.navigate().refresh();
            Thread.sleep(2000);
//            for (String i : stringPrice) {
//                i = i.replace("$", "").replace(" ","");
//                System.out.println(i);
//
//               integerPrice.add(Integer.valueOf(i));
//            }
        stringPrice.forEach(s-> integerPrice.add(Integer.valueOf(s.replace("$", "").replace(" ", ""))));

        for (Integer i : integerPrice) {
            Assert.assertTrue(i >= 100 && i <= 600);
        }

        //Detailed Property View:
        List<WebElement> listings = driver.findElements(By.xpath("//div[@class='lxq01kf atm_9s_1txwivl atm_am_kyuy1d atm_ar_d67k9l l1tup9az atm_1p4glcj_1bp4okc dir dir-ltr']"));
        WebElement firstOne = listings.get(0);

        String price = firstOne.findElement(By.xpath("(//span[@class='_tyxjp1'])[1]")).getText();
        String totalPrice = firstOne.findElement(By.xpath("(//span[contains(text(),' total')])[1]")).getText();
        totalPrice = totalPrice.replace(" total", "");
        String rating = firstOne.findElement(By.xpath("(//span[@class='r1dxllyb atm_7l_18pqv07 atm_cp_1ts48j8 dir dir-ltr'])[1]")).getText();
        String[] split = rating.split(" ");
        rating = split[0];

        firstOne.click();

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        Thread.sleep(2000);
        String expPrice = driver.findElement(By.xpath("(//div[@class='_1jo4hgw']//span[@class='_tyxjp1'][text()])[2]")).getText();
        String expTotalPrice = driver.findElement(By.xpath("//div[@class='_3u0me7']//span[@class='_j1kt73']")).getText();
        String expRating = driver.findElement(By.xpath("//div[@data-testid='pdp-reviews-highlight-banner-host-rating']//div[@aria-hidden]")).getText();

        driver.close();

        Assert.assertEquals(price, expPrice);
        Assert.assertEquals(totalPrice, expTotalPrice);
        Assert.assertEquals(rating, expRating);

        driver.switchTo().window(tabs.get(0));

        //Logout:
        driver.findElement(By.xpath("//div[@class='_167wsvl']/button[@type='button']")).click();
        driver.findElement(By.xpath("//div[text()='Log out']")).click();
        driver.findElement(By.xpath("//div[@class='_167wsvl']/button[@type='button']")).click();
        Thread.sleep(1000);
        Assert.assertTrue(driver.findElement(By.linkText("Log in")).isDisplayed());


        }
    }

