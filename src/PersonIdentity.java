import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        System.out.println(name);
        createConnection conn = new createConnection();
        Connection connect = conn.startConnection();
        ResultSet resultSet = null;
        try {
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);
            String insertQuery = "insert into person value (null, '" + name + "', null, null, null, null, null, null);";
            System.out.println(insertQuery);
            statement.executeUpdate(insertQuery);

            String selectQuery = "select * from person where name='" + name + "'";
            PersonIdentity pi2 = new PersonIdentity();
            pi2.setPersonName(name);

            resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                pi2.setId(resultSet.getString("p_id"));
            }
            connect.close();
            return pi2;

        } catch (SQLException e) {
            System.out.println("Error Occured");
            return null;
        }
    }

    Boolean recordAttributes(PersonIdentity person, Map<String, String> attributes) {
        createConnection conn = new createConnection();
        Connection connect = conn.startConnection();
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
                        statement.executeUpdate(updateQuery);
                    }
                } else if (Objects.equals(key, "bLocation")) {
                    if (value != null) {
                        bLocation = value;
                        String updateQuery = "update person set bLocation='" + bLocation + "' where p_id='" + person.getId() + "';";
                        statement.executeUpdate(updateQuery);
                    }
                } else if (Objects.equals(key, "dod")) {
                    if (value != null) {
                        dod = value;
                        String updateQuery = "update person set dod='" + dod + "' where p_id='" + person.getId() + "';";
                        statement.executeUpdate(updateQuery);
                    }
                } else if (Objects.equals(key, "dLocation")) {
                    if (value != null) {
                        dLocation = value;
                        String updateQuery = "update person set dLocation='" + dLocation + "' where p_id='" + person.getId() + "';";
                        statement.executeUpdate(updateQuery);
                    }
                } else if (Objects.equals(key, "gender")) {
                    if (value != null) {
                        gender = value;
                        String updateQuery = "update person set gender='" + gender + "' where p_id='" + person.getId() + "';";
                        statement.executeUpdate(updateQuery);
                    }
                } else if (Objects.equals(key, "occupation")) {
                    if (value != null) {
                        occupation = value;
                        String updateQuery = "update person set occupation='" + occupation + "' where p_id='" + person.getId() + "';";
                        statement.executeUpdate(updateQuery);
                    }
                }
            }
            statement.close();
            connect.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return true;
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
