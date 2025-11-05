package palindromo;

import java.io.IOException;
import java.util.Scanner;

public class ejecutable {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce cadena: ");
        String cadena = sc.nextLine();
        sc.close();

        ProcessBuilder pb = new ProcessBuilder("java", "Palindromo");
        pb.inheritIO();
        Process p = null;
        try {
            p = pb.start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error I/O");
        }

    }
}
