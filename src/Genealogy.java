import javax.swing.*;
import java.sql.*;
import java.util.*;

public class Genealogy {

    /*this method checks the person table with the given name and returns the object with all the column values relate to the given person name*/
    /*Returns null in case person is not found*/
    PersonIdentity findPerson(String name) {
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        createConnection conn = new createConnection();
        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            /*Checks for a person with the given name in person table*/
            String selectQuery = "select * from person where name='" + name + "';";
            resultSet = statement.executeQuery(selectQuery);

            /*person object in which we will be storing */
            PersonIdentity p = new PersonIdentity();

            /*If person found then it will assign each value to the object variables from person table column*/
            if (resultSet.next()) {
                p.setId(resultSet.getString("p_id"));
                p.setPersonName(resultSet.getString("name"));
                p.setDob(resultSet.getString("dob"));
                p.setbLocation(resultSet.getString("bLocation"));
                p.setDod(resultSet.getString("dod"));
                p.setdLocation(resultSet.getString("dLocation"));
                p.setOccupation(resultSet.getString("occupation"));
                statement.close();
                resultSet.close();
                connect.close();
                return p;
            } else {
                /*if no record found then it will return null with a user facing message*/
                System.out.println("Person with Name = " + name + "  not found");
                statement.close();
                connect.close();
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Unable to retrieve person date Please try again.");
            return null;
        }
    }

    /*This method fetches the person id from the given person object and checks the person table for getting the person name*/
    String findName(PersonIdentity id) {

        /*validating the object for null value*/
        if (id != null) {
            Connection connect = null;
            Statement statement = null;
            ResultSet resultSet = null;
            createConnection conn = new createConnection();
            try {
                connect = conn.startConnection();
                statement = connect.createStatement();
                statement.executeQuery("use " + conn.databaseName);

                /*Select query in person table with the given person id*/
                resultSet = statement.executeQuery("select * from person where p_id = '" + id.getId() + "';");
                if (resultSet.next()) {

                    /*if record found, then it will fetch the value from name column from person table*/
                    return resultSet.getString("name");
                } else {

                    /*In case of no records found by the given id*/
                    System.out.println("Name not found in records");
                    return null;
                }
            } catch (SQLException e) {
                System.out.println("Unable to fetch name. Please try again");
                return null;
            }
        } else {
            /*if the provided object is null, the method will return null with user facing message*/
            System.out.println("Provided person object is null");
            return null;
        }
    }

    //finding media file by location attribute
    FileIdentifier findMediaFile(String filelocationwithName) {

        if (filelocationwithName == null) {
            System.out.println("Given name is null");
            return null;
        } else if (filelocationwithName.trim().length() == 0) {
            System.out.println("Given name is empty");
            return null;
        }

        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;

        createConnection conn = new createConnection();
        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            /*Query to fetch media details from media_archieve based on the given filename with path*/
            String selectQuery = "select * from media_archieve where filename='" + filelocationwithName + "';";
            resultSet = statement.executeQuery(selectQuery);

            if (resultSet.next()) {
                /*If record found then it will store each column value in FileIdentifier object and return it*/

                FileIdentifier f = new FileIdentifier();
                f.setMediaId(resultSet.getString("mediaId"));
                f.setFileName(resultSet.getString("filename"));
                f.setLocation(resultSet.getString("location"));
                f.setLocation(resultSet.getString("date"));
                statement.close();
                resultSet.close();
                connect.close();
                return f;
            } else {
                /*if no record found then it will return null with a user facing message*/

                System.out.println("Media file " + filelocationwithName + " not found");
                statement.close();
                connect.close();
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }

    /*This method fetches the media ID from the given media object and checks the media_archieve table for getting the media filename */
    String findMediaFile(FileIdentifier fileId) {
        /*validating the object for null value*/
        if (fileId != null) {
            Connection connect = null;
            Statement statement = null;
            ResultSet resultSet = null;
            createConnection conn = new createConnection();
            try {
                connect = conn.startConnection();
                statement = connect.createStatement();
                statement.executeQuery("use " + conn.databaseName);

                /*Select query in media_archieve table with the given person id*/
                resultSet = statement.executeQuery("select * from media_archieve where mediaId = '" + fileId.getMediaId() + "';");
                if (resultSet.next()) {

                    /*if record found then it will fetch the value from column filename and return the value*/
                    return resultSet.getString("filename");
                } else {

                    /*if no record found then the method will return null with a user facing message*/
                    System.out.println("Name not found in records");
                    return null;
                }
            } catch (SQLException e) {
                System.out.println("Unable to fetch filename. Please try again");
                return null;
            }

        } else {
            System.out.println("Provided person object is null");
            return null;
        }
    }

    /*this method returns details of all media files which has the given tag value*/
    /*If dates are null then it returns all matching records without considering dates*/
    /*It returns media objects in set*/
    Set<FileIdentifier> findMediaByTag(String tag, String startDate, String endDate) {

        /*Validating tag for null or empty value*/
        if (tag == null) {
            System.out.println("Provided tag value is null");
            return null;
        } else if (tag.trim().length() == 0) {
            System.out.println("Provided tag value is empty");
            return null;
        }

        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        createConnection conn = new createConnection();
        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            String findmediabytags = "";

            /*validating dates*/
            /*Validating Start date and end Date*/
            Boolean bothDatesAreFine = true;
            if (startDate == null || endDate == null) {
                bothDatesAreFine = false;
            } else if (startDate.trim().length() == 0 || endDate.trim().length() == 0) {
                bothDatesAreFine = false;
            }

            if (bothDatesAreFine) {
                findmediabytags = "select * from media_archieve where date between '" + startDate + "' and '" + endDate + "' " +
                        "and mediaId in (select mediaId from media_tags where tag='" + tag + "');";
                System.out.println(findmediabytags);

            } else {
                findmediabytags = "select * from media_archieve where mediaId in (select mediaId from media_tags where tag = '" + tag + "');";
                System.out.println(findmediabytags);
            }

            /*List to add media Ids*/
            List<String> mediaId = new ArrayList<>();

            resultSet = statement.executeQuery(findmediabytags);

            /*Adding all the media Ids into list one by one*/
            while (resultSet.next()) {
                mediaId.add(resultSet.getString("mediaId"));
            }

            /*if no media found, then list size will be 0*/
            if (mediaId.size() == 0) {
                System.out.println("No media found based on the given tag value");
                return new HashSet<FileIdentifier>();
            }

            /*set to store media objects and return it*/
            Set<FileIdentifier> mediaFileset = new HashSet<>();
            for (int i = 0; i < mediaId.size(); i++) {
                FileIdentifier f = new FileIdentifier();
                resultSet = statement.executeQuery("select * from media_archieve where mediaId='" + mediaId.get(i) + "';");

                /*Fecthing each column value and storing it into new objects*/
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
        } catch (SQLException e) {
            System.out.println("Unable to find media files. Please try again");
            return null;
        }
    }


    /*this method returns details of all media files which has the given location value*/
    /*If dates are null then it returns all matching records without considering dates*/
    /*It returns media objects in set*/
    Set<FileIdentifier> findMediaByLocation(String location, String startDate, String endDate) {

        /*Validating Location for null or empty value*/
        if (location == null) {
            System.out.println("Provided location value is null");
            return null;
        } else if (location.trim().length() == 0) {
            System.out.println("Provided location value is empty");
            return null;
        }

        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        createConnection conn = new createConnection();

        /*Validating tag, startDate and endDate for null for null or empty value*/
        if (location == null) {
            return null;
        } else if (location.trim().length() == 0) {
            return null;
        }
        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            String findmediabyLocation = "";

            /*validating dates*/
            /*Validating Start date and end Date*/
            Boolean bothDatesAreFine = true;
            if (startDate == null || endDate == null) {
                bothDatesAreFine = false;
            } else if (startDate.trim().length() == 0 || endDate.trim().length() == 0) {
                bothDatesAreFine = false;
            }

            if (bothDatesAreFine) {
                findmediabyLocation = "select * from media_archieve where date between '" + startDate + "' and '" + endDate + "' " +
                        "and location='" + location + "';";


            } else {
                findmediabyLocation = "select * from media_archieve where location = '" + location + "';";

            }

            resultSet = statement.executeQuery(findmediabyLocation);

            List<String> mediaId = new ArrayList<>();
            while (resultSet.next()) {
                mediaId.add(resultSet.getString("mediaId"));
            }

            if (mediaId.size() == 0) {
                System.out.println("No media record found based on given location");
                return new HashSet<FileIdentifier>();
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

    /*this method returns the list of media files which includes the given set of people*/
    List<FileIdentifier> findIndividualsMedia(Set<PersonIdentity> people, String startDate, String endDate) {

        /*Validating people Set for null value*/
        if (people == null) {
            System.out.println("Provided set of people is null");
            return null;
        }
        /*if not null, then checking if its empty*/
        else if (people.size() == 0) {
            System.out.println("Provided set of people is empty");
            return null;
        }
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        createConnection conn = new createConnection();

        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            /*extracting personId and putting it in seperate string*/
            String s = "";
            for (PersonIdentity p : people) {
                s = s + "'" + p.getId() + "',";
            }

            /*Validating if there are not media files for the found person/s*/
            if (s.length() > 1) {
                s = s.substring(0, s.length() - 1);
            } else if (s.length() == 0) {
                statement.close();
                connect.close();
                return null;
            }

            /*Query to get all the media ids which are linking to the given set of people*/
            String findMediaIdbyPeople = "select mediaId from person_in_media where person in (" + s + ") group by mediaId;";

            /*getting all mediaIds*/
            List<String> finalMediaIds = new ArrayList<>();
            resultSet = statement.executeQuery(findMediaIdbyPeople);

            /*storing all mediaIds in a arrraylist*/
            while (resultSet.next()) {
                //System.out.println("MediaId = " + resultSet.getString("mediaId"));
                finalMediaIds.add(resultSet.getString("mediaId"));
            }

            if (finalMediaIds.size() == 0) {
                System.out.println("No media file found for given set of people");
                return new ArrayList<FileIdentifier>();
            }

            List<FileIdentifier> mediaFilesObject = new ArrayList<>();
            String findMediaDetails = "";

            String ss = "";
            for (String finalId : finalMediaIds) {
                ss = ss + "'" + finalId + "',";
            }
            if (ss.length() > 1) {
                ss = ss.substring(0, ss.length() - 1);
            } else if (ss.length() == 0) {
                statement.close();
                connect.close();
                return null;
            }
            /*Validating Start date and end Date*/
            Boolean bothDatesAreFine = true;
            if (startDate == null || endDate == null) {
                bothDatesAreFine = false;
            } else if (startDate.trim().length() == 0 || endDate.trim().length() == 0) {
                bothDatesAreFine = false;
            }

            if (bothDatesAreFine) {
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
            if (bothDatesAreFine) {
                System.out.println("select * from media_archieve where mediaId in (" + ss + ") where date IS NULL order by filename asc;");
                resultSet = statement.executeQuery("select * from media_archieve where mediaId in (" + ss + ") and date IS NULL order by filename asc;");

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

    /*this method gives a list containing notes and references for a given person*/
    List<String> notesAndReferences(PersonIdentity person) {

        /*validating person object for null value*/
        if (person == null) {
            System.out.println("Provided person object is null");
            return null;
        }

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

            /*validating person with the person table */
            if (resultSet.next() == false) {
                return null;
            }

            /*A list to store notes and reference in the list*/
            List<String> notesandRef = new ArrayList<>();

            /*Fetch Notes for the given person in the same order in which they were entered in DB*/
            String fetchNotes = "SELECT * FROM person_notes where p_id = '" + person.getId() + "' order by noteid asc";
            resultSet = statement.executeQuery(fetchNotes);

            /*storing notes in the list*/
            while (resultSet.next()) {
                notesandRef.add(resultSet.getString("notes"));
            }

            /*Fetch References for the given person in the same order in which they were entered in DB*/
            String fetchRef = "select * from person_references where p_id = '" + person.getId() + "' order by referenceid asc;";
            resultSet = null;
            resultSet = statement.executeQuery(fetchRef);

            /*storing references in the list*/
            while (resultSet.next()) {
                notesandRef.add(resultSet.getString("references"));
            }
            return notesandRef;
        } catch (SQLException e) {
            System.out.println("Unable to fetch notes and references. Please try again");
            return null;
        }
    }

    /*this method */
    List<FileIdentifier> findBiologicalFamilyMedia(PersonIdentity person) {

        if (person == null) {
            System.out.println("Provided person object is null");
            return null;
        }

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
                System.out.println("Person not found in database");
                return null;
            }
            String findChilderen = "select * from parentchild_relation where parentid = '" + person.getId() + "';";

            resultSet = statement.executeQuery(findChilderen);

            Set<PersonIdentity> listOfChildern = new HashSet<>();

            while (resultSet.next()) {
                PersonIdentity pr = new PersonIdentity();
                //System.out.println("Child = " +resultSet.getString("childid") );
                pr.setId(resultSet.getString("childid"));
                listOfChildern.add(pr);
            }

            if (listOfChildern.size() == 0) {
                System.out.println("No children recorded for the given person");
                return null;
            }
            List<FileIdentifier> fileList = findIndividualsMedia(listOfChildern, null, null);

            statement.close();
            connect.close();
            return fileList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /*the method returns a set of person object which are descendents of the given person till the given number of generation*/
    Set<PersonIdentity> descendents(PersonIdentity person, Integer generations) {

        /*validating person object for null value*/
        if (person == null) {
            System.out.println("Provided person object is null");
            return null;
        }

        /*validating generations for invalid value*/
        if (generations <= 0) {
            System.out.println("Number of generation is invalid");
            return null;
        }

        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        FileIdentifier f = new FileIdentifier();
        createConnection conn = new createConnection();
        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            /*validating that person mentioned in argument exists in database or not*/
            resultSet = statement.executeQuery("select * from person where p_id='" + person.getId() + "';");
            if (resultSet.next() == false) {
                System.out.println("Given person does not exists in Database");
                statement.close();
                connect.close();
                return null;
            }

            /*References:*/
            //1. https://dba.stackexchange.com/questions/94932/getting-all-descendants-of-a-parent
            //2. https://www.sqlservertutorial.net/sql-server-basics/sql-server-recursive-cte/


            String finddescendents = "with RECURSIVE descendants (parentid, descendant, lvl) as \n" +
                    "( select parentid, childid, 1 from parentchild_relation where parentid='" + person.getId() + "'\n" +
                    "union all\n" +
                    "select d.parentid, s.childid, d.lvl + 1\n" +
                    "from descendants d join parentchild_relation  s on d.descendant = s.parentid) \n" +
                    "select * from descendants where lvl < '" + (generations + 1) + "' order by lvl asc;";

            resultSet = statement.executeQuery(finddescendents);

            /*Putting all person id in one arraylist*/
            List<String> finalDecendantsId = new ArrayList<>();

            while (resultSet.next()) {
                finalDecendantsId.add(resultSet.getString("descendant"));
            }

            /*If no decendants found, then list will be empty*/
            if (finalDecendantsId.size() == 0) {
                System.out.println("No decendants found for given person");
                return new HashSet<PersonIdentity>();
            }

            String s = "";
            for (String p : finalDecendantsId) {
                s = s + "'" + p + "',";
            }

            /*creating a string like ('id1','id2','id3') so that it can be used in the query*/
            if (s.length() > 1) {
                s = s.substring(0, s.length() - 1);
            } else {
                statement.close();
                connect.close();
                return null;
            }
            /*query to find person details for all the decendants found*/
            String fetchDecedantsData = "select * from person where p_id in (" + s + ");";
            System.out.println(fetchDecedantsData);

            resultSet = statement.executeQuery(fetchDecedantsData);

            /*Set to store person objects*/
            Set<PersonIdentity> descedantsSet = new HashSet<>();

            while (resultSet.next()) {
                PersonIdentity pi = new PersonIdentity();
                pi.setId(resultSet.getString("p_id"));
                pi.setPersonName(resultSet.getString("name"));
                pi.setDob(resultSet.getString("dob"));
                pi.setbLocation(resultSet.getString("bLocation"));
                pi.setDod(resultSet.getString("dod"));
                pi.setdLocation(resultSet.getString("dLocation"));
                pi.setOccupation(resultSet.getString("occupation"));
                descedantsSet.add(pi);
            }

            statement.close();
            connect.close();
            return descedantsSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*the method returns a set of person object which are ancestors of the given person till the given number of generation*/
    Set<PersonIdentity> ancestores(PersonIdentity person, Integer generations) {

        /*validating person object for null value*/
        if (person == null) {
            System.out.println("Provided person object is null");
            return null;
        }

        /*validating generations for invalid value*/
        if (generations <= 0) {
            System.out.println("Number of generation is invalid");
            return null;
        }
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        FileIdentifier f = new FileIdentifier();
        createConnection conn = new createConnection();
        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            /*validating that person mentioned in argument exists in database or not*/
            resultSet = statement.executeQuery("select * from person where p_id='" + person.getId() + "';");
            if (resultSet.next() == false) {
                System.out.println("Given person does not exists in Database");
                statement.close();
                connect.close();
                return null;
            }

            /*References:*/
            //1. https://dba.stackexchange.com/questions/94932/getting-all-descendants-of-a-parent
            //2. https://www.sqlservertutorial.net/sql-server-basics/sql-server-recursive-cte/

            String findancestores = "with RECURSIVE ancestores (childid, ancestor, lvl) as \n" +
                    "\t\t( select childid, parentid, 1 from parentchild_relation where childid='" + person.getId() + "'\n" +
                    "\t\tunion all\n" +
                    "\t\tselect d.childid, s.parentid, d.lvl + 1\n" +
                    "\t\tfrom ancestores d join parentchild_relation s on d.ancestor = s.childid) \n" +
                    "\tselect * from ancestores where lvl < '" + (generations + 1) + "' order by lvl asc;";

            resultSet = statement.executeQuery(findancestores);

            /*Putting all person id in one arraylist*/
            List<String> finalancestoresId = new ArrayList<>();

            while (resultSet.next()) {
                finalancestoresId.add(resultSet.getString("ancestor"));
            }

            /*If no ancestors found, then list will be empty*/
            if (finalancestoresId.size() == 0) {
                System.out.println("No ancestors found for given person");
                return new HashSet<PersonIdentity>();
            }

            String s = "";
            for (String p : finalancestoresId) {
                s = s + "'" + p + "',";
            }

            /*creating a string like ('id1','id2','id3') so that it can be used in the query*/
            if (s.length() > 1) {
                s = s.substring(0, s.length() - 1);
            } else {

                statement.close();
                connect.close();
                return null;
            }

            /*query to find person details for all the ancestors found*/
            String fetchancestoresData = "select * from person where p_id in (" + s + ");";

            resultSet = statement.executeQuery(fetchancestoresData);

            /*Set to store person objects*/
            Set<PersonIdentity> ancestoresSet = new HashSet<>();

            while (resultSet.next()) {
                PersonIdentity pi = new PersonIdentity();
                pi.setId(resultSet.getString("p_id"));
                pi.setPersonName(resultSet.getString("name"));
                pi.setDob(resultSet.getString("dob"));
                pi.setbLocation(resultSet.getString("bLocation"));
                pi.setDod(resultSet.getString("dod"));
                pi.setdLocation(resultSet.getString("dLocation"));
                pi.setOccupation(resultSet.getString("occupation"));
                ancestoresSet.add(pi);
            }

            statement.close();
            connect.close();
            return ancestoresSet;
        } catch (SQLException e) {
            System.out.println("Unable to find ancestors. Please try again");
            return null;
        }
    }


    /*this method will return a BiologicalRelation object which will have CousinShip and Removal values in it, based on the relations of two person*/
    /*It will return null value of one of the given person object is null*/
    BiologicalRelation findRelation(PersonIdentity person1, PersonIdentity person2) {

        /*Validating person1 object for null value*/
        if (person1 == null) {
            System.out.println("Provided person1 object is null");
            return null;
        }
        /*Validating person2 object for null value*/
        if (person2 == null) {
            System.out.println("Provided person2 object is null");
            return null;
        }

        /*Checking if person1 and person2 are same*/
        if (Objects.equals(person1.getId(), person2.getId())) {
            System.out.println("Given person1 and person2 are same. Please try again with correct arguments.");
            return null;
        }
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PersonIdentity p = new PersonIdentity();
        createConnection conn = new createConnection();
        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            /*Finding record for a person1 in the person table.*/
            /*this validation will check if the person is known to database or not*/
            String selectQuery1 = "select * from person where p_id = '" + person1.getId() + "';";
            resultSet = statement.executeQuery(selectQuery1);
            if (resultSet.next() == false) {
                System.out.println("Person 1 is not in records");
                statement.close();
                connect.close();
                return null;
            }

            /*Finding record for a person2 in the person table.*/
            /*this validation will check if the person is known to database or not*/
            resultSet = null;
            selectQuery1 = "select * from person where p_id = '" + person2.getId() + "';";
            resultSet = statement.executeQuery(selectQuery1);
            if (resultSet.next() == false) {
                System.out.println("Person 2 is not in records");
                statement.close();
                connect.close();
                return null;
            }

            /*Query to find all the anscestors for person1*/
            String findAllAncestors1 = "with RECURSIVE ancestores (childid, ancestor, lvl) as \n" +
                    "( select childid, parentid, 1 from parentchild_relation where childid='" + person1.getId() + "'\n" +
                    "union all\n" +
                    "select d.childid, s.parentid, d.lvl + 1\n" +
                    "from ancestores d join parentchild_relation s on d.ancestor = s.childid) \n" +
                    "select * from ancestores order by lvl asc;";

            /*Query to find all the anscestors for person2*/
            String findAllAncestors2 = "with RECURSIVE ancestores (childid, ancestor, lvl) as \n" +
                    "( select childid, parentid, 1 from parentchild_relation where childid='" + person2.getId() + "'\n" +
                    "union all\n" +
                    "select d.childid, s.parentid, d.lvl + 1\n" +
                    "from ancestores d join parentchild_relation s on d.ancestor = s.childid) \n" +
                    "select * from ancestores order by lvl asc;";


            /*Fetching all ancestors of person1*/
            resultSet = statement.executeQuery(findAllAncestors1);

            // this list will help us store all anscestors of person1 from generation 1 to higher
            List<String> person1AncestorsId = new ArrayList<>();

            // this list will help us store all anscestors from generation1 to higher
            Map<String, String> person1AncestorsLevel = new HashMap<>();

            /*Adding self as one of the ancestors at level 0 . This will help us to identify if in case person1 is ancestor of person2*/
            person1AncestorsId.add(person1.getId());
            person1AncestorsLevel.put(person1.getId(), "0");

            /*Storing ancestor and their generation level in list and Map*/
            while (resultSet.next()) {
                person1AncestorsId.add(resultSet.getString("ancestor"));
                person1AncestorsLevel.put(resultSet.getString("ancestor"), resultSet.getString("lvl"));
            }

            resultSet = null;
            /*Fetching all ancestors of person1*/
            resultSet = statement.executeQuery(findAllAncestors2);

            // this list will help us store all anscestors of person2 from generation 1 to higher
            List<String> person2AncestorsId = new ArrayList<>();

            // this list will help us store all anscestors of person2 from generation1 to higher
            Map<String, String> person2AncestorsLevel = new HashMap<>();

            /*Adding self as one of the ancestors at level 0 . This will help us to identify if in case person2 is ancestor of person1*/
            person2AncestorsId.add(person2.getId());
            person2AncestorsLevel.put(person2.getId(), "0");

            /*Storing ancestor and their generation level in list and Map*/
            while (resultSet.next()) {
                person2AncestorsId.add(resultSet.getString("ancestor"));
                person2AncestorsLevel.put(resultSet.getString("ancestor"), resultSet.getString("lvl"));
            }


            /*this list will have all the common ancestors between person1 and person2.*/
            List<String> commonAncestorsId = new ArrayList<>();

            /*Comparison logic between person1 ancestor and person2 ancestor*/
            for (String s : person1AncestorsId) {
                if (person2AncestorsId.contains(s)) {
                    commonAncestorsId.add(s);
                }
            }

            /*this variables will store the ancestors and generation number at which the common ancestor are found for each person*/


            String referencesAncestor = "";                  /*for the common ancestor found */
            int person1Level = 0;                           /*to store the generation level of common ancestor as per person1*/
            int person2Level = 0;                           /*to store the generation level of common ancestor as per person2*/

            /*Calculating cousinShip and removal based on the ancestors of both person*/

            /*creating object of BiologicalRelation for returning*/
            BiologicalRelation br = new BiologicalRelation();

            /*Checking of any common ancestor found or not*/
            if (!commonAncestorsId.isEmpty()) {

                /*As the ancestors found are in ascending order of generation level, we are taking the first common ancestor found*/
                referencesAncestor = commonAncestorsId.get(0);
                person1Level = Integer.parseInt(person1AncestorsLevel.get(referencesAncestor));
                person2Level = Integer.parseInt(person2AncestorsLevel.get(referencesAncestor));

                /*Formula for CousinShip : Minimum of person1Level and person2Level -1 */
                /*Formula for Removal    : Absolute value of person1level - person2level*/

                /*Storing values in Cousinship and Removal, which are the class variables of Biologicalrelation class*/
                br.setCousinship(String.valueOf(Math.min(person1Level, person2Level) - 1));
                br.setRemoval(String.valueOf(Math.abs(person1Level - person2Level)));


            } else if (commonAncestorsId.isEmpty()) {
                System.out.println("No biological relation found between the given two person as they do not have any common ancestor");
                br.setCousinship("None");
                br.setRemoval("None");
            }
            return br;

        } catch (SQLException e) {
            System.out.println("");
            return null;
        }
    }
}
