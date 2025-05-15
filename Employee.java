package p7;

public class Employee extends Coworker {

    public static final String BANK_ACCOUNT_FORMAT = "[A-Z]{2}\\d{22}";  
    public static final int MAX_COMPANY_LENGTH = 30;

    private String company;
    private String bankAccount;

    public Employee() {}

    public Employee(String id, String name, float seniority, String country, String company, String bankAccount) throws IllegalArgumentException {
        super(id, name, seniority, country);
        setCompany(company);
        setBankAccount(bankAccount);
    }

    public static boolean isValidCompany(String company) {
        return company != null && company.length() <= MAX_COMPANY_LENGTH;
    }

    public static boolean isValidBankAccount(String bankAccount) {
        return bankAccount != null && bankAccount.matches(BANK_ACCOUNT_FORMAT);
    }


    public void setCompany(String company) throws IllegalArgumentException {
        if (!isValidCompany(company)) {
            throw new IllegalArgumentException("La empresa no es valida");
        }
        this.company = company;
    }

    public void setBankAccount(String bankAccount) throws IllegalArgumentException {
        if (!isValidBankAccount(bankAccount)) {
            throw new IllegalArgumentException("La cuenta bancaria no es valida");
        }
        this.bankAccount = bankAccount;
    }

    @Override
    public float getCredit() {
        return getSeniority() * 2000;
    }

    @Override
    public String toString() {
        return String.format("E;%s;%s;%s", super.toString(), company, bankAccount);
    }
}