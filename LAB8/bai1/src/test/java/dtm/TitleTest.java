package dtm;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class TitleTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");
    }

    // Test 1: Kiểm thử tiêu đề trang
    @Test(description = "Kiểm thử tiêu đề trang chủ")
    public void testTitle() {
        String expectedTitle = "Swag Labs";
        String actualTitle = driver.getTitle();

        Assert.assertEquals(actualTitle, expectedTitle,
                "Tiêu đề trang không đúng!");
    }

    // Test 2: Kiểm thử URL trang
    @Test(description = "Kiểm thử URL trang chủ")
    public void testURL() {
        String actualUrl = driver.getCurrentUrl();

        Assert.assertTrue(actualUrl.contains("saucedemo"),
                "URL không hợp lệ!");
    }

    // Test 3: Kiểm thử Page Source
    @Test(description = "Kiểm thử nội dung page source")
    public void testPageSource() {
        String pageSource = driver.getPageSource();

        Assert.assertTrue(pageSource.contains("Swag Labs"),
                "Page source không chứa nội dung mong đợi!");
    }

    // Test 4: Kiểm thử form login hiển thị
    @Test(description = "Kiểm thử form đăng nhập hiển thị")
    public void testLoginFormDisplayed() {

        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        Assert.assertTrue(usernameField.isDisplayed(),
                "Ô username không hiển thị");

        Assert.assertTrue(passwordField.isDisplayed(),
                "Ô password không hiển thị");

        Assert.assertTrue(loginButton.isDisplayed(),
                "Nút login không hiển thị");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}