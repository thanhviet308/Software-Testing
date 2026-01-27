package Bai5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerDAO {

    public boolean existsMaKh(String maKh) throws Exception {
        String sql = "SELECT 1 FROM KhachHang WHERE MaKhachHang = ?";
        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, maKh);
            try (ResultSet rs = p.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean existsEmail(String email) throws Exception {
        String sql = "SELECT 1 FROM KhachHang WHERE Email = ?";
        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, email);
            try (ResultSet rs = p.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void insert(Customer x) throws Exception {
        String sql = """
            INSERT INTO KhachHang(MaKhachHang, HoTen, Email, SoDienThoai, DiaChi, MatKhauHash, NgaySinh, GioiTinh)
            VALUES(?,?,?,?,?,?,?,?)
        """;
        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, x.maKh);
            p.setString(2, x.hoTen);
            p.setString(3, x.email);
            p.setString(4, x.sdt);
            p.setString(5, x.diaChi);
            p.setString(6, x.matKhauHash);
            if (x.ngaySinh == null) p.setObject(7, null);
            else p.setDate(7, java.sql.Date.valueOf(x.ngaySinh));
            p.setString(8, x.gioiTinh);
            p.executeUpdate();
        }
    }
}
