public class PhoneValidator {

    public static boolean isValid(String phone) {

        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }

        // Chỉ cho phép số, dấu + và khoảng trắng
        if (!phone.matches("[0-9+ ]+")) {
            return false;
        }

        // Bỏ khoảng trắng
        String normalized = phone.replaceAll("\\s+", "");

        // Chuẩn hóa +84 -> 0
        if (normalized.startsWith("+84")) {
            normalized = "0" + normalized.substring(3);
        }

        // Sau chuẩn hóa phải bắt đầu bằng 0
        if (!normalized.startsWith("0")) {
            return false;
        }

        // Phải đúng 10 chữ số
        if (normalized.length() != 10) {
            return false;
        }

        // Đầu số hợp lệ: 03, 05, 07, 08, 09
        if (!normalized.matches("0(3|5|7|8|9)\\d{8}")) {
            return false;
        }

        return true;
    }
}
