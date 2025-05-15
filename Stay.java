package p7;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Stay {
private Coworker coworker;
private String entryDate;
private String exitDate;
private boolean conf; 

public Stay(Coworker coworker, String entryDate, String exitDate, boolean conf ) {
    this.coworker = coworker; 
    this.entryDate = entryDate;
    this.exitDate = exitDate;
    this.conf = conf;
}

public Coworker getCoworker() {
    return coworker;
}

public void setCoworker(Coworker coworker) {
    this.coworker = coworker;
}

public String getEntryDate() {
    return entryDate;
}

public void setEntryDate(String entryDate) {
    this.entryDate = entryDate;
}

public String getExitDate() {
    return exitDate;
}

public void setExitDate(String exitDate) {
    this.exitDate = exitDate;
}

public boolean isConf() {
    return conf;
}

public void setConf(boolean conf) {
    this.conf = conf;
}
public int stayDays() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yy");
    LocalDate parsedDateIn = LocalDate.parse(entryDate, formatter);
    LocalDate parsedDateOut = LocalDate.parse(exitDate, formatter);
    long days = ChronoUnit.DAYS.between(parsedDateIn, parsedDateOut);
    return (int) days;
}
@Override
public String toString() {
    return String.format("Coworker: %s, fecha de entrada: %s, Exit Date: %s, equipo de conferencia: %s",
    coworker.getId(), entryDate, exitDate, (conf ? "Yes" : "No"));
}
}