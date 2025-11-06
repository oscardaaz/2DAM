package palindromo;

import java.io.*;

public class ejecutadorPalindromo {
    public static void main(String[] args) throws IOException, InterruptedException {

        // Leer cadena desde teclado
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduce una cadena: ");
        String cadena = br.readLine();

        // Ejecutar el JAR pasando la cadena como argumento
        ProcessBuilder pb = new ProcessBuilder("java", "-jar",
                "out/artifacts/Palindromo_jar/verificadorPalindromo.jar", cadena);
        Process proceso = pb.start();

        // Mostrar la salida de verificadorPalindromo
        BufferedReader br2 = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
        String linea;
        while ((linea = br2.readLine()) != null) {
            System.out.println(linea);
        }
        br2.close();

        proceso.waitFor();
    }
}
