package ejercicioCadenaFichero;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


public class ejercicioCadenaFichero {

    private final static Path ruta = Path.of("fichero.txt");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce una cadena: ");
        String cadena = sc.nextLine();
        sc.close();

        //try(BufferedWriter bw = Files.newBufferedWriter(rutaFichero)) {
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(ruta))) {

            if (cadena.isEmpty()) {
                System.out.println("Error: No se introdujo ninguna cadena");
                System.exit(1);
            }

            for (int i = 0; i < 5; i++) {
                    pw.println(cadena);

                }
                System.out.println("Fichero escrito correctamente.");
        } catch (IOException e) {
            System.err.println("IOE generada en escribirPrintWriter " + e.getMessage());
        }

    }
}
