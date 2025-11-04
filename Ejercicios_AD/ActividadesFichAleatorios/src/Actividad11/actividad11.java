package Actividad11;

import java.nio.file.Path;
import java.util.Scanner;

public class actividad11 {
    /**
     * Utilizando un fichero de acceso aleatorio, realiza un programa Java que muestre al usuario
     * un MENÚ que le permita realizar las siguientes acciones:
     * 1. Añadir número de tipo float al final del fichero.
     * 2. Buscar en el fichero un número que se pedirá por consola. Si se encuentra, se
     * sustituirá por otro número que también se pedirá por consola. Si no, se mostrará un
     * mensaje indicando que el valor no se encuentra en el fichero
     * Nota: si un valor está repetido (varias apariciones en el fichero), solo se sustituirá la
     * primera.
     * 3. Sustituir el número de la posición indicada (pedida por consola), si existe la posición,
     * por otro número que también se pida por consola.
     * Nota: considera que el usuario introducirá un 1 para la primera posición, 2 para la
     * segunda, … y así sucesivamente
     * 4. Mostrar el fichero creado.
     * 5. Salir
     */

    private static final Path RUTA = Path.of("src/Actividad11", "ficheroActividad11.dat");
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println();
        menu();

    }

    private static void menu(){
        int opcion;
        do {
            String cadena = """
                    1. Añadir número de tipo float al final del fichero.
                    
                    2. Buscar en el fichero un número que se pedirá por consola. Si se encuentra, se
                    sustituirá por otro número que también se pedirá por consola. Si no, se mostrará un
                    mensaje indicando que el valor no se encuentra en el fichero
                    
                    3. Sustituir el número de la posición indicada (pedida por consola), si existe la posición,
                    por otro número que también se pida por consola.
                  
                    4. Mostrar el fichero creado.
                    
                    5. Salir
                    """;
            System.out.println(cadena);
            System.out.print("Introduce una opción: ");
            opcion = sc.nextInt();
        }while (opcion != 5);
        System.out.println("Saliendo...");

    }


}
