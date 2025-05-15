package p7;
import java.io.*;
import java.util.*;

public class P7 {
    public static void main(String[] args) {
        if (args.length != 9) {
            System.out.println("Uso: java P6 <file0> <file1> <file2> <file3> <file4> <file5> <file6> <file7> <file8>");
            return;
        }

        String file0 = args[0];
        String file1 = args[1];
        String file2 = args[2];
        String file3 = args[3]; 
        String file4 = args[4];  
        String file5 = args[5];  
        String file6 = args[6]; 
        String file7 = args[7];  
        String file8 = args[8];

        try {
            CoworkerDB coworkerDb = new CoworkerDB();
            coworkerDb.readCoworkersFile(file3);

            float avgSeniority = coworkerDb.computeAverageSeniority();
            System.out.printf("Antiguedad media: %.2f%n", avgSeniority);

            float employeeRatio = coworkerDb.computeRatioEmployees();
            System.out.printf("Ratio de empleo = %.2f%n", employeeRatio);

            CoworkingCenter Center = new CoworkingCenter(file0);
            System.out.printf("Centro de coworking inicializado con dimensiones: %d; %d; %d; %d%n",
                    Center.getSizeFloors(),
                    Center.getSizeCorridors(),
                    Center.getSizeNums(),
                    Center.getUpperParticularFloor());

            processIO(coworkerDb, Center, file1);

            Center.saveCoworkingCenterToFile(file2);

            coworkerDb.sortBySeniorityAndId(); //Uso el comparador del método sortBySeniorityAndId de la clase CoworkerDB

            saveCoworkingCenterMap(file5, Center);
            System.out.println("Mapa del centro guardado en " + file5);

            coworkerDb.saveCoworkersToFile(file4);
            System.out.println("Coworkers ordenados guardados en " + file4);

            System.out.println("Centro de coworking actualizado y guardado en " + file2);
            System.out.println("Mapa del centro guardado en CoworkingCenterMap_output.txt");
            
            Center.saveStayPayments(file6);
            System.out.println("Mapa de ingresos por partes guardado en " + file6);

            Center.updateSeaViewOffices(file7, file8);
            System.out.println("Oficinas con vistas al mar disponibles guardadas en " + file8);

        } catch (IOException e) {
            System.err.println("Error manejando los ficheros: " + e.getMessage());
        }
    }

    public static void processIO(CoworkerDB coworkerDb, CoworkingCenter theCenter, String filename) throws IOException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
    
                String[] parts = line.split(";");
                char typeIO = parts[0].charAt(0);
                String id = parts[1];
    
                Coworker coworker = coworkerDb.getCoworkerFromId(id);
                if (coworker != null) {
                    System.out.println(coworker);
                    processInputLine(typeIO, parts, coworker, theCenter);
                }
            }
        }
    }
    
    private static void processInputLine(char typeIO, String[] parts, Coworker coworker, CoworkingCenter theCenter) {
        if (typeIO == 'I' && parts.length == 4) {
            processEntryCenter(parts, coworker, theCenter);
        } else if (typeIO == 'O' && parts.length == 3) {
            processExitCenter(parts, coworker, theCenter);
        }
    }      
    
    private static void processEntryCenter(String[] parts, Coworker coworker, CoworkingCenter theCenter) {
        boolean confidencial = parts[2].charAt(0) == 'Y';
        String fecha = parts[3];
        theCenter.coworkerIn(coworker, confidencial, fecha);
    }
    
    private static void processExitCenter(String[] parts, Coworker coworker, CoworkingCenter theCenter) {
        String exitDate = parts[2];
        Stay stay = theCenter.coworkerOut(coworker, exitDate);
    
        if (stay != null) {
            int cost = theCenter.computeCost(stay);
            float months = stay.stayDays() / 30.0f;
            coworker.addSeniority(months);
            theCenter.registerStayPayment(stay, cost);
        }
    }

    public static void saveCoworkingCenterMap(String fileName, CoworkingCenter theCenter) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            String map = theCenter.toMap();
            writer.print(map);
        } catch (IOException error) {
            System.err.println("Error al guardar el mapa del centro: " + error.getMessage());
        }
    }
}