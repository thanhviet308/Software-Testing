package dtm.pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

public class CheckoutStepOnePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "first-name")
    private WebElement firstName;

    @FindBy(id = "last-name")
    private WebElement lastName;

    @FindBy(id = "postal-code")
    private WebElement zipCode;

    @FindBy(id = "continue")
    private WebElement continueBtn;

    @FindBy(id = "cancel")
    private WebElement cancelBtn;

    @FindBy(css = "h3[data-test='error']")
    private java.util.List<WebElement> errorBox;

    public CheckoutStepOnePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    public void nhapThongTin(String fn, String ln, String zip) {
        firstName.clear(); firstName.sendKeys(fn == null ? "" : fn);
        lastName.clear();  lastName.sendKeys(ln == null ? "" : ln);
        zipCode.clear();   zipCode.sendKeys(zip == null ? "" : zip);
    }

    public void clickContinue() { continueBtn.click(); }

    public void clickCancel() { cancelBtn.click(); }

    public String layLoi() {
        try {
            if (errorBox == null || errorBox.isEmpty()) return null;
            return errorBox.get(0).getText().trim();
        } catch (Exception e) {
            return null;
        }
    }
}