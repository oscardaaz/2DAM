/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examenpsp_2_evaluacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import org.apache.commons.net.pop3.*;
import org.apache.commons.net.smtp.*;

/**
 *
 * @author oscar.domalo
 */
public class ComprobarCorreo {

    private static String servidorCorreo; // Dirección del servidor de correo (SMTP y POP3)

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce la dirección del servidor de correo (ej: localhost / IP a conectar / etc): ");
        servidorCorreo = "192.168.56.102".trim();

        String menu = """
                         --- Menu Opciones ---                     
                         1 - Comprobar correo                         
                         2 - Salir
                         """;

        int opcion = 0;
        do {
            System.out.println(menu);
            System.out.print("Introduce una opcion a elegir: ");
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
                continue;
            }

            switch (opcion) {
                
                case 1:
                    comprobarCorreo(sc);
                    break;
                case 2:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 2);
        sc.close();
    }

    /**
     * Opción 1: Enviar un correo electrónico pidiendo los datos por teclado.
     */
    private static void enviarCorreo(Scanner sc) {
        System.out.println("\n--- ENVIAR CORREO ---");
        System.out.print("Remitente: ");
        String remitente = sc.nextLine();
        System.out.print("Destinatario(s) (separados por coma): ");
        String destinatariosInput = sc.nextLine();
        System.out.print("Asunto: ");
        String asunto = sc.nextLine();
        System.out.print("Mensaje: ");
        String mensaje = sc.nextLine();

        // Dividir destinatarios por coma y limpiar espacios
        String[] destinatarios = destinatariosInput.split(",");
        for (int i = 0; i < destinatarios.length; i++) {
            destinatarios[i] = destinatarios[i].trim();
        }

        SMTPClient client = new SMTPClient();
        try {
            int respuesta;
            client.connect(servidorCorreo); // Usamos el servidor introducido
            System.out.println("Conectado al servidor SMTP: " + client.getReplyString());
            respuesta = client.getReplyCode();

            if (!SMTPReply.isPositiveCompletion(respuesta)) {
                client.disconnect();
                System.err.println("El servidor SMTP rechazó la conexión.");
                return;
            }

            client.login();

            // Crear cabecera con el primer destinatario como principal
            SimpleSMTPHeader cabecera = new SimpleSMTPHeader(remitente, destinatarios[0], asunto);
            // Añadir el resto de destinatarios como CC (si hay más de uno)
            for (int i = 1; i < destinatarios.length; i++) {
                cabecera.addCC(destinatarios[i]);
            }

            // Establecer remitente y destinatarios
            client.setSender(remitente);
            for (String dest : destinatarios) {
                client.addRecipient(dest);
            }

            // Enviar DATA
            Writer writer = client.sendMessageData();
            if (writer == null) {
                System.out.println("ERROR: No se pudo iniciar el envío de DATA.");
                return;
            }

            writer.write(cabecera.toString());
            writer.write("\n"); // Separador cabecera-cuerpo
            writer.write(mensaje);
            writer.close();

            if (!client.completePendingCommand()) {
                System.out.println("ERROR: Fallo al finalizar la transacción.");
                return;
            }

            client.logout();
            client.disconnect();

            // Mostrar detalle del correo enviado
            System.out.println("\n--- CORREO ENVIADO CON ÉXITO ---");
            System.out.println("Remitente: " + remitente);
            System.out.println("Destinatarios: " + String.join(", ", destinatarios));
            System.out.println("Asunto: " + asunto);
            System.out.println("Mensaje: " + mensaje);

        } catch (IOException e) {
            System.err.println("ERROR: No se pudo conectar al servidor SMTP.");
            e.printStackTrace();
        }
    }

    /**
     * Opción 2: Comprobar correo en el servidor POP3 (puerto 110)
     * con diferentes niveles de detalle.
     */
    private static void comprobarCorreo(Scanner sc) {
        final int PUERTO_POP3 = 110;

        System.out.println("\n--- COMPROBAR CORREO ---");
        System.out.println("Servidor: " + servidorCorreo + ":" + PUERTO_POP3);
        
        System.out.println("Introduce los datos de usuario:");
        System.out.print("Usuario: ");
        String usuario = sc.nextLine().trim();
        System.out.print("Contraseña: ");
        String password = sc.nextLine().trim();

        String submenu = """
                         ¿Qué información desea recuperar?
                         1 - Solo número de mensajes
                         2 - Cabeceras de los mensajes
                         3 - Mensajes completos
                         Elija una opción: """;
        System.out.print(submenu);
        int nivel;
        try {
            nivel = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opción no válida.");
            return;
        }

        POP3Client pop3 = new POP3Client();
        try {
            pop3.connect(servidorCorreo, PUERTO_POP3);
            System.out.println("Conectado al servidor POP3 " + servidorCorreo + ":" + PUERTO_POP3);

            if (!pop3.login(usuario, password)) {
                System.out.println("Error: Login incorrecto. Compruebe usuario y contraseña.");
                pop3.disconnect();
                return;
            }

            POP3MessageInfo[] mensajes = pop3.listMessages();
            if (mensajes == null || mensajes.length == 0) {
                System.out.println("No hay mensajes en el buzón.");
            } else {
                System.out.println("Número de mensajes: " + mensajes.length);

                switch (nivel) {
                    case 1:
                        // Solo número ya mostrado
                        break;
                    case 2:
                        recuperarCabeceras(mensajes, pop3);
                        break;
                    case 3:
                        recuperarMensajesCompletos(mensajes, pop3);
                        break;
                    default:
                        System.out.println("Opción de recuperación no válida.");
                }
            }

            pop3.logout();
            pop3.disconnect();

        } catch (IOException e) {
            System.err.println("Error de conexión con el servidor POP3: " + e.getMessage());
            System.err.println("Asegúrate de que el servidor esté activo y los datos sean correctos.");
            // e.printStackTrace(); // Ocultamos traza para no ensuciar
        }
    }

    /**
     * Muestra las cabeceras de todos los mensajes.
     */
    private static void recuperarCabeceras(POP3MessageInfo[] mensajes, POP3Client pop3) throws IOException {
        System.out.println("\n--- CABECERAS DE LOS MENSAJES ---");
        for (int i = 0; i < mensajes.length; i++) {
            POP3MessageInfo msginfo = mensajes[i];
            System.out.println("Mensaje " + (i + 1) + " (ID: " + msginfo.identifier + ", Tamaño: " + msginfo.size + "):");
            BufferedReader reader = (BufferedReader) pop3.retrieveMessageTop(msginfo.number, 0);
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println("  " + linea);
            }
            reader.close();
            System.out.println(); // línea en blanco entre mensajes
        }
    }

    /**
     * Muestra el contenido completo de todos los mensajes.
     */
    private static void recuperarMensajesCompletos(POP3MessageInfo[] mensajes, POP3Client pop3) throws IOException {
        System.out.println("\n--- MENSAJES COMPLETOS ---");
        for (int i = 0; i < mensajes.length; i++) {
            POP3MessageInfo msginfo = mensajes[i];
            System.out.println("Mensaje " + (i + 1) + " (ID: " + msginfo.identifier + ", Tamaño: " + msginfo.size + "):");
            BufferedReader reader = (BufferedReader) pop3.retrieveMessage(msginfo.number);
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println("  " + linea);
            }
            reader.close();
            System.out.println(); // línea en blanco entre mensajes
        }
    }
}