package main;

public class Demo {

    public static void mostrarDemostracion() {
        CifradorCesar cifrador = new CifradorCesar();

        System.out.println("\n🎬 DEMOSTRACIÓN COMPLETA PARA LA PRESENTACIÓN");
        System.out.println("==============================================");

        // Ejemplo 1: Cifrado básico
        System.out.println("\n1. CIFRADO BÁSICO:");
        String mensaje = "HOLA COMPAÑEROS DE CLASE";
        int desplazamiento = 3;
        String cifrado = cifrador.cifrar(mensaje, desplazamiento);
        System.out.println("   Original:     " + mensaje);
        System.out.println("   Desplazamiento: " + desplazamiento);
        System.out.println("   Cifrado:      " + cifrado);

        // Ejemplo 2: Descifrado
        System.out.println("\n2. DESCIFRADO:");
        String descifrado = cifrador.descifrar(cifrado, desplazamiento);
        System.out.println("   Cifrado:      " + cifrado);
        System.out.println("   Desplazamiento: " + desplazamiento);
        System.out.println("   Original:     " + descifrado);

        // Ejemplo 3: Por qué es inseguro
        System.out.println("\n3. POR QUÉ ES INSECURO:");
        String secreto = "EXAMEN FACIL";
        String secretoCifrado = cifrador.cifrar(secreto, 7);
        System.out.println("   Secreto:      " + secreto);
        System.out.println("   Cifrado (d=7): " + secretoCifrado);
        System.out.println("\n   Probemos fuerza bruta...");

        // Mini fuerza bruta para la demostración
        System.out.println("\n   Posibilidades más probables:");
        System.out.println("   d=19: " + cifrador.descifrar(secretoCifrado, 19));
        System.out.println("   d=20: " + cifrador.descifrar(secretoCifrado, 20));
        System.out.println("   ¡Ahí está! d=7: " + cifrador.descifrar(secretoCifrado, 7));

        // Ejemplo 4: Comparación con criptografía real
        System.out.println("\n4. COMPARACIÓN CON CRIPTOGRAFÍA MODERNA:");
        System.out.println("   Cifrado César: 25 posibilidades → se prueba en 0.01 segundos");
        System.out.println("   AES-128: 2^128 posibilidades → tomaría billones de años");

        System.out.println("\n✨ Demostración finalizada ✨");
    }
}