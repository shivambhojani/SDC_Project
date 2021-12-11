import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            statement.executeQuery("use " + conn.databaseName);
            String selectQuery = "select * from person where name='" + name + "';";
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
    FileIdentifier findMediaFile(String location) {
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        FileIdentifier f = new FileIdentifier();
        createConnection conn = new createConnection();
        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);
            String selectQuery = "select * from media_archieve where location='" + location + "';";
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


    Set<FileIdentifier> findMediaByTag(String tag, String startDate, String endDate) {
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        createConnection conn = new createConnection();

        /*Validating tag, startDate and endDate for null for null or empty value*/
        if (tag == null) {
            return null;
        } else if (tag.trim().length() == 0) {
            return null;
        }
        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            String findmediabytags = "";

            if (startDate == null || endDate == null) {
                findmediabytags = "select * from media_archieve where mediaId in (select mediaId from media_tags where tag = '" + tag + "');";
            } else if (startDate.trim().length() != 0 || endDate.trim().length() != 0) {
                findmediabytags = "select * from media_archieve where date between '" + startDate + "' and '" + endDate + "' " +
                        "and mediaId in (select mediaId from media_tags where tag='" + tag + "');";
            }

            resultSet = statement.executeQuery(findmediabytags);

            List<String> mediaId = new ArrayList<>();
            while (resultSet.next()) {
                mediaId.add(resultSet.getString("mediaId"));
            }

            Set<FileIdentifier> mediaFileset = new HashSet<>();
            for (int i = 0; i < mediaId.size(); i++) {
                FileIdentifier f = new FileIdentifier();
                resultSet = statement.executeQuery("select * from media_archieve where mediaId='" + mediaId.get(i) + "';");
                while (resultSet.next()) {
                    f.setMediaId(resultSet.getString("mediaId"));
                    f.setLocation(resultSet.getString("location"));
                    f.setFileName(resultSet.getString("filename"));
                    f.setDate(resultSet.getString("date"));
                }
                mediaFileset.add(f);
            }
            statement.close();
            connect.close();
            return mediaFileset;
        } catch (
                SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    List<FileIdentifier> findIndividualsMedia(Set<PersonIdentity> people, String startDate, String
            endDate) {

        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        createConnection conn = new createConnection();

        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            /*extracting personId and putting it in seperate array*/
            String s = "";
            for (PersonIdentity p : people) {
                s = s + "'" + p.getId() + "',";
            }

            /*Validating if there are not media files for the found person/s*/
            if(s.length()>1) {
                s = s.substring(0, s.length() - 1);
            }
            else if (s.length()==0)
            {
                statement.close();
                connect.close();
                return null;
            }



            String findMediaIdbyPeople = "select mediaId from person_in_media where person in (" + s + ") group by mediaId;";
            //System.out.println(findMediaIdbyPeople);

            /*getting all mediaIds*/
            List<String> finalMediaIds = new ArrayList<>();
            resultSet = statement.executeQuery(findMediaIdbyPeople);

            while (resultSet.next()) {
                //System.out.println("MediaId = " + resultSet.getString("mediaId"));
                finalMediaIds.add(resultSet.getString("mediaId"));
            }

            /*Validating Start date and end Date*/
            Boolean datesAreFine = true;
            if (startDate == null || endDate == null) {
                datesAreFine = false;
            } else if (startDate.trim().length() == 0 || endDate.trim().length() == 0) {
                datesAreFine = false;
            }

            List<FileIdentifier> mediaFilesObject = new ArrayList<>();
            String findMediaDetails = "";

            String ss = "";
            for (String finalId : finalMediaIds) {
                ss = ss + "'" + finalId + "',";
            }
            ss = ss.substring(0, ss.length() - 1);

            if (datesAreFine) {
                findMediaDetails = "select * from media_archieve where mediaId in (" + ss + ") " +
                        "and date between '" + startDate + "' and '" + endDate + "' order by filename asc;";
                System.out.println(findMediaDetails);

            } else {
                findMediaDetails = "select * from media_archieve where mediaId in (" + ss + ") order by filename asc;";
                System.out.println(findMediaDetails);
            }

            /*First adding the data which is found from above queries*/
            resultSet = statement.executeQuery(findMediaDetails);
            while (resultSet.next()) {
                FileIdentifier f = new FileIdentifier();
                f.setMediaId(resultSet.getString("mediaId"));
                f.setLocation(resultSet.getString("location"));
                f.setFileName(resultSet.getString("filename"));
                f.setDate(resultSet.getString("date"));
                mediaFilesObject.add(f);
            }

            /*Suppose if both the dates are valid and datesAreFine = true. So now it will add the filenames with null in the list as well*/

            if (datesAreFine) {
                resultSet = statement.executeQuery("select * from media_archieve where date IS NULL order by filename asc;");
                while (resultSet.next()) {
                    FileIdentifier f = new FileIdentifier();
                    f.setMediaId(resultSet.getString("mediaId"));
                    f.setLocation(resultSet.getString("location"));
                    f.setFileName(resultSet.getString("filename"));
                    f.setDate(resultSet.getString("date"));
                    mediaFilesObject.add(f);
                }
            }

            statement.close();
            connect.close();
            return mediaFilesObject;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


    List<String> notesAndReferences(PersonIdentity person) {
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PersonIdentity p = new PersonIdentity();
        createConnection conn = new createConnection();
        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);
            resultSet = statement.executeQuery("select * from person where p_id='" + person.getId() + "';");

            if (resultSet.next() == false) {
                return null;
            }

            List<String> notesandRef = new ArrayList<>();

            /*Fetch Notes*/
            String fetchNotes = "SELECT * FROM person_notes where p_id = '" + person.getId() + "' order by noteid asc";
            resultSet = statement.executeQuery(fetchNotes);
            while (resultSet.next()) {
                notesandRef.add(resultSet.getString("notes"));
            }

            /*Fetch References*/
            String fetchRef = "select * from person_references where p_id = '" + person.getId() + "' order by referenceid asc;";
            resultSet = null;
            resultSet = statement.executeQuery(fetchRef);
            while (resultSet.next()) {
                notesandRef.add(resultSet.getString("references"));
            }
            return notesandRef;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    List<FileIdentifier> findBiologicalFamilyMedia(PersonIdentity person) {
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        FileIdentifier f = new FileIdentifier();
        createConnection conn = new createConnection();
        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);
            resultSet = statement.executeQuery("select * from person where p_id = '" + person.getId() + "';");
            if (resultSet.next() == false) {
                System.out.println("Person not found in databse");
                return null;
            }

            /*finding child of the given person*/

            String findChilderen = "select * from parentchild_relation where parentid = '" + person.getId() + "';";

            resultSet = statement.executeQuery(findChilderen);

            Set<PersonIdentity> personObject = new HashSet<>();

            while (resultSet.next()) {

                PersonIdentity pr = new PersonIdentity();
                //System.out.println("Child = " +resultSet.getString("childid") );
                pr.setId(resultSet.getString("childid"));
                personObject.add(pr);
            }
            List<FileIdentifier> fileList = findIndividualsMedia(personObject, null, null);

            statement.close();
            connect.close();
            return fileList;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
