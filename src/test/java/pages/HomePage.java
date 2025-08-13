package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "flash")
    private WebElement flashMessage;

    @FindBy(xpath = "//a[contains(@href, 'logout')]")
    private WebElement logoutButton;

    @FindBy(tagName = "h2")
    private WebElement pageTitle;

    @FindBy(tagName = "h4")
    private WebElement welcomeMessage;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getFlashMessage() {
        wait.until(ExpectedConditions.visibilityOf(flashMessage));
        return flashMessage.getText();
    }

    public String getPageTitle() {
        wait.until(ExpectedConditions.visibilityOf(pageTitle));
        return pageTitle.getText();
    }

    public String getWelcomeMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(welcomeMessage));
            return welcomeMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        logoutButton.click();
    }

    public boolean isOnSecurePage() {
        return driver.getCurrentUrl().contains("secure");
    }
}
