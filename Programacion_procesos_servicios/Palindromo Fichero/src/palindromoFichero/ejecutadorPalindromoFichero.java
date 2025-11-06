package palindromoFichero;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ejecutadorPalindromoFichero {
    public static void main(String[] args) throws IOException, InterruptedException {

        // Leer cadena desde fichero
        String cadena = Files.readString(Paths.get("src/palindromoFichero/entrada.txt")).trim();
        System.out.println("Cadena leída: " + cadena);

        ProcessBuilder pb = new ProcessBuilder("java", "-jar",
                "out/artifacts/Palindromo_Fichero_jar/verificadorPalindromo.jar", cadena);

        // Salida normal por pantalla, errores a fichero errores.,txt
        pb.redirectError(new File("errores.txt"));
        // redirectOutput me da error
        // usamos redirectError así la salida normal va a la consola

        Process proceso = pb.start();

        // Mostrar salida normal por pantalla
        BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
        String linea;
        while ((linea = reader.readLine()) != null) {
            System.out.println(linea);
        }
        reader.close();

        int exitCode = proceso.waitFor();

        // Solo mustro errores si ha ocurrido un error si no salida normal.
        if (exitCode != 0 && Files.exists(Paths.get("errores.txt"))) {
            System.out.println("Error detectado (Mostrando errores.txt):");
            String contenido = Files.readString(Paths.get("errores.txt"));
            System.out.println(contenido);
        }
    }
}