import java.sql.*;
import java.util.ArrayList;

public final class DataBase {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:stats.db");
            statement = connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private DataBase(){}


}
