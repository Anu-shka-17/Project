import java.sql.Connection;
import java.sql.DriverManager;
public class DbConnection {

    static String url="jdbc:mysql://localhost:3307/studentdb";
    static String username="root";
    static String password="vuanu@!17";

    public static Connection getConnection(){
        Connection con=null;
        try{

            con=DriverManager.getConnection(url,username,password);
            System.out.println("Database connected successfully!");
        }catch(Exception e){
            e.printStackTrace();
        }
        return con;
    }
}
