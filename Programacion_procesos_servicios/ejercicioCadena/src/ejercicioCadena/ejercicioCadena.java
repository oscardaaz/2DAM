package ejercicioCadena;

import java.util.Scanner;

public class ejercicioCadena {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce una cadena: ");
        String cadena = sc.nextLine();
        sc.close();

        if (cadena.isEmpty()) {
            System.out.println("Error: No se introdujo ninguna cadena");
            System.exit(1);
        }

        for (int i = 0; i < 5; i++) {
            System.out.println(cadena);
        }
    }
}
