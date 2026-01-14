package main;

import java.io.*;
import java.net.*;

public class ServidorTCP {

    public static void main(String[] args) {
        final int PUERTO = 5000;

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor esperando conexión...");

            try (Socket cliente = servidor.accept();
                 BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                 PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)) {

                System.out.println("Cliente conectado");

                String cadena;
                while ((cadena = entrada.readLine()) != null) {
                    if (cadena.equals("*")) {
                        System.out.println("Servidor finalizado.");
                        break;
                    }
                    salida.println(cadena.length());
                }

            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
