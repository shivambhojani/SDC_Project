import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FileIdentifier {



    private String mediaId;
    private String fileName;
    private String location;
    private String date;


    FileIdentifier addMediaFile( String fileLocation ){

        System.out.println("fileLocation: "+fileLocation);
        createConnection conn = new createConnection();
        Connection connect = conn.startConnection();
        ResultSet resultSet = null;
        try {
            Statement statement = connect.createStatement();
            statement.executeQuery("use " + conn.databaseName);
            String insertQuery = "insert into media_archieve value (null, '" + fileLocation + "', null, null);";
            System.out.println(insertQuery);
            statement.executeUpdate(insertQuery);
            FileIdentifier f = new FileIdentifier();
            f.setLocation(fileLocation);

            String selectQuery = "select * from media_archieve where location='" + fileLocation + "'";
            resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                f.setMediaId(resultSet.getString("mediaId"));
            }
            statement.close();
            connect.close();
            return f;
        }
        catch (SQLException e)
        {
            System.out.println(e);
            return null;
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
