package src.utilities;

import java.time.*;
import java.time.format.*;

public class DateTime {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy");
    public static String formattedDate = LocalDate.now().format(DATE_FORMATTER);
}