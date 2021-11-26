import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class main {
    public static void main(String[] args) {

        UUID uuid= UUID.randomUUID();
        System.out.println(uuid);

        PersonIdentity p = new PersonIdentity();
        Date thisDate = new Date();
        SimpleDateFormat dateFormt = new SimpleDateFormat("MMMM dd Y");
        System.out.println(dateFormt.format(thisDate));
        PersonIdentity p1 = new PersonIdentity();
    }
}
