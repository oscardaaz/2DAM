package programa1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class p1 {
    public static void main(String[] args) throws IOException {

        ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "DIR");
        Process p = pb.start();


        try (InputStream is = p.getInputStream()) {

            int c;
            while ((c = is.read()) != -1)
                System.out.print((char) c);

        } catch (IOException e) {
            System.err.println("Error " + e.getMessage());
        }


        try (OutputStream os = p.getOutputStream()) {
            os.write("16/10/2025".getBytes());
            os.flush();
        } catch (IOException e) {
            System.err.println("Error " + e.getMessage());
        }


        try (InputStream er = p.getInputStream()) {
            int c;
            while ((c = er.read()) != -1) {
                System.out.print((char) c);
            }
            int exitVal = p.waitFor();
            System.out.println("Valores de salida: " + exitVal);
        } catch (IOException e) {
            System.err.println("Error " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}