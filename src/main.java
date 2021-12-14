import java.io.File;
import java.util.*;

public class main {
    public static void main(String[] args) {

//        String dob= "1998/04/20";
//        System.out.println(dob);
//        dob = dob.substring(0,4) + "-" + dob.substring(5,dob.length());
//        System.out.println(dob);
//
//        dob = dob.substring(0,7) + "-" + dob.substring(8,dob.length());
//        System.out.println(dob);

        /*findName--------------*/
//        Genealogy g = new Genealogy();
//        PersonIdentity pp = g.findPerson("Shivam5");
//        System.out.println("name: " + g.findName(pp));


        /*findBiologicalFamilyMedia------------*/
        Genealogy g = new Genealogy();
        PersonIdentity p1 = g.findPerson("J");

        List<FileIdentifier> fileObjects = g.findBiologicalFamilyMedia(p1);
        if (fileObjects != null) {
            System.out.println("size = " + fileObjects.size());
        }
        for (int i = 0; i < fileObjects.size(); i++) {
            System.out.println(fileObjects.get(i).getMediaId());

        }
        Set<PersonIdentity> sp = new HashSet<>();
        sp = g.descendents(p1, 4);

        if (sp != null) {
            for (PersonIdentity s : sp) {
                System.out.println("D: " + s.getId() + "  " + s.getPersonName());
            }


            sp = g.ancestores(p1, 3);

            if (sp != null) {
                for (PersonIdentity s : sp) {
                    System.out.println("A: " + s.getId() + "  " + s.getPersonName());
                }
            }


            /*find relations between two person*/
        Genealogy g1 = new Genealogy();
        PersonIdentity p11 = g1.findPerson("A");
        PersonIdentity p2 = g1.findPerson("K");

        BiologicalRelation b = g1.findRelation(p11,p2);

        System.out.println("Removal: "+b.getRemoval());
        System.out.println("CousinShip:" + b.getCousinship());


            /*find individual in media----------------*/

//        Genealogy gene = new Genealogy();
//        PersonIdentity p1 = gene.findPerson("Ben");
//        PersonIdentity p2 = gene.findPerson("person1");
//
//        Set<PersonIdentity> s = new HashSet<>();
//        s.add(p1);
//        //s.add(p2);
//       List<FileIdentifier> file=  gene.findIndividualsMedia(s,"1950-00-00","2000-01-01");
//
//        for(FileIdentifier ff : file)
//        {
//            System.out.println("--" + ff.getMediaId() +" " + ff.getFileName() + " " + ff.getDate());
//        }
            /*ADD PERSON--------------------------------------------------*/
//        PersonIdentity person1 = new PersonIdentity();
//        PersonIdentity person2 = new PersonIdentity();
//        person1.addPerson("Kandarp");
//        person2.addPerson("Meghna");

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
//        fi = fi.addMediaFile("Image1");
//        fi = fi.addMediaFile("Image2");
//        fi = fi.addMediaFile("Image3");
//        fi = fi.addMediaFile("Image4");
//
//        System.out.println("New File: "+fi.getMediaId());
//        System.out.println("New File: "+fi.getFileName());

            /*Find mediaFile String*/

//        Genealogy g1 = new Genealogy();
//        System.out.println("Name: "+g1.findMediaFile(fi));
//
//
//        System.out.println();

            /*find file by filelocationwithName----------------------------------*/
//        Genealogy gn = new Genealogy();
//        FileIdentifier f = new FileIdentifier();
//        f = gn.findMediaFile("Image1");
//        System.out.println(gn.findMediaFile(f));
//        System.out.println(f.getMediaId() + f.getFileName() + f.getLocation() + f.getDate());

            /*Add media tags-------------------------*/
//        FileIdentifier fid = new FileIdentifier();
//        fid.tagMedia(f,"Test tag");

            /*Add media attributes-------------*/
//        Genealogy gn = new Genealogy();
//        FileIdentifier f = gn.findMediaFile("Image4");
//
//        Map<String, String> mediaMap = new HashMap<>();
//        mediaMap.put("date", "1960-07");
//        mediaMap.put("location", "Halifax");
//        f.recordMediaAttributes(f, mediaMap);

            /*Add people in media*/
//        List<PersonIdentity> people = new ArrayList<>();
//        Genealogy g = new Genealogy();
//        PersonIdentity p1 = g.findPerson("Ben");
//        PersonIdentity p2 = g.findPerson("Shivam Bhojani");
//
//        people.add(p1);
//        people.add(p2);
//
//        FileIdentifier FileIdentify = new FileIdentifier();
//        FileIdentify = g.findMediaFile("Image4");
//        FileIdentify.peopleInMedia(FileIdentify, people);


            /*Find media by location*/
//    Genealogy ggn = new Genealogy();
//
//    Set<FileIdentifier> s = new HashSet<>();
//    s=ggn.findMediaByLocation("Halifax","","1960-12-12");
//
//        for(
//    FileIdentifier file:s)
//
//    {
//        System.out.println(file.getMediaId());
//    }


            /*findmedia by tag*/
//        Genealogy ggn = new Genealogy();
//
//        Set<FileIdentifier> s = new HashSet<>();
//        s = ggn.findMediaByTag("Tag2", "", null);
//
//        for (FileIdentifier file : s) {
//            System.out.println(file.getFileName());
//            System.out.println(file.getMediaId());
//            System.out.println(file.getLocation());
//        }

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

//        Genealogy g = new Genealogy();
//        PersonIdentity p1 = g.findPerson("Shivam");
//
//        System.out.println(p1.getId() + p1.getPersonName());
//        PersonIdentity p2 = g.findPerson("person1");
//        PersonIdentity p3 = g.findPerson("person3");
//        PersonIdentity p4 = g.findPerson("person4");
//
//        System.out.println("Orignal ID: " + p1.getId());
//
//        System.out.println("New Id: " + p2.getId());
//        System.out.println("New Id: " + p3.getId());
//        System.out.println("New Id: " + p4.getId());
//        PersonIdentity record = new PersonIdentity();
//        Map<String, String> m = new HashMap<>();
//        m.put("dob", "1998");
//        m.put("dod", "2021-03");
//        m.put("bLocation", "London");
//        m.put("dLocation", "Canada");
//        m.put("gender", "female");
//        m.put("occupation", "Sales Manager");

            // System.out.println(p1.recordAttributes(p1, m));
//
//
//        System.out.println(record.recordAttributes(p1, m));
//        System.out.println(record.recordAttributes(p2, m));
//        System.out.println(record.recordAttributes(p3, m));
//        System.out.println(record.recordAttributes(p4, m));

            /*RECORD CHILD------------------------------------*/
//            Genealogy g3 = new Genealogy();
//            PersonIdentity person3 = g3.findPerson("L");
//            PersonIdentity person4 = g3.findPerson("K");
//
//            BiologicalRelation br = new BiologicalRelation();
//            System.out.println(br.recordChild(person3, person4));
            //System.out.println(br.recordChild(person4, person5));


            /*Record references and notes---------------------------*/
//        Genealogy g4 = new Genealogy();
//        PersonIdentity person5 = g4.findPerson("Shivam");
//        System.out.println(person5.recordReference(person5,"Rajkot"));
//
//        System.out.println(person5.recordNote(person5,"Note 1"));


            /*Add Relations between two perso */
//        Genealogy gg = new Genealogy();
//        PersonIdentity person3 = g3.findPerson("Kandarp");
//        PersonIdentity person4 = g3.findPerson("Rachel");
//        PersonIdentity person5 = g3.findPerson("J");
//        BiologicalRelation br = new BiologicalRelation();
//
//        System.out.println(br.recordPartnering(person3, person4));


            /*Dissolution*/

//        BiologicalRelation brrr = new BiologicalRelation();
//        System.out.println(brrr.recordDissolution(person3,person4));
//        System.out.println(brrr.recordDissolution(person3,person5));

        }
    }
}





