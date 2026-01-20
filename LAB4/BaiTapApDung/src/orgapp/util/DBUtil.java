package BaiTapApDung.src.orgapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String URL =
            "jdbc:sqlserver://localhost:1433;"
          + "databaseName=OrgManagement;"
          + "encrypt=false;"
          + "trustServerCertificate=true;";

    private static final String USER = "sa";
    private static final String PASS = "P@ssw0rd123!"; // đúng như docker-compose

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
