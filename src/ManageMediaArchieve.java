import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManageMediaArchieve {

    /*this method creates new record in media archieve. It will enter filename as given by method  */
    FileIdentifier addMediaFile(String fileLocationwithName) {

        if (fileLocationwithName == null) {
            System.out.println("Given filename is null");
            return null;
        } else if (fileLocationwithName.trim().length() == 0) {
            System.out.println("Given filename is empty");
            return null;
        }

        createConnection conn = new createConnection();
        Connection connect = conn.startConnection();
        ResultSet resultSet = null;
        try {
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            /*Here it is assumed that filename will be given with full path.*/
            /*Checking of the file with same name exists at the given path. */
            resultSet = statement.executeQuery("select * from media_archieve where filename='" + fileLocationwithName + "';");

            if (resultSet.next() == true) {

                /*if file exists duplicate file cannot be added. It will return null with user facing message*/
                System.out.println("File with similar name exists at this location");
                statement.close();
                connect.close();
                return null;
            }

            /*If file does not exists at given location with this name then we will enter the data in media_archieve.*/

            String insertQuery = "insert into media_archieve value (null, '" + fileLocationwithName + "', null);";

            /*Inserting medai record with the given filepath with filename*/
            /*Example: C:/Users/Shivam/test.png*/

            statement.executeUpdate(insertQuery);
            FileIdentifier f = new FileIdentifier();
            f.setFileName(fileLocationwithName);

            /*Select query to fetch mediaID of recently added media*/
            String selectQuery = "select * from media_archieve order by mediaId desc limit 1";
            resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                f.setMediaId(resultSet.getString("mediaId"));
            }
            statement.close();
            connect.close();

            /*returning the file object which has filelocationWithName and a unique ID from db*/
            return f;

        } catch (SQLException e) {
            System.out.println("Unable to add media record. Please try again.");
            return null;
        }

    }

    /*this methods records attributes like location and date for a particular media file*/
    /*this method validates date when an attribute name is "date".*/
    /*this method supports dynamic entry of different types of attributes and stores it in media_attributes table*/
    Boolean recordMediaAttributes(FileIdentifier fileIdentifier, Map<String, String> attributes) {
        if (fileIdentifier == null) {
            System.out.println("Provided file object is null");
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
        ResultSet resultSet = null;

        try {
            Connection connect = conn.startConnection();
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            /*check whether the media file exists in media_archive or not*/
            resultSet = statement.executeQuery("select * from media_archieve where mediaId = '" + fileIdentifier.getMediaId() + "';");
            if (resultSet.next() == false) {
                System.out.println("Given media file does not exists in database");
                statement.close();
                connect.close();
                return false;
            }
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
//
                /*this for loop will check for each expected attributes and update it in database*/
                String key = entry.getKey();
                String value = entry.getValue();

                if (key != null && value != null) {
                    if (key.trim().length() != 0 && value.trim().length() != 0) {
                        if (key.equals("date")) {
                            dateFormat df = new dateFormat();
                            value = df.checkDateFormat(value);
                            if (value != null) {
                                statement.executeUpdate("update media_archieve set date = '" + value + "' where mediaId = '" + fileIdentifier.getMediaId() + "';");
                            }
                        } else {
                            statement.executeUpdate("insert into media_attributes values (null, '" + fileIdentifier.getMediaId() + "'," +
                                    " '" + key + "', '" + value + "');");
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to add attributes. Please try again");
            return false;
        }

    }


    /*this method will add tags to the given media object*/
    /*the data will be recorded to media_tags*/
    Boolean tagMedia(FileIdentifier fileIdentifier, String tag) {
        if (fileIdentifier == null) {
            System.out.println("Provided file object is null");
            return false;
        }

        if (tag == null) {
            System.out.println("Given tag is null");
            return false;
        } else if (tag.trim().length() == 0) {
            System.out.println("Given tag is empty");
            return false;
        }
        createConnection conn = new createConnection();
        ResultSet resultSet = null;

        try {
            Connection connect = conn.startConnection();
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            /*check whether the media file exists in media_archive or not*/
            resultSet = statement.executeQuery("select * from media_archieve where mediaId = '" + fileIdentifier.getMediaId() + "';");

            if (resultSet.next() == false) {
                /*if given media does not exsists in media_archieve then it will return false*/
                System.out.println("Given media file does not exists in records");
                statement.close();
                connect.close();
                return false;
            }

            String insertTags = "insert into media_tags values (null,'" + fileIdentifier.getMediaId() + "','" + tag + "')";

            /*Inserting the data of media and tags in media_tags table*/
            statement.executeUpdate(insertTags);

            statement.close();
            connect.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Unable to record tags. Please try again");
            return false;
        }
    }


    /*this method will validate the media ID and all the person in media, before entering that into table*/
    /*this method will also validation the existing record in people in media and eliminate duplicate records */
    Boolean peopleInMedia(FileIdentifier fileIdentifier, List<PersonIdentity> people) {
        if (fileIdentifier == null) {
            System.out.println("Provided file object is null");
            return false;
        }

        if (people == null) {
            System.out.println("Provided person list is null");
            return false;
        }

        if (people.size() == 0) {
            System.out.println("Provided person list is empty");
            return false;
        }

        createConnection conn = new createConnection();
        ResultSet resultSet = null;

        try {
            Connection connect = conn.startConnection();
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            /*check whether the media file exists in media_archive or not*/
            resultSet = statement.executeQuery("select * from media_archieve where mediaId = '" + fileIdentifier.getMediaId() + "';");

            if (resultSet.next() == false) {
                /*if no records found that means, given media file does not exists in our databse. Return false */
                System.out.println("Given media file does not exists in records");
                statement.close();
                connect.close();
                return false;
            }

            /*check of list of people mentioned are known to database*/
            List<PersonIdentity> newpeople = new ArrayList<>();

            /*this loop will filter the people who are not know to database*/
            /*the list newpeople will have the final list of people who will go ahead*/
            for (int i = 0; i < people.size(); i++) {
                resultSet = null;
                resultSet = statement.executeQuery("select * from person where p_id='" + people.get(i).getId() + "';");
                if (resultSet.next() == true) {
                    newpeople.add(people.get(i));
                }
            }

            /*checks of the new list of poeple is empty or not*/
            /*if no people in the list, that means none of the given people are in the database*/
            if (newpeople.size() == 0) {
                System.out.println("None of the given person exists. Please check the input again");
                statement.close();
                connect.close();
                return false;

            }

            /*this for loop will add record in person_in_media table one by one for each person to media relation*/
            /*before adding it will validate that if the particular record already exists*/
            String insertpeopleinmedia = "";
            for (int i = 0; i < newpeople.size(); i++) {

                /*check if that entry already exists and validate for duplication of data*/
                resultSet = null;
                resultSet = statement.executeQuery("select * from person_in_media where mediaId='" + fileIdentifier.getMediaId() + "' " +
                        "and person='" + newpeople.get(i).getId() + "';");

                /*if there is no record for media and people, then it will add the new record here.*/
                if (resultSet.next() == false) {
                    insertpeopleinmedia = "insert into person_in_media values('" + fileIdentifier.getMediaId() + "','" + newpeople.get(i).getId() + "');";
                    statement.executeUpdate(insertpeopleinmedia);
                }
            }
            statement.close();
            connect.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Unable to add record. Please try again");
            return false;
        }
    }
}
