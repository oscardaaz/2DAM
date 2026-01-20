package main;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteUDP {
    private static final int TIMEOUT = 5000;
    private static final String HOST = "localhost";
    private static final int PUERTO = 8888;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in);
             DatagramSocket socket = new DatagramSocket()) {

            // Configurar timeout
            socket.setSoTimeout(TIMEOUT);

            InetAddress direccionServidor = InetAddress.getByName(HOST);
            String mensaje;

            System.out.println("Cliente UDP iniciado. Escribe mensajes (* para terminar):");

            do {
                // Leer entrada del usuario
                System.out.print("> ");
                mensaje = sc.nextLine();

                if (mensaje.equals("*")) {
                    System.out.println("Finalizando cliente...");
                    break;
                }

                // Enviar mensaje al servidor
                byte[] bufferEnvio = mensaje.getBytes();
                DatagramPacket paqueteEnvio = new DatagramPacket(
                        bufferEnvio,
                        bufferEnvio.length,
                        direccionServidor,
                        PUERTO
                );
                socket.send(paqueteEnvio);

                // Recibir respuesta del servidor
                byte[] bufferRecepcion = new byte[1024];
                DatagramPacket paqueteRecepcion = new DatagramPacket(
                        bufferRecepcion,
                        bufferRecepcion.length
                );

                try {
                    socket.receive(paqueteRecepcion);
                    String respuesta = new String(
                            paqueteRecepcion.getData(),
                            0,
                            paqueteRecepcion.getLength()
                    );
                    System.out.println("Servidor responde: " + respuesta);
                } catch (SocketTimeoutException e) {
                    System.out.println("Error: El paquete se ha perdido (tiempo de espera agotado)");
                }

            } while (!mensaje.equals("*"));

        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}