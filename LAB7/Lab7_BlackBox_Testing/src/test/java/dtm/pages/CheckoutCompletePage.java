package dtm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutCompletePage {
    private final WebDriver driver;

    @FindBy(css = ".complete-header")
    private WebElement header;

    @FindBy(id = "back-to-products")
    private WebElement backHomeBtn;

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String layHeader() {
        return header.getText().trim();
    }

    public void clickBackHome() {
        backHomeBtn.click();
    }
}