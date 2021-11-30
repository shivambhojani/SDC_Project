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
        //fi = fi.addMediaFile("F:\\Shivam");


//        Genealogy gn = new Genealogy();
//        FileIdentifier f = new FileIdentifier();
//        f = gn.findMediaFile("F:\\Shivam");
//
//        System.out.println(f.getLocation());
//        System.out.println(f.getMediaId());
//
//
//        System.out.println(fi.getMediaId());
//        System.out.println(fi.getFileName());
//        System.out.println(fi.getLocation());
//
//        Map<String, String> mediaMap = new HashMap<>();
//        mediaMap.put("date", "27/01/1998");
//        mediaMap.put("filename", "Dublin");
//        f.recordMediaAttributes(f,mediaMap);

        PersonIdentity parent = new PersonIdentity();
        Genealogy gparent = new Genealogy();
       // parent = parent.addPerson("Ross");
        parent = gparent.findPerson("Ross");


        Genealogy gchild = new Genealogy();
        PersonIdentity child = new PersonIdentity();
       // child = child.addPerson("Emma");
        child =gchild.findPerson("Emma");


        BiologicalRelation br = new BiologicalRelation();
        br.recordChild(parent, child);
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
