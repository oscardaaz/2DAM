package datos.binarios;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class DataStreams_apend {
    private static final Path RUTA = Path.of("src\\datos\\binarios\\data\\personas.dat");
    public static void main(String[] args) {


       // escribirInicial();
        leerPersonas(RUTA);
        anadirRegistro("Antonio",14);
        anadirRegistro("Luis",15);
        leerPersonas(RUTA);
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

    private static void anadirRegistro(String nombre, int edad){
        try (OutputStream flujoEscritura = Files.newOutputStream(RUTA,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
             DataOutputStream dos = new DataOutputStream(flujoEscritura)) {

                dos.writeUTF(nombre);
                dos.writeInt(edad);

            System.out.printf("Añadido => nombre: %s, edad: %d%n",nombre,edad);

        } catch (IOException e) {
            System.out.println("Error al añadir registro. " + e.getMessage());
        }
    }
    }

