package datos.binarios.objetos;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ObjectStreams_v1 {

    private static final Path RUTA = Path.of("personas_objetos.dat");


    public static void main(String[] args) {

    escribirPersonas();
    System.out.println();
    leerPersonas();

    }

    private static void leerPersonas() {
        try (InputStream is = Files.newInputStream(RUTA);
             ObjectInputStream ois = new ObjectInputStream(is)) {
                 int contador = 1;
            while (true) {
                try {

                    Persona persona = (Persona) ois.readObject();
                    System.out.printf("Persona %d leida correctamente%n", contador++);
                    System.out.println(persona);

                }catch (EOFException eofe) {
                    System.out.println("Fin de lectura");
                    break;

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }


        } catch (IOException e) {
            System.err.println("Error al leer Persona " + e.getMessage());
        }
    }

    private static void escribirPersonas() {

        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona("nombre1", 1));
        personas.add(new Persona("nombre2", 2));
        personas.add(new Persona("nombre3", 3));
        personas.add(new Persona("nombre4", 4));
        personas.add(new Persona("nombre5", 5));
        personas.add(new Persona("nombre6", 6));
        personas.add(new Persona("nombre7", 7));
        personas.add(new Persona("nombre8", 8));
        personas.add(new Persona("nombre9", 9));

        try (OutputStream os = Files.newOutputStream(RUTA);
             ObjectOutputStream oos = new ObjectOutputStream(os)) {

            int contador = 1;
            //Recorremos el ArrayList para escribir Persona a Persona.
            for (Persona persona : personas) {
                oos.writeObject(persona);
                System.out.printf("Persona %d escrita correctamente%n", contador++);
            }
        } catch (IOException ioe) {
            System.err.println("Error al escribir Persona " + ioe.getMessage());
        }
    }

}
