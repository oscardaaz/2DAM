import java.io.File;
import java.io.IOException;

public class ejecutador1 {
    public static void main(String[] args) {
        File directorio = new File("out/artifacts/ejerciciosPSP_jar");
        System.out.println("Directorio absoluto: " + directorio.getAbsolutePath());

        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "ejerciciosPSP.jar");
        pb.directory(directorio);
        pb.inheritIO();  // Hereda entrada/salida/error para que se muestren directamente

        try {
            Process p = pb.start();
            int exitCode = p.waitFor();
            System.out.println("Proceso terminado con código: " + exitCode);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando el proceso: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
