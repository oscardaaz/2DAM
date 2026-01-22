package main;

public class Main {


    public static void main(String[] args) {

        String[] lista = {"Juan", "Jorge", "Oscar", "Patri"};
        System.out.printf("El tamaño de tu lista es de %d elementos\n",tamano(lista));
        System.out.println();

        caracteres("Caracteres reversos");


    }

    // Ejercicio 1:
    // Escribe una función que devuelva la longitud
    // de una lista o cadena, sin usar la función len().

    public static int tamano (String[] lista) {
        int tamano = 0;
        for (String elemento : lista) {
            tamano++;
        }
        return tamano;
    }

    /* 2.	Escribir un programa que pida al
    usuario una palabra y luego muestre por pantalla
    una a una las letras de la palabra introducida empezando por la última.
     */

    private static void caracteres(String palabra) {
        for (int i = palabra.length() - 1; i >= 0 ; i--) {
            System.out.println(palabra.charAt(i));
        }

    }

}
