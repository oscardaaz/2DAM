package Actividad11;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
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


    private static final int BYTES_INT = 4;

    private static final int TAM_REGISTRO =
            BYTES_INT;

    public static void main(String[] args) {

        System.out.println();

        menu();


    }

    private static void menu() {
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

            if (opcion == 1) anadirNumero();
            else if (opcion == 2) buscarNumero();
            else if (opcion == 3) sustituirNumero();
            else if (opcion == 4) leerFichero();
            else if (opcion == 5) break;
            else System.out.println("La opción no existe!, Intentalo de nuevo:");

        } while (true);
        System.out.println("Saliendo...");

    }

    private static void anadirNumero() {

        try (RandomAccessFile raf = new RandomAccessFile(
                RUTA.toFile(), "rw")) {

            long posicion = raf.length();
            raf.seek(posicion);
            System.out.print("Introduce un numero a añadir: ");
            float numero = sc.nextFloat();
            raf.writeFloat(numero);
            System.out.println("Numero añadido correctamente al final del fichero\n");
        } catch (IOException e) {
            System.err.println("Error de E/S al escribir " + e.getMessage());
        }
    }

    private static void buscarNumero() {
        try (RandomAccessFile raf = new RandomAccessFile(
                RUTA.toFile(), "rw")) {

            raf.seek(0);
            long posPuntero = raf.getFilePointer(); //Equivalente a long posPuntero = 0; Coge el dato de la variable;
            System.out.print("Introduce el numero a buscar y sustituir: ");
            float numeroScanner = sc.nextFloat();
            while (posPuntero != raf.length()) {

                float numeroLectura = raf.readFloat();
                //System.out.printf("%f%n",numero);
                if (numeroLectura == numeroScanner) {
                    System.out.println("Numero encontrado!");
                    System.out.print("Introduce otro numero para sustituirle:");
                    long posicion = raf.getFilePointer() - 4;
                    raf.seek(posicion);
                    float numeroIntroducir = sc.nextFloat();
                    raf.writeFloat(numeroIntroducir);
                    break;
                } else posPuntero = raf.getFilePointer();
                if (posPuntero == raf.length()) System.out.println("Numero no encontrado en el fichero!");
            }
            System.out.println("Fin de lectura\n");


        } catch (IOException e) {
            System.err.println("Error de E/S al buscar y sustituir numero " + e.getMessage());
        }
    }

    private static void sustituirNumero() {

        try (RandomAccessFile raf = new RandomAccessFile(
                RUTA.toFile(), "rw")) {
            System.out.println("Introduce la posición del numero a modificar: ");
            int posUsuario = sc.nextInt();
            long posPuntero = (long) TAM_REGISTRO * (posUsuario - 1);
            boolean existe = posPuntero < raf.length() && posPuntero >= 0;
            if (existe) {
                System.out.println("La posición existe!");
                raf.seek(posPuntero);
                System.out.print("Introducir el nuevo numero: ");
                float numero = sc.nextFloat();
                raf.writeFloat(numero);
            } else System.out.println("Esa posición no existe!");

        } catch (IOException e) {
            System.err.println("Error de E/S al buscar numero por posición " + e.getMessage());
        }
    }

    private static void leerFichero() {
        try (RandomAccessFile raf = new RandomAccessFile(
                RUTA.toFile(), "r")) {

            raf.seek(0);
            long posPuntero = raf.getFilePointer(); //Equivalente a long posPuntero = 0; Coge el dato de la variable;
            int posicion = 1;
            System.out.println("---- Contenido del fichero ----");
            while (posPuntero != raf.length()) {
                float numero = raf.readFloat();
                System.out.printf("Posición %d: %f%n", posicion, numero);
                posPuntero = raf.getFilePointer();
                posicion++;
            }
            System.out.println("Fin de lectura\n");


        } catch (IOException e) {
            System.err.println("Error de E/S al leer fichero " + e.getMessage());
        }
    }

}
