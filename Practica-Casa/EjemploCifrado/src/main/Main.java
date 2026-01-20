package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CifradorCesar cifrador = new CifradorCesar();

        System.out.println("🎯 CIFRADO CÉSAR - PRESENTACIÓN CRIPTOGRAFÍA");
        System.out.println("===========================================");

        while (true) {
            System.out.println("\n¿Qué quieres hacer?");
            System.out.println("1. Cifrar un mensaje");
            System.out.println("2. Descifrar un mensaje");
            System.out.println("3. Ataque por fuerza bruta");
            System.out.println("4. Ver demostración completa");
            System.out.println("5. Salir");
            System.out.print("Opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            if (opcion == 5) {
                System.out.println("¡Gracias por usar el sistema!");
                break;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Mensaje a cifrar: ");
                    String texto = scanner.nextLine();
                    System.out.print("Desplazamiento (ej. 3): ");
                    int desplazamiento = scanner.nextInt();
                    String cifrado = cifrador.cifrar(texto, desplazamiento);
                    System.out.println("✅ Mensaje cifrado: " + cifrado);
                    break;

                case 2:
                    System.out.print("Mensaje cifrado: ");
                    texto = scanner.nextLine();
                    System.out.print("Desplazamiento usado: ");
                    desplazamiento = scanner.nextInt();
                    String descifrado = cifrador.descifrar(texto, desplazamiento);
                    System.out.println("✅ Mensaje original: " + descifrado);
                    break;

                case 3:
                    System.out.print("Mensaje cifrado para atacar: ");
                    texto = scanner.nextLine();
                    cifrador.ataqueFuerzaBruta(texto);
                    break;

                case 4:
                    Demo.mostrarDemostracion();
                    break;

                default:
                    System.out.println("❌ Opción no válida");
            }
        }
        scanner.close();
    }
}