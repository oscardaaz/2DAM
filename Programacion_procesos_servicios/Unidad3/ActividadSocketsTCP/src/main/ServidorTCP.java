package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {

    public static void main(String[] args) {
        final int PUERTO = 5000;

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor esperando conexión...");

            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado");

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(cliente.getInputStream())
            );
            PrintWriter salida = new PrintWriter(
                    cliente.getOutputStream(), true
            );

            String cadena;

            while (true) {
                cadena = entrada.readLine();

                if (cadena == null || cadena.equals("*")) {
                    System.out.println("Servidor finalizado.");
                    break;
                }

                int longitud = cadena.length();
                salida.println(longitud);
            }

            cliente.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
