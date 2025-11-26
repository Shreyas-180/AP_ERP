package Common;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL1 = "jdbc:mysql://localhost:3306/Auth?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String URL2 = "jdbc:mysql://localhost:3306/erp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    //private static final String URL2 = "jdbc:mysql://localhost:3306/erp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";        // your MySQL username
    private static final String PASSWORD = "pichu panda"; // your MySQL password

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL1, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Connection getConnection2() {
        try {
            return DriverManager.getConnection(URL2, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
