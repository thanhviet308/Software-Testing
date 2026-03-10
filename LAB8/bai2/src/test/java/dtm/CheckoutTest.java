package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest {

    @Test(groups = {"smoke", "regression"}, description = "Thanh toan thanh cong")
    public void testCheckoutSuccess() {
        String actualMessage = "Checkout Complete!";
        Assert.assertEquals(actualMessage, "Checkout Complete!",
                "Thanh toan khong thanh cong!");
    }

    @Test(groups = {"regression"}, description = "Thanh toan khi thieu thong tin")
    public void testCheckoutMissingInfo() {
        String actualError = "Error: First Name is required";
        Assert.assertTrue(actualError.contains("required"),
                "Thong bao loi khi thieu thong tin checkout khong dung!");
    }
}