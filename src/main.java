import javax.sound.midi.Soundbank;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class main {
    public static void main(String[] args) {
//
//        Connection connect = null;
//        Statement statement = null;
//        ResultSet resultSet = null;
//        String jdbcURL = "jdbc:mysql://127.0.0.1:3306";
//        String username = "root";
//        String password = "root";
//
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            //jdbc:mysql://<hostname>
//            connect = DriverManager.getConnection(jdbcURL, username, password);
//
//            if(connect!=null){
//                System.out.println("Database Connected");
//            }
//            statement = connect.createStatement();
//
//            System.out.println("Connection Successful");
//
//            resultSet = statement.executeQuery("select * from person;");
//            while (resultSet.next()) {
//                System.out.println("Person name: " + resultSet.getString("name"));
//            }
//            resultSet.close();
//            statement.close();
//            connect.close();
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }

        PersonIdentity pi = new PersonIdentity();
        Genealogy g = new Genealogy();
        pi = pi.addPerson("Raghav");

        PersonIdentity person= g.findPerson("Raghav");
        System.out.println("New Id: "+person.getId());
        Map<String, String> m = new HashMap<>();
        m.put("dob", "27/01/1998");
        m.put("bLocation", "Dublin");
        m.put("gender", null);
        m.put("occupation", "Teacher");
        pi.recordAttributes(person, m);

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
