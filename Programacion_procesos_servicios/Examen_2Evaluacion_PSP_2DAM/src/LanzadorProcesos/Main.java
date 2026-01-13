package LanzadorProcesos;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("\nEjercicio 1 fecha del sistema");
        Process p = new ProcessBuilder("CMD", "/C", "DATE /T").start();

        // Leer la salida del comando
        InputStream is = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String fecha;
        while ((fecha = reader.readLine()) != null) {
            System.out.println("Fecha actual del sistema: " + fecha);
        }
        reader.close();

        // comprobación de error - 0 bien -1 mal
        int exitVal;
        try {
            exitVal = p.waitFor();
            System.out.println("Valor de Salida: " + exitVal + "\n");
        } catch (InterruptedException e) {
            System.err.println("Error en proceso Date " + e.getMessage());
        }



        // Ejecutamos el proceso DIR Ejercicio 2
        System.out.println("\nEjercicio 2 CMD Mostrado por consola");
        ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "DIR");
        Process pDIR = pb.start();

        try {
            InputStream is2 = pDIR.getInputStream();
            int c;
            while ((c = is2.read()) != -1) {
                System.out.print((char) c);
            }
            is2.close();
        } catch (Exception e) {
            System.err.println("Error en ejercicio2 DIR" + e.getMessage());
        }

        // Comprobamos si hay errores
        int exitValDIR;
        try {
            exitValDIR = pDIR.waitFor(); // recoge la salida de System.exit()
            System.out.println("Valor de salida: " + exitValDIR);
        } catch (InterruptedException e) {
            System.err.println("Error al recoger errores " + e.getMessage());
        }



        // Ejercicio 3
        ProcessBuilder pb3 = new ProcessBuilder("CMD","/C","DIR");

        File fOut = new File("salida.txt");
        File fErr = new File("error.txt");

        pb3.redirectOutput(fOut);
        pb3.redirectError(fErr);

        Process proceso = pb3.start();
        try {
            int exitVal3 = proceso.waitFor();
            System.out.println("\n\nEjercicio 3 Proceso CMD a ficheros.");
            System.out.println("Valor de salida: " + exitVal3);
        } catch (InterruptedException e) {
            System.err.println("Error al recoger errores " + e.getMessage());
        }



    }
}
