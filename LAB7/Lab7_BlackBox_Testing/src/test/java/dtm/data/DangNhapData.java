package dtm.data;

import org.testng.annotations.DataProvider;

public class DangNhapData {

    @DataProvider(name = "du_lieu_dang_nhap")
    public Object[][] getData() {
        // Mỗi dòng: { username, password, ketQuaMongDoi, moTa }
        // ketQuaMongDoi: "THANH_CONG", "SAI_THONG_TIN", "BI_KHOA", "TRUONG_TRONG"

        return new Object[][]{
                // ===== 1) Tất cả tài khoản hợp lệ do saucedemo cung cấp =====
                {"standard_user", "secret_sauce", "THANH_CONG", "Login OK - standard_user"},
                {"problem_user", "secret_sauce", "THANH_CONG", "Login OK - problem_user (UI có thể lỗi sau login)"},
                {"performance_glitch_user", "secret_sauce", "THANH_CONG", "Login OK - performance_glitch_user (load chậm)"},
                {"error_user", "secret_sauce", "THANH_CONG", "Login OK - error_user (một số action có thể lỗi)"},

                // ===== 2) Tài khoản bị khoá =====
                {"locked_out_user", "secret_sauce", "BI_KHOA", "User bị khóa - locked_out_user"},

                // ===== 3) Tài khoản không tồn tại (tự đặt) =====
                {"not_exist_user", "secret_sauce", "SAI_THONG_TIN", "Username không tồn tại"},
                {"standard_user", "wrong_pass", "SAI_THONG_TIN", "Sai password"},
                {"not_exist_user", "wrong_pass", "SAI_THONG_TIN", "Sai cả username và password"},

                // ===== 4) Để trống username/password/cả hai =====
                {"", "secret_sauce", "TRUONG_TRONG", "Bỏ trống username"},
                {"standard_user", "", "TRUONG_TRONG", "Bỏ trống password"},
                {"", "", "TRUONG_TRONG", "Bỏ trống cả username và password"},

                // ===== 5) Username có ký tự đặc biệt, khoảng trắng đầu/cuối =====
                {"standard_user!", "secret_sauce", "SAI_THONG_TIN", "Username có ký tự đặc biệt"},
                {" standard_user", "secret_sauce", "SAI_THONG_TIN", "Username có khoảng trắng đầu"},
                {"standard_user ", "secret_sauce", "SAI_THONG_TIN", "Username có khoảng trắng cuối"},
                {"  standard_user  ", "secret_sauce", "SAI_THONG_TIN", "Username có khoảng trắng đầu/cuối"},

                // ===== 6) Giá trị null (test method phải xử lý) =====
                {null, "secret_sauce", "TRUONG_TRONG", "Username = null"},
                {"standard_user", null, "TRUONG_TRONG", "Password = null"},
                {null, null, "TRUONG_TRONG", "Username & Password = null"},
        };
    }
}