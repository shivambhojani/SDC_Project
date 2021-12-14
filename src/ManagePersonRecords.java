import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Objects;

public class ManagePersonRecords {

    PersonIdentity addPerson(String name) {

        if (name == null) {
            System.out.println("Given name is null");
            return null;
        } else if (name.trim().length() == 0) {
            System.out.println("Given name is empty");
            return null;
        }
        createConnection conn = new createConnection();

        ResultSet resultSet = null;
        try {
            Connection connect = conn.startConnection();
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            String insertQuery = "insert into person value (null, '" + name + "');";
            statement.executeUpdate(insertQuery);

            String selectQuery = "select * from person order by p_id desc limit 1";
            PersonIdentity pi2 = new PersonIdentity();
            pi2.setPersonName(name);

            resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                pi2.setId(resultSet.getString("p_id"));
            }
            statement.close();
            resultSet.close();
            connect.close();
            return pi2;

        } catch (SQLException e) {
            System.out.println("Failed to add person. Please try again");
            return null;
        }
    }

    Boolean recordAttributes(PersonIdentity person, Map<String, String> attributes) {
        if (person == null) {
            System.out.println("Provided person object is null");
            return false;
        }

        if (attributes == null) {
            System.out.println("Provided attributes map is null");
            return false;
        } else if (attributes.size() == 0) {
            System.out.println("Provided attributes map is empty");
            return false;
        }

        createConnection conn = new createConnection();
        Connection connect = conn.startConnection();
        ResultSet resultSet = null;
        try {
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            /*Checking if the given person is there in database*/
            resultSet = statement.executeQuery("select * from person where p_id = '" + person.getId() + "';");

            if (resultSet.next() == false) {
                statement.close();
                connect.close();
                System.out.println("Person not found");
                return false;
            }

            //Considering that map key names will are dob, dLocation, dod, dLocation, gender, occupation

            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (key != null && value != null) {
                    if (key.trim().length() != 0 && value.trim().length() != 0) {
                        if (key.equals("birthDate") || key.equals("deathDate")) {
                            dateFormat df = new dateFormat();
                            value = df.checkDateFormat(value);
                            if (value != null) {
                                statement.executeUpdate("insert into person_attributes values (null, '" + person.getId() + "'," +
                                        " '" + key + "', '" + value + "');");
                            }
                        } else {
                            statement.executeUpdate("insert into person_attributes values (null, '" + person.getId() + "'," +
                                    " '" + key + "', '" + value + "');");
                        }
                    }
                }
            }
            return true;
        } catch (SQLException e) {

            return false;
        }
    }

    Boolean recordReference(PersonIdentity person, String reference) {
        if (person == null) {
            System.out.println("Provided person object is null");
            return false;
        }
        if (reference == null) {
            System.out.println("Given reference is null");
            return false;
        } else if (reference.trim().length() == 0) {
            System.out.println("Given reference is empty");
            return false;
        }

        createConnection conn = new createConnection();
        ResultSet resultSet = null;

        try {
            Connection connect = conn.startConnection();
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            resultSet = statement.executeQuery("select * from person where p_id = '" + person.getId() + "'");

            if (resultSet.next() == false) {
                statement.close();
                resultSet.close();
                connect.close();
                System.out.println("Person not found");
                return false;
            }
            resultSet = null;

            resultSet = statement.executeQuery("select * from person_references where p_id = '" + person.getId() + "' " +
                    "and personReferences = '" + reference + "';");

            if (resultSet.next() == false) {
                String insertReference = "insert into person_references values (null,'" + person.getId() + "','" + reference + "')";
                statement.executeUpdate(insertReference);

                statement.close();
                resultSet.close();
                connect.close();
                return true;
            } else {
                System.out.println("Record already exists");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Failed to record reference. Please try again.");
            return false;

        }
    }

    Boolean recordNote(PersonIdentity person, String note) {
        if (person == null) {
            System.out.println("Provided person object is null");
            return false;
        }
        if (note == null) {
            System.out.println("Given note is null");
            return false;
        } else if (note.trim().length() == 0) {
            System.out.println("Given note is empty");
            return false;
        }
        createConnection conn = new createConnection();

        ResultSet resultSet = null;
        try {
            Connection connect = conn.startConnection();
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            resultSet = statement.executeQuery("select * from person where p_id = '" + person.getId() + "'");

            if (resultSet.next() == false) {
                statement.close();
                resultSet.close();
                connect.close();
                System.out.println("");
                return false;
            }

            resultSet = null;

            resultSet = statement.executeQuery("select * from person_notes where p_id = '" + person.getId() + "' and notes = '" + note + "';");

            if (resultSet.next() == false) {

                String insertReference = "insert into person_notes values (null,'" + person.getId() + "','" + note + "')";
                statement.executeUpdate(insertReference);
                statement.close();
                resultSet.close();
                connect.close();
                return true;
            }
            else {
                System.out.println("Record already exists");
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e);
            return false;

        }
    }

    /*this method will record child - aprent relation in parentchild_relation table */
    Boolean recordChild(PersonIdentity parent, PersonIdentity child) {

        /*checks if parent object is null*/
        if (parent == null) {
            System.out.println("Provided parent object is null");
            return false;
        }

        /*checks if child object is null*/
        if (child == null) {
            System.out.println("Provided child object is null");
            return false;
        }


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
            statement.executeQuery("use " + conn.databaseName);

            /*checking if parent exists in person table*/
            resultSet1 = statement.executeQuery("select * from person where p_id='" + parent.getId() + "'");
            if (resultSet1.next() == false) {
                statement.close();
                connect.close();
                System.out.println("Person mentioned as parent, does not exist");
                return false;
            }

            /*Checks if child exists in person table*/
            resultSet2 = statement.executeQuery("select * from person where p_id='" + child.getId() + "'");
            if (resultSet2.next() == false) {
                statement.close();
                connect.close();
                System.out.println("Person mentioned as child, does not exist");
                return false;
            }

            /*checks if a record already exists where child is marked as parent and parent is marked as child*/
            resultSet = statement.executeQuery("select * from parentchild_relation where parentid='" + child.getId() + "' " +
                    "and childid='" + parent.getId() + "';");

            if (resultSet.next()) {
                System.out.println("Unable to record relation. A record exists where " + child.getPersonName() + " is parent of " + parent.getPersonName());
                return false;
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

        if (partner1 == null) {
            System.out.println("Provided parent object is null");
            return false;
        }

        if (partner2 == null) {
            System.out.println("Provided child object is null");
            return false;
        }


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

        if (partner1 == null) {
            System.out.println("Provided parent object is null");
            return false;
        }

        if (partner2 == null) {
            System.out.println("Provided child object is null");
            return false;
        }


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

            String checkrelation = "select * from partner_relation where partner1Id='" + partner1.getId() + "' and partner2Id='" + partner2.getId() + "';";

            resultSet1 = null;
            resultSet1 = statement.executeQuery(checkrelation);
            if (resultSet1.next() == true) {
                relationExists = true;
            }

            resultSet1 = null;
            checkrelation = "select * from partner_relation where partner1Id='" + partner2.getId() + "' and partner2Id='" + partner1.getId() + "';";
            resultSet1 = statement.executeQuery(checkrelation);
            if (resultSet1.next() == true) {
                relationExists = true;
            }

            if (relationExists == false) {
                System.out.println("Partner 1 and Partner 2 does not have partnering history. Dissolution is not possible without partnering history.");
                statement.close();
                connect.close();
                return false;
            }

            String insertdissolution = "insert into person_dissolution values (null,'" + partner1.getId() + "','" + partner2.getId() + "');";
            System.out.println(insertdissolution);
            statement.executeUpdate(insertdissolution);
            statement.close();
            connect.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Unable to record. Please try again.");
            return false;
        }
    }


}
