package proyecto2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class p2 {

    public static void main(String[] args) throws IOException {

        File directorio = new File("D:\\Users\\oscar.domalo\\Documents\\2DAM\\Programacion_procesos_servicios\\programa1\\out\\artifacts\\programa1_jar");

        ProcessBuilder pb = new ProcessBuilder("java","-jar","programa1.jar");

        pb.directory(directorio);
        System.out.printf("Directorio de trabajo: %s%n",pb.directory());

        Process p = pb.start();

        try (InputStream is = p.getInputStream()){
            int c;
            while ((c= is.read()) != -1){
                System.out.print((char) c);
            }

        } catch (IOException e) {
            System.err.println("Error " + e.getMessage());
        }


    }
}
