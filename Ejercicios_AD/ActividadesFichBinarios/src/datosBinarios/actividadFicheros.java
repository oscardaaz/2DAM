package datosBinarios;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class actividadFicheros {

    static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        //Actividades ficheros binarios (1.5 y 1.6)


        escribirPersonas();

        //leerPersonas();


    }


    private static void escribirPersonas() {

        Path ruta = obtenerRutaFichero("Escribe el nombre del fichero a escribir: ");

        try (OutputStream flujoEscritura = Files.newOutputStream(ruta);
             DataOutputStream dos = new DataOutputStream(flujoEscritura)) {

            for (int i = 0; i <= 2; i++) {

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

                /*while (!respuestaPintura.equals("si") && !respuestaPintura.equals("no")) {
                    System.out.println("Respuesta no válida. Por favor, escribe 'si' o 'no'.");
                    respuestaPintura = sc.nextLine().trim().toLowerCase();
                }*/

                if (!respuestaPintura.equalsIgnoreCase("si") && !respuestaPintura.equalsIgnoreCase("no")) {
                    System.out.println("La respuesta no ha sido (si/no); se añadirá un valor por defecto (no).");
                    pinturaMetalizada = false;
                }

                System.out.print("Escribe el año de adquisición: ");
                int año = sc.nextInt();

                sc.nextLine();


                dos.writeUTF(marca);
                dos.writeUTF(modelo);
                dos.writeUTF(color);
                dos.writeBoolean(pinturaMetalizada);
                dos.writeInt(año);

                i++;
                System.out.println();
            }

            System.out.println("Datos escritos correctamente en: " + ruta.toAbsolutePath());
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

                    System.out.printf("Marca: %s, Modelo: %s, Color: %s%n%n", marca, modelo, color);

                } catch (EOFException e) {
                    System.out.println("Fin del fichero");
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
}