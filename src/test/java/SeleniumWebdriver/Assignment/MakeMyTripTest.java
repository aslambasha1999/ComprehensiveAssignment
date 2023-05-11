package SeleniumWebdriver.Assignment;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MakeMyTripTest {

    WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        if (browser.equalsIgnoreCase("firefox")) {
            // Launch Firefox browser
            System.setProperty("webdriver.gecko.driver", "src\\test\\java\\SeleniumWebdriver\\Assignment\\Drivers\\geckodriver.exe");
            FirefoxOptions options = new FirefoxOptions();
            options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
            driver = new FirefoxDriver(options);
            Reporter.log("FireFox Browser Launched");
            
        } else if (browser.equalsIgnoreCase("chrome")) {
            // Set path for chromedriver.exe
            System.setProperty("webdriver.chrome.driver", "src\\test\\java\\SeleniumWebdriver\\Assignment\\Drivers\\chromedriver.exe");

            // Set binary path for Google Chrome
            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");

            // Launch Google Chrome browser
            driver = new ChromeDriver(options);
            Reporter.log("Chrome Browser Launched");
        }

        // Maximize browser window and navigate to MakeMyTrip homepage
        driver.manage().window().maximize();
        Reporter.log("Window Maximized");
        driver.get("https://www.makemytrip.com/");
        Reporter.log("URL Launched");
        
    }

    @Test
    public void verifyLogoPresent() {
        // Verify if MakeMyTrip logo is present on the page
        WebElement logoElement = driver.findElement(By.xpath("//img[@alt='Make My Trip']"));
        Assert.assertTrue(logoElement.isDisplayed(), "MakeMyTrip logo is not present on the page");
        Reporter.log("Make my trip Logo Successfully Verified");
    }

    @Test
    public void searchFlights() throws InterruptedException {
        // Click on Flights tab
        WebElement flights = findElementByXPath("//a[@class='makeFlex hrtlCenter column active']");
        flights.click();
        
        Reporter.log("Clicked on Flights Menu");
        Thread.sleep(1000);

        // Select one-way trip option if it's not already selected
        WebElement oneWayTrip = findElementByXPath("//li[@data-cy='oneWayTrip']");
        if (!(oneWayTrip.isSelected())) {
            oneWayTrip.click();
            Reporter.log("One Way Trip Button Selected");
        }else
        {
        	Reporter.log("One Way Trip Button Already Selected By Default");
        }

        // Enter "New Delhi" in the "From" input box and select the suggestion
        WebElement fromCityBox = findElementByXPath("//input[@id='fromCity']");
        fromCityBox.sendKeys("New Delhi");
        Reporter.log("From City Entered");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement fromSuggestionList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='listbox']")));
        List<WebElement> fromSuggestions = driver.findElements(By.xpath("//li[@role='option']"));
        for (WebElement item : fromSuggestions) {
            if (item.getText().contains("DEL, Indira Gandhi International Airport India")) {
                item.click();
                Reporter.log("From City Selected from Suggestions");
            }
        }
     // Enter "Bengaluru" in the "To" input box and select the suggestion
        WebElement toCity = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='toCity']")));
        toCity.click();
        WebElement toCityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='To']")));
        toCityInput.sendKeys("Bengaluru");
        Reporter.log("to City Entered");
        Thread.sleep(2000);
        WebElement enterToCity=findElementByXPath("(//li[@data-suggestion-index='0'])[1]");
        enterToCity.click();
        
        Reporter.log("To City Selected from Suggestions");
        
        // Select today's date in the calendar
        WebElement selectTodayDate = driver.findElement(By.cssSelector("div[class*='today']"));
        selectTodayDate.click();
        Reporter.log("Selected Today's Date");
        
        // Click on the search button
        WebElement searchBtn=findElementByXPath("//a[contains(text(),'Search')]");
        searchBtn.click();
        Reporter.log("Clicked on Search Button");
        Thread.sleep(1000);
      }

      @AfterMethod
      public void tearDown() {
          // Quit the browser
          driver.quit();
          Reporter.log("Browser Closed");;
      }

      private WebElement findElementByXPath(String xpath) {
          // Utility method to find an element by its XPath
          return driver.findElement(By.xpath(xpath));
      }
  }
