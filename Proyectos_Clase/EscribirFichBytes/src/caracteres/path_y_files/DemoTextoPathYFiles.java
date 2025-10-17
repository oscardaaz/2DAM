package caracteres.path_y_files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DemoTextoPathYFiles {

    private static final Path rutaFichero = Path.of("src/caracteres/path_y_files", "ficheroPathYFiles.txt");
    //private static final String rutaFichero = "src/caracteres/path_y_files/ficheroPathBuffered.txt";

    public static void main(String[] args) {

        escribirPathYFIles();
        leerPathYFiles();

    }

    private static void escribirPathYFIles() {

        List<String> lineas = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            lineas.add("Fila numero: " + i);
        }
        try {
            Files.write(rutaFichero, lineas);
            System.out.println("Fichero escrito correctamente.");
        } catch (IOException e) {
            System.err.println("IOE generada en escribirPathYFiles " + e.getMessage());
        }
    }

    private static void leerPathYFiles() {

        try {
            List<String> lineas = Files.readAllLines(rutaFichero);

            for (String elemento : lineas) {
                System.out.println(elemento);
            }
        } catch (IOException e) {
            System.err.println("IOE generada en leerPathYFiles " + e.getMessage());
        }


    }
}
