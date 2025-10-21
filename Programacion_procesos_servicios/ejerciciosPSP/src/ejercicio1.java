import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ejercicio1 {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int a, b, suma;
        System.out.print("\nIntroduce un numero entero: ");
        a = sc.nextInt();

        System.out.print("Introduce un numero entero: ");
        b = sc.nextInt();

        suma = a + b ;
        System.out.printf("%nLa suma de %d + %d es: %d " ,a,b,suma);

    }
}
