package tareaTCP;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("DNI: ");
        String dni = sc.nextLine();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Edad: ");
        int edad = sc.nextInt(); sc.nextLine();

        System.out.print("Departamento: ");
        String dep = sc.nextLine();

        System.out.print("Salario: ");
        double salario = sc.nextDouble();

        sc.close();

        Empleado emp = new Empleado(dni, nombre, edad, dep, salario);

        try (Socket socket = new Socket("localhost", 5000);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {

            oos.writeObject(emp);
            String respuesta = dis.readUTF();
            System.out.println(respuesta);

        } catch (IOException e) {
            System.err.println("Error al mandar hilo cliente(Main) " + e.getMessage());
            e.printStackTrace();
        }
    }
}