package main;

public class CifradorCesar {

    public String cifrar(String texto, int desplazamiento) {
        StringBuilder resultado = new StringBuilder();
        texto = texto.toUpperCase();

        for (char caracter : texto.toCharArray()) {
            if (caracter >= 'A' && caracter <= 'Z') {
                char cifrado = (char) ((caracter - 'A' + desplazamiento) % 26 + 'A');
                resultado.append(cifrado);
            } else {
                resultado.append(caracter); // Mantener espacios, etc.
            }
        }
        return resultado.toString();
    }

    public String descifrar(String texto, int desplazamiento) {
        return cifrar(texto, 26 - (desplazamiento % 26));
    }

    public void ataqueFuerzaBruta(String textoCifrado) {
        System.out.println("\n🔓 ATAQUE POR FUERZA BRUTA");
        System.out.println("Probando los 25 desplazamientos posibles:\n");

        for (int d = 1; d < 26; d++) {
            String posible = descifrar(textoCifrado, d);
            System.out.printf("Desplazamiento %2d: %s%n", d, posible);
        }

        System.out.println("\n⚠️  ¡El cifrado César se rompe en segundos!");
    }
}