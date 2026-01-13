import java.io.File;
import java.io.IOException;

public class ejecutador1 {
    public static void main(String[] args) {
        File directorio = new File("out/artifacts/ejerciciosPSP_jar");
        System.out.print("\nDirectorio absoluto: " + directorio.getAbsolutePath());

        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "ejerciciosPSP.jar");
        pb.directory(directorio);
        System.out.println("\n");
        pb.inheritIO();  // Hereda entrada/salida/error del hijo para que se muestren directamente

        try {
            Process p = pb.start();
            int exitCode = p.waitFor();
            System.out.println("\nProceso terminado con éxito " + exitCode);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando el proceso: " + e.getMessage());

        }
    }
}
