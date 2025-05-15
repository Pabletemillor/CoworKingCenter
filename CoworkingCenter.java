package p7;

import java.io.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.TreeMap;


public class CoworkingCenter {

    public static final int PARTICULAR_COST_PER_DAY = 50;  
    public static final int EMPLOYEE_COST_PER_DAY = 75;    
    public static final int EXECUTIVE_COST_PER_DAY = 100;  
    public static final int CONF_COST = 5;  


    private short sizeFloors;
    private short sizeCorridors;
    private short sizeNums;
    private short upperParticularFloor;
    

    private Set<Office> busyOffices; 
    private Set<Office> freeOffices; 

    
    private Map<Stay, Integer> stayPayments;

    public CoworkingCenter(short sizeFloors, short sizeCorridors, short sizeNums, short upperParticularFloor) {

        this.sizeFloors = sizeFloors;
        this.sizeCorridors = sizeCorridors;
        this.sizeNums = sizeNums;
        this.upperParticularFloor = upperParticularFloor;

        this.busyOffices = new HashSet<>();
        this.freeOffices = new TreeSet<Office>(new Comparator<Office>() {

            @Override
            public int compare(Office of1, Office of2) {
                return of1.compareTo(of2);
            }
        });

        initializeOffices();
    }

    public CoworkingCenter(String filename) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            String line;
    
            while (scanner.hasNextLine()) {
                line = scanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
    
                String[] dimensions = line.split(";");
                if (dimensions.length == 4) {
                    setDimensions(dimensions);
                    initializeOffices();
                    break;
                }
            }
    
            while (scanner.hasNextLine()) {
                line = scanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
    
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    processCoworkerOfficeData(parts);
                }
            }
        }
    }
    
    private void setDimensions(String[] dimensions) {

        try {
            this.sizeFloors = Short.parseShort(dimensions[0]);
            this.sizeCorridors = Short.parseShort(dimensions[1]);
            this.sizeNums = Short.parseShort(dimensions[2]);
            this.upperParticularFloor = Short.parseShort(dimensions[3]);
    
            System.out.printf("Dimensiones leidas de forma correcta: %d; %d; %d; %d%n", 
                              sizeFloors, sizeCorridors, sizeNums, upperParticularFloor);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error convirtiendo las dimensiones a numeros: " + String.join(";", dimensions), e);
        }
        
        this.busyOffices = new HashSet<>();
        this.freeOffices = new TreeSet<>();
        this.stayPayments = new TreeMap<>(new StayComparatorByDateAndId());
    }
    
    private void processCoworkerOfficeData(String[] parts) {

        try {
            String[] locatorParts = parts[0].split(":");
            short floor = Short.parseShort(locatorParts[0]);
            short corridor = Short.parseShort(locatorParts[1]);
            short num = Short.parseShort(locatorParts[2]);
            String idCoworker = parts[1];
            char conf = parts[2].charAt(0);
            String date = parts[3];
    
            OfficeLocator locator = new OfficeLocator(floor, corridor, num);
            Office office = findOfficeByLocator(locator);
    
            if (office == null) {
                throw new IllegalArgumentException("Oficina con localizador no encontrada: " + locator);
            }
    
            office.setIdCoworker(idCoworker);
            office.setEntryDate(date);
    
            if (office instanceof OfficeConf) {            
                ((OfficeConf) office).setConfService(new ConfService());
                if (conf == 'Y') {
                    ((OfficeConf) office).activateService();
                }
            }
    
            freeOffices.remove(office); 
            busyOffices.add(office);
    
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error convirtiendo el localizador o los datos de oficina: " + String.join(";", parts), e);
        }
    }
    
    private Office findOfficeByLocator(OfficeLocator locator) {
    Office dummy = new Office(locator); // Office con solo el locator
    Office candidate = ((TreeSet<Office>) freeOffices).ceiling(dummy);

    if (candidate != null && candidate.getOfficeLocator().equals(locator)) {
        return candidate;
    }
    return null;
}


    private void initializeOffices() {
        
        for (short f = 0; f < sizeFloors; f++) {
            for (short c = 0; c < sizeCorridors; c++) {
                for (short n = 0; n < sizeNums; n++) {

                    OfficeLocator officeLocator = new OfficeLocator(f, c, n);
                
                    Office office;
    
                    if (f > upperParticularFloor) {
                        office = new OfficeConf(officeLocator);
                    } else {
                        office = new Office(officeLocator);
                    }
                
                    freeOffices.add(office);
                }
            }
        }
        
    }
    
    public short getSizeFloors() {
        return sizeFloors;
    }

    public void setSizeFloors(short sizeFloors) {
        this.sizeFloors = sizeFloors;
    }

    public short getSizeCorridors() {
        return sizeCorridors;
    }

    public void setSizeCorridors(short sizeCorridors) {
        this.sizeCorridors = sizeCorridors;
    }

    public short getSizeNums() {
        return sizeNums;
    }

    public void setSizeNums(short sizeNums) {
        this.sizeNums = sizeNums;
    }

    public short getUpperParticularFloor() {
        return upperParticularFloor;
    }

    public void setUpperParticularFloor(short upperParticularFloor) {
        this.upperParticularFloor = upperParticularFloor;
    }
 
    //guardamos
    public void saveCoworkingCenterToFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            
            writer.printf("%d;%d;%d;%d%n",
               sizeFloors, sizeCorridors, sizeNums, upperParticularFloor);
    
            Comparator<Office> comp = (o1, o2) -> {
                OfficeLocator l1 = o1.getOfficeLocator();
                OfficeLocator l2 = o2.getOfficeLocator();

                int cmp = Short.compare(l2.getFloor(), l1.getFloor());
                if (cmp != 0) return cmp;
                // los pasillos de menor a mayor
                cmp = Short.compare(l1.getCorridor(), l2.getCorridor());
                if (cmp != 0) return cmp;

                return Short.compare(l1.getNum(), l2.getNum());
            };
            TreeSet<Office> sortedBusy = new TreeSet<>(comp);
            sortedBusy.addAll(busyOffices);

            for (Office office : sortedBusy) {
                writer.println(office.toString());
            }
        }
    }
    
    public void coworkerIn(Coworker coworker, boolean conf, String fecha) {
        Office office = getAvailableOfficeForCoworker(coworker);
    
        if (office != null) {
            office.setIdCoworker(coworker.getId());
            office.setEntryDate(fecha);
    
            if (office instanceof OfficeConf) {
                ((OfficeConf) office).setConfService(new ConfService());
                if (conf) {
                    ((OfficeConf) office).activateService();
                }
            }
    
            // Mover oficina de libre a ocupada
            freeOffices.remove(office);
            busyOffices.add(office);
        }
    }
    
    private Office getAvailableOfficeForCoworker(Coworker coworker) {
        OfficeLocator referenceLocator = getReferenceLocatorForCoworker(coworker);
        Office referenceOffice = new Office(referenceLocator);
    
        Office office = ((TreeSet<Office>) freeOffices).ceiling(referenceOffice);
        
        if (office == null || !isOfficeSuitableForCoworker(office, coworker)) {
            return null;  // No hay oficina disponible o la oficina no es adecuada
        }
    
        return office;
    }
    
    private OfficeLocator getReferenceLocatorForCoworker(Coworker coworker) {
        if (coworker instanceof Particular) {
            return getParticularLocator();
        } else if (coworker instanceof Executive) {
            return getExecutiveLocator();
        } else if (coworker instanceof Employee) {
            return getEmployeeLocator();
        }
        return null;  // Si el coworker no es de ninguno de los tipos, retornamos null
    }
    
    private OfficeLocator getParticularLocator() {
        return new OfficeLocator(upperParticularFloor, (short) 0, (short) 0);
    }
    
    private OfficeLocator getExecutiveLocator() {
        return new OfficeLocator((short) (sizeFloors - 1), (short) 0, (short) 0);
    }
    
    private OfficeLocator getEmployeeLocator() {
        return new OfficeLocator((short) (sizeFloors - 2), (short) 0, (short) 0);
    }
    
    
    private boolean isOfficeSuitableForCoworker(Office office, Coworker coworker) {
        boolean isParticularAndFloorTooHigh = coworker instanceof Particular && office.getOfficeLocator().getFloor() > upperParticularFloor;
        boolean isEmployeeAndOnExecutiveFloor = coworker instanceof Employee && !(coworker instanceof Executive) && office.getOfficeLocator().getFloor() == (short) (sizeFloors - 1);
        
        return !(isParticularAndFloorTooHigh || isEmployeeAndOnExecutiveFloor);
    }
    
    public Stay coworkerOut(Coworker coworker, String exitDate) {
        for (Office office : busyOffices) {
            if (coworker.getId().equals(office.getIdCoworker())) {

                String entryDate = office.getEntryDate();
                boolean conf = office.hasConfService();
    
                clearOfficeData(office);
    
                deactivateConfServiceIfNeeded(office);
    
                updateOfficeCollections(office);
    
                return createStay(coworker, entryDate, exitDate, conf);
            }
        }
        return null;
    }
    

    private void clearOfficeData(Office office) {
        office.setIdCoworker(null);
        office.setEntryDate(null);
    }
    
    private void deactivateConfServiceIfNeeded(Office office) {
        if (office instanceof OfficeConf) {
            ((OfficeConf) office).deactivateService();
        }
    }
    
    private void updateOfficeCollections(Office office) {
        busyOffices.remove(office);
        freeOffices.add(office);
    }

    private Stay createStay(Coworker coworker, String entryDate, String exitDate, boolean conf) {
        return new Stay(coworker, entryDate, exitDate, conf);
    }
    
    public int getNumCoworkers() {
        return busyOffices.size();
    }
        
    private String repeatChar(char c, int count) {      //mapa, stringBuilder
        StringBuilder builder = new StringBuilder();    //evita tener que crear y destruir cualquier string
        for (int i = 0; i < count; i++) {               //en cada repetición del for
            builder.append(c);
        }
        return builder.toString();
    }

    public int computeCost(Stay stay) {     //STAY devuelve diferencia entre día de entrada y salida


        int days = stay.stayDays();
    
        Coworker coworker = stay.getCoworker();
        int costPerDay;
        if (coworker instanceof Executive) {
            costPerDay = EXECUTIVE_COST_PER_DAY;
        } else if (coworker instanceof Employee) {
            costPerDay = EMPLOYEE_COST_PER_DAY;
        } else {
            costPerDay = PARTICULAR_COST_PER_DAY;
        }
    
        int total = days * costPerDay;
    
        if (stay.isConf()) {
            total += days * CONF_COST;
        }
    
        return total;
    }
    
    public void registerStayPayment(Stay stay, int income) {    //clave(stay)-valor(income)
        stayPayments.put(stay, income);
    }

    public boolean saveStayPayments(String fileName) {
    File file = new File(fileName);

    try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) { 
         
        Set<String> idsSet = new TreeSet<>();
        for (Stay stay : stayPayments.keySet()) {
            idsSet.add(stay.getCoworker().getId());
        }

        writer.print("ids:");
        if (!idsSet.isEmpty()) {
            writer.println(String.join(";", idsSet));
        } else {
            writer.println();
        }

        int total = 0;
        for (int value : stayPayments.values()) {
            total += value;
        }
        writer.println("total:" + total);

        for (Map.Entry<Stay, Integer> entry : stayPayments.entrySet()) {
            Stay stay = entry.getKey();
            int payment = entry.getValue();
            writer.printf("%s;%s;%d%n", stay.getEntryDate(), stay.getCoworker().getId(), payment);
        }

        return true;

    } catch (IOException e) {
        System.err.println("Error al escribir el fichero: " + e.getMessage());
        return false;
    }
}

public boolean updateSeaViewOffices(String fileNameIn, String fileNameOut) {
    try (
        Scanner scanner = new Scanner(new File(fileNameIn));
        PrintWriter writer = new PrintWriter(new FileWriter(fileNameOut))
    ) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (isLineValid(line)) {
                processLineAndWriteToFile(line, writer);
            }
        }

        return true;

    } catch (IOException e) {
        System.err.println("Error procesando el archivo: " + e.getMessage());
        return false;
    }
}

private boolean isLineValid(String line) {
    return !line.isEmpty() && !line.startsWith("#");
}

private void processLineAndWriteToFile(String line, PrintWriter writer) {
    OfficeLocator locator = new OfficeLocator(line);
    Office officeRef = new Office(locator);

    if (!busyOffices.contains(officeRef)) {
        writer.println(locator);
    }
}

public String toMap() {
    final char separator = '|';     //finals porque no se pueden modificar 
    final int numGuiones = 4 + 18 * sizeNums;

    StringBuilder map = new StringBuilder();
    TreeSet<Office> totalSet = new TreeSet<>(Comparator.comparing(Office::getOfficeLocator));
    totalSet.addAll(freeOffices);
    totalSet.addAll(busyOffices);

    for (short floor = (short) (sizeFloors - 1); floor >= 0; floor--) {
        map.append(repeatChar('-', numGuiones)).append(" FLOOR ").append(floor).append("\n");

        for (short corridor = 0; corridor < sizeCorridors; corridor++) {
            String prefix = "XEP";
            if (floor > upperParticularFloor) prefix = "XE ";
            if (floor == sizeFloors - 1) prefix = "X  ";

            StringBuilder line = new StringBuilder(prefix).append(separator);

            for (short num = 0; num < sizeNums; num++) {
                Office office = null;

                for (Office o : totalSet) {
                    OfficeLocator loc = o.getOfficeLocator();
                    if (loc.getFloor() == floor && loc.getCorridor() == corridor && loc.getNum() == num) {
                        office = o;
                        break;
                    }
                }

                String loc = (office != null) ? office.getOfficeLocator().toString() : "---";
                String id = (office != null && office.getIdCoworker() != null) ? office.getIdCoworker() : "";
                if (id.isEmpty()) id = "         ";  // 9 espacios
                String bloque = String.format(" %-5s %-9s ", loc, id); // bloque de 18 chars
                line.append(bloque).append(separator);
            }

            map.append(line).append("\n");
        }
    }

    map.append(repeatChar('-', numGuiones)).append("\n");
    return map.toString();
}
    
}
