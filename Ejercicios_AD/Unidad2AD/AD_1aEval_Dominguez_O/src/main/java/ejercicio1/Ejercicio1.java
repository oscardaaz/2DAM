package ejercicio1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Scanner;

public class Ejercicio1 {

    private static final int LONG_TIPO = 5;
    private static final int BYTES_INT = 4;
    private static final int BYTES_DOUBLE = 8;
    private static final int BYTES_CHAR = 2;
    private static final int TAM_REGISTRO =
            BYTES_INT
                    + (LONG_TIPO * BYTES_CHAR)
                    + BYTES_INT
                    + BYTES_DOUBLE;

    private static final Scanner sc = new Scanner(System.in);

    private static final Path RUTA = Path.of("src/main/java/ejercicio1", "animales.dat");
    public static void main(String[] args) {
    try {
        if (RUTA.getParent() != null) Files.createDirectories(RUTA.getParent());

        escribirRegistros();
        leerRegistrosCadenaFija();
        mostrarDatos();


    }catch (IOException e) {
        System.err.println("Error de I/O en el main");
    }


    }

    private static void escribirRegistros() {
        int edad;
        double peso;
        String tipo;
        System.out.print("Tipo (Perro / Gato / otro): ");
        tipo = sc.nextLine();

        if (tipo.toLowerCase().trim().equals("perro")){
            tipo = "Perro";
        }
        else if (tipo.toLowerCase().trim().equals("gato")){
            tipo = "Gato";
        } else tipo = "Otro";

        System.out.print("Edad: ");
        edad = sc.nextInt();
        sc.nextLine();

        System.out.print("Peso: ");
        peso = sc.nextDouble();
        sc.nextLine();
        try (RandomAccessFile raf = new RandomAccessFile(
                RUTA.toFile(), "rw")) {

            long posicion = raf.length();
            raf.seek(posicion);

            escribirCadenaFija(raf, tipo);

            raf.writeInt(edad);
            raf.writeDouble(peso);
            System.out.println("Animal registrado correctamente.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void escribirCadenaFija(RandomAccessFile raf, String ape) {

        if (ape == null) ape = "";
        StringBuilder sb = new StringBuilder(ape);
        //Si el apellido es mayor que el tamaño definido (20 caracteres),
        // lo "truncamos"
        if (sb.length() > Ejercicio1.LONG_TIPO) sb.setLength(Ejercicio1.LONG_TIPO);
            // Si no, rellenamos con espacios hasta longitud
        else while (sb.length() < Ejercicio1.LONG_TIPO) sb.append(' ');

        for (int i = 0; i < Ejercicio1.LONG_TIPO; i++) {
            try {
                raf.writeChar(sb.charAt(i));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static void leerRegistrosCadenaFija() {

        int id, edad;
        String tipo;
        double peso;
        try (RandomAccessFile raf = new RandomAccessFile(RUTA.toFile(), "r")) {
            //Nos colocamos al inicio del fichero
            raf.seek(0);
            //long posPuntero = 0;
            long posPuntero = raf.getFilePointer(); //Equivalente a long posPuntero = 0; Coge el dato de la variable;
            System.out.println("\n--- LISTADO DE ANIMALES ---");
            while (posPuntero != raf.length()) {
                //id = raf.readInt();
                //apellido = raf.readUTF();
                tipo = leerCadenaFija(raf, LONG_TIPO);
                edad = raf.readInt();
                peso = raf.readDouble();
                System.out.printf(Locale.US, "Tipo: %-9s, Edad: %-3d, Peso: %9.2f kg %n"
                        , tipo, edad, peso);
                posPuntero = raf.getFilePointer();

            }
        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S al leer " + e.getMessage());
        }


    }


    private static String leerCadenaFija(RandomAccessFile raf, int longApellido) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longApellido; i++) {
            try {
                sb.append(raf.readChar());
            } catch (IOException e) {
                System.err.println("Error de E/S al leer " + e.getMessage());
            }
        }
        return sb.toString();

    }

    private static void mostrarDatos(){
        try (RandomAccessFile raf = new RandomAccessFile(RUTA.toFile(), "r")) {

            int totalPerros = 0, totalGatos = 0;
            int i =0;
            double pesoMedio = 0;

            raf.seek(0);
            long posicion = raf.getFilePointer();

            while (posicion != raf.length()) {

               String aux = leerCadenaFija(raf,LONG_TIPO);
                if (aux.trim().equals("Perro")){
                    totalPerros++;
                }
                if (aux.trim().equals("Gato")){
                    totalGatos++;
                }
                int edad = raf.readInt();
                double peso = raf.readDouble();
                pesoMedio += peso;
                i++;
                posicion = raf.getFilePointer();

            }

            System.out.println("\nPerros: "+ totalPerros);
            System.out.println("Gatos: "+ totalGatos);
            System.out.println("Peso medio: " + pesoMedio / i);

        } catch (IOException e) {
            System.err.println("Error de E/S al mostrar datos " + e.getMessage());
        }
    }
}
