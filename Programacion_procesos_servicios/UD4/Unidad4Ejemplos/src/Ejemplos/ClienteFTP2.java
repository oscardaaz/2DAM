package Ejemplos;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;


public class ClienteFTP2 {
    public static void main(String[] args) {
        FTPClient cliente = new FTPClient();
//	String servFTP = "ftp.rediris.es";
        if (args.length < 3) {
            System.out.println("Debes proporcionar servidor, usuario, y clave como argumentos.");
            return;
        }
        String servFTP = args[0];

//      System.out.println("Nos conectamos a: " + servFTP);
        System.out.println("Nos conectamos a args[0]");

//      	String usuario = "anonymous";
//      	String clave = "anonymous";

        String usuario = args[1];
        String clave = args[2];
        try {
            cliente.connect(servFTP);
            cliente.enterLocalPassiveMode(); //modo pasivo

            boolean login = cliente.login(usuario, clave);
            if (login)
                System.out.println("Login correcto...");
            else {
                System.out.println("Login Incorrecto...");
                cliente.disconnect();
                System.exit(1);
            }
            System.out.println("Directorio actual: "
                    + cliente.printWorkingDirectory());

            FTPFile[] files = cliente.listFiles();
            System.out.println("Ficheros en el directorio actual:"
                    + files.length);
            //array para visualizar el tipo de fichero
            String tipos[] = {"Fichero", "Directorio", "Enlace simb."};

            for (int i = 0; i < files.length; i++) {
                System.out.println("\t" + files[i].getName() + " => "
                        + tipos[files[i].getType()]);
            }
            boolean logout = cliente.logout();
            if (logout)
                System.out.println("Logout del servidor FTP...");
            else
                System.out.println("Error al hacer Logout...");
            //
            cliente.disconnect();
            System.out.println("Desconectado...");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}// ..

