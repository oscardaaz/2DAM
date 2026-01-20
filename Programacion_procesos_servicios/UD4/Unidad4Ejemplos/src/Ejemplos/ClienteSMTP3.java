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

public class ClienteSMTP3 {
	public static void main(String[] args) throws NoSuchAlgorithmException, UnrecoverableKeyException,
			KeyStoreException, InvalidKeyException, InvalidKeySpecException {

		// se crea cliente SMTP seguro
		AuthenticatingSMTPClient client = new AuthenticatingSMTPClient();

		// datos del usuario y del servidor
		String server = "smtp.gmail.com";
		String username = "correo@gmail.com";
		String password = "claveusuario";
		int puerto = 587;
		String remitente = "correo@gmail.com";

		try {
			int respuesta;

			// Creaci�n de la clave para establecer un canal seguro
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(null, null);
			KeyManager km = kmf.getKeyManagers()[0];

			// nos conectamos al servidor SMTP
			client.connect(server, puerto);
			System.out.println("1 - " + client.getReplyString());
			// se establece la clave para la comunicaci�n segura
			client.setKeyManager(km);

			respuesta = client.getReplyCode();
			if (!SMTPReply.isPositiveCompletion(respuesta)) {
				client.disconnect();
				System.err.println("CONEXI�N RECHAZADA.");
				System.exit(1);
			}

			// se env�a el commando EHLO
			client.ehlo(server);// necesario
			System.out.println("2 - " + client.getReplyString());

			// NECESITA NEGOCIACI�N TLS - MODO NO IMPLICITO
			// Se ejecuta el comando STARTTLS y se comprueba si es true
			if (client.execTLS()) {
				System.out.println("3 - " + client.getReplyString());

				// se realiza la autenticaci�n con el servidor
				if (client.auth(AuthenticatingSMTPClient.AUTH_METHOD.LOGIN, username, password)) {
					System.out.println("4 - " + client.getReplyString());
					String destino1 = "mariajesusramos@brianda.net";
					String asunto = "Prueba de SMTPClient con GMAIL";
					String mensaje = "Hola. \nEnviando saludos.\nUsando  GMAIL.\nChao.";
					// se crea la cabecera
					SimpleSMTPHeader cabecera = new SimpleSMTPHeader(remitente, destino1, asunto);

					// el nombre de usuario y el email de origen coinciden
					client.setSender(remitente);
					client.addRecipient(destino1);
					System.out.println("5 - " + client.getReplyString());

					// se envia DATA
					Writer writer = client.sendMessageData();
					if (writer == null) { // fallo
						System.out.println("FALLO AL ENVIAR DATA.");
						System.exit(1);
					}

					writer.write(cabecera.toString()); // cabecera
					writer.write(mensaje);// luego mensaje
					writer.close();
					System.out.println("6 - " + client.getReplyString());

					boolean exito = client.completePendingCommand();
					System.out.println("7 - " + client.getReplyString());

					if (!exito) { // fallo
						System.out.println("FALLO AL FINALIZAR TRANSACCI�N.");
						System.exit(1);
					} else
						System.out.println("MENSAJE ENVIADO CON EXITO......");

				} else
					System.out.println("USUARIO NO AUTENTICADO.");
			} else
				System.out.println("FALLO AL EJECUTAR  STARTTLS.");

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
