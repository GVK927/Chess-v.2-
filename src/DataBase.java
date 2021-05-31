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

    public static void pushToDB(Game game, boolean whiteWin){
        try {
            statement.execute(
                    "INSERT INTO 'games' " +
                            "('date', 'white_win', 'moves_count', 'time') " +
                            "VALUES ('" + game.getBeginDate() + "', '" + whiteWin + "', '" + game.getGameBoard().getMoves().size() + "', '" + game.getPlayTime() + "')"
            );
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
