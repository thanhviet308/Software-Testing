package Bai5;

import java.util.ArrayList;
import java.util.List;

public class Bai5 {

    // Lớp Học Viên
    public static class HocVien {
        private String maHV;
        private String hoTen;
        private String queQuan;
        private double diemMon1;
        private double diemMon2;
        private double diemMon3;

        public HocVien(String maHV, String hoTen, String queQuan,
                       double diemMon1, double diemMon2, double diemMon3) {
            this.maHV = maHV;
            this.hoTen = hoTen;
            this.queQuan = queQuan;
            this.diemMon1 = diemMon1;
            this.diemMon2 = diemMon2;
            this.diemMon3 = diemMon3;
        }

        public String getMaHV() {
            return maHV;
        }

        public String getHoTen() {
            return hoTen;
        }

        public String getQueQuan() {
            return queQuan;
        }

        public double getDiemMon1() {
            return diemMon1;
        }

        public double getDiemMon2() {
            return diemMon2;
        }

        public double getDiemMon3() {
            return diemMon3;
        }

        // Điểm trung bình 3 môn chính
        public double getDiemTrungBinh() {
            return (diemMon1 + diemMon2 + diemMon3) / 3.0;
        }

        // Điều kiện nhận học bổng:
        // - Điểm TB >= 8.0
        // - Không có môn nào dưới 5
        public boolean duocHocBong() {
            if (diemMon1 < 5.0 || diemMon2 < 5.0 || diemMon3 < 5.0) {
                return false;
            }
            return getDiemTrungBinh() >= 8.0;
        }
    }

    // Hàm tiện ích: từ danh sách học viên, trả về danh sách được học bổng
    public static List<HocVien> timHocVienNhanHocBong(List<HocVien> ds) {
        List<HocVien> ketQua = new ArrayList<>();
        for (HocVien hv : ds) {
            if (hv.duocHocBong()) {
                ketQua.add(hv);
            }
        }
        return ketQua;
    }
}

