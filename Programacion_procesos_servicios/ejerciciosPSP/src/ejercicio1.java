import java.util.Locale;
import java.util.Scanner;

public class ejercicio1 {

    private static final Scanner sc = new Scanner(System.in).useLocale(Locale.US);

    public static void main(String[] args) {

        int a, b;

        a = leerEntero("\nIntroduce un numero entero: ");
        b = leerEntero("Introduce otro numero entero: ");

        int suma = a + b;
        System.out.printf("%nLa suma de %d + %d es: %d%n", a, b, suma);

        sc.close();
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.println("Error: No es un numero entero valido. Intenta de nuevo.");
            sc.next();
            System.out.print(mensaje);
        }
        return sc.nextInt();
    }
}