package Ejemplos;

import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;

import java.io.IOException;

public class ClienteSMTP1 {
  public static void main(String[] args) {
	SMTPClient client = new SMTPClient();
	try {
	    int respuesta;
	    //NOS CONECTAMOS AL PUERTO 25
          client.connect("localhost");	    
	    System.out.print(client.getReplyString());
	    respuesta = client.getReplyCode();
		
	    if (!SMTPReply.isPositiveCompletion(respuesta)) {
		client.disconnect();
		System.err.println("CONEXION RECHAZADA.");
		System.exit(1);
	    }
			
	    // REALIZAR ACCIONES, por ejemplo enviar un correo
		
	    // NOS DESCONECTAMOS
	    client.disconnect();
		
	} catch (IOException e) {
	   System.err.println("NO SE PUEDE CONECTAR AL SERVIDOR.");		   
	   e.printStackTrace();
	   System.exit(2);
	}		
		
  }//main
}// ..
