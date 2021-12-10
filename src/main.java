import javax.sound.midi.Soundbank;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class main {
    public static void main(String[] args) {

        Genealogy gene = new Genealogy();
        PersonIdentity p1 = gene.findPerson("Chandler");
        PersonIdentity p2 = gene.findPerson("person1");

        Set<PersonIdentity> s = new HashSet<>();
        s.add(p1);
        s.add(p2);
       List<FileIdentifier> file=  gene.findIndividualsMedia(s,"1950-01-01","1990-01-01");

        for(FileIdentifier ff : file)
        {
            System.out.println(ff.getMediaId() +" " + ff.getFileName());
        }

        /*ADD PERSON--------------------------------------------------*/
//        PersonIdentity person1 = new PersonIdentity();
//        person1 = person1.addPerson("person1");
//
//        PersonIdentity person2 = new PersonIdentity();
//        person2 = person2.addPerson("person2");
//
//        PersonIdentity person3 = new PersonIdentity();
//        person3 = person3.addPerson("person3");
//
//        PersonIdentity person4 = new PersonIdentity();
//        person4 = person4.addPerson("person4");


        /*Add media file-----------------------*/
//        FileIdentifier fi = new FileIdentifier();
//        fi = fi.addMediaFile("F:/newFile");

        /*find file by filelocation----------------------------------*/
//        Genealogy gn = new Genealogy();
//        FileIdentifier f = new FileIdentifier();
//        f = gn.findMediaFile("F:/newFile");

        /*Add media tags-------------------------*/
//        FileIdentifier fid = new FileIdentifier();
//        fid.tagMedia(f,"Test tag");


        /*Add media attributes-------------*/
//        Map<String, String> mediaMap = new HashMap<>();
//        mediaMap.put("date", "1990-04-15");
//        mediaMap.put("filename", "Test File name 123456");
//        f.recordMediaAttributes(f,mediaMap);

        /*Add people in media*/
//        List<PersonIdentity> people = new ArrayList<>();
//        Genealogy g = new Genealogy();
//        PersonIdentity p1 = g.findPerson("person1");
//        PersonIdentity p2 = g.findPerson("person2");
//        PersonIdentity p3 = g.findPerson("person3");
//        PersonIdentity p4 = g.findPerson("person4");
//        PersonIdentity p5 = g.findPerson("Chandler");
//        people.add(p1);
//        people.add(p2);
//        people.add(p3);
//        people.add(p4);
//        people.add(p5);
//        FileIdentifier FileIdentify = new FileIdentifier();
//        FileIdentify.peopleInMedia(f, people);


        /*findmedia by tag*/
//        Genealogy ggn = new Genealogy();
//
//        Set<FileIdentifier> s = new HashSet<>();
//        s = ggn.findMediaByTag("Tag2", "1960-01-01", "1990-01-01");
//
//        for (FileIdentifier file : s) {
//            System.out.println(file.getFileName());
//            System.out.println(file.getMediaId());
//            System.out.println(file.getLocation());
//        }


        System.out.println("-----------");

        /*Get notes and reference*/
//        Genealogy gen = new Genealogy();
//        PersonIdentity p = gen.findPerson("Shivam Bhojani");
//
//        List<String> getNotesRef = gen.notesAndReferences(p);
//
//        System.out.println(getNotesRef.size());
//
//        for (String a : getNotesRef) {
//            System.out.println(a);
//        }

        /*FIND PERSON AND RECORD ATTRIBUTES---------------------------*/
        Genealogy g = new Genealogy();
//        PersonIdentity p1 = g.findPerson("Chandler");
//        PersonIdentity p2 = g.findPerson("person1");
//        PersonIdentity p3 = g.findPerson("person3");
//        PersonIdentity p4 = g.findPerson("person4");
//
//        System.out.println("New Id: " + p1.getId());
//        System.out.println("New Id: " + p2.getId());
//        System.out.println("New Id: " + p3.getId());
//        System.out.println("New Id: " + p4.getId());
//
//        PersonIdentity record = new PersonIdentity();
//        Map<String, String> m = new HashMap<>();
//        m.put("dob", "2000-03-20");
//        m.put("dod", "2021-03-20");
//        m.put("bLocation", "London");
//        m.put("dLocation", "Canada");
//        m.put("gender", "female");
//        m.put("occupation", "Sales Manager");
//
//        System.out.println(record.recordAttributes(p1, m));
//        System.out.println(record.recordAttributes(p2, m));
//        System.out.println(record.recordAttributes(p3, m));
//        System.out.println(record.recordAttributes(p4, m));

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


        /*Add Relations between two perso */
//        Genealogy gg = new Genealogy();
//        BiologicalRelation br = new BiologicalRelation();
//
//        System.out.println(br.recordPartnering(gg.findPerson("Ross"), gg.findPerson("Rachel"))  );
    }
}
