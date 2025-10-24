package aleatorios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Locale;

public class AccesoAleatorio_v1 {

    private static final Path RUTA = Path.of("src/aleatorios", "aleatorios_v1.dat");

    public static void main(String[] args) {

        escribirRegistros();
        leerRegistro();

    }

    private static void escribirRegistros() {
        String[] apellidos = {"Fernández", "Gil", "Nuñez"};
        int[] dept = {10, 20, 30};
        double[] salarios = {1000.45, 1100.00, 25000.00};

        try (RandomAccessFile raf = new RandomAccessFile(
                RUTA.toFile(), "rw")) {

            //Si quiero sobreescribir...
            raf.setLength(0); // "Borramos" el contenido del fichero

            for (int i = 0; i < apellidos.length; i++) {
                raf.writeInt(i + 1);
                raf.writeUTF(apellidos[i]);
                raf.writeInt(dept[i]);
                raf.writeDouble(salarios[i]);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void leerRegistro() {

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
                apellido = raf.readUTF();
                dept = raf.readInt();
                salario = raf.readDouble();
                System.out.printf(Locale.US, "Id: %-3d, Apellido: %-9s, Dept: %-3d, Salario: %-12.2f%n"
                        , id, apellido, dept, salario);
                posPuntero = raf.getFilePointer();

            }
        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S al leer " + e.getMessage());
        }


    }
}
