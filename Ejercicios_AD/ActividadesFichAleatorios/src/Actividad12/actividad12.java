package Actividad12;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class actividad12 {

    /**
     * 1. Crear un fichero de acceso aleatorio que contenga referencias de productos (de
     * tipo entero) y su precio (de tipo double).
     * Rellenar el fichero con 25 productos con su referencia y precio. El precio será un
     * valor aleatorio entre 1 y 200.
     * 2. Una vez relleno el fichero, mostrarlo por consola.
     * 3. Recorrer el fichero y modificar los precios:
     * – Si el precio es > 100, decrementarlo un 10%
     * – Si el precio es <= 100, incrementarlo un 10%
     * 4. Mostrar de nuevo el contenido del fichero para comprobar que los cambios se
     * han realizado correctamente
     */

    private static final Path RUTA = Path.of("src/Actividad12", "ficheroActividad12.dat");
    private static final Scanner sc = new Scanner(System.in);


    private static final int BYTES_INT = 4;
    private static final int BYTES_DOUBLE = 8;

    private static final int TAM_REGISTRO =
            BYTES_INT + BYTES_DOUBLE; //12 BYTES

    public static void main(String[] args) {
        ejecucion();
    }

    private static void crearFichero() {
        try {
            Files.deleteIfExists(RUTA);
            Files.createFile(RUTA);
            System.out.println("\nFichero creado correctamente");

        } catch (IOException e) {
            System.err.println("Error E/S al crear fichero");
        }
    }

    private static void anadirProductos() {
        try (RandomAccessFile raf = new RandomAccessFile(
                RUTA.toFile(), "rw")) {
//            for (int i = 1 ; i <= 25 ; i++){
//                Random generadorAleatorios = new Random();
//                double aleatorio = 1 + generadorAleatorios.nextDouble(200);
//                double valor = Math.random();
//                Producto producto = new Producto(i,aleatorio);
//                raf.writeUTF(producto.toString());
//            }

            for (int i = 1; i <= 25; i++) {
                Random generadorAleatorios = new Random();
                double aleatorio = 1 + generadorAleatorios.nextDouble(200);
                raf.writeInt(i);
                raf.writeDouble(aleatorio);

            }

        } catch (IOException e) {
            System.out.println("Error E/S al añadir productos " + e.getMessage());
        }
    }

    private static void leerFichero() {
        try (RandomAccessFile raf = new RandomAccessFile(RUTA.toFile(), "r")) {

            System.out.println("Productos: (referencia y precio) ");
            raf.seek(0);
            long posPuntero = raf.getFilePointer();
            while (posPuntero != raf.length()){

               int referencia = raf.readInt();
               double precio = raf.readDouble();
                System.out.printf(Locale.US,"Referencia: %-3d Precio: %7.2f€%n",referencia,precio);
                posPuntero = raf.getFilePointer();
            }
        } catch (IOException e) {
            System.err.println("Error E/S al leer fichero");
        }
    }

    private static void actualizarPrecios(){
        try (RandomAccessFile raf = new RandomAccessFile(RUTA.toFile(),"rw")){
            System.out.println("\nActualizando precios...");
            raf.seek(0);
            long posPuntero = raf.getFilePointer();
            while (posPuntero != raf.length()){

                int referencia = raf.readInt();
                double precio = raf.readDouble();
                posPuntero = raf.getFilePointer() - 8;
                raf.seek(posPuntero);
                if (precio > 100){
                    raf.writeDouble(precio * 0.90);
                }
                if (precio <= 100){
                    raf.writeDouble(precio * 1.10);
                }

                //System.out.printf("Referencia: %-3d Precio: %7.2f€%n",referencia,precio);
                posPuntero = raf.getFilePointer();
            }

            System.out.println("Precios actualizados correctamente!\n");
        }catch (IOException e) {
            System.err.println("Error E/S al actualizar precios en fichero");
        }
    }

    private static void ejecucion() {
        crearFichero();
        anadirProductos();
        leerFichero();
        actualizarPrecios();
        leerFichero();
    }
}
