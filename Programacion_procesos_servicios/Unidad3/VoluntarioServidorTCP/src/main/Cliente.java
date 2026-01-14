package main;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    private static final String HOST = "localhost";
    private static final int PUERTO = 5050;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    añadirEmpleado(sc);
                    break;
                case 2:
                    mostrarTotalEmpleados();
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 3);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n=== MENÚ EMPLEADOS ===");
        System.out.println("1. Añadir empleado");
        System.out.println("2. Mostrar total de empleados");
        System.out.println("3. Salir");
        System.out.print("Elige una opción: ");
    }

    private static void añadirEmpleado(Scanner sc) {
        System.out.println("\n--- Nuevo Empleado ---");

        System.out.print("DNI: ");
        String dni = sc.nextLine();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Edad: ");
        int edad = sc.nextInt();
        sc.nextLine();

        System.out.print("Departamento: ");
        String departamento = sc.nextLine();

        System.out.print("Salario: ");
        double salario = sc.nextDouble();
        sc.nextLine();

        Empleado empleado = new Empleado(dni, nombre, edad, departamento, salario);

        try (Socket socket = new Socket(HOST, PUERTO);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            oos.writeObject("AÑADIR");

            oos.writeObject(empleado);

            String respuesta = (String) ois.readObject();
            System.out.println("Servidor: " + respuesta);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: No se pudo conectar al servidor.");
        }
    }

    private static void mostrarTotalEmpleados() {
        try (Socket socket = new Socket(HOST, PUERTO);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            oos.writeObject("TOTAL");

            String respuesta = (String) ois.readObject();
            System.out.println("Servidor: " + respuesta);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: No se pudo conectar al servidor.");
        }
    }
}