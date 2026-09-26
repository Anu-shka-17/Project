import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    public static Connection getConnection() {

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/vehicle_rental",
                "root",
                "vuanu@!17"
            );

            System.out.println("Database Connected Successfully!");
            return con;

        } catch (Exception e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }
}