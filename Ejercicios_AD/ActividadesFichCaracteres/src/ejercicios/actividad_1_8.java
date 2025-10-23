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

public class actividad_1_8 {

    private static final Scanner sc = new Scanner(System.in).useLocale(Locale.US);
    private static final Path rutaFichero = Path.of("deportistas.txt");


    public static void main(String[] args) {

        anadirDeportistas();
        leerConScanner();

    }

    private static void anadirDeportistas() {


        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(rutaFichero,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND))) {
            System.out.print("¿Cuántos deportistas quieres introducir? ");
            int cantidadDeportistas = sc.nextInt();
            sc.nextLine();

            for (int i = 1; i <= cantidadDeportistas; i++) {
                String nombre;
                int edad;
                double estatura;
                System.out.println("\nDeportista " + i + ":");

                System.out.print("Nombre: ");
                nombre = sc.nextLine();

                System.out.print("Edad (entero): ");
                edad = sc.nextInt();

                System.out.print("Estatura (double, usa punto): ");
                estatura = sc.nextDouble();
                sc.nextLine();

                Deportista deportista = new Deportista(nombre, edad, estatura);
                //Write no escribe objetos solo Strings
                //pw.write(String.valueOf(deportista)); //Y uso el salto de linea en el toString()
                pw.println(deportista); //usando este me ahorro el String.ValueOf, ya que pw.println escribe objetos
                //Quitar en el toString el salto de linea sino out of bounds
            }
            sc.close();
            System.out.println("\nFichero escrito correctamente.");
        } catch (IOException e) {
            System.err.println("IOE generada en escribirPrintWriter " + e.getMessage());
        }
    }

    private static void leerConScanner() {
        Locale.setDefault(Locale.US);
        try (Scanner sc = new Scanner(Files.newBufferedReader(rutaFichero,
                StandardCharsets.UTF_8))) {
            System.out.println("\n--- CONTENIDO COMPLETO DEL FICHERO ---");
            String linea;
            int contador = 0;
            double edadTotal = 0;
            double estaturaMax = 0;
            String nombreMax = "";
            while (sc.hasNextLine()) {
                linea = sc.nextLine();
                System.out.println(linea);

                String[] auxiliar = linea.trim().split(" ");
                double estaturaAux = Double.parseDouble(auxiliar[2]);
                double edadAux = Double.parseDouble(auxiliar[1]);
                String nombre = auxiliar[0];

                edadTotal += edadAux;
                if (estaturaMax < estaturaAux) {
                    estaturaMax = estaturaAux;
                    nombreMax = nombre;
                }
                contador++;
            }
            if (contador > 0) {
                System.out.println("\n--- RESULTADOS ---");
                System.out.printf("Total de deportistas: %d%n", contador);
                System.out.printf("Media de edad: %.2f%n", edadTotal / contador);
                System.out.printf("Deportista más alto: %s (%.2f)%n", nombreMax, estaturaMax);
            } else {
                System.out.println("No se encontraron datos.");
            }
        } catch (IOException e) {
            System.err.println("IOE generada en leerConScanner " + e.getMessage());
        }

    }
}



