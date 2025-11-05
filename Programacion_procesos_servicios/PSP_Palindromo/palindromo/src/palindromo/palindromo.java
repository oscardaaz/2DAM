package palindromo;

import java.util.Scanner;

public class palindromo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce una cadena: ");
        String cadena = sc.nextLine().trim();
        sc.close();

        if (cadena.isEmpty()) {
            System.exit(1);
        }

        String invertida = new StringBuilder(cadena).reverse().toString();

        if (cadena.equalsIgnoreCase(invertida)) {
            System.out.println("Es palindromo");
        } else {
            System.out.println("No es palindromo");
        }
    }
}
