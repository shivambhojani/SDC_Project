public class dateFormat {

    String checkDateFormat(String date) {
        if (date.length() == 10) {
            if (date.charAt(4) == '/' || date.charAt(4) == '\\' || date.charAt(4) == '-') {
                date = date.substring(0, 4) + "-" + date.substring(5, date.length());

                if (date.charAt(7) == '/' || date.charAt(7) == '\\' || date.charAt(7) == '-') {
                    date = date.substring(0, 7) + "-" + date.substring(8, date.length());
                }
            }
            return date;
        } else if (date.length() == 4) {
            date = date + "-01-01";
            return date;
        } else if (date.length() == 7) {
            if (date.charAt(4) == '/' || date.charAt(4) == '\\' || date.charAt(4) == '-') {
                date = date + "-01";
            }
            return date;
        }
        return null;
    }
}
