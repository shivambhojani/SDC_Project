import javax.swing.*;
import java.sql.*;
import java.util.*;

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

    List<FileIdentifier> findIndividualsMedia(Set<PersonIdentity> people, String startDate, String endDate) {

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
            if (s.length() > 1) {
                s = s.substring(0, s.length() - 1);
            } else if (s.length() == 0) {
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
            if (ss.length() > 1) {
                ss = ss.substring(0, ss.length() - 1);
            } else if (ss.length() == 0) {
                statement.close();
                connect.close();
                return null;
            }


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

    Set<PersonIdentity> descendents(PersonIdentity person, Integer generations) {
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
                    "select * from descendants where lvl< '" + generations + 1 + "' order by parentid, lvl, descendant;";

            resultSet = statement.executeQuery(finddescendents);

            /*Putting all person id in one arraylist*/
            List<String> finalDecendantsId = new ArrayList<>();

            while (resultSet.next()) {
                finalDecendantsId.add(resultSet.getString("descendant"));
            }

            String s = "";
            for (String p : finalDecendantsId) {
                s = s + "'" + p + "',";
            }
            if (s.length() > 1) {
                s = s.substring(0, s.length() - 1);
            } else {
                statement.close();
                connect.close();
                return null;
            }

            String fetchDecedantsData = "select * from person where p_id in (" + s + ");";
            System.out.println(fetchDecedantsData);

            resultSet = statement.executeQuery(fetchDecedantsData);

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

    Set<PersonIdentity> ancestores(PersonIdentity person, Integer generations) {

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
                    "\tselect * from ancestores where lvl < '" + generations + 1 + "' order by childid, lvl, ancestor;";

            resultSet = statement.executeQuery(findancestores);

            /*Putting all person id in one arraylist*/
            List<String> finalancestoresId = new ArrayList<>();

            while (resultSet.next()) {
                finalancestoresId.add(resultSet.getString("ancestor"));
            }

            String s = "";
            for (String p : finalancestoresId) {
                s = s + "'" + p + "',";
            }
            if (s.length() > 1) {
                s = s.substring(0, s.length() - 1);
            } else {
                statement.close();
                connect.close();
                return null;
            }

            String fetchancestoresData = "select * from person where p_id in (" + s + ");";
            System.out.println(fetchancestoresData);

            resultSet = statement.executeQuery(fetchancestoresData);

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
            e.printStackTrace();
            return null;
        }
    }

    BiologicalRelation findRelation(PersonIdentity person1, PersonIdentity person2) {
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PersonIdentity p = new PersonIdentity();
        createConnection conn = new createConnection();
        try {
            connect = conn.startConnection();
            statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            /*Checking if persons are valid or not*/
            String selectQuery1 = "select * from person where p_id = '" + person1.getId() + "';";
            resultSet = statement.executeQuery(selectQuery1);
            if (resultSet.next() == false) {
                statement.close();
            }

            resultSet = null;
            selectQuery1 = "select * from person where p_id = '" + person2.getId() + "';";
            resultSet = statement.executeQuery(selectQuery1);
            if (resultSet.next() == false) {
                statement.close();
            }

            String findAllAncestors1 = "with RECURSIVE ancestores (childid, ancestor, lvl) as \n" +
                    "( select childid, parentid, 1 from parentchild_relation where childid='" + person1.getId() + "'\n" +
                    "union all\n" +
                    "select d.childid, s.parentid, d.lvl + 1\n" +
                    "from ancestores d join parentchild_relation s on d.ancestor = s.childid) \n" +
                    "select * from ancestores order by lvl asc;";

            String findAllAncestors2 = "with RECURSIVE ancestores (childid, ancestor, lvl) as \n" +
                    "( select childid, parentid, 1 from parentchild_relation where childid='" + person2.getId() + "'\n" +
                    "union all\n" +
                    "select d.childid, s.parentid, d.lvl + 1\n" +
                    "from ancestores d join parentchild_relation s on d.ancestor = s.childid) \n" +
                    "select * from ancestores order by lvl asc;";


            resultSet = statement.executeQuery(findAllAncestors1);
            List<String> person1AncestorsId = new ArrayList<>();
            Map<String, String> person1AncestorsLevel = new HashMap<>();

            /*adding self as ancestor at level 0*/
            person1AncestorsId.add(person1.getId());
            person1AncestorsLevel.put(person1.getId(), "0");

            while (resultSet.next()) {
                person1AncestorsId.add(resultSet.getString("ancestor"));
                person1AncestorsLevel.put(resultSet.getString("ancestor"), resultSet.getString("lvl"));
            }

            System.out.println(person1AncestorsId);

            resultSet = null;
            resultSet = statement.executeQuery(findAllAncestors2);
            List<String> person2AncestorsId = new ArrayList<>();
            Map<String, String> person2AncestorsLevel = new HashMap<>();

            /*adding self as ancestor at level 0*/
            person2AncestorsId.add(person2.getId());
            person2AncestorsLevel.put(person2.getId(), "0");

            while (resultSet.next()) {
                person2AncestorsId.add(resultSet.getString("ancestor"));
                person2AncestorsLevel.put(resultSet.getString("ancestor"), resultSet.getString("lvl"));
            }

            System.out.println(person2AncestorsId);

            List<String> commonAncestorsId = new ArrayList<>();
//            for (String s : person1AncestorsId) {
//                commonAncestorsId.add(s);
//            }
            boolean commonAncestors = commonAncestorsId.retainAll(person2AncestorsId);

            if (!person1AncestorsId.isEmpty() || !person2AncestorsId.isEmpty())
            {
                for (String s : person1AncestorsId){
                    if(person2AncestorsId.contains(s))
                    {
                        commonAncestorsId.add(s);
                    }
                }
            }

            String referencesAncestor = "";
            int person1Level = 0;
            int person2Level = 0;

            if (!commonAncestorsId.isEmpty()) {

                referencesAncestor = commonAncestorsId.get(0);
                person1Level = Integer.parseInt(person1AncestorsLevel.get(referencesAncestor));
                person2Level = Integer.parseInt(person2AncestorsLevel.get(referencesAncestor));

                System.out.println("Counsinship = " + (Math.min(person1Level,person2Level)- 1));
            }
            else if (commonAncestorsId.isEmpty())
            {
                /*if there are no ancestors of person1 but person2 has*/
                if (person1AncestorsId.isEmpty() && !person2AncestorsId.isEmpty())
                {
                    /*checking if person 1 is one of the ancestor of person 2*/
                    if(person2AncestorsId.contains(person1.getId()))
                    {
                        System.out.println("Counsinship = " + -1);
                    }
                }
                else if (!person1AncestorsId.isEmpty() && person2AncestorsId.isEmpty())
                {
                    /*checking if person 2 is one of the ancestor of person 1*/
                    if(person2AncestorsId.contains(person1.getId()))
                    {
                        System.out.println("Counsinship = " + -1);
                    }
                }
            }



        } catch (SQLException e) {

            e.printStackTrace();
        }

        return new BiologicalRelation();
    }
}
