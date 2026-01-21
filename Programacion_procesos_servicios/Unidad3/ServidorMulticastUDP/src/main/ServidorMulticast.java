package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServidorMulticast {
    public static void main(String[] args) {
        String direccion = "230.0.0.0";
        int puerto = 5000;

        System.out.println("Servidor Multicast iniciado");

        try {
            InetAddress grupo = InetAddress.getByName(direccion);
            DatagramSocket socket = new DatagramSocket();

            String[] mensajes = {
                    "Hola clientes",
                    "Mensaje de prueba",
                    "Java UDP multicast",
                    "Ultimo mensaje"
            };

            for (String mensaje : mensajes) {
                // Codificar mensaje (invertir texto)
                String mensajeCodificado = invertirTexto(mensaje);

                System.out.println("Original: " + mensaje);
                System.out.println("Codificado: " + mensajeCodificado);

                byte[] datos = mensajeCodificado.getBytes();
                DatagramPacket paquete = new DatagramPacket(
                        datos,
                        datos.length,
                        grupo,
                        puerto
                );

                socket.send(paquete);
                System.out.println("Mensaje enviado\n");

                Thread.sleep(3000);
            }

            socket.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String invertirTexto(String texto) {
        String invertido = "";
        for (int i = texto.length() - 1; i >= 0; i--) {
            invertido += texto.charAt(i);
        }
        return invertido;
    }
}