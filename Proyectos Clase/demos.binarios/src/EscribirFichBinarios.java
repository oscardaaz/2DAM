public class EscribirFichBinarios {
    public static void main(String[] args) throws Exception {

        /*java.io.File fichero = new java.io.File("fichero.dat");

        // Crear el fichero y escribir los números del 1 al 100 usando FileOutputStream básico

        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(fichero)) {
            for (int i = 1; i <= 100; i++) {
                fos.write(i);
            }
        }

        // Leer el fichero y mostrar los números por pantalla usando FileInputStream básico

        try (java.io.FileInputStream fis = new java.io.FileInputStream(fichero)) {
            int numero;
            while ((numero = fis.read()) != -1) {
                System.out.printf("Numero: " + numero + "\n");
            }
        }*/
        
        // Ejemplo usando java.nio.file.Path y Files
        java.nio.file.Path path = java.nio.file.Paths.get("fichero.dat");

        // Escribir los números del 1 al 100
        try (java.io.OutputStream out = java.nio.file.Files.newOutputStream(path)) {
            for (int i = 1; i <= 100; i++) {
                out.write(i);
            }
        }

        // Leer y mostrar los números
        try (java.io.InputStream in = java.nio.file.Files.newInputStream(path)) {
            int numero;
            while ((numero = in.read()) != -1) {
                System.out.printf("Numero (NIO): %d\n", numero);
            }
        }


        // Ejemplo usando try-catch-finally (sin try-with-resources) con java.nio

        java.nio.file.Path path2 = java.nio.file.Paths.get("fichero2.dat");
        java.io.OutputStream out2 = null;

        try {
            out2 = java.nio.file.Files.newOutputStream(path2);
            for (int i = 1; i <= 100; i++) {
                out2.write(i);
            }
        } catch (Exception e) {
            System.err.println("Error escribiendo fichero2.dat: " + e.getMessage());
        } finally {
            if (out2 != null) {
                try { out2.close(); } catch (Exception e) { /* Ignorar */ }
            }
        }


        java.io.InputStream in2 = null;
        try {
            in2 = java.nio.file.Files.newInputStream(path2);
            int numero;
            while ((numero = in2.read()) != -1) {
                System.out.printf("Numero (NIO, finally): %d\n", numero);
            }
        } catch (Exception e) {
            System.err.println("Error leyendo fichero2.dat: " + e.getMessage());
        } finally {
            if (in2 != null) {
                try { in2.close(); } catch (Exception e) { /* Ignorar */ }
            }
        }

        
    }
}
