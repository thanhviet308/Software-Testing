import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TextBoxWhiteBoxTest {

    WebDriver driver;
    TextBoxPage textBoxPage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/text-box");
        textBoxPage = new TextBoxPage(driver);
    }

    @Test(description = "TC1: Du lieu hop le -> hien thi output")
    public void testValidInput() {
        textBoxPage.fillAndSubmit("Nguyen Van A", "vana@gmail.com", "123 Le Loi");
        Assert.assertTrue(textBoxPage.isOutputDisplayed(),
                "Sai: du lieu hop le thi output phai hien thi.");
    }

    @Test(description = "TC2: Email sai dinh dang -> khong hien thi output")
    public void testInvalidEmail() {
        textBoxPage.fillAndSubmit("Nguyen Van A", "vana@gmail", "123 Le Loi");
        Assert.assertFalse(textBoxPage.isOutputDisplayed(),
                "Sai: email sai dinh dang thi output khong duoc hien thi.");
    }

    @Test(description = "TC3: Name rong, email hop le -> output van hien thi")
    public void testEmptyName() {
        textBoxPage.fillAndSubmit("", "vana@gmail.com", "123 Le Loi");
        Assert.assertTrue(textBoxPage.isOutputDisplayed(),
                "Sai: name rong nhung email hop le thi form van co the submit.");
    }

    @Test(description = "TC4: Name chi co khoang trang -> output van hien thi")
    public void testBlankSpacesName() {
        textBoxPage.fillAndSubmit("   ", "vana@gmail.com", "123 Le Loi");
        Assert.assertTrue(textBoxPage.isOutputDisplayed(),
                "Sai: name chi co khoang trang, neu email hop le thi output van hien thi.");
    }

    @Test(description = "TC5: Name co ky tu dac biet -> output hien thi")
    public void testSpecialCharacterName() {
        textBoxPage.fillAndSubmit("@#$%^", "vana@gmail.com", "123 Le Loi");
        Assert.assertTrue(textBoxPage.isOutputDisplayed(),
                "Sai: ky tu dac biet trong name khong bi chan, output van phai hien thi.");
    }

    @Test(description = "TC6: Address rong, email hop le -> output hien thi")
    public void testEmptyAddress() {
        textBoxPage.fillAndSubmit("Nguyen Van A", "vana@gmail.com", "");
        Assert.assertTrue(textBoxPage.isOutputDisplayed(),
                "Sai: address rong nhung email hop le thi output van hien thi.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}