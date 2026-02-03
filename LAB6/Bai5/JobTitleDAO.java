package Bai5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobTitleDAO {

    // Nếu hệ thống yêu cầu Job Title không trùng (tùy bài), dùng check này
    public boolean existsJobTitle(String jobTitle) throws SQLException {
        String sql = "SELECT 1 FROM job_title WHERE lower(job_title) = lower(?) LIMIT 1";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, jobTitle);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void insert(
            String jobTitle,
            String description,
            String note,
            String fileName,
            String filePath,
            Integer fileSizeKb
    ) throws SQLException {

        String sql = """
            INSERT INTO job_title(job_title, description, note, spec_file_name, spec_file_path, spec_file_size_kb)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, jobTitle);
            ps.setString(2, description);
            ps.setString(3, note);
            ps.setString(4, fileName);
            ps.setString(5, filePath);

            if (fileSizeKb == null) ps.setNull(6, java.sql.Types.INTEGER);
            else ps.setInt(6, fileSizeKb);

            ps.executeUpdate();
        }
    }
}
