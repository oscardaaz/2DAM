package examenpsp_2_evaluacion;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.smtp.SimpleSMTPHeader;

/**
 *
 * @author oscar.domalo
 */
public class Servidor {

    private static final Path rutaFichero = Path.of("Incidencias_" + LocalDate.now() + ".txt");

    public static void main(String[] args) {

        try (ServerSocket servidor = new ServerSocket(5000)) {
            System.out.println("Servidor escuchando en puerto 5000...");

            while (true) {
                  // Tarda un poco al mandar el email no me ha dado tiempo a poner el sleep, esperar un poco en el output
                Socket cliente = servidor.accept();
                new HiloCliente(cliente).start();

            }

        } catch (Exception e) {
            System.err.println("Error en hilo servidor al recibir, " + e.getMessage());
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
                    DataInputStream ois = new DataInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                ) {

                String cadena = ois.readUTF();
                String[] cadenaAux = cadena.split(";");
                dos.writeUTF("REGISTRO OK");

                if (cadenaAux[4].equals("ALTA")) {

                    mandarCorreoPrioAlto(cadenaAux[1], cadenaAux[0], cadenaAux[2], cadenaAux[3]);
                    dos.writeUTF("YA HAY UN TECNICO ATENDIENDO SU INCIDENCIA");
                }

                escribirPathBuffered(cadena);
                dos.writeUTF("Incidencia añadida correctamente.");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static void escribirPathBuffered(String cadena) {

        try (BufferedWriter bw = Files.newBufferedWriter(rutaFichero,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            bw.write(cadena);
            bw.newLine();

            System.out.println("Fichero escrito correctamente.");
        } catch (IOException e) {
            System.err.println("IOE generada en escribirBuffered " + e.getMessage());
        }
    }

    private static void mandarCorreoPrioAlto(String id_departamento,
            String datosUsuario,
            String dispositivo,
            String incidenciaRegistrada) {
        SMTPClient client = new SMTPClient();
        try {
            int respuesta;
//		      client.connect("localhost");
            client.connect("192.168.56.102");
            System.out.print(client.getReplyString());
            respuesta = client.getReplyCode();

            if (!SMTPReply.isPositiveCompletion(respuesta)) {
                client.disconnect();
                System.err.println("SMTP server refused connection.");
                System.exit(1);
            }

            client.login();

            String remitente = "gestorincidencias@nettasksolutions.es";
            String destino1 = "correo1@correo.local";
            String destino2 = "mariajesusramos@brianda.net";
            String asunto = "Incidencia ALTA en departamento (" + id_departamento + ")";
            String mensaje = "Mensaje: \n" + "Datos usuario: " + datosUsuario + "\nDatos departamento: " + id_departamento + "\nDatos dispositivo: " + dispositivo + "Datos incidencia: \n " + incidenciaRegistrada;

            //se crea la cabecera
            SimpleSMTPHeader cabecera = new SimpleSMTPHeader(remitente, destino1, asunto);
            cabecera.addCC(destino2);

            //establecer el correo de origen
            client.setSender(remitente);

            //a�adir correos destino 
            client.addRecipient(destino1);//hay que a�adir los dos
            client.addRecipient(destino2);

            //se envia DATA
            Writer writer = client.sendMessageData();
            if (writer == null) { //fallo	       
                System.out.println("FALLO AL ENVIAR DATA.");
                System.exit(1);
            }

            System.out.println(cabecera.toString());
            writer.write(cabecera.toString()); //primero escribo cabecera    
            writer.write(mensaje);//luego mensaje
            writer.close();

            if (!client.completePendingCommand()) { //fallo
                System.out.println("FALLO AL FINALIZAR LA TRANSACCI�N.");
                System.exit(1);
            }

            client.logout();
            client.disconnect();

        } catch (IOException e) {
            System.err.println("NO SE PUEDE CONECTAR AL SERVIDOR.");
            e.printStackTrace();

        }

    }
}
