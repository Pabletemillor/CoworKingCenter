package p7;

import java.util.Comparator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StayComparatorByDateAndId implements Comparator<Stay> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yy");

    @Override
    public int compare(Stay s1, Stay s2) {
        LocalDate date1 = LocalDate.parse(s1.getEntryDate(), formatter);
        LocalDate date2 = LocalDate.parse(s2.getEntryDate(), formatter);

        int dateComparison = date1.compareTo(date2);

        if (dateComparison != 0) {
            return dateComparison;
            
        } else {
            return s1.getCoworker().getId().compareTo(s2.getCoworker().getId());
        }
    }
}

