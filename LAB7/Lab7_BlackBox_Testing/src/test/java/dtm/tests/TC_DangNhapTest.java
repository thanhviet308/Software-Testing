package dtm.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import dtm.base.BaseTest;
import dtm.data.DangNhapData;
import dtm.pages.LoginPage;

public class TC_DangNhapTest extends BaseTest {

    @Test(
            dataProvider = "du_lieu_dang_nhap",
            dataProviderClass = DangNhapData.class,
            description = "Kiểm thử đăng nhập với nhiều bộ dữ liệu"
    )
    public void kiemThuDangNhap(String username,
                                String password,
                                String ketQuaMongDoi,
                                String moTa) {

        // Mở trang login
        getDriver().get("https://www.saucedemo.com");

        LoginPage loginPage = new LoginPage(getDriver());

        // Thực hiện đăng nhập
        loginPage.dangNhap(username, password);

        // Kiểm tra kết quả
        switch (ketQuaMongDoi) {

            case "THANH_CONG":
                Assert.assertTrue(
                        loginPage.isDangOTrangSanPham(),
                        "[FAIL] " + moTa + " - Đáng lẽ phải đăng nhập thành công"
                );
                break;

            case "BI_KHOA":
                String loiKhoa = loginPage.layThongBaoLoi();
                Assert.assertNotNull(loiKhoa,
                        "[FAIL] " + moTa + " - Phải hiển thị lỗi tài khoản bị khóa");
                Assert.assertTrue(loiKhoa.toLowerCase().contains("locked"),
                        "[FAIL] " + moTa + " - Nội dung lỗi phải chứa 'locked'");
                break;

            case "SAI_THONG_TIN":
                String loiSai = loginPage.layThongBaoLoi();
                Assert.assertNotNull(loiSai,
                        "[FAIL] " + moTa + " - Phải hiển thị lỗi sai thông tin");
                Assert.assertTrue(loiSai.toLowerCase().contains("username") ||
                                  loiSai.toLowerCase().contains("password"),
                        "[FAIL] " + moTa + " - Nội dung lỗi không đúng");
                break;

            case "TRUONG_TRONG":
                String loiTrong = loginPage.layThongBaoLoi();
                Assert.assertNotNull(loiTrong,
                        "[FAIL] " + moTa + " - Phải hiển thị lỗi trường trống");
                break;

            default:
                Assert.fail("Giá trị ketQuaMongDoi không hợp lệ: " + ketQuaMongDoi);
        }
    }
}