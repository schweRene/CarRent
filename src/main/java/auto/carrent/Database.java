package auto.carrent;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    public static Connection connectDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connect = DriverManager.getConnection("Jdbc:mysql://localhost/registration", "root", "");
            return connect;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



}
