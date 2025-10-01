import java.nio.file.*;

public class App {
    

    public static void main(String[] args) throws Exception {

        Path carpetaPrueba = Path.of("D:\\Users\\oscar.domalo", "prueba");

        try {

            if (Files.notExists(carpetaPrueba)) {

                Files.createDirectory(carpetaPrueba);
                System.out.printf("Carpeta prueba creada en: %s%n", carpetaPrueba.toAbsolutePath());
            } else {
                System.out.printf("Carpeta prueba ya existe: %s%n", carpetaPrueba.toAbsolutePath());
            }
        } catch (FileAlreadyExistsException e) {
            System.out.println("Error" + e.getMessage());
        }

        try {
            Path fichero1 = carpetaPrueba.resolve("fichero.txt");
            Path fichero2 = carpetaPrueba.resolve("fichero2.txt");

            Files.createFile(fichero1);
            Files.createFile(fichero2);
            System.out.printf("Fichero 1 creado en: %s%n",
                    fichero1.toAbsolutePath());
            System.out.printf("Fichero 2  creado en: %s%n",
                    fichero2.toAbsolutePath());

        } catch (FileAlreadyExistsException e) {
            System.out.println("El fichero fichero.txt ya existe en: " + e.getMessage());
        }

        try {
            Path rutaRelativa = Path.of("D:\\Users\\oscar.domalo\\prueba", "subcarpeta1/subcarpeta2");
            Files.createDirectories(rutaRelativa);
            System.out.printf("Jerarquia creada en: %s%n",
                    rutaRelativa.toAbsolutePath());
        } catch (FileAlreadyExistsException e) {
            System.out.println("La Jerarquia ya existe en: " + e.getMessage());
            e.getMessage();
        }

        try {
            Path crearFichero = Path.of("D:\\Users\\oscar.domalo\\prueba\\subcarpeta1\\subcarpeta2", "documento.txt");
            Files.createFile(crearFichero);
            System.out.printf("Fichero creado en: %s%n",
                    crearFichero.toAbsolutePath());
        } catch (FileAlreadyExistsException e) {
            System.out.println("El documento.txt ya existe en: " + e.getMessage());
        }

       
            /*Path ruta = Path.of("D:\\Users\\oscar.domalo\\prueba\\subcarpeta1\\subcarpeta2\\documento.txt");
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(ruta)) {
                for (Path entrada : stream) {
                    System.out.println(entrada.getFileName());
                }
            }*/
        
        
    
        

        
    }
}
