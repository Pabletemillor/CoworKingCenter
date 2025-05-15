package p7;

public class Particular extends Coworker{

    public static final String CREDIT_CARD_FORMAT = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";

    private String creditCard;

    public Particular() {} 

    public Particular(String id, String name, float seniority, String country, String creditCard) throws IllegalArgumentException{
        super(id, name, seniority, country);
        setCreditCard(creditCard);
    }

    public Particular(Particular p) {
        this(p.getId(), p.getName(), p.getSeniority(), p.getCountry(), p.creditCard);
    }

    public static boolean isValidCreditCard(String creditCard) {
        return creditCard != null && creditCard.matches(CREDIT_CARD_FORMAT);
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) throws IllegalArgumentException {
        if (!isValidCreditCard(creditCard)) {
            throw new IllegalArgumentException("Tarjeta de credito no valida");
        }
        this.creditCard = creditCard;
    }
    
    @Override
    public float getCredit() {
    return getSeniority() * 1000;
    }

    @Override
    public String toString() {
        return String.format("P;%s;%s", super.toString(), creditCard);
    }
}

