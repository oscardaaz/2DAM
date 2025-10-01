package demos.binarios;
import java.io.*;

import java.nio.file.Path;

public class App {
    public static void main(String[] args) throws Exception {
        //Clase NIO
        // Ruta
        Path ruta = Path.of("fichBytes.dat");

        
        int i;

        try (FileOutputStream flujoSalida = new FileOutputStream(ruta.toFile());
            FileInputStream flujoEntrada = new FileInputStream(ruta.toFile())) {

            //Escribimos los numeros del 1 al 100 como bytes.    
            for (i = 0; i < 100; i++) {
                flujoSalida.write(i);
                
            }
            
            while ((i = flujoEntrada.read()) != -1) {

                System.out.println(i);
                
            }
        } catch (IOException e) {
            System.out.println("Error E/S: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
