package AutomationProject3;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AutomationProject3 {

    @Test
    public void testEdmunds() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        //1. Navigate to https://www.edmunds.com/
        driver.get("https://www.edmunds.com/");

        //2. Click on Shop Used
        driver.findElement(By.xpath("//a[text()='Shop Used']")).click();

        //3. In the next page, clear the zipcode field and enter 22031
        driver.findElement(By.xpath("//input[@name='zip']")).sendKeys(Keys.CONTROL, "a");
        driver.findElement(By.xpath("//input[@name='zip']")).sendKeys("22031", Keys.ENTER);

        //4. Check the following checkbox
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//label[@class='checkbox-facet d-flex align-items-center mb-0 justify-content-between'])[2]")).click();

        //5. Choose Tesla for a make
        Select select = new Select(driver.findElement(By.xpath("//select[@name='make']")));
        select.selectByVisibleText("Tesla");

        //6. Verify that the default selected option in Models dropdown is Any Model for Model dropdown. And the default years are 2012 and 2023 in year input fields.
        Assert.assertTrue((driver.findElement(By.xpath("//input[@value='2012']"))).isDisplayed());
        Assert.assertTrue((driver.findElement(By.xpath("//input[@value='2023']"))).isDisplayed());

        //7. Verify that Model dropdown options are [Any Model, Model 3, Model S, Model X, Model Y, Cybertruck, Roadster]
        Select selectModel = new Select(driver.findElement(By.xpath("//select[@id='usurp-model-select']")));
        List<WebElement> models = selectModel.getOptions();
        List<String> listedModels =  new ArrayList<>();
        for (WebElement model : models) {
            listedModels.add(model.getText());
        }
        List<String> expectedModels = List.of("Add Model", "Model 3", "Model S", "Model X", "Model Y", "Cybertruck", "Roadster");
        Assert.assertEquals(listedModels, expectedModels);

        //8. In Models dropdown, choose Model 3
        selectModel.selectByVisibleText("Model 3");

        //9. Enter 2020 for year min field and hit enter (clear the existing year first)
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@id='min-value-input-Year']")).click();
        driver.findElement(By.xpath("//input[@id='min-value-input-Year']")).sendKeys(  "2020", Keys.ENTER);
        driver.findElement(By.xpath("(//label[@class='checkbox-facet d-flex align-items-center mb-0 justify-content-between'])[2]")).click();

        //10. In the results page, verify that there are 21 search results, excluding the sponsored result/s. And verify that each search result title contains ‘Tesla Model 3’
        //To isolate the 21 results, excluding the sponsored one/s, use a custom xpath which uses the common class for the search results that you need.
        List<WebElement> resultList = driver.findElements(By.xpath("//li[@class='d-flex mb-0_75 mb-md-1_5 col-12 col-md-6']"));
        Assert.assertEquals(resultList.size(), 21);

        List<WebElement> searchResults = driver.findElements(By.xpath("//div[contains(text(),'Tesla Model 3')]"));
        List<String> searchResultsString =  new ArrayList<>();

        searchResults.forEach( s -> searchResultsString.add(s.getText()));
        searchResultsString.forEach(s -> Assert.assertTrue(s.contains("Tesla Model 3")));

        //11. Extract the year from each search result title and verify that each year is within the selected range (2020-2023)
        List<Integer> year = new ArrayList<>();
        searchResultsString.forEach(s -> year.add(Integer.valueOf(s.substring(0, 4))));

        for (Integer i : year) {
           Assert.assertTrue(i >= 2020 && i <= 2023);
        }

        //12. From the dropdown on the right corner choose “Price: Low to High” option and verify that the results are sorted from lowest to highest price.
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",(driver.findElement(By.xpath("//div[@class='srp-sort-by size-14 pos-r']"))));
        driver.findElement(By.xpath("//select[@id='sort-by']//option[text()='Price: Low to High']")).click();

        driver.navigate().refresh();
        Thread.sleep(2000);

        List<WebElement> price = driver.findElements(By.xpath("//span[@class='heading-3'][text()]"));
        List<String> priceString = new ArrayList<>();
        price.forEach(s -> priceString.add(s.getText()));

        List<Integer> priceInteger = new ArrayList<>();
        priceString.forEach(s -> priceInteger.add(Integer.valueOf(s.substring(1).replace(",", ""))));

        List<Integer> copyPrice = new ArrayList<>(priceInteger);
        copyPrice.sort(Comparator.naturalOrder());
        Assert.assertEquals(copyPrice, priceInteger);

        //13. From the dropdown menu, choose “Price: High to Low” option and verify that the results are sorted from highest to lowest price.
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",(driver.findElement(By.xpath("//div[@class='srp-sort-by size-14 pos-r']"))));
        driver.findElement(By.xpath("//select[@id='sort-by']//option[text()='Price: High to Low']")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='results-container']")));

        List<WebElement> priceHighToLow = driver.findElements(By.xpath("//div[@class='pricing-details d-flex flex-column']//span[@class='heading-3'][text()]"));
        List<String> priceHtLString = new ArrayList<>();

        try {
            priceHighToLow.forEach(s -> priceHtLString.add(s.getText()));

        }catch (StaleElementReferenceException e){
            driver.navigate().refresh();
        }

        List<Integer> priceHtLInteger = new ArrayList<>();
        priceHtLString.forEach(s -> priceHtLInteger.add(Integer.valueOf(s.substring(1).replace(",", ""))));

        List<Integer> copyHtLPrice = new ArrayList<>(priceHtLInteger);
        copyHtLPrice.sort(Comparator.reverseOrder());
        Assert.assertEquals(copyHtLPrice, priceHtLInteger);

        //14. From the dropdown menu, choose “Mileage: Low to High” option and verify that the results are sorted from lowest to highest mileage.
        driver.findElement(By.xpath("//div[@class='srp-sort-by size-14 pos-r']")).click();
        driver.findElement(By.xpath("//select[@id='sort-by']//option[text()='Mileage: Low to High']")).click();

        Thread.sleep(1000);
        List<WebElement> milesLowToHigh = driver.findElements(By.xpath("//div[@class='size-14 d-flex align-items-baseline mt-0_5 col-12']//span[contains(text(),'miles')]"));
        List<String> milesLtHString = new ArrayList<>();

        try {
            milesLowToHigh.forEach(s -> milesLtHString.add(s.getText()));
        }catch (StaleElementReferenceException e){
            driver.navigate().refresh();
        }

        List<Integer> milesLtHInteger = new ArrayList<>();
        milesLtHString.forEach(s -> milesLtHInteger.add(Integer.valueOf(s.replace(",", "").replace(" miles", ""))));

        List<Integer> copyLtHInteger = new ArrayList<>(milesLtHInteger);
        //Collections.sort(copyLtHInteger);
        copyLtHInteger.sort(Comparator.naturalOrder());
        Assert.assertEquals(copyLtHInteger, milesLtHInteger);



        //15. Find the last result and store its title, price and mileage (get the last result dynamically, your code should click on the last result regardless of how many results are there).
        //Click on it to open the details about the result.
        List<WebElement> searchList = driver.findElements(By.xpath("//div[@class='visible-vehicle-info d-flex flex-column']"));
        WebElement lastOne = searchList.get(searchList.size()-1);

        String expectedTitle = lastOne.findElement(By.xpath("(//div[@class='visible-vehicle-info d-flex flex-column']//div[contains(text(), 'Model')])[last()]")).getText();
        String expectedPrice = lastOne.findElement(By.xpath("(//span[@class='heading-3'])[last()]")).getText();
        String expectedMillage = lastOne.findElement(By.xpath("(//div[@class='size-14 d-flex align-items-baseline mt-0_5 col-12']//span[contains(text(),'miles')])[last()]")).getText();
        lastOne.click();

        //16. Verify the title price and mileage matches the info from the previous step

        String actualTitle = driver.findElement(By.xpath("//h1[@class='d-inline-block mb-0 heading-2 mt-0_25']")).getText();
        String actualPrice = driver.findElement(By.xpath("//div[@class='heading-2 mb-0']")).getText();
        String actualMillage = driver.findElement(By.xpath("//div[@class='pr-0 font-weight-bold text-right ml-1 col'][1]")).getText();

        Assert.assertEquals(actualTitle, expectedTitle);
        Assert.assertEquals(actualPrice, expectedPrice);
        Assert.assertEquals(actualMillage, expectedMillage.replace(" miles", ""));

        //17. Go back to the results page and verify that the clicked result has “Viewed” element on it.
        driver.navigate().back();
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='bg-white text-gray-darker']")).isDisplayed());

        //18. Close the browser.
        driver.quit();


}
}