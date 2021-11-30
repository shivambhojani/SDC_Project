import java.sql.*;

public class Genealogy {

    PersonIdentity findPerson(String name) {
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PersonIdentity p = new PersonIdentity();
        createConnection conn = new createConnection();
        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use "+conn.databaseName);
            String selectQuery = "select * from person where name='"+name+"';";
            resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {

                p.setId(resultSet.getString("p_id"));
                p.setPersonName(resultSet.getString("name"));
                p.setDob(resultSet.getString("dob"));
                p.setbLocation(resultSet.getString("bLocation"));
                p.setDod(resultSet.getString("dod"));
                p.setdLocation(resultSet.getString("dLocation"));
                p.setOccupation(resultSet.getString("occupation"));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        System.out.println(p.getPersonName());

        return p;
    }
}
