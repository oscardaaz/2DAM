import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException{

        Path carpetaBase = Path.of("AD");

        if (Files.notExists(carpetaBase)) {

            Files.createDirectory(carpetaBase);
            System.out.printf("Carpeta base creada en: %s%n" , carpetaBase.toAbsolutePath()) ;
        }else{
            System.out.printf("Carpeta base ya existe: %s%n", carpetaBase.toAbsolutePath());
        }


        //Crear ficheros en esa carpeta
        Path fichero = carpetaBase.resolve("fichero.txt");

        try {
            Files.createFile(fichero);
            System.out.printf("Fichero creado en: %s%n",
                    fichero.toAbsolutePath());

        } catch (FileAlreadyExistsException e) {
            System.out.println("El fichero fichero.txt ya existe");
        }

        //Creamos fichero2 y lo borramos


        Path fichero2 = carpetaBase.resolve("fichero2.txt");

        try {
            Files.createFile(fichero2);
            System.out.printf("Fichero creado en: %s%n",
                    fichero2.toAbsolutePath());

            if (Files.deleteIfExists(fichero2)) {
                System.out.printf("Fichero borrado en: %s%n",
                        fichero2.toAbsolutePath());
            }else System.out.println("El fichero no existia. " +
                    "No se ha borradonada");

        } catch (IOException ioe) {

        }

        /*try {
            Files.delete(fichero2);
             System.out.printf("Fichero borrado en: %s%n",
                fichero2.toAbsolutePath());
        } catch (IOException ioe) {

            System.out.printf("El " +  fichero2.getFileName() + " no ha podido ser elminiado");
            ioe.printStackTrace();
        }*/

        //Crear jerarquía de carpetas
        Path rutaRelativa = Path.of("carpeta1/carpeta2/carpeta3");
        Files.createDirectories(rutaRelativa);
        System.out.printf("Fichero borrado en: %s%n",
                rutaRelativa.toAbsolutePath());



    }
}