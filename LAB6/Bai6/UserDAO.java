package Bai6;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean existsUsername(String username, Integer excludeId) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE lower(username)=lower(?)" + (excludeId != null ? " AND id <> ?" : "") + " LIMIT 1";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            if (excludeId != null) ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean existsEmail(String email, Integer excludeId) throws SQLException {
        if (email == null || email.isBlank()) return false;
        String sql = "SELECT 1 FROM users WHERE lower(email)=lower(?)" + (excludeId != null ? " AND id <> ?" : "") + " LIMIT 1";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            if (excludeId != null) ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void insert(User u) throws SQLException {
        String sql = """
            INSERT INTO users(username, password, full_name, email, role, status)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullName());
            ps.setString(4, (u.getEmail() == null || u.getEmail().isBlank()) ? null : u.getEmail());
            ps.setString(5, u.getRole());
            ps.setString(6, u.getStatus());
            ps.executeUpdate();
        }
    }

    public void update(User u) throws SQLException {
        String sql = """
            UPDATE users
            SET username=?, password=?, full_name=?, email=?, role=?, status=?
            WHERE id=?
        """;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullName());
            ps.setString(4, (u.getEmail() == null || u.getEmail().isBlank()) ? null : u.getEmail());
            ps.setString(5, u.getRole());
            ps.setString(6, u.getStatus());
            ps.setInt(7, u.getId());
            ps.executeUpdate();
        }
    }

    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void updateStatus(int id, String status) throws SQLException {
        String sql = "UPDATE users SET status=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT id, username, password, full_name, email, role, status FROM users ORDER BY id DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<User> list = new ArrayList<>();
            while (rs.next()) {
                list.add(map(rs));
            }
            return list;
        }
    }

    public List<User> search(String keyword) throws SQLException {
        if (keyword == null) keyword = "";
        keyword = keyword.trim();
        if (keyword.isEmpty()) return findAll();

        String sql = """
            SELECT id, username, password, full_name, email, role, status
            FROM users
            WHERE lower(username) LIKE lower(?) OR lower(full_name) LIKE lower(?) OR lower(email) LIKE lower(?) OR lower(role) LIKE lower(?)
            ORDER BY id DESC
        """;
        String like = "%" + keyword + "%";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);

            try (ResultSet rs = ps.executeQuery()) {
                List<User> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        }
    }

    private User map(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("full_name"),
                rs.getString("email"),
                rs.getString("role"),
                rs.getString("status")
        );
    }
}
