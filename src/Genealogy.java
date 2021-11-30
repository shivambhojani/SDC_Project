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
            statement.close();
            resultSet.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        System.out.println(p.getPersonName());

        return p;
    }

    //finding media file by location attribute
    FileIdentifier findMediaFile( String location )
    {
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        FileIdentifier f = new FileIdentifier();
        createConnection conn = new createConnection();
        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use "+conn.databaseName);
            String selectQuery = "select * from media_archieve where location='"+location+"';";
            resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {

                f.setMediaId(resultSet.getString("mediaId"));
                f.setFileName(resultSet.getString("filename"));
                f.setLocation(resultSet.getString("location"));
            }
            statement.close();
            resultSet.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return f;
    }
}
