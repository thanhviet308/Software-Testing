package Bai4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationUnitDAO {

    public boolean existsUnitId(String unitId) throws SQLException {
        String sql = "SELECT 1 FROM organization_unit WHERE unit_id = ? LIMIT 1";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, unitId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void insert(String unitId, String name, String description) throws SQLException {
        String sql = "INSERT INTO organization_unit(unit_id, name, description) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, unitId);
            ps.setString(2, name);
            ps.setString(3, description);
            ps.executeUpdate();
        }
    }
}
