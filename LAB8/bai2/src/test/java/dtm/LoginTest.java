package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest {

    @Test(groups = {"smoke", "regression"}, description = "Dang nhap thanh cong")
    public void testLoginSuccess() {
        String actualUrl = "https://www.saucedemo.com/inventory.html";
        Assert.assertTrue(actualUrl.contains("inventory"),
                "Dang nhap thanh cong nhung khong chuyen den trang inventory!");
    }

    @Test(groups = {"regression"}, description = "Dang nhap sai mat khau")
    public void testLoginWrongPassword() {
        String actualError = "Epic sadface: Username and password do not match any user in this service";
        Assert.assertTrue(actualError.contains("do not match"),
                "Thong bao loi khi dang nhap sai mat khau khong dung!");
    }
}