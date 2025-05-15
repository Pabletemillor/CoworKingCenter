package p7;

public class Executive extends Employee {

    public static final String PASS_ID_FORMAT = "\\d{6}";
    private String passId;
 
    public Executive(String id, String name, float seniority, String country, String company, String bankAccount, String passId) throws IllegalArgumentException {
        super(id, name, seniority, country, company, bankAccount);
        setPassId(passId);
    }

    public static boolean isValidPassId(String passId) {
        return passId != null && passId.matches(PASS_ID_FORMAT);
    }

    public String getPassId() {
        return passId; 

    }
    public void setPassId(String passId) throws IllegalArgumentException{
        if (!isValidPassId(passId)) {
            throw new IllegalArgumentException("Valor no valido de passId");
            
        }
        this.passId = passId;
    }

    @Override
    public float getCredit() {
    return getSeniority() * 3000;
    }
    
    @Override
    public String toString() {
     return String.format("X;%s;%s", super.toString().substring(2), passId);
    }
}