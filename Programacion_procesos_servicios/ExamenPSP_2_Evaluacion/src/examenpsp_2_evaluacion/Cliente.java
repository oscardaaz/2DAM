package examenpsp_2_evaluacion;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 *
 * @author oscar.domalo
 */
public class Cliente {
    
    // Ejecutar desde el panel de projects servidor y luego cliente en el lado izquierdo panel.
    public static void main(String[] args) {
        String opcion;
        int i = 1;

        do {

            Scanner sc = new Scanner(System.in);

            System.out.println("Introduce la incidencia: " + i);

            System.out.print("iD_USUARIO: ");
            String id_usuario = sc.nextLine();

            System.out.print("ID_DEPARTAMENTO: ");
            String id_departamento = sc.nextLine().trim();

            System.out.print("ID_DISPOSITIVO: ");
            String id_dispositivo = sc.nextLine().trim();

            System.out.print("DESCRIPCION_INCIDENCIA: ");
            String incidencia = sc.nextLine().trim();

            System.out.print("¡PRIORIDAD: ");
            String prioridad = sc.nextLine().toUpperCase().trim();

            LocalDate fecha = LocalDate.now();
            System.out.println("FECHA: " + fecha);

            LocalTime hora = LocalTime.now();
            System.out.print("HORA: " + hora);

            String cadena = id_usuario + ";" + id_departamento + ";" + id_dispositivo + ";" + incidencia + ";" + prioridad + ";" + fecha.toString() + ";" + hora.toString();

            System.out.println("");
            
            try (Socket socket = new Socket("localhost", 5000); DataOutputStream oos = new DataOutputStream(socket.getOutputStream()); DataInputStream dis = new DataInputStream(socket.getInputStream())) {

                oos.writeUTF(cadena);
                String respuesta = dis.readUTF();
                System.out.println(respuesta);
                 String respuesta2 = dis.readUTF();
                System.out.println(respuesta2); 

            } catch (IOException e) {
                System.err.println("Error al mandar hilo cliente(Main) " + e.getMessage());
                e.printStackTrace();
            }
            
            
            System.out.println("Introduce (*) para salir, cualquier cosa para continuar");
            opcion = sc.nextLine().trim();
            
            if (opcion.equals("*")) {
                System.out.println("Saliendo....");
            }
            i++;
        } while (!opcion.equals("*"));

    }

}
