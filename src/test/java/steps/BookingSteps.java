package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class BookingSteps {

    WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("User is on search screen")
    public void userIsOnSearchScreen() {
        driver.get("https://www.booking.com/searchresults.en-gb.html");
    }


    @When("User enters the name of the {string} in the search field")
    public void userEntersTheNameOfTheHotelInTheSearchField(String hotelName) {
        WebElement element = driver.findElement(By.name("ss"));
        element.click();
        element.sendKeys(hotelName);
    }

    @And("User click {string} button")
    public void userClickButton(String buttonName) {
//        String buttonXpath = "//button[contains(@class, 'sb-searchbox__button ')]";
        String buttonXpath = String.format("//button[contains(., '%s')]", buttonName);
        driver.findElement(By.xpath(buttonXpath)).click();
    }

    @And("Searching {string} is displayed")
    public void searchingHotelIsDisplayed(String hotelName) {
        String displayedHotelXpath = String.format("//a/div[contains(., '%s')]", hotelName);
        Assert.assertTrue(driver.findElement(By.xpath(displayedHotelXpath)).isDisplayed(), "Hotel not found");
    }

    @Then("Compare {string} {string}")
    public void compareHotelRating(String hotelName, String rating) {
        String scoreXpath = String.format("(//div[contains(., '%s')]/following-sibling::div//div[contains(@data-testid, 'review-score')]/div[contains(@aria-label, 'Scored')])[1]", hotelName);
        String actualResult = driver.findElement(By.xpath(scoreXpath)).getText();
        System.out.println(actualResult);
        Assert.assertEquals(rating, actualResult, String.format("Actual result of rating = %s, but expected result of rating = %s", actualResult, rating));

    }
}
