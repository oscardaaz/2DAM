package palindromo;

import java.io.IOException;
import java.util.Scanner;

public class ejecutable {

    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "out/artifacts/palindromo_jar/palindromo.jar");
        pb.inheritIO();
        Process p = pb.start();
        p.waitFor();
    }
}
