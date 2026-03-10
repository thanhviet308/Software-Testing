package dtm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ParallelLoginTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver("chrome");
        driver = DriverFactory.getDriver();
        driver.get("https://www.saucedemo.com");
    }

    @Test(description = "Dang nhap thanh cong trong che do chay song song")
    public void testLoginSuccessParallel() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("inventory"),
                "Dang nhap thanh cong nhung khong chuyen den inventory trong che do parallel!"
        );
    }

    @Test(description = "Kiem tra title trang login trong che do chay song song")
    public void testLoginPageTitleParallel() {
        String actualTitle = driver.getTitle();
        Assert.assertEquals(
                actualTitle,
                "Swag Labs",
                "Tieu de trang login khong dung trong che do parallel!"
        );
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}