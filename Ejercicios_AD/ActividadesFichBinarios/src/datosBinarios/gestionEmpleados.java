package datosBinarios;

import Objetos.Empleado;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Scanner;


public class gestionEmpleados {

    private static final Scanner sc = new Scanner(System.in).useLocale(Locale.US);

    public static void main(String[] args) {
        //Actividades ficheros binarios (1.6)

        escribirEmpleado();
        //leerEmpleados();
    }



    private static void escribirEmpleado() {

        Path ruta = obtenerRutaFichero("Escribe el nombre del fichero a escribir: ");

        try (OutputStream os = Files.newOutputStream(ruta);
             ObjectOutputStream oos = new ObjectOutputStream(os)) {

            for (int i = 1; i <= 3; i++) {

                System.out.print("Introduce el nombre del empleado: ");
                String nombre = sc.nextLine();

                System.out.print("Introduce el departamento del empleado: ");
                int departamento = sc.nextInt();

                System.out.print("Introduce el salario del empleado: (Separador de decimales con punto ...");
                double salario = sc.nextDouble();
                sc.nextLine();

                Empleado empleado = new Empleado(nombre, departamento, salario);
                oos.writeObject(empleado);
                System.out.printf("Persona %d escrita correctamente%n", i);

            }

        } catch (IOException ioe) {
            System.err.println("Error al escribir Persona " + ioe.getMessage());
        }
    }

    private static void leerEmpleados() {

        Path ruta = obtenerRutaFichero("Escribe el nombre del fichero a leer: ");
        try (InputStream is = Files.newInputStream(ruta);
             ObjectInputStream ois = new ObjectInputStream(is)) {
            //int contador = 1;
            while (true) {
                try {

                    Empleado empleados = (Empleado) ois.readObject();
                    //System.out.printf("Persona %d leida correctamente%n%n", contador++);

                    System.out.println(empleados);

                }catch (EOFException eofe) {

                    System.out.println("\nFin de lectura");
                    break;

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }


        } catch (IOException e) {
            System.err.println("Error al leer Persona " + e.getMessage());
        }
    }

    private static Path obtenerRutaFichero(String mensaje) {
        System.out.print(mensaje);
        String nombreFichero = sc.nextLine();
        System.out.println();
        return Path.of(System.getProperty("user.dir"),nombreFichero);
    }

}