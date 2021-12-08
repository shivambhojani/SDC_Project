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



        /*ADD PERSON--------------------------------------------------*/
//        PersonIdentity person = new PersonIdentity();
//        person = person.addPerson("Rachel");



        /*FIND PERSON AND RECORD ATTRIBUTES---------------------------*/
//        Genealogy g = new Genealogy();
//        PersonIdentity person2 = g.findPerson("Ross");
//
//        PersonIdentity record = new PersonIdentity();
//        System.out.println("New Id: " + person2.getId());
//        Map<String, String> m = new HashMap<>();
//        m.put("dob", "1998-03-20");
//        m.put("bLocation", "Dublin");
//        m.put("gender", null);
//        m.put("occupation", "Teacher");
//        record.recordAttributes(person2, m);


        //person = gparent.findPerson("Ross");


        /*RECORD CHILD------------------------------------*/
//        Genealogy g3 = new Genealogy();
//        PersonIdentity person3 = g3.findPerson("Chandler");
//
//        PersonIdentity person4 = g3.findPerson("Ben");
//        BiologicalRelation br = new BiologicalRelation();
//        boolean b = br.recordChild(person3, person4);
//        System.out.println(b);


        /*Record references and notes---------------------------*/
//        Genealogy g4 = new Genealogy();
//        PersonIdentity person5 = g4.findPerson("Shivam");
//        System.out.println(person5.recordReference(person5,"Rajkot"));
//
//        System.out.println(person5.recordNote(person5,"Note 1"));


        Genealogy gg = new Genealogy();
        BiologicalRelation br = new BiologicalRelation();

        System.out.println(br.recordPartnering(gg.findPerson("Ross"), gg.findPerson("Rachel"))  );
    }
}
