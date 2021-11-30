import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BiologicalRelation {

    Boolean recordChild( PersonIdentity parent, PersonIdentity child )
    {
        createConnection conn = new createConnection();
        Connection connect = conn.startConnection();
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        try{
            Statement statement = connect.createStatement();

            System.out.println(parent.getId());
            System.out.println(child.getId());
            statement.executeQuery("use " + conn.databaseName);
            resultSet1 = statement.executeQuery("select * from person where p_id='"+parent.getId()+"'");
            resultSet2 = statement.executeQuery("select * from person where p_id='"+child.getId()+"'");

            String insertQuery = "insert into parentchild_relation values (null,'"+parent.getId()+"','"+child.getId()+"')";
            System.out.println(insertQuery);
            statement.executeUpdate(insertQuery);
            statement.close();
            connect.close();
        }
        catch (SQLException e){
            System.out.println(e);
        }

        return true;
    }

}
