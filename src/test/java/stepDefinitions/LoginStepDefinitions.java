package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginPage;
import pages.HomePage;
import java.time.Duration;


import static org.assertj.core.api.Assertions.assertThat;

public class LoginStepDefinitions {

    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;

    @Before
    public void setUp() {

        String browserName = System.getProperty("browser", "firefox").toLowerCase();

        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();

                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");


//                chromeOptions.addArguments("--headless");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
//              firefoxOptions.addArguments("--headless");
                driver = new FirefoxDriver(firefoxOptions);
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am on the SauceDemo login page")
    public void i_am_on_the_sauce_demo_login_page() {
        loginPage.navigateToLoginPage();
        assertThat(loginPage.isLoginPageDisplayed())
                .as("Login page should be displayed")
                .isTrue();
    }

    @When("I enter username {string}")
    public void i_enter_username(String username) {
        loginPage.enterUsername(username);
    }

    @When("I enter password {string}")
    public void i_enter_password(String password) {
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        loginPage.clickLoginButton();
    }

    @Then("I should be redirected to the home page")
    public void i_should_be_redirected_to_the_home_page() {
        assertThat(homePage.isHomePageDisplayed())
                .as("Home page should be displayed after successful login")
                .isTrue();

        assertThat(homePage.getCurrentUrl())
                .as("URL should contain inventory")
                .contains("inventory.html");
    }

    @Then("I should see the products inventory")
    public void i_should_see_the_products_inventory() {
        homePage.waitForPageToLoad();
        assertThat(homePage.getNumberOfProducts())
                .as("Products should be displayed")
                .isGreaterThan(0);
    }

    @Then("the page title should contain {string}")
    public void the_page_title_should_contain(String expectedTitle) {
        assertThat(homePage.getPageTitle())
                .as("Page title should contain: " + expectedTitle)
                .containsIgnoringCase(expectedTitle);
    }

    @Then("I should see an error message")
    public void i_should_see_an_error_message() {
        assertThat(loginPage.isErrorMessageDisplayed())
                .as("Error message should be displayed")
                .isTrue();
    }

    @Then("I should remain on the login page")
    public void i_should_remain_on_the_login_page() {
        assertThat(loginPage.isLoginPageDisplayed())
                .as("Should remain on login page when login fails")
                .isTrue();

        assertThat(loginPage.getCurrentUrl())
                .as("URL should still be the login page")
                .doesNotContain("inventory.html");
    }

    @Then("the error message should contain {string}")
    public void the_error_message_should_contain(String expectedErrorText) {
        String actualErrorMessage = loginPage.getErrorMessage();
        assertThat(actualErrorMessage)
                .as("Error message should contain expected text")
                .containsIgnoringCase(expectedErrorText);
    }

    @When("I click on the menu button")
    public void i_click_on_the_menu_button() {
        homePage.openMenu();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @When("I click on logout")
    public void i_click_on_logout() {
        try {
            homePage.clickLogout();

            Thread.sleep(2500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Then("I should be redirected back to the login page")
    public void i_should_be_redirected_back_to_the_login_page() {
        assertThat(loginPage.isLoginPageDisplayed())
                .as("Should be redirected to login page after logout")
                .isTrue();
    }

    @Then("I should see the login form")
    public void i_should_see_the_login_form() {
        assertThat(loginPage.isLoginPageDisplayed())
                .as("Login form should be visible")
                .isTrue();
    }

    @Then("I should see the app logo")
    public void i_should_see_the_app_logo() {
        assertThat(homePage.isAppLogoDisplayed())
                .as("App logo should be displayed on home page")
                .isTrue();
    }

    @Then("I should see the shopping cart icon")
    public void i_should_see_the_shopping_cart_icon() {
        assertThat(homePage.isHomePageDisplayed())
                .as("Shopping cart should be visible as part of home page")
                .isTrue();
    }

    @Then("I should see at least {int} products")
    public void i_should_see_at_least_products(int minProductCount) {
        int actualProductCount = homePage.getNumberOfProducts();
        assertThat(actualProductCount)
                .as("Should see at least " + minProductCount + " products")
                .isGreaterThanOrEqualTo(minProductCount);
    }

    @Then("I should see product names and prices")
    public void i_should_see_product_names_and_prices() {
        assertThat(homePage.getProductNames())
                .as("Product names should not be empty")
                .isNotEmpty()
                .allMatch(name -> !name.trim().isEmpty());

        assertThat(homePage.getProductPrices())
                .as("Product prices should not be empty")
                .isNotEmpty()
                .allMatch(price -> price.contains("$"));
    }
}