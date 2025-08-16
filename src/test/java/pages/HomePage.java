package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "app_logo")
    private WebElement appLogo;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(css = ".bm-burger-button")
    private WebElement alternativeMenuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(linkText = "Logout")
    private WebElement alternativeLogoutLink;

    @FindBy(css = ".inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(css = ".title")
    private WebElement pageTitle;

    @FindBy(id = "inventory_container")
    private WebElement inventoryContainer;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean isHomePageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(appLogo));
            return appLogo.isDisplayed() && inventoryContainer.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        try {
            wait.until(ExpectedConditions.visibilityOf(pageTitle));
            return pageTitle.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isAppLogoDisplayed() {
        try {
            return appLogo.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getNumberOfProducts() {
        wait.until(ExpectedConditions.visibilityOfAllElements(inventoryItems));
        return inventoryItems.size();
    }

    public List<String> getProductNames() {
        wait.until(ExpectedConditions.visibilityOfAllElements(productNames));
        return productNames.stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<String> getProductPrices() {
        wait.until(ExpectedConditions.visibilityOfAllElements(productPrices));
        return productPrices.stream()
                .map(WebElement::getText)
                .toList();
    }

    public void openMenu() {
        boolean menuOpened = false;

        try {
            wait.until(ExpectedConditions.elementToBeClickable(menuButton));
            menuButton.click();
            Thread.sleep(1000);
            menuOpened = isMenuOpen();
        } catch (Exception e1) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", menuButton);
                Thread.sleep(1000);
                menuOpened = isMenuOpen();
            } catch (Exception e2) {
                try {
                    alternativeMenuButton.click();
                    Thread.sleep(1000);
                    menuOpened = isMenuOpen();
                } catch (Exception e3) {
                    System.err.println("Failed to open menu with all approaches");
                }
            }
        }

        if (!menuOpened) {
            throw new RuntimeException("Could not open the hamburger menu");
        }
    }

    private boolean isMenuOpen() {
        try {
            return logoutLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogout() {
        if (!isMenuOpen()) {
            openMenu();
        }

        try {
            wait.until(ExpectedConditions.visibilityOf(logoutLink));
            wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
            logoutLink.click();
        } catch (Exception e1) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", logoutLink);
            } catch (Exception e2) {
                try {
                    alternativeLogoutLink.click();
                } catch (Exception e3) {
                    throw new RuntimeException("Could not click logout link with any approach");
                }
            }
        }
    }

    public void waitForPageToLoad() {
        wait.until(ExpectedConditions.visibilityOf(inventoryContainer));
    }
}