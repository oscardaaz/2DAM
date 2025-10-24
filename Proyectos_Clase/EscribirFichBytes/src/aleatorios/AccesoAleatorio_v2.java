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

    private static final Path RUTA = Path.of("src/aleatorios","aleatorios_v2.dat");
    public static void main(String[] args) {
        try {
            if (RUTA.getParent() != null) Files.createDirectories(RUTA.getParent());

            escribirRegistros();

            leerRegistros();
            modificarDepartamento(2);

            //sumarSalarios();

            //Leer directamente el 5º registro y mostrarlo por consola
            //leerRegistro(5);

            //Actualizar el salario del 3er registro y mostrarlo
            //actualizarSalario(3);

            leerRegistros();

        } catch (IOException e) {
            System.err.println("Error de I/O en el main");
        }

    }

    private static void modificarDepartamento(int numEmpleado) {
        int dept;

        try (Scanner sc = new Scanner(System.in); RandomAccessFile raf = new RandomAccessFile(RUTA.toFile(),"rw")){

            System.out.println("Introduce el nuevo departamento: ");
            int nuevoDepartamento = sc.nextInt();

            //Coloco el puntero
            long posicion = (long) TAM_REGISTRO * (numEmpleado - 1) + BYTES_INT + (LONG_APELLIDO*BYTES_CHAR);

            raf.seek(posicion);

            //Escribimos
            raf.writeInt(nuevoDepartamento);


        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S al leer " +e.getMessage());
        }

    }

    private static void escribirRegistros(){
        String[] apellidos = {"Fernández","Gil","Nuñez","otro","otro2","otro 3"};
        int[] dept = {10,20,30,10,20,30};
        double[] salarios = {1000.45,1100.00,25000.00,1500.00,1890.50,2789.23};

        try(RandomAccessFile raf = new RandomAccessFile(
                RUTA.toFile(),"rw")){

            //Si quiero sobreescribir...
            raf.setLength(0); // "Borramos" el contenido del fichero

            for (int i = 0; i < apellidos.length; i++) {
                raf.writeInt(i+1);
                raf.writeUTF(apellidos[i]);
                raf.writeInt(dept[i]);
                raf.writeDouble(salarios[i]);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void leerRegistros(){

        int id, dept;
        String apellido;
        double salario;
        try (RandomAccessFile raf = new RandomAccessFile(RUTA.toFile(),"r")){
            //Nos colocamos al inicio del fichero
            raf.seek(0);
            //long posPuntero = 0;
            long posPuntero = raf.getFilePointer(); //Equivalente a long posPuntero = 0; Coge el dato de la variable;

            while (posPuntero != raf.length()){
                id = raf.readInt();
                apellido = raf.readUTF();
                dept = raf.readInt();
                salario = raf.readDouble();
                System.out.printf(Locale.US,"Id: %-3d, Apellido: %-9s, Dept: %-3d, Salario: %-12.2f%n"
                        ,id ,apellido ,dept, salario);
                posPuntero = raf.getFilePointer();

            }
        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S al leer " +e.getMessage());
        }


    }
}
