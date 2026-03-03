package dtm.pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

public class CheckoutStepTwoPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = ".summary_subtotal_label")
    private WebElement itemTotal;

    @FindBy(css = ".summary_tax_label")
    private WebElement tax;

    @FindBy(css = ".summary_total_label")
    private WebElement total;

    @FindBy(id = "finish")
    private WebElement finishBtn;

    @FindBy(id = "cancel")
    private WebElement cancelBtn;

    public CheckoutStepTwoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    private double parseMoney(String text) {
        // Ví dụ: "Item total: $39.98"
        String num = text.replaceAll("[^0-9.]", "");
        return Double.parseDouble(num);
    }

    public double layItemTotal() { return parseMoney(itemTotal.getText()); }
    public double layTax() { return parseMoney(tax.getText()); }
    public double layTotal() { return parseMoney(total.getText()); }

    public void clickFinish() { finishBtn.click(); }
    public void clickCancel() { cancelBtn.click(); }
}