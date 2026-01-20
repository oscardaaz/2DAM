package main;

import java.util.Random;

public class Utilidades {

    public static String generarMensajeAleatorio(int longitud) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";

        for (int i = 0; i < longitud; i++) {
            sb.append(letras.charAt(rand.nextInt(letras.length())));
        }
        return sb.toString();
    }

    public static void mostrarTablaCesar() {
        System.out.println("\n📊 TABLA DE CIFRADO CÉSAR (desplazamiento 3):");
        System.out.println("Original:  A B C D E F G H I J K L M N O P Q R S T U V W X Y Z");
        System.out.println("Cifrado:   D E F G H I J K L M N O P Q R S T U V W X Y Z A B C");
    }
}
