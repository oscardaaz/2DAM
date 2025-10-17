package caracteres.buffered;

import java.io.*;
import java.nio.file.Path;

public class DemoTextoBuffered {

    //private static final Path rutaFichero = Path.of("src/caracteres/buffered","ficheroBuffered.txt");
    private static final String rutaFichero = "src/caracteres/buffered/ficheroBuffered.txt";
    public static void main(String[] args) {

        escribirBuffered();
        leerBuffered();



    }



    private static void escribirBuffered(){

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(rutaFichero))) {
            for (int i = 0; i <= 10; i++) {
                bw.write("Fila número " + i + "\n");
                //bw.newLine();
            }
            System.out.println("Fichero escrito correctamente.");
        }catch (IOException e){
            System.err.println( "IOE generada en escribirBuffered "+ e.getMessage());
        }
    }

    private static void leerBuffered() {
        try(BufferedReader br = new BufferedReader(new FileReader(rutaFichero))){
            String linea;
            while ((linea = br.readLine()) != null){
                System.out.println(linea);
            }


        }catch (IOException e){
            System.err.println( "IOE generada en escribirBuffered "+ e.getMessage());
        }
    }


}
