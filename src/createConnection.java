import java.sql.*;

public class createConnection {

    Connection connect = null;
    //Statement statement = null;
    //ResultSet resultSet = null;
    String jdbcURL = "jdbc:mysql://127.0.0.1:3306";
    String username = "root";
    String password = "root";
    String databaseName = "project";

    Connection startConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connect = DriverManager.getConnection(jdbcURL, username, password);
            if (connect != null) {
            }
            //resultSet.close();
            //statement.close();
            //connect.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connect;
    }

}
