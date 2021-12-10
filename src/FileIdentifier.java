import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FileIdentifier {

    private String mediaId;
    private String fileName;
    private String location;
    private String date;


    FileIdentifier addMediaFile(String fileLocation) {

        System.out.println("fileLocation: " + fileLocation);
        createConnection conn = new createConnection();
        Connection connect = conn.startConnection();
        ResultSet resultSet = null;
        try {
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);
            String insertQuery = "insert into media_archieve value (null, '" + fileLocation + "', null, null);";
            //System.out.println(insertQuery);
            statement.executeUpdate(insertQuery);
            FileIdentifier f = new FileIdentifier();
            f.setLocation(fileLocation);

            String selectQuery = "select * from media_archieve order by mediaId desc limit 1";
            resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                f.setMediaId(resultSet.getString("mediaId"));
            }
            statement.close();
            connect.close();
            return f;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    Boolean recordMediaAttributes(FileIdentifier fileIdentifier, Map<String, String> attributes) {
        createConnection conn = new createConnection();
        ResultSet resultSet = null;

        try {
            Connection connect = conn.startConnection();
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);
            String date;
            String filename;

            /*check whether the media file exists in media_archive or not*/
            resultSet = statement.executeQuery("select * from media_archieve where mediaId = '" + fileIdentifier.getMediaId() + "';");
            if (resultSet.next() == false) {
                statement.close();
                connect.close();
                return false;
            }

            /*Media file is there in media archive and now the attributes will be recorded from map*/
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (Objects.equals(key, "date")) {
                    if (value != null) {
                        date = value;
                        String updateQuery = "update media_archieve set date='" + date + "' where mediaId='" + fileIdentifier.getMediaId() + "';";
                        try {
                            statement.executeUpdate(updateQuery);
                        } catch (SQLException e) {
                            System.out.println("Problem while giving date input. Date format should be yyyy-mm-dd");
                        }
                    }
                } else if (Objects.equals(key, "filename")) {
                    if (value != null) {
                        filename = value;
                        String updateQuery = "update media_archieve set filename='" + filename + "' where mediaId='" + fileIdentifier.getMediaId() + "';";
                        statement.executeUpdate(updateQuery);
                    }
                }

            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    Boolean tagMedia(FileIdentifier fileIdentifier, String tag) {
        createConnection conn = new createConnection();
        ResultSet resultSet = null;

        try {
            Connection connect = conn.startConnection();
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);


            /*check whether the media file exists in media_archive or not*/
            resultSet = statement.executeQuery("select * from media_archieve where mediaId = '" + fileIdentifier.getMediaId() + "';");
            if (resultSet.next() == false) {
                statement.close();
                connect.close();
                return false;

            }

            String insertTags = "insert into media_tags values (null,'" + fileIdentifier.getMediaId() + "','" + tag + "')";
            statement.executeUpdate(insertTags);

            statement.close();
            connect.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*this method will validate the media ID and all the person in media, before entering that into table*/
    /*this method will also validation the existing record in people in media and eliminate duplicate records */
    Boolean peopleInMedia(FileIdentifier fileIdentifier, List<PersonIdentity> people) {
        createConnection conn = new createConnection();
        ResultSet resultSet = null;

        try {
            Connection connect = conn.startConnection();
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);

            /*check whether the media file exists in media_archive or not*/
            resultSet = statement.executeQuery("select * from media_archieve where mediaId = '" + fileIdentifier.getMediaId() + "';");
            if (resultSet.next() == false) {
                statement.close();
                connect.close();
                return false;

            }

            /*check of list of people mentioned are known to database*/

            List<PersonIdentity> newpeople = new ArrayList<>();
            String checkpeople="";

            /*this loop will filter the people who are not know to databse*/
            /*the list newpeople will have the final list of people who will go ahead*/
            for(int i =0; i< people.size(); i++)
            {
                resultSet = null;
                resultSet = statement.executeQuery("select * from person where p_id='"+people.get(i).getId()+"';");
                if (resultSet.next()==true)
                {
                    newpeople.add(people.get(i));
                }
            }

            /*adding data in peopleinmedia with newpeople list*/
            String addPeopleinMedia="";
            String insertpeopleinmedia="";
            for (int i =0; i< newpeople.size(); i++)
            {
                /*check if that entry already exists and validate for duplication of data*/
                resultSet=null;
                resultSet = statement.executeQuery("select * from person_in_media where mediaId='"+fileIdentifier.getMediaId()+"' " +
                            "and person='"+newpeople.get(i).getId()+"';");

                if(resultSet.next()==false)
                {
                    insertpeopleinmedia="insert into person_in_media values('"+fileIdentifier.getMediaId()+"','"+newpeople.get(i).getId()+"');";
                    statement.executeUpdate(insertpeopleinmedia);
                }
            }
            statement.close();
            connect.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
