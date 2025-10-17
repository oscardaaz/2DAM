package caracteres.path_y_files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class DemoTextoPathBuffered {

    private static final Path rutaFichero = Path.of("src/caracteres/path_y_files","ficheroPathBuffered.txt");
    //private static final String rutaFichero = "src/caracteres/path_y_files/ficheroPathBuffered.txt";

    public static void main(String[] args) {

        escribirPathBuffered();
        leerPathBuffered();

    }



    private static void escribirPathBuffered(){

        try(BufferedWriter bw = Files.newBufferedWriter(rutaFichero)) {
            for (int i = 1; i <= 10; i++) {

                //bw.write("Fila número " + i + "\n"); //Opcion1

                bw.write("Fila número " + i ); //Opcion2
                bw.newLine();
            }
            System.out.println("Fichero escrito correctamente.");
        }catch (IOException e){
            System.err.println( "IOE generada en escribirBuffered "+ e.getMessage());
        }
    }

    private static void leerPathBuffered() {
        try(BufferedReader br = Files.newBufferedReader(rutaFichero)){
            String linea;
            while ((linea = br.readLine()) != null){
                System.out.println(linea);
            }


        }catch (IOException e){
            System.err.println( "IOE generada en escribirBuffered "+ e.getMessage());
        }
    }


}
