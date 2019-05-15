package financetxn;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static LocalDateTime parse(String dateTime) {
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }
}
