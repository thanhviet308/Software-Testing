package BaiTapApDung.src.orgapp.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDatabase {

    public static void main(String[] args) {
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement()) {

            String createTable = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='ORGANIZATION' AND xtype='U') " +
                    "CREATE TABLE ORGANIZATION (" +
                    "   OrgID INT IDENTITY(1,1) PRIMARY KEY," +
                    "   OrgName NVARCHAR(255) NOT NULL UNIQUE," +
                    "   Address NVARCHAR(255) NULL," +
                    "   Phone VARCHAR(20) NULL," +
                    "   Email VARCHAR(100) NULL," +
                    "   CreatedDate DATETIME NOT NULL DEFAULT GETDATE()" +
                    ");";

            st.executeUpdate(createTable);
            System.out.println("✔ Table ORGANIZATION created or already exists!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

