package dtm.pages;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final String URL = "https://www.saucedemo.com/";

    @FindBy(id = "user-name")
    private WebElement userNameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    // div[data-test='error'] theo đề
    @FindBy(css = "div[data-test='error']")
    private WebElement errorMessage;

    // Trang inventory dùng để check đăng nhập thành công
    @FindBy(css = ".inventory_list")
    private WebElement inventoryList;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    /** Mở trang login */
    public void open() {
        driver.get(URL);
    }

    public void nhapUsername(String username) {
        userNameField.clear();
        // xử lý null theo yêu cầu đề (null -> "")
        if (username != null) {
            userNameField.sendKeys(username);
        }
    }

    public void nhapPassword(String password) {
        passwordField.clear();
        if (password != null) {
            passwordField.sendKeys(password);
        }
    }

    public void clickDangNhap() {
        loginButton.click();
    }

    /** Thực hiện đăng nhập đầy đủ */
    public void dangNhap(String user, String pass) {
        nhapUsername(user);
        nhapPassword(pass);
        clickDangNhap();
    }

    /** Trả về nội dung thông báo lỗi, null nếu không có lỗi */
    public String layThongBaoLoi() {
        try {
            // đợi lỗi xuất hiện nhanh (nếu có)
            WebElement el = wait.until(ExpectedConditions.visibilityOf(errorMessage));
            String text = el.getText();
            return (text == null || text.trim().isEmpty()) ? null : text.trim();
        } catch (TimeoutException e) {
            return null;
        }
    }

    /** Kiểm tra đã chuyển sang trang inventory chưa */
    public boolean isDangOTrangSanPham() {
        try {
            // /inventory.html + có inventory_list
            wait.until(ExpectedConditions.urlContains("/inventory.html"));
            wait.until(ExpectedConditions.visibilityOf(inventoryList));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}