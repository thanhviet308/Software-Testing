package dtm.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(id = "checkout")
    private List<WebElement> checkoutBtn; // dùng list để tránh lỗi khi không có

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingBtn;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    public List<String> layTenSanPhamTrongGio() {
        List<String> names = new ArrayList<>();
        for (WebElement item : cartItems) {
            names.add(item.findElement(By.cssSelector(".inventory_item_name")).getText().trim());
        }
        return names;
    }

    public void xoaSanPhamTheoTen(String ten) {
        for (WebElement item : cartItems) {
            String name = item.findElement(By.cssSelector(".inventory_item_name")).getText().trim();
            if (name.equalsIgnoreCase(ten.trim())) {
                item.findElement(By.cssSelector("button.cart_button")).click();
                wait.until(ExpectedConditions.stalenessOf(item));
                return;
            }
        }
        throw new NoSuchElementException("Không tìm thấy sản phẩm trong giỏ: " + ten);
    }

    public void xoaTatCa() {
        for (WebElement item : new ArrayList<>(cartItems)) {
            try {
                item.findElement(By.cssSelector("button.cart_button")).click();
            } catch (Exception ignored) {}
        }
    }

    public boolean coTheCheckout() {
        return checkoutBtn != null && !checkoutBtn.isEmpty() && checkoutBtn.get(0).isEnabled();
    }

    public void clickCheckout() {
        if (!coTheCheckout()) throw new IllegalStateException("Giỏ trống/không thể checkout");
        checkoutBtn.get(0).click();
    }

    public void clickContinueShopping() {
        continueShoppingBtn.click();
    }
}