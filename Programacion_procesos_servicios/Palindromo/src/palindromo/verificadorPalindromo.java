package palindromo;

public class verificadorPalindromo {
    public static void main(String[] args) {
        // Leer desde argumentos del main (entrada consola/estándar)
        if (args.length == 0) {
            System.out.println("La cadena está vacía.");
            System.exit(1);
        }

        String cadena = args[0];

        if (cadena.isEmpty()) {
            System.out.println("La cadena está vacía.");
            System.exit(1);
        }

        // Verificar si es palíndromo con StringBuilder
        String limpia = cadena.replaceAll(" ", "").toLowerCase();
        String reverso = new StringBuilder(limpia).reverse().toString();

        if (limpia.equals(reverso)) {
            System.out.println("La cadena: " + cadena + " ES palindromo");
        } else {
            System.out.println("La cadena: " + cadena + " NO es palíndromo");
        }
    }
}
