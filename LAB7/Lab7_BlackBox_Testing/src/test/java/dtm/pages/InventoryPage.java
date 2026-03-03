package dtm.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

public class InventoryPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "select[data-test='product_sort_container']")
    private WebElement sortDropdown;

    @FindBy(css = ".inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = "a.shopping_cart_link")
    private WebElement cartLink;

    @FindBy(css = "span.shopping_cart_badge")
    private List<WebElement> cartBadge; // có thể không tồn tại

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    /** Thêm sản phẩm theo tên */
    public void themSanPhamTheoTen(String tenSanPham) {
        for (WebElement item : inventoryItems) {
            String name = item.findElement(By.cssSelector(".inventory_item_name")).getText().trim();
            if (name.equalsIgnoreCase(tenSanPham.trim())) {
                WebElement btn = item.findElement(By.cssSelector("button.btn_inventory"));
                btn.click();
                return;
            }
        }
        throw new NoSuchElementException("Không tìm thấy sản phẩm: " + tenSanPham);
    }

    /** Thêm N sản phẩm đầu tiên trong danh sách */
    public void themNSanPhamDauTien(int n) {
        int count = Math.min(n, inventoryItems.size());
        for (int i = 0; i < count; i++) {
            WebElement btn = inventoryItems.get(i).findElement(By.cssSelector("button.btn_inventory"));
            if (btn.getText().toLowerCase().contains("add")) btn.click();
        }
    }

    /** Trả về số lượng badge giỏ hàng, 0 nếu không có badge */
    public int laySoLuongBadge() {
        if (cartBadge == null || cartBadge.isEmpty()) return 0;
        String text = cartBadge.get(0).getText().trim();
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }

    /** Sort sản phẩm theo tùy chọn: 'az','za','lohi','hilo' */
    public void sortSanPham(String option) {
        String value;
        switch (option) {
            case "az":   value = "az"; break;
            case "za":   value = "za"; break;
            case "lohi": value = "lohi"; break;
            case "hilo": value = "hilo"; break;
            default: throw new IllegalArgumentException("Option sort không hợp lệ: " + option);
        }
        new Select(sortDropdown).selectByValue(value);
        wait.until(ExpectedConditions.visibilityOfAllElements(inventoryItems));
    }

    /** Lấy danh sách tên sản phẩm theo thứ tự hiển thị */
    public List<String> layDanhSachTenSanPham() {
        List<String> names = new ArrayList<>();
        for (WebElement item : inventoryItems) {
            names.add(item.findElement(By.cssSelector(".inventory_item_name")).getText().trim());
        }
        return names;
    }

    /** Lấy danh sách giá sản phẩm theo thứ tự hiển thị */
    public List<Double> layDanhSachGiaSanPham() {
        List<Double> prices = new ArrayList<>();
        for (WebElement item : inventoryItems) {
            String priceText = item.findElement(By.cssSelector(".inventory_item_price")).getText().replace("$", "").trim();
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    public void moGioHang() {
        cartLink.click();
    }
}