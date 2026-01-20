package BaiTapApDung.src.orgapp.dao;

import BaiTapApDung.src.orgapp.model.Organization;
import BaiTapApDung.src.orgapp.util.DBUtil;
import java.sql.*;

public class OrganizationDAO {

    // Kiểm tra OrgName đã tồn tại chưa (KHÔNG phân biệt hoa/thường)
    public boolean existsOrgName(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ORGANIZATION WHERE LOWER(OrgName) = LOWER(?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // >0 nghĩa là có rồi
                }
            }
        }
        return false;
    }

    // Thêm Organization vào DB
    public int insertOrganization(Organization org) throws SQLException {
        String sql = "INSERT INTO ORGANIZATION (OrgName, Address, Phone, Email) VALUES (?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, org.getOrgName());
            ps.setString(2, org.getAddress());
            ps.setString(3, org.getPhone());
            ps.setString(4, org.getEmail());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }

            // Lấy OrgID vừa tạo (Identity)
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    org.setOrgId(newId);
                    return newId;
                } else {
                    throw new SQLException("Insert failed, no ID returned.");
                }
            }
        }
    }
}
