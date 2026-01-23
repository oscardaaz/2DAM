package main;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteNumeros {
    public static void main(String[] args) {
        Socket cliente = null;
        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;
        Scanner sc = new Scanner(System.in);

        try {
            cliente = new Socket("localhost", 5050);
            System.out.println("Conectado al servidor.");

            salida = new ObjectOutputStream(cliente.getOutputStream());
            entrada = new ObjectInputStream(cliente.getInputStream());

            int numero;
            boolean continuar = true;

            while (continuar) {
                System.out.print("Introduce un número (<= 0 para salir): ");

                while (!sc.hasNextInt()) {
                    System.out.println("Error: Debes introducir un número entero.");
                    sc.nextLine();
                    System.out.print("Introduce un número (<= 0 para salir): ");
                }

                numero = sc.nextInt();
                sc.nextLine();

                Numeros numeroObjeto = new Numeros(numero);
                salida.reset();
                salida.writeObject(numeroObjeto);

                if (numero <= 0) {
                    System.out.println("Cerrando cliente...");
                    continuar = false;
                } else {
                    Numeros resultado = (Numeros) entrada.readObject();
                    System.out.println("Resultado: " + resultado.getNumero() +
                            "² = " + resultado.getCuadrado() +
                            ", " + resultado.getNumero() +
                            "³ = " + resultado.getCubo() + "\n");
                }
            }

        } catch (IOException e) {
            System.out.println("Error: No se puede conectar al servidor o error de comunicación");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (entrada != null) entrada.close();
                if (salida != null) salida.close();
                if (cliente != null) cliente.close();
                sc.close();
                System.out.println("Cliente cerrado.");
            } catch (IOException e) {
                System.out.println("Error al cerrar recursos");
            }
        }
    }
}