package tareaTCP;
import java.io.*;
import java.net.*;
import java.util.*;
public class Servidor {

    public static void main(String[] args) {

        List<Empleado> empleados = new ArrayList<>();

        try (ServerSocket servidor = new ServerSocket(5000)) {
            System.out.println("Servidor escuchando en puerto 5000...");

            while (true) {
                Socket cliente = servidor.accept();
                ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
                DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());

                Empleado emp = (Empleado) ois.readObject();
                System.out.println("Empleado recibido: " + emp);

                empleados.add(emp);
                dos.writeUTF("Empleado añadido correctamente. Total: " + empleados.size());

                cliente.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
