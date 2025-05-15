package p7;

public class Office implements Comparable<Office> {

    private OfficeLocator officeLocator;
    private String idCoworker;
    private String entryDate;

    public Office(OfficeLocator officeLocator, String idCoworker) {
        this.officeLocator = officeLocator;
        this.idCoworker = idCoworker;
        this.entryDate = null;
    }

    public Office(OfficeLocator officeLocator) {
        this(officeLocator, null);
    }

    public OfficeLocator getOfficeLocator() {
        return officeLocator;
    }

    public void setOfficeLocator(OfficeLocator officeLocator) {
        this.officeLocator = officeLocator;
    }

    public String getIdCoworker() {
        return idCoworker;
    }

    public void setIdCoworker(String idCoworker) {
        this.idCoworker = idCoworker;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public boolean hasConfService() {
        return false;
    }

    @Override
    public int compareTo(Office other) {
        return this.officeLocator.compareTo(other.officeLocator);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Office)) return false;
        Office office = (Office) object;
        return officeLocator.equals(office.officeLocator);
    }

    @Override
    public int hashCode() {
        return officeLocator.hashCode();
    }

    @Override
    public String toString() {
        return officeLocator.toString() + (idCoworker != null ? ";" + idCoworker + ";N" + ";" + entryDate : "");
    }

    public void setConf(char conf) {
        throw new UnsupportedOperationException("Metodo no implementado: setConf");
    }
}