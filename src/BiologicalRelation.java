import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class BiologicalRelation {

    Boolean recordChild(PersonIdentity parent, PersonIdentity child) {
        // if both person identity are same then it will return false, as a person cannot be a child of himself/herself.
        if (Objects.equals(parent.getId(), child.getId())) {
            return false;
        }
        createConnection conn = new createConnection();
        Connection connect = conn.startConnection();
        ResultSet resultSet = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        try {
            Statement statement = connect.createStatement();

            System.out.println(parent.getId());
            System.out.println(child.getId());
            statement.executeQuery("use " + conn.databaseName);

            /*checking if parent exists in person table*/
            resultSet1 = statement.executeQuery("select * from person where p_id='" + parent.getId() + "'");
            if (resultSet1.next() == false) {
                statement.close();
                connect.close();
                return false;
            }

            resultSet2 = statement.executeQuery("select * from person where p_id='" + child.getId() + "'");

            if (resultSet2.next() == false) {
                statement.close();
                connect.close();
                return false;
            }

            // to check whether the child is already marked under any other parent
            resultSet = statement.executeQuery("select * from parentchild_relation where childid='" + child.getId() + "';");

            //this validation will check if this particular child already exists or not
            if (resultSet.next()) {
                statement.close();
                connect.close();
                return false;
            } else {

                String insertQuery = "insert into parentchild_relation values (null,'" + parent.getId() + "','" + child.getId() + "')";
                // System.out.println(insertQuery);
                statement.executeUpdate(insertQuery);
                statement.close();
                connect.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    Boolean recordPartnering(PersonIdentity partner1, PersonIdentity partner2) {
        // if both person identity are same then it will return false, as a person cannot be a child of himself/herself.
        if (Objects.equals(partner1.getId(), partner2.getId())) {
            return false;
        }
        createConnection conn = new createConnection();
        Connection connect = conn.startConnection();
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;

        try {
            Statement statement = connect.createStatement();

            statement.executeQuery("use " + conn.databaseName);
            resultSet1 = statement.executeQuery("select * from person where p_id='" + partner1.getId() + "'");

            /*checking if partner1 exists in person table*/
            if (resultSet1.next() == false) {
                statement.close();
                connect.close();
                return false;
            }

            resultSet2 = statement.executeQuery("select * from person where p_id='" + partner2.getId() + "'");

            /*checking if partner2 exists in person table*/
            if (resultSet2.next() == false) {
                statement.close();
                connect.close();
                return false;
            }

            // to check whether the partner1 is not in a relationship already
            resultSet1 = null;
            resultSet1 = statement.executeQuery("select * from partner_relation where partner1Id='" + partner1.getId() + "'" +
                    "or partner2Id='" + partner1.getId() + "';");

            if (resultSet1.next() == true) {
                statement.close();
                connect.close();
                return false;
            }

            // to check whether the partner2 is not in a relationship already
            resultSet2 = null;
            resultSet2 = statement.executeQuery("select * from partner_relation where partner1Id='" + partner2.getId() + "'" +
                    "or partner2Id='" + partner2.getId() + "';");

            if (resultSet2.next() == true) {
                statement.close();
                connect.close();
                return false;
            }

            String insertRelationship = "insert into partner_relation values (null,'" + partner1.getId() + "','" + partner2.getId() + "');";

            statement.executeUpdate(insertRelationship);
            statement.close();
            connect.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

}
