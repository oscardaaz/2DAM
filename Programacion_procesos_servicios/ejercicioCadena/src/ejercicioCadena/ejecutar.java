package ejercicioCadena;

import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;
public class ejecutar {
    public static void main(String[] args) {
        /*Path directorio = Path.of("out/artifacts/ejercicioCadena_jar");
        ProcessBuilder pb = new ProcessBuilder("java","-jar", "ejercicioCadena.jar");
        pb.directory(directorio.toFile());
        pb.inheritIO();
        Process p = null;
        try {
            p = pb.start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error I/O");
        }*/

        // Pedir cadena en el proceso PADRE
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce una cadena: ");
        String cadena = sc.nextLine() + "\n";  // ← \n para simular ENTER
        sc.close();

        Path directorio = Path.of("out/artifacts/ejercicioCadena_jar");
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "ejercicioCadena.jar");
        pb.directory(directorio.toFile());

        try {
            Process p = pb.start();

            // Enviar la cadena al proceso hijo
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            writer.write(cadena);  // ← Enviamos lo que escribió el usuario
            writer.flush();
            writer.close();

            // Leer lo que el hijo escribe
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
            reader.close();

            p.waitFor();

        } catch (IOException | InterruptedException e) {
            System.err.println("Error I/O: " + e.getMessage());
        }
    }
}

