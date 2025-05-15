package p7;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CoworkerDB {
    private ArrayList<Coworker> coworkers;

    public CoworkerDB(){
        coworkers = new ArrayList<>();
    }

    public void readCoworkersFile(String filename) {
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.startsWith("#") || line.isEmpty()) continue;

                String[] fields = line.split(";");
                if (fields.length < 5) continue;

                String type = fields[0];
                float seniority = Float.parseFloat(fields[3].replace(",", "."));

                switch (type) {
                    case "P":
                        if (fields.length == 6 &&
                            Particular.isValidId(fields[1]) &&
                            Particular.isValidName(fields[2]) &&
                            Particular.isValidSeniority(seniority) &&
                            Particular.isValidCountry(fields[4]) &&
                            Particular.isValidCreditCard(fields[5])) {

                            coworkers.add(new Particular(
                                fields[1], fields[2], seniority, fields[4], fields[5]));
                        }
                        break;

                    case "E":
                        if (fields.length == 7 &&
                            Employee.isValidId(fields[1]) &&
                            Employee.isValidName(fields[2]) &&
                            Employee.isValidSeniority(seniority) &&
                            Employee.isValidCountry(fields[4]) &&
                            Employee.isValidCompany(fields[5]) &&
                            Employee.isValidBankAccount(fields[6])) {

                            coworkers.add(new Employee(
                                fields[1], fields[2], seniority, fields[4], fields[5], fields[6]));
                        }
                        break;

                    case "X":
                        if (fields.length == 8 &&
                            Executive.isValidId(fields[1]) &&
                            Executive.isValidName(fields[2]) &&
                            Executive.isValidSeniority(seniority) &&
                            Executive.isValidCountry(fields[4]) &&
                            Executive.isValidCompany(fields[5]) &&
                            Executive.isValidBankAccount(fields[6]) &&
                            Executive.isValidPassId(fields[7])) {

                            coworkers.add(new Executive(
                                fields[1], fields[2], seniority, fields[4], fields[5], fields[6], fields[7]));
                        }
                        break;
                }
            }
            sc.close();
            
            

        } catch (IOException error) {
            System.out.println("Error en la lectura del archivo: " + error.getMessage());
        }
    }

    public Coworker getCoworkerFromId(String id) {
        for(Coworker c : coworkers){
            if(c.getId().equals(id)){
                return c;
            }

        }
        return null;
    }
        
    public float computeAverageSeniority() {
        float total = 0;
        for (Coworker c : coworkers) {
            total += c.getSeniority();
        }
        return coworkers.size() > 0 ? total / coworkers.size() : 0;
    }

    public float computeRatioEmployees() {
        int countEmployees = 0;
        for (int i = 0; i < coworkers.size(); i++) {
            Coworker c = coworkers.get(i);
            if (c instanceof Employee){
                countEmployees ++;
            }
        
        }

         return coworkers.size() > 0 ? (float) countEmployees / coworkers.size() : 0;
    }

    public void saveCoworkersToFile (String filename){
        try (PrintWriter PrintWriter = new PrintWriter(filename)) {
        for (int i = 0; i < coworkers.size(); i++) {
        Coworker c = coworkers.get(i);
        PrintWriter.println(c.toString());
        } 
        }catch (IOException error) {
        System.out.println("Error guardando el archivo: " + error.getMessage());

        }

    }
    public void sortById () {
        Collections.sort(coworkers);

    }
    //privado porque solo se usa en sortBySeniorityAndId
    private static class CoworkerComparatorBySeniorityAndId implements Comparator<Coworker> { 
        @Override                                                                             
        public int compare(Coworker c1, Coworker c2) {
            int result = Float.compare(c1.getSeniority(), c2.getSeniority());
            if (result == 0) {
                return c1.getId().compareTo(c2.getId());
            }
            return result;
        }
    }
    //Esto se usa en P7 linea 43
    public void sortBySeniorityAndId() {
        coworkers.sort(new CoworkerComparatorBySeniorityAndId());
    }

    public float computeTotalCredit() {
        float total = 0;
        for (Coworker c : coworkers) {
            total += c.getCredit();
        }
        return total;
    }
}