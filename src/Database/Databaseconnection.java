package Database;
import java.sql.*;

public class Databaseconnection {
    private static final String url = "jdbc:mysql://localhost:3306/ecommerce";
    private static final String username = "root";
    private static final String password = "";
    public static Connection getconnection()  throws SQLException{
        return DriverManager.getConnection(url, username, password);
    }
}
