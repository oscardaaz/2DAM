package datosBinarios;

import Objetos.Empleado;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class gestionEmpleados {

    private static Scanner sc = new Scanner(System.in);
    private static String nombreFichero;
    private static Path ruta = Path.of("src",nombreFichero);


    public static void main(String[] args) {
        //Actividades ficheros binarios (1.6)


        //escribirEmpleado();


    }


    private static void escribirEmpleado() {


        try (OutputStream os = Files.newOutputStream(RUTA);
             ObjectOutputStream oos = new ObjectOutputStream(os)) {

            int contador = 1;
            //Recorremos el ArrayList para escribir Persona a Persona.

            for (int i = 0; i <= 3; i++) {

                Empleado empleado1 = new Empleado();
                System.out.print("Introduce el nombre del empleado: ");
                empleado1.setNombre(sc.nextLine());
                empleados.add(empleado1);
                oos.writeObject(empleado1);
                System.out.printf("Persona %d escrita correctamente%n", contador++);
            }
        } catch (IOException ioe) {
            System.err.println("Error al escribir Persona " + ioe.getMessage());
        }
    }

}