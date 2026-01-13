package visualizaCadena;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ejecutorCadena {

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Introduce una cadena: ");
            String cadena = br.readLine();

            // Construir el proceso con ProcessBuilder
            ProcessBuilder pb = new ProcessBuilder("java", "-jar",
                    "out/artifacts/visualizaCadena_jar/mainCadena.jar", cadena );

            // Heredar la salida de error al mismo flujo
            pb.redirectErrorStream(true);

            // Iniciar el proceso
            Process proceso = pb.start();

            // Leer la salida del proceso
            BufferedReader salida = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()));

            String linea;
            while ((linea = salida.readLine()) != null) {
                System.out.println(linea);
            }

            // Esperar a que termine el proceso
            int codigoSalida = proceso.waitFor();

            if (codigoSalida != 0) {
                System.out.println("El programa terminó con código de error: " + codigoSalida);
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Error al ejecutar el programa: " + e.getMessage());
        }
    }
}
