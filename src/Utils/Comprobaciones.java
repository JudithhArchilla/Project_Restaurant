package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Comprobaciones {

    public static LocalDate comprobacionFecha(String fecha) {
        LocalDate localDate = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
            localDate = LocalDate.parse(fecha, formatter);
        } catch (DateTimeParseException e) {
            e.getMessage();
        }

        if (localDate != null) {
            return localDate;
        }
        return null;

    }

    public static boolean comprobacionVgMenu(String vgMenu) {

        if (vgMenu.toLowerCase(Locale.US).equals("yes")){
            return true;
        }
        if (vgMenu.toLowerCase(Locale.US).equals("y")){
            return true;
        }
        if (vgMenu.toLowerCase(Locale.US).equals("ye")){
            return true;
        }
        if (vgMenu.toLowerCase(Locale.US).equals("no")){
            return false;
        }
        if (vgMenu.toLowerCase(Locale.US).equals("n")){
            return false;
        }
       return false;


    }

}
