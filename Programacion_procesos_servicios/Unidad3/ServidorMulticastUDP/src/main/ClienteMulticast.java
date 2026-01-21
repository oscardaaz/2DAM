package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ClienteMulticast {
    public static void main(String[] args) {
        String direccion = "230.0.0.0";
        int puerto = 5000;

        System.out.println("Cliente iniciado. ID: " + (args.length > 0 ? args[0] : "1"));

        try {
            InetAddress grupo = InetAddress.getByName(direccion);
            MulticastSocket socket = new MulticastSocket(puerto);

            socket.joinGroup(grupo);
            System.out.println("Conectado al grupo multicast\n");

            byte[] buffer = new byte[1024];

            for (int i = 0; i < 4; i++) {
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                socket.receive(paquete);

                String mensajeCodificado = new String(
                        paquete.getData(),
                        0,
                        paquete.getLength()
                );

                String mensajeDecodificado = invertirTexto(mensajeCodificado);

                System.out.println("Mensaje codificado: " + mensajeCodificado);
                System.out.println("Mensaje descodificado: " + mensajeDecodificado);
                System.out.println("-------------------");
            }

            socket.leaveGroup(grupo);
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