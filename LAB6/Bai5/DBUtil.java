package Bai5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    // DB file local
    private static final String URL = "jdbc:sqlite:hrm.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initDatabase() {
        String sql = """
            CREATE TABLE IF NOT EXISTS job_title (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                job_title TEXT NOT NULL,
                description TEXT,
                note TEXT,
                spec_file_name TEXT,
                spec_file_path TEXT,
                spec_file_size_kb INTEGER,
                created_at TEXT DEFAULT (datetime('now'))
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
