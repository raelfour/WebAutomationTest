import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Selenium {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup(); // Setup driver
        driver = new ChromeDriver(); // Create driver instance
    }

    @Test
    public void helloWorld() {
        driver.get("https://jayjay.co");

        String text = driver.findElement(By.className("content-info")).getText();

        assertEquals("Belajar dari para ahli terbaik", text);
    }
}
