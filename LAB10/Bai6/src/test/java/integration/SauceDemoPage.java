package integration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SauceDemoPage {

    private final WebDriver driver;

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");

    private final By addBackpackButton = By.id("add-to-cart-sauce-labs-backpack");
    private final By addBikeLightButton = By.id("add-to-cart-sauce-labs-bike-light");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");
    private final By cartItems = By.className("cart_item");

    public SauceDemoPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openLoginPage() {
        driver.get("https://www.saucedemo.com/");
    }

    public void login(String username, String password) {
        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void addFirstTwoProducts() {
        driver.findElement(addBackpackButton).click();
        driver.findElement(addBikeLightButton).click();
    }

    public String getCartBadgeText() {
        return driver.findElement(cartBadge).getText();
    }

    public void openCart() {
        driver.findElement(cartLink).click();
    }

    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }
}