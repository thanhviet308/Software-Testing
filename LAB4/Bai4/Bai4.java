package Bai4;

public class Bai4 {

    // Lớp điểm trong mặt phẳng 2D
    public static class Diem {
        private double x; // hoành độ
        private double y; // tung độ

        public Diem(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    // Hình chữ nhật xác định bởi điểm trên bên trái và điểm dưới bên phải
    public static class HinhChuNhat {
        private Diem topLeft;      // điểm trên bên trái
        private Diem bottomRight;  // điểm dưới bên phải

        public HinhChuNhat(Diem topLeft, Diem bottomRight) {
            // Kiểm tra dữ liệu hợp lệ:
            // x trái < x phải, y trên > y dưới
            if (topLeft.getX() >= bottomRight.getX()
                    || topLeft.getY() <= bottomRight.getY()) {
                throw new IllegalArgumentException("Invalid Rectangle");
            }
            this.topLeft = topLeft;
            this.bottomRight = bottomRight;
        }

        // Tính diện tích hình chữ nhật
        public double dienTich() {
            double width = bottomRight.getX() - topLeft.getX();
            double height = topLeft.getY() - bottomRight.getY();
            return width * height;
        }

        // Kiểm tra hai hình chữ nhật có giao nhau hay không
        public boolean giaoNhau(HinhChuNhat other) {
            double left1 = this.topLeft.getX();
            double right1 = this.bottomRight.getX();
            double top1 = this.topLeft.getY();
            double bottom1 = this.bottomRight.getY();

            double left2 = other.topLeft.getX();
            double right2 = other.bottomRight.getX();
            double top2 = other.topLeft.getY();
            double bottom2 = other.bottomRight.getY();

            // Nếu một hình nằm hoàn toàn bên trái/phải/trên/dưới hình kia → không giao
            if (right1 <= left2 || right2 <= left1
                    || bottom1 >= top2 || bottom2 >= top1) {
                return false;
            }
            return true; // ngược lại là có giao nhau
        }
    }
}

