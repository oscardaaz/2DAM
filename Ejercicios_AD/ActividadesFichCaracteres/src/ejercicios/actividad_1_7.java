package ejercicios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class actividad_1_7 {

    private static final Path rutaFichero = Path.of("caracteres.txt");
    public static void main(String[] args) {

        //escribirFichero();
        leerFichero();
        //escribirPathBuffered();
        //leerPathBuffered();
    }

    private static void escribirFichero() {

        List<String> auxiliar = new ArrayList<>();
        String mensaje = "Este es un método que añade contenido a un fichero de texto carácter a\n" +
                "carácter o utilizando buffers.";
        auxiliar.add(mensaje);

        try {
            Files.write(rutaFichero, auxiliar);
            System.out.println("Fichero escrito correctamente." + rutaFichero.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("IOE generada en escribirPathYFiles " + e.getMessage());
        }
    }

    private static void leerFichero() {

        try {
            List<String> auxiliar = Files.readAllLines(rutaFichero);

            for (String elemento : auxiliar) {

                //System.out.println(elemento.toUpperCase().trim());
                System.out.println(elemento.replace(" ","").toUpperCase());

            }
        } catch (IOException e) {
            System.err.println("IOE generada en leerPathYFiles " + e.getMessage());
        }


    }

   /* private static void escribirPathBuffered(){
        String mensaje = "Este es un método que añade contenido a un fichero de texto carácter a\n" +
                "carácter o utilizando buffers.";
        try(BufferedWriter bw = Files.newBufferedWriter(rutaFichero)) {

            bw.write(mensaje);

            System.out.println("Fichero escrito correctamente.");
        }catch (IOException e){
            System.err.println( "IOE generada en escribirBuffered "+ e.getMessage());
        }'
    }

    private static void leerPathBuffered() {
        try(BufferedReader br = Files.newBufferedReader(rutaFichero)){
            String linea;
            while ((linea = br.readLine()) != null){
                String mensaje = linea.replace(" ", "").toUpperCase();
                System.out.println(mensaje);
            }

        }catch (IOException e){
            System.err.println( "IOE generada en escribirBuffered "+ e.getMessage());
        }
    }*/


}
