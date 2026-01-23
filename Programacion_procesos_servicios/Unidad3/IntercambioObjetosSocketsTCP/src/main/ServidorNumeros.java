package main;

import java.io.*;
import java.net.*;

public class ServidorNumeros {
    public static void main(String[] args) {
        ServerSocket servidor = null;
        Socket cliente = null;
        ObjectInputStream entrada = null;
        ObjectOutputStream salida = null;

        try {
            servidor = new ServerSocket(5050);
            System.out.println("Servidor iniciado. Esperando cliente...");

            cliente = servidor.accept();
            System.out.println("Cliente conectado desde: " + cliente.getInetAddress());

            entrada = new ObjectInputStream(cliente.getInputStream());
            salida = new ObjectOutputStream(cliente.getOutputStream());

            Numeros numeroObjeto;
            boolean continuar = true;

            while (continuar) {
                numeroObjeto = (Numeros) entrada.readObject();
                System.out.println("Recibido número: " + numeroObjeto.getNumero());

                int num = numeroObjeto.getNumero();

                if (num <= 0) {
                    System.out.println("Número <= 0. Cerrando servidor...");
                    continuar = false;
                } else {
                    long cuadrado = (long) num * num;
                    long cubo = cuadrado * num;

                    numeroObjeto.setCuadrado(cuadrado);
                    numeroObjeto.setCubo(cubo);

                    System.out.println("Calculado - Cuadrado: " + cuadrado + ", Cubo: " + cubo);

                    salida.reset();
                    salida.writeObject(numeroObjeto);
                }
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Objeto incorrecto recibido");
        } finally {
            try {
                if (entrada != null) entrada.close();
                if (salida != null) salida.close();
                if (cliente != null) cliente.close();
                if (servidor != null) servidor.close();
                System.out.println("Servidor cerrado.");
            } catch (IOException e) {
                System.out.println("Error al cerrar recursos");
            }
        }
    }
}