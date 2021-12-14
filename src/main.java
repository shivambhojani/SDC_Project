import java.io.File;
import java.util.*;

public class main {
    public static void main(String[] args) {

        /*Add Person*/

       ManagePersonRecords mpr = new ManagePersonRecords();
//        mpr.addPerson("A1");
//        mpr.addPerson("A2");
//        mpr.addPerson("A3");
//        mpr.addPerson("A4");
//        mpr.addPerson("A5");
//        mpr.addPerson("A6");
//        mpr.addPerson("A7");
//        mpr.addPerson("A8");
//        mpr.addPerson("A9");
//        mpr.addPerson("A10");
//        mpr.addPerson("A11");
//        mpr.addPerson("A12");
//        mpr.addPerson("A13");
//        mpr.addPerson("A14");
//        mpr.addPerson("A15");

        //------------------

        Map<String, String> m = new HashMap<>();
        Genealogy g = new Genealogy();
//        m.put("Gender", "male");
//        m.put("birthDate", "1990");
//        m.put("deathDate", "2021-05");
//
//        System.out.println(mpr.recordAttributes(g.findPerson("A3"), m));
//        System.out.println(mpr.recordAttributes(g.findPerson("A4"), m));
//        System.out.println(mpr.recordAttributes(g.findPerson("A5"), m));
//        System.out.println(mpr.recordAttributes(g.findPerson("A6"), m));
//        System.out.println(mpr.recordAttributes(g.findPerson("A7"), m));
//        System.out.println(mpr.recordAttributes(g.findPerson("A8"), m));
//        System.out.println(mpr.recordAttributes(g.findPerson("A50"), m));

        System.out.println(mpr.recordDissolution(g.findPerson("A1"), g.findPerson("Rachel")));

        /**/
        ManageMediaArchieve mma = new ManageMediaArchieve();
//        mma.addMediaFile("samplefile.txt");
//
//        Map<String, String> mm = new HashMap<>();
//        mm.put("location", "Toronto");
//        mm.put("date", "1980-06");
//        mma.recordMediaAttributes(g.findMediaFile("samplefile.txt"), mm);
        Set<FileIdentifier> f = g.findMediaByTag("Tag2", "1950", "2000");

//        for (FileIdentifier file : f)
//        {
//            System.out.println(file.getMediaId() + " " + file.getFileName());
//        }

        f = g.findMediaByLocation("Halifax", "1950-05" , "2000");

//        for (FileIdentifier file : f)
//        {
//            System.out.println(file.getMediaId() + " " + file.getFileName());
//        }

        /*----------*/

    //    mpr.recordReference(g.findPerson("A1"), "references A1 3");
//        System.out.println(mpr.recordReference(g.findPerson("A2"), "references 2"));

    //    mpr.recordPartnering(g.findPerson("A1"), g.findPerson("Shivam Bhojani"));




        /*------------*/

//        System.out.println(mpr.recordNote(g.findPerson("A1"), ""));
//        System.out.println(mpr.recordNote(g.findPerson("A2"), "noteA2"));
//
//        List<String> a = g.notesAndReferences(g.findPerson("A1"));
//
//        for (String s: a)
//        {
//            System.out.println(s);
//        }

        /*----*/
//        mpr.recordChild(g.findPerson("A2"), g.findPerson("A1"));
//        mpr.recordChild(g.findPerson("A3"), g.findPerson("A2"));
//        mpr.recordChild(g.findPerson("A4"), g.findPerson("A2"));
//        mpr.recordChild(g.findPerson("A6"), g.findPerson("A4"));
//        mpr.recordChild(g.findPerson("A5"), g.findPerson("A4"));
//        mpr.recordChild(g.findPerson("A7"), g.findPerson("A3"));
//        mpr.recordChild(g.findPerson("A8"), g.findPerson("A3"));

        /*-----*/
    //        Set<PersonIdentity> s = g.descendents(g.findPerson("A1"), 10);
    //        for (PersonIdentity p : s)
    //        {
    //            System.out.println(p.getPersonName());
    //        }
    //
    //        s = g.ancestores(g.findPerson("A1"), 10);
    //        for (PersonIdentity p : s)
    //        {
    //            System.out.println(p.getPersonName());
    //        }

        /*-----*/

//        BiologicalRelation b = g.findRelation(g.findPerson("A1"), g.findPerson("A5"));
//        System.out.println("Cousin: " +b.getCousinship());
//        System.out.println("Removal: " +b.getRemoval());
    }
}






