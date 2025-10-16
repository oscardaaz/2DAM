package programa1;

import java.io.IOException;
import java.io.InputStream;

public class p1 {
    public static void main(String[] args) {

        ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "DIR");
        try {
            Process p = pb.start();

            // Leer salida estándar
            try (InputStream is = p.getInputStream()) {
                int c;
                while ((c = is.read()) != -1) {
                    System.out.print((char) c);
                }
            }

            // Leer salida de error
            try (InputStream es = p.getErrorStream()) {
                int c;
                while ((c = es.read()) != -1) {
                    System.err.print((char) c);
                }
            }

            int exitVal = p.waitFor();
            System.out.println("\nValor de salida: " + exitVal);

        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
