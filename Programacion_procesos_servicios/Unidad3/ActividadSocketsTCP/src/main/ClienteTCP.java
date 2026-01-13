package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteTCP {

    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PUERTO = 5000;

        try (Socket socket = new Socket(HOST, PUERTO)) {

            BufferedReader teclado = new BufferedReader(
                    new InputStreamReader(System.in)
            );
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            PrintWriter salida = new PrintWriter(
                    socket.getOutputStream(), true
            );

            String cadena;

            do {
                System.out.print("Introduce una cadena (* para salir): ");
                cadena = teclado.readLine();

                salida.println(cadena);

                if (!cadena.equals("*")) {
                    String respuesta = entrada.readLine();
                    System.out.println("Número de caracteres: " + respuesta);
                }

            } while (!cadena.equals("*"));

            System.out.println("Cliente finalizado.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
