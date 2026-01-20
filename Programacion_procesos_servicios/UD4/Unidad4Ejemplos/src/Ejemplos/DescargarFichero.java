package Ejemplos;

import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class DescargarFichero {
	public static void main(String[] args) {
		FTPClient cliente = new FTPClient();
		
	    String servidor = "localhost";
		String user = "usuario1";
		String pasw = "usuario1";

		try {
			System.out.println("Conectandose a " + servidor);
			cliente.connect(servidor);			
			cliente.enterLocalPassiveMode();
			boolean login = cliente.login(user, pasw);		
			
			if (login) {
				System.out.println("Login correcto");		

				//descargar fichero
				String direc = "/NUEVODIREC";
				cliente.changeWorkingDirectory(direc); 		
				
				//stream de salida para recibir el fichero descargado
				BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream("D:\\CAPIT4\\TEXTO2nuevo.txt"));
				
				if(cliente.retrieveFile("TEXTO2.txt", out))
					System.out.println("Recuperado correctamente... ");
				else
					System.out.println("No se ha podido descargar... ");
				
				out.close();
	
				cliente.logout();
				cliente.disconnect();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}//main

	
}//..
