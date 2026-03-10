import org.testng.Assert;
import org.testng.annotations.Test;

public class PhoneValidatorTest {

    @Test(description = "TC1: phone = null -> false")
    public void testNullPhone() {
        Assert.assertFalse(PhoneValidator.isValid(null),
                "Sai: phone null phai khong hop le.");
    }

    @Test(description = "TC2: phone rong -> false")
    public void testEmptyPhone() {
        Assert.assertFalse(PhoneValidator.isValid(""),
                "Sai: phone rong phai khong hop le.");
    }

    @Test(description = "TC3: phone co ky tu khong hop le -> false")
    public void testInvalidCharacter() {
        Assert.assertFalse(PhoneValidator.isValid("09a1234567"),
                "Sai: phone co chu cai phai khong hop le.");
    }

    @Test(description = "TC4: phone bat dau +84 hop le -> true")
    public void testValidPhoneWith84() {
        Assert.assertTrue(PhoneValidator.isValid("+84 912345678"),
                "Sai: so +84 hop le phai duoc chap nhan.");
    }

    @Test(description = "TC5: phone khong bat dau bang 0 hoac +84 -> false")
    public void testWrongPrefix() {
        Assert.assertFalse(PhoneValidator.isValid("912345678"),
                "Sai: so khong bat dau bang 0 hoac +84 phai khong hop le.");
    }

    @Test(description = "TC6: do dai sai -> false")
    public void testWrongLength() {
        Assert.assertFalse(PhoneValidator.isValid("091234567"),
                "Sai: so khong du 10 chu so phai khong hop le.");
    }

    @Test(description = "TC7: dau so khong hop le -> false")
    public void testWrongNetworkPrefix() {
        Assert.assertFalse(PhoneValidator.isValid("0212345678"),
                "Sai: dau so 02 khong nam trong danh sach hop le.");
    }

    @Test(description = "TC8: phone dang 0 hop le -> true")
    public void testValidPhoneNormal() {
        Assert.assertTrue(PhoneValidator.isValid("0912345678"),
                "Sai: so dien thoai hop le phai tra ve true.");
    }

    @Test(description = "TC9: dau so 03 hop le -> true")
    public void testBoundaryPrefix03() {
        Assert.assertTrue(PhoneValidator.isValid("0312345678"),
                "Sai: dau so 03 hop le.");
    }

    @Test(description = "TC10: dau so 05 hop le -> true")
    public void testBoundaryPrefix05() {
        Assert.assertTrue(PhoneValidator.isValid("0512345678"),
                "Sai: dau so 05 hop le.");
    }

    @Test(description = "TC11: phone co khoang trang hop le -> true")
    public void testPhoneWithSpaces() {
        Assert.assertTrue(PhoneValidator.isValid("0912 345 678"),
                "Sai: phone co khoang trang nhung hop le sau chuan hoa.");
    }

    @Test(description = "TC12: phone chi co khoang trang -> false")
    public void testOnlySpaces() {
        Assert.assertFalse(PhoneValidator.isValid("   "),
                "Sai: chuoi chi co khoang trang phai khong hop le.");
    }
}
