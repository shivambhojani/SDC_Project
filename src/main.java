import javax.sound.midi.Soundbank;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class main {
    public static void main(String[] args) {

        FileIdentifier fi = new FileIdentifier();
        fi = fi.addMediaFile("F:\\Dalhousie\\Term 1\\5100\\written 3\\3.3");
        System.out.println(fi.getMediaId());
        System.out.println(fi.getFileName());
        System.out.println(fi.getLocation());



//        PersonIdentity pi = new PersonIdentity();
//        Genealogy g = new Genealogy();
//        pi = pi.addPerson("Raghav");
//
//        PersonIdentity person= g.findPerson("Raghav");
//        System.out.println("New Id: "+person.getId());
//        Map<String, String> m = new HashMap<>();
//        m.put("dob", "27/01/1998");
//        m.put("bLocation", "Dublin");
//        m.put("gender", null);
//        m.put("occupation", "Teacher");
//        pi.recordAttributes(person, m);




    }
}
