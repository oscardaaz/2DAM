package palindromoFichero;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ejecutableFichero {

    public static void main(String[] args) {
        // Leer cadena desde fichero
        String cadena = null;
        try {
            cadena = Files.readString(Path.of("entrada.txt")).trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ProcessBuilder pb = new ProcessBuilder("java", "Palindromo");

        // Redirigir error a fichero
        pb.redirectError(new File("errores.txt"));
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);

        Process p = null;
        try {
            p = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Escribir cadena en la entrada del proceso
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        try {
            writer.write(cadena);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            p.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
