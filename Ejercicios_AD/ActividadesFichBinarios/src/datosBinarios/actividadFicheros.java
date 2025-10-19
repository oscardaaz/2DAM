package datosBinarios;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class actividadFicheros {

    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        //Actividad ficheros binarios (1.5) (Vehiculos)

        escribirPersonas();
        leerPersonas();

    }

    private static void escribirPersonas() {

        Path ruta = obtenerRutaFichero("Escribe el nombre del fichero a " +
                "escribir o sobreescribir (Recuerda que sea extension .dat o .bin): ");
        boolean existe = Files.exists(ruta);

        try (OutputStream flujoEscritura = Files.newOutputStream(ruta,
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE);
             DataOutputStream dos = new DataOutputStream(flujoEscritura)) {

            int cantidad = existe ? cantidadVehiculos() : 2;
            String mensaje = existe ? " a añadir: "
                    : ": ";
            String mensajeFichero = existe ? "Vehículos añadidos en el fichero correctamente: "
                    : "Vehículos escritos correctamente en el fichero: ";

            for (int i = 1; i <= cantidad; i++) {

                System.out.println("--Escribe los datos del vehiculo a registrar--");
                System.out.print("Escribe la marca del coche: ");
                String marca = sc.nextLine();

                System.out.print("Escribe el modelo del coche: ");
                String modelo = sc.nextLine();

                System.out.print("Escribe el color del coche: ");
                String color = sc.nextLine();

                System.out.print("¿Pintura metalizada? (si/no): ");
                String respuestaPintura = sc.nextLine();
                boolean pinturaMetalizada = respuestaPintura.equalsIgnoreCase("si");

                if (!respuestaPintura.equalsIgnoreCase("si") && !respuestaPintura.equalsIgnoreCase("no")) {
                    System.out.println("La respuesta no ha sido (si/no); se asignara un valor por defecto (no).");
                }

                System.out.print("Escribe el año de adquisición: ");
                int año = sc.nextInt();
                sc.nextLine();

                dos.writeUTF(marca);
                dos.writeUTF(modelo);
                dos.writeUTF(color);
                dos.writeBoolean(pinturaMetalizada);
                dos.writeInt(año);

                System.out.printf("El Vehiculo ( %s  %s ) ha sido registrado correctamente %n", marca, modelo);
                System.out.println();
            }

            System.out.println(mensajeFichero + ruta.toAbsolutePath() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void leerPersonas() {

        Path ruta = obtenerRutaFichero("Escribe el nombre del fichero a leer: ");

        try (InputStream flujoLectura = Files.newInputStream(ruta); DataInputStream dis = new DataInputStream(flujoLectura)) {

            while (true) {

                try {

                    String marca = dis.readUTF();
                    String modelo = dis.readUTF();
                    String color = dis.readUTF();
                    boolean pinturaMetalizada = dis.readBoolean();
                    int año = dis.readInt();

                    //System.out.printf("Marca: %s, Modelo: %s, Color: %s, Pintura Metalizada %s, año: %d%n%n", marca, modelo, color, (pinturaMetalizada ? "Sí" : "No"), año);

                    System.out.printf("Marca: %-8s  Modelo: %-12s  Color: %-8s%n", marca, modelo, color);


                } catch (EOFException e) {
                    System.out.println("\nFin de lectura");
                    break;
                }
            }

        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

    }

    private static Path obtenerRutaFichero(String mensaje) {
        System.out.print(mensaje);
        String nombreFichero = sc.nextLine();
        System.out.println();
        return Path.of(System.getProperty("user.dir"), nombreFichero);
    }

    private static int cantidadVehiculos() {
        System.out.print("Introduce la cantidad de vehículos a añadir: ");
        int cantidadEmpleados = sc.nextInt();
        System.out.println();
        sc.nextLine();
        return cantidadEmpleados;
    }
}