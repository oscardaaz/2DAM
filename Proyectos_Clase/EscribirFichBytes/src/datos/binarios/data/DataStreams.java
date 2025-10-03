package datos.binarios.data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataStreams {

    public static void main(String[] args) {
        System.out.println();

        Path ruta = Path.of("src/datos/binarios/data/datosPersonas.dat");

        escribirPersonas(ruta);

        leerPersonas(ruta);

    }
    //prueba
    private static void escribirPersonas(Path ruta) {

        String[] nombres = {"Oscar", "Juan", "Pedro","Manuel","Juanma"};
        int[] edades = {20, 30, 40, 50, 60};

        try (OutputStream flujoEscritura = Files.newOutputStream(ruta);DataOutputStream dos = new DataOutputStream(flujoEscritura)) {

    for (int i = 0; i < nombres.length; i++) {
        dos.writeUTF(nombres[i]);
        dos.writeInt(edades[i]);

    }

            System.out.println("Datos escritos correctamente en: " + ruta.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void leerPersonas(Path ruta) {

        try (InputStream flujoLectura = Files.newInputStream(ruta); DataInputStream dis = new DataInputStream(flujoLectura)){

        while(true){

            try {

                String nombre = dis.readUTF();
                int edad = dis.readInt();

                System.out.printf("Nombre: %s, Edad: %d%n",nombre,
                        edad);



            } catch (EOFException e) {
                System.out.println("Fin del fichero");
               break;
            }
        }



        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

    }
}
