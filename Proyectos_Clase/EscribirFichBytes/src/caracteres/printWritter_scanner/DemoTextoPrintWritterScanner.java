package caracteres.printWritter_scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class DemoTextoPrintWritterScanner {

    private static final Path rutaFichero = Path.of("src/caracteres/printWritter_scanner", "ficheroPrintWriter.txt");
    //private static final String rutaFichero = "src/caracteres/path_y_files/ficheroPathBuffered.txt";

    public static void main(String[] args) {

        escribirPrintWriter();
        leerConScanner();

    }


    private static void escribirPrintWriter() {

        //try(BufferedWriter bw = Files.newBufferedWriter(rutaFichero)) {
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(rutaFichero))) {
            for (int i = 1; i <= 10; i++) {

//                //bw.write("Fila número " + i + "\n"); //Opcion1
//                pw.write("Fila número " + i ); //Opcion2
//                pw.newLine();
                pw.println("Fila número: " + i);
            }
            System.out.println("Fichero escrito correctamente.");
        } catch (IOException e) {
            System.err.println("IOE generada en escribirPrintWriter " + e.getMessage());
        }
    }

    private static void leerConScanner() {
        try (Scanner sc = new Scanner(Files.newBufferedReader(rutaFichero,
                StandardCharsets.UTF_8 ))) {
            String linea;
            while (sc.hasNextLine()) {
                linea = sc.nextLine();
                System.out.println(linea);
            }


        } catch (IOException e) {
            System.err.println("IOE generada en leerConScanner " + e.getMessage());
        }
    }


}
