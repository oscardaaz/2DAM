package datos.binarios.datos;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataStreams {

    public static void main(String[] args) {

        Path ruta = Path.of("src/demos/binarios/datos/personas.dat");

        escribirPersonas(ruta);

        leerPersonas(ruta);

    }

    private static void escribirPersonas(Path ruta) {

        String[] nombres = {"Oscar", "Juan", "Pedro","Manuel","Juanma"};
        int[] edades = {20, 30, 40, 50, 60};

        try (OutputStream flujoEscritura = Files.newOutputStream(ruta);DataOutputStream dos = new DataOutputStream(flujoEscritura)) {

    for (int i = 0; i < nombres.length; i++) {
        dos.writeUTF(nombres[i]);
        dos.writeInt(edades[i]);
    }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void leerPersonas(Path ruta) {


    }
}
