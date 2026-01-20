package Ejemplos;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.smtp.SimpleSMTPHeader;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import java.io.IOException;
import java.io.Writer;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.spec.InvalidKeySpecException;

public class ClienteSMTP3_NOTLS {
	public static void main(String[] args) throws NoSuchAlgorithmException, UnrecoverableKeyException,
			KeyStoreException, InvalidKeyException, InvalidKeySpecException {

		AuthenticatingSMTPClient client = new AuthenticatingSMTPClient("SSL");

		String server = "servidorsmtp";
		String username = "usuario";
		String password = "clave";
		int puerto = 25;
		String remitente = "correo@servidor.es";

		try {
			int respuesta;
			// Creaci�n de la clave para establecer un canal seguro
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(null, null);
			KeyManager km = kmf.getKeyManagers()[0];

			// nos conectamos
			client.connect(server, puerto);
			System.out.println("1 - " + client.getReplyString());

			client.login(server);		

			// se establece la clave
			client.setKeyManager(km);

			respuesta = client.getReplyCode();
			if (!SMTPReply.isPositiveCompletion(respuesta)) {
				client.disconnect();
				System.err.println("SMTP server refused connection.");
				System.exit(1);
			}

			client.ehlo(server);// necesario

			System.out.println("2 - " + client.getReplyString());

			// NO NECESITA NEGOCIACI�N TLS
		

			if (client.auth(AuthenticatingSMTPClient.AUTH_METHOD.LOGIN, username, password)) {
				System.out.println("4 - " + client.getReplyString());

				String destino1 = "mariajesusramos@brianda.net";
				String asunto = "Prueba de SMTPClient NO TLS";
				String mensaje = "Hola. \nEnviando saludos.\nSin negociacion TLS.\nChao.";

				// se crea la cabecera
				SimpleSMTPHeader cabecera = new SimpleSMTPHeader(remitente, destino1, asunto);
				client.setSender(remitente);
				client.addRecipient(destino1);
				System.out.println("5 - " + client.getReplyString());

				// se envia DATA
				Writer writer = client.sendMessageData();

				if (writer == null) { // fallo
					System.out.println("FALLO AL ENVIAR DATA.");
					System.exit(1);
				}
				writer.write(cabecera.toString()); // primero escribo
													// cabecera
				writer.write(mensaje);// luego mensaje
				writer.close();

				System.out.println("6 - " + client.getReplyString());

				boolean exito = client.completePendingCommand();

				System.out.println("7 - " + client.getReplyString());

				if (!exito) { // fallo
					System.out.println("FALLO AL FINALIZAR LA TRANSACCI�N.");
					System.exit(1);
				} else
					System.out.println("MENSAJE ENVIADO CON EXITO......");

			} else {
				System.out.println("USUARIO NO AUTENTICADO: ");
				System.out.println(client.getReplyString());

			}

		} catch (IOException e) {
			System.err.println("Could not connect to server.");
			e.printStackTrace();
			System.exit(1);
		}
		try {
			client.disconnect();
		} catch (IOException f) {
			f.printStackTrace();
		}

		System.out.println("Fin de env�o.");
		System.exit(0);
	}// main

}// ..ClienteSMTP3