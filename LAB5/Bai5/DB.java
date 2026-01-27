
package Bai5;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {

    private static final String URL =
        "jdbc:sqlserver://localhost:1433;"
      + "databaseName=Lab5_KiemThu;"
      + "encrypt=false;"
      + "trustServerCertificate=true;";

    private static final String USER = "sa";
    private static final String PASS = "P@ssw0rd123!";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
