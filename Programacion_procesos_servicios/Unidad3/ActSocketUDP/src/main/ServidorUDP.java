package main;

import java.io.*;
import java.net.*;

public class ServidorUDP {
    private static final int PUERTO = 8888;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            System.out.println("Servidor UDP iniciado en puerto " + PUERTO);

            byte[] buffer = new byte[1024];

            while (true) {
                // Recibir paquete
                DatagramPacket paqueteRecepcion = new DatagramPacket(buffer, buffer.length);
                socket.receive(paqueteRecepcion);

                // Convertir a String
                String mensaje = new String(
                        paqueteRecepcion.getData(),
                        0,
                        paqueteRecepcion.getLength()
                );

                System.out.println("Mensaje recibido de " +
                        paqueteRecepcion.getAddress() + ":" +
                        paqueteRecepcion.getPort() + " -> " + mensaje);

                // Verificar si es asterisco para terminar
                if (mensaje.equals("*")) {
                    System.out.println("Recibido asterisco. Finalizando servidor...");
                    break;
                }

                // Convertir a mayúsculas y enviar respuesta
                String respuesta = mensaje.toUpperCase();
                byte[] bufferRespuesta = respuesta.getBytes();

                DatagramPacket paqueteEnvio = new DatagramPacket(
                        bufferRespuesta,
                        bufferRespuesta.length,
                        paqueteRecepcion.getAddress(),
                        paqueteRecepcion.getPort()
                );
                socket.send(paqueteEnvio);

                System.out.println("Respuesta enviada: " + respuesta);
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}