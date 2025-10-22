package ejercicios;

import Objetos.Deportista;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Locale;
import java.util.Scanner;

public class prueba {

    private static final Scanner sc = new Scanner(System.in).useLocale(Locale.US);
    private static final Path rutaFichero = Path.of("deportistas.txt");

    public static void main(String[] args) {

        anadirDeportistas();
        //leerConScanner();

    }

    private static void anadirDeportistas(){


        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(rutaFichero,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND))) {
            System.out.print("¿Cuántos deportistas quieres introducir? ");
            int cantidadDeportistas = sc.nextInt();
            sc.nextLine();

            for (int i = 1; i <= cantidadDeportistas; i++) {
                String nombre ; int edad ; double estatura;
                System.out.println("\nDeportista " + i + ":");

                System.out.print("Nombre: ");
                nombre = sc.nextLine();

                System.out.print("Edad: ");
                edad = sc.nextInt();

                System.out.print("Estatura: ");
                estatura = sc.nextDouble();
                sc.nextLine();
               
                Deportista deportista = new Deportista(nombre,edad,estatura);
                pw.write(String.valueOf(deportista));

            }
            System.out.println("\nFichero escrito correctamente.");
        } catch (IOException e) {
            System.err.println("IOE generada en escribirPrintWriter " + e.getMessage());
        }
            leerConScanner();

    }

    private static void leerConScanner() {
        System.out.println("\n--- CONTENIDO COMPLETO DEL FICHERO ---\n");
        try (Scanner sc = new Scanner(Files.newBufferedReader(rutaFichero,
                StandardCharsets.UTF_8 ))) {
            String linea;
            while (sc.hasNextLine()) {
                linea = sc.nextLine();
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.err.println("IOE generada en leerConScanner " + e.getMessage());
        }

        System.out.println("\n--- RESULTADOS ---\n");
        try (Scanner sc = new Scanner(Files.newBufferedReader(rutaFichero,
                StandardCharsets.UTF_8))) {
            double estaturaTotal = 0;
            int contador = 0;

            double estatura = 0;
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();

                // Asumiendo que los datos están separados por ";"
                String[] partes = linea.split(";");
                estatura = 0;
                if (partes.length >= 3) {
                    String estaturaAuxiliar = partes[2].trim();
                    try {
                        estatura = Double.parseDouble(estaturaAuxiliar);
                        estaturaTotal += estatura;


                    } catch (NumberFormatException e) {
                        System.err.println("Error al convertir estatura a double: " + estaturaAuxiliar);
                    }
                } else {
                    System.out.println("Línea mal formada: " + linea);

                }

                contador++;
            }

            System.out.println("Estatura del deportista: " + estatura / contador);
            System.out.println("Total de deportistas: " + contador);

        } catch (IOException e) {
            System.err.println("IOE generada en leerConScanner (cálculo): " + e.getMessage());
        }
    }

    }




