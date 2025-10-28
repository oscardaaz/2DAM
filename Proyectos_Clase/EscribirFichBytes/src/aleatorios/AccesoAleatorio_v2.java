package aleatorios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Scanner;

/**
 * En esta version 2 la longitud de los apellidos va a ser fija.
 */
public class AccesoAleatorio_v2 {

    private static final int LONG_APELLIDO = 20;
    private static final int BYTES_INT = 4;
    private static final int BYTES_DOUBLE = 8;
    private static final int BYTES_CHAR = 2;
    private static final int TAM_REGISTRO =
            BYTES_INT
                    + (LONG_APELLIDO * BYTES_CHAR)
                    + BYTES_INT
                    + BYTES_DOUBLE; //56 Bytes en total

    private static final Path RUTA = Path.of("src/aleatorios", "aleatorios_v2.dat");

    public static void main(String[] args) {
        try {
            if (RUTA.getParent() != null) Files.createDirectories(RUTA.getParent());

            escribirRegistros();

            //leerRegistrosCadenaFija();

            //modificarDepartamento(3);

            //modificarDepartamento(4);

            //Leer directamente el 5º registro y mostrarlo por consola
            //leerRegistroIndividual(5);

            //sumarSalarios();

            //Actualizar el salario del 3.er registro y mostrarlo
            //Subir un 10%
            //actualizarSalario(3);

            leerRegistrosCadenaFija();
            System.out.println();
            sumarSalarios();

        } catch (IOException e) {
            System.err.println("Error de I/O en el main");
        }

    }

    private static void leerRegistroIndividual(int numEmpleado) {

        int id, dept;
        String apellido;
        double salario;
        try (RandomAccessFile raf = new RandomAccessFile(RUTA.toFile(), "r")) {

            long posicion = (long) TAM_REGISTRO * (numEmpleado - 1);
            raf.seek(posicion);

            id = raf.readInt();
            apellido = leerCadenaFija(raf, LONG_APELLIDO);
            dept = raf.readInt();
            salario = raf.readDouble();
            System.out.printf(Locale.US, "Id: %-3d, Apellido: %-9s, Dept: %-3d, Salario: %12.2f%n%n"
                    , id, apellido, dept, salario);

        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S al leer " + e.getMessage());
        }
    }

    private static void sumarSalarios() {

        try (RandomAccessFile raf = new RandomAccessFile(RUTA.toFile(), "r")) {

            double salarioTotal = 0;
            int i = 1;
            long posicion = 0;

            while (posicion != raf.length()) {
                posicion = (long) TAM_REGISTRO * (i - 1) + (BYTES_INT * 2) + (LONG_APELLIDO * BYTES_CHAR);
                raf.seek(posicion);
                double salario = raf.readDouble();
                salarioTotal += salario;
                i++;
                posicion = raf.getFilePointer();
            }
            System.out.printf(Locale.US, "Salario total de todos los trabajadores = %.2f € %n", salarioTotal);

        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S al leer " + e.getMessage());
        }

    }

    private static void actualizarSalario(int numEmpleado) {

        try (RandomAccessFile raf = new RandomAccessFile(RUTA.toFile(), "rw")) {

            //Coloco el puntero
            long posicion = (long) TAM_REGISTRO * (numEmpleado - 1) + (BYTES_INT * 2) + (LONG_APELLIDO * BYTES_CHAR);
            raf.seek(posicion);

            double salario = raf.readDouble();

            salario = salario * 1.10;

            raf.seek(posicion);
            raf.writeDouble(Math.round(salario * 100) / 100.0);

            System.out.printf(Locale.US, "Salario del trabajador %d, actualizado de %.2f€ a %.2f€ %n", numEmpleado, salario / 1.10, salario);

        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S al leer " + e.getMessage());
        }
    }

    private static void escribirCadenaFija(RandomAccessFile raf, String ape, int longitud) {

        if (ape == null) ape = "";
        StringBuilder sb = new StringBuilder(ape);
        //Si el apellido es mayor que el tamaño definido (20 caracteres),
        // lo "truncamos"
        if (sb.length() > longitud) sb.setLength(longitud);
            // Si no, rellenamos con espacios hasta longitud
        else while (sb.length() < longitud) sb.append(' ');

        for (int i = 0; i < longitud; i++) {
            try {
                raf.writeChar(sb.charAt(i));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void modificarDepartamento(int numEmpleado) {

        try (Scanner sc = new Scanner(System.in); RandomAccessFile raf = new RandomAccessFile(RUTA.toFile(), "rw")) {

            System.out.print("Introduce el nuevo departamento: ");
            int nuevoDepartamento = sc.nextInt();
            sc.nextLine();
            System.out.println();

            //Coloco el puntero
            long posicion = (long) TAM_REGISTRO * (numEmpleado - 1) + BYTES_INT + (LONG_APELLIDO * BYTES_CHAR);

            raf.seek(posicion);

            //Escribimos
            raf.writeInt(nuevoDepartamento);

        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S al leer " + e.getMessage());
        }
    }

    private static void escribirRegistros() {
        String[] apellidos = {"Fernández", "Gil", "Nuñez", "otro", "otro2", "otro 3"};
        int[] dept = {10, 20, 30, 10, 20, 30};
        double[] salarios = {1000.45, 1100.00, 25000.00, 1500.00, 1890.50, 2789.23};

        try (RandomAccessFile raf = new RandomAccessFile(
                RUTA.toFile(), "rw")) {

            //Si quiero sobreescribir...
            raf.setLength(0); // "Borramos" el contenido del fichero

            for (int i = 0; i < apellidos.length; i++) {
                raf.writeInt(i + 1);
                escribirCadenaFija(raf, apellidos[i], LONG_APELLIDO);
                raf.writeInt(dept[i]);
                raf.writeDouble(salarios[i]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * v2 Lee todos los registros
     * utilizando leerCadenaFija para el apellido
     * en lugar de readUTF.
     */
    private static void leerRegistrosCadenaFija() {

        int id, dept;
        String apellido;
        double salario;
        try (RandomAccessFile raf = new RandomAccessFile(RUTA.toFile(), "r")) {
            //Nos colocamos al inicio del fichero
            raf.seek(0);
            //long posPuntero = 0;
            long posPuntero = raf.getFilePointer(); //Equivalente a long posPuntero = 0; Coge el dato de la variable;

            while (posPuntero != raf.length()) {
                id = raf.readInt();
                //apellido = raf.readUTF();
                apellido = leerCadenaFija(raf, LONG_APELLIDO);
                dept = raf.readInt();
                salario = raf.readDouble();
                System.out.printf(Locale.US, "Id: %-3d, Apellido: %-9s, Dept: %-3d, Salario: %9.2f € %n"
                        , id, apellido, dept, salario);
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

}
