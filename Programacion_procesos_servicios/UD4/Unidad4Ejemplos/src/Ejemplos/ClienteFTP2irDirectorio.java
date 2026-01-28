package Ejemplos;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import java.io.IOException;

public class ClienteFTP2irDirectorio {
    public static void main(String[] args) {
        FTPClient cliente = new FTPClient();

        try {
            // ========== ftp.ujaen.es ==========
            System.out.println("\n=== Conectando a ftp.ujaen.es ===");
            cliente.connect("ftp.ujaen.es");
            cliente.enterLocalPassiveMode();
            cliente.login("anonymous", "anonymous");

            // Listar /pub/linux/ofimatica/
            cliente.changeWorkingDirectory("/pub/linux/ofimatica/");
            listarDirectorio(cliente);

            // Listar /pub/windows/
            cliente.changeWorkingDirectory("/pub/windows/");
            listarDirectorio(cliente);

            // Listar /pub/windows/electronica/
            cliente.changeWorkingDirectory("/pub/windows/electronica/");
            listarDirectorio(cliente);

            cliente.disconnect();

            // ========== ftp.nluug.nl ==========
            System.out.println("\n=== Conectando a ftp.nluug.nl ===");
            cliente.connect("ftp.nluug.nl");
            cliente.enterLocalPassiveMode();
            cliente.login("anonymous", "anonymous");

            cliente.changeWorkingDirectory("/pub/");
            listarDirectorio(cliente);

            cliente.disconnect();

            // ========== test.rebex.net ==========
            System.out.println("\n=== Conectando a test.rebex.net ===");
            cliente.connect("test.rebex.net");
            cliente.enterLocalPassiveMode();
            cliente.login("demo", "password"); // Credenciales específicas

            cliente.changeWorkingDirectory("/pub/example/");
            listarDirectorio(cliente);

            cliente.disconnect();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Método auxiliar para listar directorios
    private static void listarDirectorio(FTPClient cliente) throws IOException {
        System.out.println("Directorio: " + cliente.printWorkingDirectory());
        FTPFile[] files = cliente.listFiles();
        System.out.println("Ficheros: " + files.length);

        String[] tipos = {"Fichero", "Directorio", "Enlace simb."};
        for (FTPFile file : files) {
            System.out.println("  " + file.getName() + " - " + tipos[file.getType()]);
        }
        System.out.println();
    }
}