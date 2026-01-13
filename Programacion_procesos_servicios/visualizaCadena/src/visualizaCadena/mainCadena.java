package visualizaCadena;

public class mainCadena {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("No se ha introducido ninguna cadena como argumento.");
            System.exit(1);
        }

        String cadena = args[0];

        for (int i = 0; i < 5; i++) {
            System.out.println(cadena);
        }
    }
}
