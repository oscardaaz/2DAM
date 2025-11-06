package cadenaFichero;

import java.io.*;

public class mainCadenaFichero {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("No se ha introducido ninguna cadena como argumento.");
            System.exit(1);
        }

        String cadena = args[0];

        try (PrintWriter pw = new PrintWriter(new FileWriter("salida.txt"))) {
            for (int i = 0; i < 5; i++) {
                pw.println(cadena);
            }
            System.out.println("Cadena guardada en salida.txt correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el fichero: " + e.getMessage());
        }
    }
}