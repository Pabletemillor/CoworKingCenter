package p7;


public abstract class Coworker implements Comparable<Coworker> {

    public static final String ID_FORMAT = "\\d{8}[A-Z]";
    public static final int MAX_SENIORITY = 30;
    public static final int MAX_COUNTRY_LENGTH = 20;

    private String id;
    private String name;
    private float seniority;
    private String country;

    public Coworker () {}
    public Coworker(String id, String name, float seniority, String country)throws IllegalArgumentException{
        setId(id);
        setName(name);
        setCountry(country);
        setSeniority(seniority);        
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public float getSeniority() {
        return seniority;
    }
    public String getCountry() {
        return country;
    }
    

    public void setId(String id) throws IllegalArgumentException{
        if (!isValidId(id)) {
            throw new IllegalArgumentException("ID no valido");
        }
        this.id = id;
    }

    public void setName(String name) throws IllegalArgumentException{
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Nombre no valido");
        }
        this.name = name;
    }

    public void setSeniority(float seniority)throws IllegalArgumentException {
        if (!isValidSeniority(seniority)) {
            throw new IllegalArgumentException("Antiguedad no valida");
        }
        this.seniority = seniority;
    }

    public void setCountry(String country)throws IllegalArgumentException {
        if (!isValidCountry(country)) {
            throw new IllegalArgumentException("Pais no valido");
        }
        this.country = country;
    
    }
    public abstract float getCredit();

    public void addSeniority(float extraSeniority) {
        if (extraSeniority > 0) {
            this.seniority += extraSeniority;
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s;%s;%.2f;%s", id, name, seniority, country);
    } 
    public static boolean isValidId(String id) {
        return id != null && id.matches(ID_FORMAT);
    }
    public static boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z]+");
    }
    public static boolean isValidSeniority(float seniority) {
        return seniority >= 0 && seniority <= MAX_SENIORITY;    
    }
    public static boolean isValidCountry(String country) {
        return country != null && country.matches("[a-zA-Z]{1," + MAX_COUNTRY_LENGTH + "}");
    }

    @Override
    public int compareTo(Coworker other) {
        return this.id.compareTo(other.id);
    }
}

