package main;

import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {

    private static final int PUERTO = 5050;
    private static List<Empleado> listaEmpleados = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Servidor iniciado...");

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {

            while (true) {
                Socket cliente = servidor.accept();
                new HiloCliente(cliente).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class HiloCliente extends Thread {
        private Socket socket;

        public HiloCliente(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())
            ) {
                // Leer la opción del cliente
                String opcion = ois.readObject().toString();

                if (opcion.equals("AÑADIR")) {
                    // Recibir empleado
                    Empleado emp = (Empleado) ois.readObject();

                    System.out.println("Empleado recibido:");
                    System.out.println(emp);

                    boolean añadido = listaEmpleados.add(emp);
                    int total = listaEmpleados.size();

                    String mensaje = añadido
                            ? "Empleado añadido correctamente. Total empleados: " + total
                            : "No se pudo añadir el empleado.";

                    oos.writeObject(mensaje);

                } else if (opcion.equals("TOTAL")) {
                    // Enviar solo el total de empleados
                    int total = listaEmpleados.size();
                    oos.writeObject("Total de empleados registrados: " + total);
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}