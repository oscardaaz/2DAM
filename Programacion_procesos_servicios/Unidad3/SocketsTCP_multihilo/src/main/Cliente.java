package main;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    private static final String HOST = "localhost";
    private static final int PUERTO = 5050;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Dni: ");
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

        Empleado empleado = new Empleado(dni, nombre, edad, departamento, salario);

        try (Socket socket = new Socket(HOST, PUERTO);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            oos.writeObject(empleado);
            String respuesta = ois.readObject().toString();
            System.out.println("Servidor: " + respuesta);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
