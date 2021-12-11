import java.sql.*;
import java.util.*;

public class PersonIdentity {
    private String Id;
    private String personName;
    private String dob;
    private String bLocation;
    private String dod;
    private String dLocation;
    private String gender;
    private String occupation;
    private List<String> reference = new ArrayList<>();
    private List<String> notes = new ArrayList<>();

    PersonIdentity addPerson(String name) {

        if(name==null)
        {
            return null;
        }
        else if (name.trim().length()==0)
        {
            return null;
        }
        System.out.println(name);
        createConnection conn = new createConnection();

        ResultSet resultSet = null;
        try {
            Connection connect = conn.startConnection();
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            String insertQuery = "insert into person value (null, '" + name + "', null, null, null, null, null, null);";
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
            System.out.println("e");
            return null;
        }
    }

    Boolean recordAttributes(PersonIdentity person, Map<String, String> attributes) {
        createConnection conn = new createConnection();
        Connection connect = conn.startConnection();
        ResultSet resultSet = null;
        try {
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);
            String dob;
            String bLocation;
            String dod;
            String dLocation;
            String gender;
            String occupation;

            //conderdeing that map key names will are dob, dLocation, dod, dLocation, gender, occupation

            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (Objects.equals(key, "dob")) {
                    if (value != null) {
                        dob = value;
                        String updateQuery = "update person set dob='" + dob + "' where p_id='" + person.getId() + "';";
                        try {
                            statement.executeUpdate(updateQuery);
                        } catch (Exception e) {
                            System.out.println("Date of Birth format is wrong");
                        }
                    }
                } else if (Objects.equals(key, "bLocation")) {
                    if (value != null) {
                        bLocation = value;
                        String updateQuery = "update person set bLocation='" + bLocation + "' where p_id='" + person.getId() + "';";
                        try {
                            statement.executeUpdate(updateQuery);
                        } catch (Exception e) {
                            System.out.println("Date of Birth format is wrong");
                        }
                    }
                } else if (Objects.equals(key, "dod")) {
                    if (value != null) {
                        dod = value;
                        String updateQuery = "update person set dod='" + dod + "' where p_id='" + person.getId() + "';";
                        try {
                            statement.executeUpdate(updateQuery);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (Objects.equals(key, "dLocation")) {
                    if (value != null) {
                        dLocation = value;
                        String updateQuery = "update person set dLocation='" + dLocation + "' where p_id='" + person.getId() + "';";
                        try {
                            statement.executeUpdate(updateQuery);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (Objects.equals(key, "gender")) {
                    if (value != null) {
                        gender = value;
                        String updateQuery = "update person set gender='" + gender + "' where p_id='" + person.getId() + "';";
                        try {
                            statement.executeUpdate(updateQuery);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (Objects.equals(key, "occupation")) {
                    if (value != null) {
                        occupation = value;
                        String updateQuery = "update person set occupation='" + occupation + "' where p_id='" + person.getId() + "';";
                        try {
                            statement.executeUpdate(updateQuery);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            statement.close();
            connect.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

    }

    Boolean recordReference(PersonIdentity person, String reference) {
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
                return false;
            } else {

                String insertReference = "insert into person_references values (null,'" + person.getId() + "','" + reference + "')";
                statement.executeUpdate(insertReference);

                statement.close();
                resultSet.close();
                connect.close();
                return true;

            }

        } catch (SQLException e) {
            System.out.println(e);
            return false;

        }
    }

    Boolean recordNote(PersonIdentity person, String note) {
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
                return false;
            } else {

                String insertReference = "insert into person_notes values (null,'" + person.getId() + "','" + note + "')";
                statement.executeUpdate(insertReference);

                statement.close();
                resultSet.close();
                connect.close();
                return true;

            }

        } catch (SQLException e) {
            System.out.println(e);
            return false;

        }
    }



    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getbLocation() {
        return bLocation;
    }

    public void setbLocation(String bLocation) {
        this.bLocation = bLocation;
    }

    public String getDod() {
        return dod;
    }

    public void setDod(String dod) {
        this.dod = dod;
    }

    public String getdLocation() {
        return dLocation;
    }

    public void setdLocation(String dLocation) {
        this.dLocation = dLocation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public List<String> getReference() {
        return reference;
    }

    public void setReference(List<String> reference) {
        this.reference = reference;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }


}
