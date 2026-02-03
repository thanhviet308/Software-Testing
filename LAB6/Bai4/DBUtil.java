package Bai4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    // Tạo file DB local: org.db (nằm trong folder dự án)
    private static final String URL = "jdbc:sqlite:org.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Tạo bảng nếu chưa có
    public static void initDatabase() {
        String sql = """
            CREATE TABLE IF NOT EXISTS organization_unit (
                unit_id TEXT PRIMARY KEY,
                name TEXT NOT NULL,
                description TEXT
            );
        """;

        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Init DB failed: " + e.getMessage(), e);
        }
    }
}
