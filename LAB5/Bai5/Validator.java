package Bai5;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class Validator {
    private static final Pattern MAKH = Pattern.compile("^[a-zA-Z0-9]{6,10}$");
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern SDT = Pattern.compile("^0\\d{9,11}$"); // 10-12 digits, starts 0
    private static final Pattern HOTEN = Pattern.compile("^[\\p{L} ]{5,50}$"); // chữ Unicode + space

    public static String validateAll(
            String maKh, String hoTen, String email, String sdt, String diaChi,
            String mk, String xacNhan, LocalDate ngaySinh, boolean dongYDieuKhoan
    ) {
        if (isBlank(maKh)) return "Mã khách hàng là bắt buộc.";
        if (!MAKH.matcher(maKh).matches()) return "Mã khách hàng phải 6-10 ký tự, chỉ gồm chữ và số.";

        if (isBlank(hoTen)) return "Họ và tên là bắt buộc.";
        if (!HOTEN.matcher(hoTen.trim()).matches()) return "Họ và tên phải 5-50 ký tự, chỉ chữ và khoảng trắng.";

        if (isBlank(email)) return "Email là bắt buộc.";
        if (!EMAIL.matcher(email).matches()) return "Email không đúng định dạng (vd: nguyenvana@email.com).";

        if (isBlank(sdt)) return "Số điện thoại là bắt buộc.";
        if (!SDT.matcher(sdt).matches()) return "SĐT phải 10-12 số và bắt đầu bằng số 0.";

        if (isBlank(diaChi)) return "Địa chỉ là bắt buộc.";
        if (diaChi.length() > 255) return "Địa chỉ tối đa 255 ký tự.";

        if (isBlank(mk)) return "Mật khẩu là bắt buộc.";
        if (mk.length() < 8) return "Mật khẩu tối thiểu 8 ký tự.";

        if (isBlank(xacNhan)) return "Xác nhận mật khẩu là bắt buộc.";
        if (!mk.equals(xacNhan)) return "Xác nhận mật khẩu không khớp.";

        if (ngaySinh != null) {
            int age = Period.between(ngaySinh, LocalDate.now()).getYears();
            if (age < 18) return "Nếu nhập ngày sinh, người dùng phải đủ 18 tuổi.";
        }

        if (!dongYDieuKhoan) return "Bạn phải tích chọn đồng ý điều khoản dịch vụ.";

        return null; // OK
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
