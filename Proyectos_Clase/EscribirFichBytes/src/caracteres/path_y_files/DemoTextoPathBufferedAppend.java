package caracteres.path_y_files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class DemoTextoPathBufferedAppend {

    private static final Path rutaFichero = Path.of("src/caracteres/path_y_files", "ficheroPathBufferedAppend.txt");
    //private static final String rutaFichero = "src/caracteres/path_y_files/ficheroPathBuffered.txt";

    public static void main(String[] args) {

        escribirPathBuffered();
        leerPathBuffered();

    }

    private static void escribirPathBuffered() {

        try (BufferedWriter bw = Files.newBufferedWriter(rutaFichero,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            for (int i = 1; i <= 10; i++) {

                //bw.write("Fila número " + i + "\n"); //Opcion1

                bw.write("Fila número " + i); //Opcion2
                bw.newLine();
            }
            System.out.println("Fichero escrito correctamente.");
        } catch (IOException e) {
            System.err.println("IOE generada en escribirBuffered " + e.getMessage());
        }
    }

    private static void leerPathBuffered() {
        try (BufferedReader br = Files.newBufferedReader(rutaFichero)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }


        } catch (IOException e) {
            System.err.println("IOE generada en escribirBuffered " + e.getMessage());
        }
    }


}
