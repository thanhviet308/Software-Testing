package dtm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ParallelCartTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver("chrome");
        driver = DriverFactory.getDriver();
        driver.get("https://www.saucedemo.com");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }

    @Test(description = "Them san pham vao gio hang trong che do chay song song")
    public void testAddToCartParallel() {
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        String cartBadge = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(
                cartBadge,
                "1",
                "Them san pham vao gio hang that bai trong che do parallel!"
        );
    }

    @Test(description = "Kiem tra URL trang inventory trong che do chay song song")
    public void testInventoryUrlParallel() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("inventory"),
                "Khong o dung trang inventory trong che do parallel!"
        );
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}