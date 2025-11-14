package Actividad10;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

public class actividad10 {
    private static final Path RUTA = Path.of("src/Actividad10", "fichero.dat");

    private static final int BYTES_INT = 4;
    private static final int TAM_REGISTRO =
            BYTES_INT;
    //4 Bytes en total

    /**
     * a. Cree un fichero de acceso aleatorio llamado fichero.dat
     * b. Muestre por consola el tamaño del fichero (que en este punto debería ser 0
     * bytes)
     * c. Escriba en el fichero los números del 0 al 199
     * d. Muestre de nuevo el tamaño del fichero
     * e. Muestre por consola el primer número
     * f. Muestre por consola el segundo número
     * g. Muestre por consola el décimo número
     * h. Modifique el undécimo número, que deberá ser ahora 555
     * i. Añada el número 999 al final del fichero
     * j. Muestre de nuevo el tamaño del fichero
     * k. Muestre por consola el undécimo número
     * l. Por último, muestre el contenido completo del fichero por consola.
     */

    public static void main(String[] args) {

//        crearFichero();
//        tamanoFichero();
//        escribirFichero();
//        tamanoFichero();
//        leerFicheroIndividual("El primer número es:", 1);
//        leerFicheroIndividual("El segundo número es:", 2);
//        leerFicheroIndividual("El décimo número es:", 10);
//        modificarNumero(11, 555);
//        anadirAlFinal(999);
//        tamanoFichero();
//        leerFicheroIndividual("El undécimo número es:", 11);
//        leerFichero();

        ejercicioCompleto();
    }

    // a. Cree un fichero de acceso aleatorio llamado fichero.dat
    private static void crearFichero() {
        try {
            Files.deleteIfExists(RUTA);
            Files.createFile(RUTA);
            System.out.println("\nFichero creado correctamente");

        } catch (IOException e) {
            System.err.println("Error E/S fichero");
        }
    }

    // b. Muestre por consola el tamaño del fichero (que en este punto debería ser 0 * bytes)
    private static void tamanoFichero() {
        try (RandomAccessFile raf = new RandomAccessFile(
                RUTA.toFile(), "r")) {

            System.out.printf("La longitud actual del fichero %s%n", raf.length());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // c. Escriba en el fichero los números del 0 al 199
    private static void escribirFichero() {
        try (RandomAccessFile raf = new RandomAccessFile(
                RUTA.toFile(), "rw")) {

            //Si quiero sobreescribir...
            raf.setLength(0); // "Borramos" el contenido del fichero
            System.out.println("Escribimos los numeros del 0 al 199 en el fichero");
            for (int i = 0; i < 200; i++) {
                raf.writeInt(i);
            }

        } catch (IOException e) {
            System.err.println("Error de E/S al escribir " + e.getMessage());
        }

    }

    // e. Muestre por consola el primer número
    private static void leerFicheroIndividual(String mensaje, int numero) {

        int valor;
        try (RandomAccessFile raf = new RandomAccessFile(RUTA.toFile(), "r")) {

            long posicion = (long) TAM_REGISTRO * (numero - 1);
            raf.seek(posicion);


            valor = raf.readInt();
            System.out.printf("%s %d%n", mensaje, valor);


        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S al leer " + e.getMessage());
        }
    }

    //h. Modifique el undécimo número, que deberá ser ahora 555
    private static void modificarNumero(int numero, int nuevoValor) {
        try (RandomAccessFile raf = new RandomAccessFile(RUTA.toFile(), "rw")) {

            //Coloco el puntero
            long posicion = (long) TAM_REGISTRO * (numero - 1);
            raf.seek(posicion);

            raf.writeInt(nuevoValor);
            System.out.println("Modificamos el undécimo valor (lo cambiamos por 555)...");

        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S al leer " + e.getMessage());
        }
    }

    // l. Por último, muestre el contenido completo del fichero por consola.
    private static void leerFichero() {

        try (RandomAccessFile raf = new RandomAccessFile(RUTA.toFile(), "r")) {
            //Nos colocamos al inicio del fichero
            raf.seek(0);
            //long posPuntero = 0;
            long posPuntero = raf.getFilePointer(); //Equivalente a long posPuntero = 0; Coge el dato de la variable;
            System.out.println("-----------------  CONTENIDO FICHERO  ---------------");
            while (posPuntero != raf.length()) {
                int numero = raf.readInt();
                System.out.printf("Número %d%n", numero);
                posPuntero = raf.getFilePointer();
            }
            System.out.println("-----------------  FIN DEL FICHERO  ---------------");
        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S al leer " + e.getMessage());
        }
    }

    //i. Añada el número 999 al final del fichero
    private static void anadirAlFinal(int valor) {
        try (RandomAccessFile raf = new RandomAccessFile(
                RUTA.toFile(), "rw")) {
            long posicion = raf.length();
            raf.seek(posicion);
            raf.writeInt(valor);

        } catch (IOException e) {
            System.err.println("Error de E/S al escribir " + e.getMessage());
        }
    }

    /*Creo una función solo para ejecutar las funciones en orden según lo que
    pide el programa y con el output esperado. (Solo por comodidad, esto no se suele hacer) */
    private static void ejercicioCompleto() {
        crearFichero();
        tamanoFichero();
        escribirFichero();
        tamanoFichero();
        leerFicheroIndividual("El primer número es:", 1);
        leerFicheroIndividual("El segundo número es:", 2);
        leerFicheroIndividual("El décimo número es:", 10);
        modificarNumero(11, 555);
        anadirAlFinal(999);
        tamanoFichero();
        leerFicheroIndividual("El undécimo número es:", 11);
        leerFichero();
    }


}



