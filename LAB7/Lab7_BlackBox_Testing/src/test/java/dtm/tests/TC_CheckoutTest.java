package dtm.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dtm.base.BaseTest;
import dtm.pages.InventoryPage;
import dtm.pages.LoginPage;

public class TC_CheckoutTest extends BaseTest {

    @BeforeMethod
    public void chuanBi() {
        getDriver().get("https://www.saucedemo.com");
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.dangNhap("standard_user", "secret_sauce");
        Assert.assertTrue(loginPage.isDangOTrangSanPham(), "Login thất bại, không vào được inventory.");
    }

    private double parseMoney(String text) {
        // "$29.99" hoặc "Item total: $39.98" -> lấy số
        String num = text.replaceAll("[^0-9.]", "");
        return Double.parseDouble(num);
    }

    @Test(groups = {"regression"}, description = "TC_CART_010: Kiểm tra tổng tiền chính xác")
    public void kiemTraTongTien() {
        InventoryPage inventoryPage = new InventoryPage(getDriver());

        // 1) Thêm ít nhất 3 sản phẩm có giá khác nhau
        inventoryPage.themNSanPhamDauTien(3);
        inventoryPage.moGioHang();

        // 2) Checkout step 1
        getDriver().findElement(By.id("checkout")).click();
        getDriver().findElement(By.id("first-name")).sendKeys("Nguyen");
        getDriver().findElement(By.id("last-name")).sendKeys("A");
        getDriver().findElement(By.id("postal-code")).sendKeys("700000");
        getDriver().findElement(By.id("continue")).click();

        // 3) Ở step 2: tự tính Item total từ giá từng item
        List<WebElement> priceEls = getDriver().findElements(By.cssSelector(".cart_item .inventory_item_price"));
        Assert.assertTrue(priceEls.size() >= 3, "Chưa đủ 3 sản phẩm ở step 2.");

        double sum = 0.0;
        for (WebElement el : priceEls) {
            sum += parseMoney(el.getText());
        }

        // Lấy số từ UI
        double itemTotalUI = parseMoney(getDriver().findElement(By.cssSelector(".summary_subtotal_label")).getText());
        double taxUI = parseMoney(getDriver().findElement(By.cssSelector(".summary_tax_label")).getText());
        double totalUI = parseMoney(getDriver().findElement(By.cssSelector(".summary_total_label")).getText());

        // 4) Assert với delta 0.01
        Assert.assertEquals(itemTotalUI, sum, 0.01, "Item total sai.");
        Assert.assertEquals(taxUI, itemTotalUI * 0.08, 0.01, "Tax (8%) sai.");
        Assert.assertEquals(totalUI, itemTotalUI + taxUI, 0.01, "Total sai.");
    }
}