import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args)  {

        System.out.println("\nActividad 1. Creación de archivos y directorios\n");
        crearDirectorioArchivos(Path.of("C:\\Users\\Oscar","prueba"));

        System.out.println("\nActividad 2. Borrar un directorio y su contenido\n");
        borrarDirectorioContenido(Path.of("C:\\Users\\Oscar\\prueba"));

        Path rutaProyecto = Path.of("C:\\Users\\Oscar\\IdeaProjects\\2DAM\\Ejercicios_AD\\actividad1");

        System.out.println("\nActividad 3. Mostrar el contenido del directorio (Sin recursividad)\n");
        mostrarContenidoDirectorio(rutaProyecto);

        System.out.println("\nActividad 4. Mostrar el contenido del directorio (Con recursividad)\n");
        mostrarContenidoRecursivo(rutaProyecto);
    }

    /*
    AD_UT1_ACT01. Creación de archivos y directorios
    Haz un programa Java que realice lo siguiente:
    1. Crear un directorio en tu carpeta personal de la unidad D llamado prueba. Utiliza
    una ruta absoluta.
    2. Crear 2 documentos de texto en la carpeta prueba
    3. Crear la jerarquía prueba/subcarpeta1/subcarpeta2
    4. Crear un documento de texto dentro de subcarpeta2
    Nota: Mostrar en consola la ruta absoluta de cada fichero/directorio creado.
    */

    public static void crearDirectorioArchivos(Path ruta) {

        try {
            // 1. Crear carpeta "prueba"
            if (Files.notExists(ruta)) {
                Files.createDirectories(ruta);
                System.out.printf("Carpeta creada en: %s%n", ruta.toAbsolutePath());
            } else {
                System.out.printf("Carpeta ya existe en: %s%n", ruta.toAbsolutePath());
            }

            // 2. Crear 2 documentos en "prueba"
            Path fichero1 = ruta.resolve("fichero1.txt");
            Path fichero2 = ruta.resolve("fichero2.txt");

            if (Files.notExists(fichero1)) {
                Files.createFile(fichero1);
                System.out.printf("Fichero 1 creado en: %s%n", fichero1.toAbsolutePath());
            } else {
                System.out.printf("Fichero 1 ya existe: %s%n", fichero1.toAbsolutePath());
            }

            if (Files.notExists(fichero2)) {
                Files.createFile(fichero2);
                System.out.printf("Fichero 2 creado en: %s%n", fichero2.toAbsolutePath());
            } else {
                System.out.printf("Fichero 2 ya existe: %s%n", fichero2.toAbsolutePath());
            }

            // 3. Crear jerarquía prueba/subcarpeta1/subcarpeta2
            Path rutaJerarquica = ruta.resolve("subcarpeta1").resolve("subcarpeta2");

            if (Files.notExists(rutaJerarquica)) {
                Files.createDirectories(rutaJerarquica);
                System.out.printf("Jerarquía creada en: %s%n", rutaJerarquica.toAbsolutePath());
            } else {
                System.out.printf("La jerarquía ya existe en: %s%n", rutaJerarquica.toAbsolutePath());
            }

            // 4. Crear documento dentro de subcarpeta2
            Path rutaDocumento = rutaJerarquica.resolve("documento.txt");

            if (Files.notExists(rutaDocumento)) {
                Files.createFile(rutaDocumento);
                System.out.printf("Fichero creado en: %s%n", rutaDocumento.toAbsolutePath());
            } else {
                System.out.printf("Fichero ya existe en: %s%n", rutaDocumento.toAbsolutePath());
            }

        } catch (IOException e) {
            System.out.println("Error durante la creación de archivos o directorios: " + e.getMessage());
        }
    }

    /*
    AD_UT1_ACT02. Borrar un directorio y su contenido
    Añadir al final de la actividad1,1 un metodo que nos permita borrar
    el directorio prueba y todos los archivos y directorios contenidos en él
    */

    public static void borrarDirectorioContenido(Path ruta) {
        if (Files.isDirectory(ruta)) {
            // 1) Borrar primero el contenido del directorio
            try (DirectoryStream<Path> entradas = Files.newDirectoryStream(ruta)) {
                for (Path entrada : entradas) {
                    borrarDirectorioContenido(entrada);
                }
            } catch (IOException e) {
                System.out.println("Error al listar el contenido de: " + ruta + " → " + e.getMessage());
            }

            // 2) Borrar el directorio y etiquetar correctamente
            try {
                Files.deleteIfExists(ruta);
                System.out.printf("Directorio borrado: %s%n", ruta.toAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error al borrar directorio: " + e.getMessage());
            }

        } else {
            // Es archivo (u otro tipo no directorio)
            try {
                Files.deleteIfExists(ruta);
                System.out.printf("Archivo borrado: %s%n", ruta.toAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error al borrar archivo: " + e.getMessage());
            }
        }
    }

    /*
    AD_UT1_ACT03.
    • Crea un programa que muestre el contenido del directorio de trabajo (proyecto),
    indicando si cada elemento es un fichero o un directorio.
    NOTA: Sin utilizar recursividad. Únicamente mostrar el contenido del directorio sin entrar en
    los subdirectorios */

    public static void mostrarContenidoDirectorio(Path ruta)  {

        try (DirectoryStream<Path> contenido = Files.newDirectoryStream(ruta)) {

            for (Path elemento : contenido) {
                if (Files.isDirectory(elemento)) {
                    System.out.println("Directorio --> " + elemento.getFileName());
                } else if (Files.isRegularFile(elemento)) {
                    System.out.println("Archivo --> " + elemento.getFileName());
                } else {
                    System.out.println("Otro tipo --> " + elemento.getFileName());
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el contenido del directorio: " + e.getMessage());
        }
    }

    /*
    AD_UT1_ACT04.
    Modifica el ejercicio anterior para que muestre el contenido del directorio del proyecto y de
    todos sus subdirectorios de forma recursiva, indicando si cada elemento es un fichero o un
    subdirectorio. */

    //La sangria usando un atributo extra en el metodo "Nivel" lo he tenido que buscar la solución
    // en internet no conseguia solucionarlo

    public static void mostrarContenidoRecursivo(Path ruta) {
        mostrarContenidoRecursivo(ruta, 0);
    }

    private static void mostrarContenidoRecursivo(Path ruta, int nivel) {
        try (DirectoryStream<Path> contenido = Files.newDirectoryStream(ruta)) {
            for (Path elemento : contenido) {

                String sangria = "  ".repeat(nivel);

                if (Files.isDirectory(elemento)) {
                    System.out.println(sangria + "Directorio --> " + elemento.getFileName());

                    mostrarContenidoRecursivo(elemento, nivel + 1);
                } else if (Files.isRegularFile(elemento)) {
                    System.out.println(sangria + "Archivo --> " + elemento.getFileName());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el directorio: " + e.getMessage());
        }
    }

}

