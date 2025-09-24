import java.io.IOException;
//import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) throws Exception {

        
        /*Path carpetaBase = Path.of("D:\\Users\\oscar.domalo", "prueba");
        
        if (Files.notExists(carpetaBase)) {
            
            Files.createDirectory(carpetaBase);
            System.out.printf("Carpeta base creada en: %s%n" , carpetaBase.toString()) ;
        }else{
            System.out.printf("Carpeta base ya existe: %s%n", carpetaBase.toString());
        }

        
        try {
            Path fichero1 = carpetaBase.resolve("fichero.txt");
        Path fichero2 = carpetaBase.resolve("fichero2.txt");

            Files.createFile(fichero1);
            Files.createFile(fichero2);
            System.out.printf("Fichero 1 creado en: %s%n",
                fichero1.toAbsolutePath());
                System.out.printf("Fichero 2  creado en: %s%n",
                fichero2.toAbsolutePath());

        } catch (FileAlreadyExistsException e) {
            System.out.println("El fichero fichero.txt ya existe");
        }

        try {
            Path rutaRelativa = Path.of("D:\\Users\\oscar.domalo\\prueba","subcarpeta1/subcarpeta2");
        Files.createDirectories(rutaRelativa);
            System.out.printf("Jerarquia creada en: %s%n",
                rutaRelativa.toAbsolutePath());
        } catch (Exception e) {
            
        }
        
        Path crearFichero = Path.of("D:\\Users\\oscar.domalo\\prueba\\subcarpeta1\\subcarpeta2","documento.txt");
        Files.createFile(crearFichero);
        System.out.printf("Fichero creado en: %s%n",
                crearFichero.toAbsolutePath());*/



        Path carpetaBase = Path.of("D:\\Users\\oscar.domalo", "prueba");

        // Crear la carpeta base si no existe
        crearCarpeta(carpetaBase);

        // Crear ficheros dentro de la carpeta base
        crearFichero(carpetaBase, "fichero.txt");
        crearFichero(carpetaBase, "fichero2.txt");

        // Crear una jerarquía de subcarpetas
        Path rutaSubcarpeta = carpetaBase.resolve("subcarpeta1/subcarpeta2");
        crearDirectorios(rutaSubcarpeta);

        // Crear un archivo dentro de la jerarquía de subcarpetas
        crearFichero(rutaSubcarpeta, "documento.txt");
    }

    // Método para crear una carpeta si no existe
    private static void crearCarpeta(Path carpeta) {
        try {
            if (Files.exists(carpeta)) {
                System.err.printf("Error: La carpeta base ya existe en: %s%n", carpeta.toAbsolutePath());
            } else {
                Files.createDirectory(carpeta);
                System.out.printf("Carpeta base creada en: %s%n", carpeta.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.printf("Error al crear la carpeta base: %s%n", e.getMessage());
        }
    }

    // Método para crear un archivo y manejar excepciones específicas
    private static void crearFichero(Path carpeta, String nombreFichero) {
        Path fichero = carpeta.resolve(nombreFichero);
        try {
            if (Files.exists(fichero)) {
                System.err.printf("Error: El fichero '%s' ya existe en: %s%n", nombreFichero, fichero.toAbsolutePath());
            } else {
                Files.createFile(fichero);
                System.out.printf("Fichero '%s' creado en: %s%n", nombreFichero, fichero.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.printf("Error al crear el fichero '%s': %s%n", nombreFichero, e.getMessage());
        }
    }

    // Método para crear directorios y manejar excepciones
    private static void crearDirectorios(Path ruta) {
        try {
            if (Files.exists(ruta)) {
                System.err.printf("Error: La jerarquía de directorios ya existe en: %s%n", ruta.toAbsolutePath());
            } else {
                Files.createDirectories(ruta);
                System.out.printf("Jerarquía creada en: %s%n", ruta.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.printf("Error al crear la jerarquía de directorios: %s%n", e.getMessage());
        } 
    }
}
        
   
