import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class Actividad1 {
    

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

        // Ruta absoluta al directorio 'prueba'
        Path dir = Path.of("D:\\Users\\oscar.domalo\\prueba");

        // --- OPCIÓN 1: Usando NIO y Streams (moderno, eficiente, pero avanzado) ---
        borrarDirectorioRecursivo(dir);
        System.out.println("Directorio 'prueba' y su contenido han sido eliminados (NIO stream).\n");

        // --- OPCIÓN 2: Usando NIO clásico recursivo (más fácil de leer) ---
        // Descomenta para probar esta opción
        // borrarDirectorioRecursivoClasico(dir);
        // System.out.println("Directorio 'prueba' y su contenido han sido eliminados (NIO clásico).\n");

        // --- OPCIÓN 3: Usando java.io.File (la más simple y clásica) ---
        // Descomenta para probar esta opción
        // File dirFile = new File("D:\\Users\\oscar.domalo\\prueba");
        // borrarDirectorioConFile(dirFile);
        // System.out.println("Directorio 'prueba' y su contenido han sido eliminados (java.io.File).\n");
    }

    // OPCIÓN 1: Borra un directorio y todo su contenido usando NIO y Streams
    // Recorre todos los archivos y subdirectorios, borra primero los más profundos
    public static void borrarDirectorioRecursivo(Path path) throws IOException {
        if (Files.exists(path)) {
            // Files.walk recorre todos los archivos y carpetas dentro de 'path'
            Files.walk(path)
                    // Ordena de mayor a menor profundidad (primero hijos, luego padres)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(p -> {
                        try {
                            // Borra cada archivo/directorio
                            Files.delete(p);
                        } catch (IOException e) {
                            System.err.printf("No se pudo borrar: %s (%s)%n", p, e.getMessage());
                        }
                    });
        }
    }

    // OPCIÓN 2: Borra un directorio y su contenido usando recursividad clásica con NIO
    // Más fácil de entender: entra en cada subdirectorio, borra todo dentro, luego borra el propio directorio
    public static void borrarDirectorioRecursivoClasico(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            // Abre el directorio y recorre cada entrada (archivo o subdirectorio)
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    // Llama recursivamente para borrar el contenido
                    borrarDirectorioRecursivoClasico(entry);
                }
            }
        }
        // Borra el archivo o directorio (ya vacío)
        Files.deleteIfExists(path);
    }

    // OPCIÓN 3: Borra un directorio y su contenido usando java.io.File (muy simple)
    // Funciona igual que la opción 2, pero usando la API antigua de Java
    public static void borrarDirectorioConFile(File file) {
        if (file.isDirectory()) {
            // Obtiene la lista de archivos y subdirectorios
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    // Llama recursivamente para borrar el contenido
                    borrarDirectorioConFile(f);
                }
            }
        }
        // Borra el archivo o directorio (ya vacío)
        if (!file.delete()) {
            System.err.println("No se pudo borrar: " + file.getAbsolutePath());
        }
    
        

        
    }
}
