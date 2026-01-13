package cadenaFichero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ejecutadorCadenaFichero {

    public static void main(String[] args) {
        try {
            // Leer la cadena desde teclado
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Introduce una cadena: ");
            String cadena = br.readLine();

            // Construir el proceso
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-cp", "bin", "cadenaFichero.mainCadenaFichero", cadena
            );

            // Redirigir error a salida estándar
            pb.redirectErrorStream(true);

            // Ejecutar el proceso
            Process proceso = pb.start();

            // Leer la salida del proceso hijo
            BufferedReader BufferedReader = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream())
            );

            String linea;
            while ((linea = BufferedReader.readLine()) != null) {
                System.out.println(linea);
                
            }

            // Cerrar el BufferedReader
            BufferedReader.close();

            // Esperar a que termine el proceso
            int exitCode = proceso.waitFor();

            System.out.println("Proceso terminado correctamente");
            System.out.println("Fichero salida.txt guardado en: " + System.getProperty("user.dir"));

        } catch (IOException | InterruptedException e) {
            System.out.println("Error al ejecutar el programa: " + e.getMessage());
        }
    }


}
