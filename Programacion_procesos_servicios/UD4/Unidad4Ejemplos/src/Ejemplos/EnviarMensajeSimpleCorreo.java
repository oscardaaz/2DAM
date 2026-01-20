package Ejemplos;

import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;

import java.io.IOException;
import java.net.ConnectException;

public class EnviarMensajeSimpleCorreo {

	public static void main(String[] args) {
		SMTPClient client = new SMTPClient();
		try {
			int respuesta;
			client.connect("localhost");
			System.out.print(client.getReplyString());
			respuesta = client.getReplyCode();

			if (!SMTPReply.isPositiveCompletion(respuesta)) {
				client.disconnect();
				System.err.println("CONEXION RECHAZADA.");
				System.exit(1);
			}

			// REALIZAR ACCIONES
			client.login(); // inicio de sesi�n HELO
			String destinatario = "mariajesusramos@brianda.net";
			String mensaje = "Hola. \nEnviando saludos.\nChao.";
			String remitente = "yo@localhost.es";

			if (client.sendSimpleMessage(remitente, destinatario, mensaje))
				System.out.println("Mensaje enviado a " + destinatario);
			else
				System.out.println("No se ha podido enviar");

			client.logout();// final de sesi�n QUIT



			client.disconnect();

		}catch(ConnectException ce){
			System.err.println("EL SERVIDOR NO EST� INICIADO.");			
			System.err.println(ce.getMessage());
			System.exit(2);
		}
		catch (IOException e) {
			System.err.println("NO SE PUEDE CONECTAR AL SERVIDOR.");
			e.printStackTrace();
			System.exit(2);
		}

	}//main

}
