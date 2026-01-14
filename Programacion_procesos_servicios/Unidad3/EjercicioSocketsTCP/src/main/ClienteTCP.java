package main;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteTCP {

    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PUERTO = 5000;

        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner sc = new Scanner(System.in)) {

            String cadena;

            do {
                System.out.print("Introduce una cadena (* para salir): ");
                cadena = sc.nextLine();

                salida.println(cadena); // Enviamos al servidor

                if (!cadena.equals("*")) {
                    String respuesta = entrada.readLine();
                    System.out.println("Número de caracteres: " + respuesta);
                }

            } while (!cadena.equals("*"));

            System.out.println("Cliente finalizado.");

        } catch (IOException e) {
            System.err.println("Error de comunicación con el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
