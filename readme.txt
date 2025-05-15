Pablo Millor Costas
número de cuenta: 130
DNI: 35635941V

Información sobre las variables locales en CoworkerDB.java:

Método readCoworkersFile(String filename)
scanner (Scanner): para leer el archivo línea por línea.

line (String): Cada línea leída del archivo. almacena una línea de texto

fields (String[]): Array que almacena los valores obtenidos al dividir la línea por el carácter ;. Cada campo representa una parte de la información de un coworker.

type (String): Representa el tipo de coworker (P, E, X). Su nombre indica su papel.

seniority (float): La antigüedad del coworker en número flotante.

Método getCoworkerFromId(String id)
c (Coworker): Un objeto utilizado para iterar sobre la lista de coworkers y buscar un coworker con un ID específico.

id (String): Identificador que se pasa como argumento para buscar un coworker.

Método computeAverageSeniority()
total (float): Suma de las antigüedades de todos los coworkers.

Método computeRatioEmployees()
countEmployees (int): Número de empleados dentro de la lista de coworkers.

Método saveCoworkersToFile(String filename)
PrintWriter (PrintWriter): Utilizado para escribir en el archivo.

Método computeTotalCredit()
total (float): Suma total de los créditos de todos los coworkers.



Archivo CoworkingCenter.java

floor, corridor, num (short): Variables que representan el piso, pasillo y número de la oficina respectivamente.

Método processOfficeLine(String[] parts)
parts (String[]): Array con los datos de cada línea procesada para una oficina.

locatorParts (String[]): Array que contiene la división de la localización de la oficina (piso, pasillo, número).

idCoworker (String): El ID del coworker asignado a la oficina.

conf (char): Representa si la oficina tiene o no servicio de conferencia.

date (String): La fecha asociada al coworker en la oficina.

Método initializeOffices()

officeLocator (OfficeLocator): Objeto que almacena la localización de una oficina.

office (Office): Objeto que representa una oficina.

Método getAvailableOfficeForCoworker(Coworker coworker)
referenceLocator (OfficeLocator): Referencia para encontrar una oficina disponible.

referenceOffice (Office): Oficina de referencia utilizada para encontrar la oficina disponible.

Método getReferenceLocatorForCoworker(Coworker coworker)

referenceLocator (OfficeLocator): Objeto utilizado para determinar la localización de una oficina según el tipo de coworker.

Método registerStayPayment(Stay stay, int income)

income (int): El ingreso generado por el pago de la estancia.