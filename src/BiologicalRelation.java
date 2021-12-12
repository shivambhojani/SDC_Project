import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class BiologicalRelation {
    private String Cousinship = "";
    private String Removal = "";

    public String getCousinship() {
        return Cousinship;
    }

    public void setCousinship(String cousinship) {
        Cousinship = cousinship;
    }

    public String getRemoval() {
        return Removal;
    }

    public void setRemoval(String removal) {
        Removal = removal;
    }


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
                System.out.println("Person mentioned as parent, does not exist");
                return false;
            }

            resultSet2 = statement.executeQuery("select * from person where p_id='" + child.getId() + "'");
            if (resultSet2.next() == false) {
                statement.close();
                connect.close();
                System.out.println("Person mentioned as child, does not exist");
                return false;
            }

            resultSet = statement.executeQuery("select * from parentchild_relation where parentid='" + child.getId() + "' " +
                    "and childid='" + parent.getId() + "';");
            if (resultSet.next()) {
                System.out.println("Unable to record relation. A record exists where " + child.getPersonName() + " is parent of " + parent.getPersonName());
            }

            /*Cheking if the record already exists for the given parent-child relation*/
            resultSet = statement.executeQuery("select * from parentchild_relation where parentid='" + parent.getId() + "' " +
                    "and childid='" + child.getId() + "';");
            if (resultSet.next() == true) {
                System.out.println("Record already exists");
                statement.close();
                connect.close();
                return true;
            }


            /*This code allows one child with more than 2 parent. So in a way, it allows biological and social relations as well*/
            /*Biological = First Farther, First Mother*/
            /*Social = Step Farther, Step Mother*/

            String insertChildRecord = "insert into parentchild_relation values (null,'" + parent.getId() + "','" + child.getId() + "')";
            statement.executeUpdate(insertChildRecord);
            statement.close();
            connect.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Failed to record child relation. Please try again.");
            return false;
        }
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
                System.out.println("Partner 1 does not exists.");
                statement.close();
                connect.close();
                return false;
            }

            resultSet2 = statement.executeQuery("select * from person where p_id='" + partner2.getId() + "'");

            /*checking if partner2 exists in person table*/
            if (resultSet2.next() == false) {
                System.out.println("Partner 2 does not exists.");
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
            System.out.println("Unable to record. Please try again.");
            return false;
        }
    }

    Boolean recordDissolution(PersonIdentity partner1, PersonIdentity partner2) {
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
                System.out.println("Partner 1 does not exists.");
                statement.close();
                connect.close();
                return false;
            }

            resultSet2 = statement.executeQuery("select * from person where p_id='" + partner2.getId() + "'");

            /*checking if partner2 exists in person table*/
            if (resultSet2.next() == false) {
                System.out.println("Partner 2 does not exists.");
                statement.close();
                connect.close();
                return false;
            }

            Boolean relationExists = false;

            String checkrelation = "select * from partner_relation where partner1Id='"+partner1.getId()+"' and partner2Id='"+partner2.getId()+"';";

            resultSet1 = null;
            resultSet1 = statement.executeQuery(checkrelation);
            if (resultSet1.next() == true)
            {
                relationExists = true;
            }

            resultSet1 = null;
            checkrelation = "select * from partner_relation where partner1Id='"+partner2.getId()+"' and partner2Id='"+partner1.getId()+"';";
            resultSet1 = statement.executeQuery(checkrelation);
            if (resultSet1.next() ==true)
            {
                relationExists = true;
            }

            if (relationExists == false)
            {
                System.out.println("Partner 1 and Partner 2 does not have partnering history. Dissolution is not possible without partnering history.");
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
            System.out.println("Unable to record. Please try again.");
            return false;
        }
    }

}
