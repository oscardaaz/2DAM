package tareaTCP;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {
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

        Empleado emp = new Empleado(dni, nombre, edad, dep, salario);

        try (Socket socket = new Socket("localhost", 5000);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {

            oos.writeObject(emp);
            String resp = dis.readUTF();
            System.out.println(resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}