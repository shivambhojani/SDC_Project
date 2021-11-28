import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class main {
    public static void main(String[] args) {

        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //jdbc:mysql://<hostname>
            connect = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306", "root", "root");

            statement = connect.createStatement();
            System.out.println("Connection Successful");
            statement.execute("use project;");
            resultSet = statement.executeQuery("select name from person limit 3;");
            while (resultSet.next()) {
                System.out.println("Person name: " + resultSet.getString("name"));
            }
            resultSet.close();
            statement.close();
            connect.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


//        UUID uuid= UUID.randomUUID();
//        System.out.println(uuid);
//
//        PersonIdentity p = new PersonIdentity();
//        Date thisDate = new Date();
//        SimpleDateFormat dateFormt = new SimpleDateFormat("MMMM dd Y");
//        System.out.println(dateFormt.format(thisDate));
//        PersonIdentity p1 = new PersonIdentity();
    }
}
