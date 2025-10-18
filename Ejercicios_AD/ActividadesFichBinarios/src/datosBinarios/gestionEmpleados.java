package datosBinarios;

import Objetos.Empleado;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Locale;
import java.util.Scanner;


public class gestionEmpleados {

    private static final Scanner sc = new Scanner(System.in).useLocale(Locale.US);

    public static void main(String[] args) {
        //Actividades ficheros binarios (1.6)

        escribirEmpleado();
        leerEmpleados();
    }

    private static int cantidadEmpleados(){
        System.out.println("Introduce la cantidad de empleados a añadir");
        int cantidadEmpleados = sc.nextInt();
        sc.nextLine();
        return cantidadEmpleados;
    }

    private static void escribirEmpleado() {

        Path ruta = obtenerRutaFichero("Escribe el nombre del fichero a escribir: ");
        boolean existe = Files.exists(ruta);

        try (OutputStream os = Files.newOutputStream(
                ruta,
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE);
             ObjectOutputStream oos = existe //Valor booleano true/false para si existe se hace sin cabecera y no da error..
                     ? new ObjectOutputStreamSinCabecera(os)
                     : new ObjectOutputStream(os)) {

            int cantidadEmpleados = existe ? cantidadEmpleados() : 3;
            String mensaje = existe ? " a añadir: "
                                    : ": " ;
            String mensajeFichero = existe  ? "\nEmpleados añadidos a fichero correctamente"
                                            : "\nFichero escrito correctamente" ;

            for (int i = 1; i <= cantidadEmpleados; i++) {

                System.out.print("Introduce el nombre del empleado" + mensaje);
                String nombre = sc.nextLine();

                System.out.print("Introduce el departamento del empleado" + mensaje);
                int departamento = sc.nextInt();

                System.out.print("Introduce el salario del empleado"+mensaje);
                double salario = sc.nextDouble();
                sc.nextLine();

                Empleado empleado = new Empleado(nombre, departamento, salario);
                oos.writeObject(empleado);
                System.out.printf("Empleado %d escrito correctamente%n", i);

            }

            System.out.println(mensajeFichero);

        } catch (IOException ioe) {
            System.err.println("Error al escribir el Empleado " + ioe.getMessage());
        }
    }

    private static void leerEmpleados() {

        Path ruta = obtenerRutaFichero("Escribe el nombre del fichero a leer: ");
        try (InputStream is = Files.newInputStream(ruta);
             ObjectInputStream ois = new ObjectInputStream(is)) {
            //int contador = 1;
            System.out.println("Empleados registrados: \n");

            while (true) {
                try {

                    Empleado empleados = (Empleado) ois.readObject();
                    //System.out.printf("Persona %d leida correctamente%n%n", contador++);

                    System.out.println(empleados);

                }catch (EOFException eofe) {

                    System.out.println("\nFin de lectura");
                    break;

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }


        } catch (IOException e) {
            System.err.println("Error al leer el fichero " + e.getMessage());
        }
    }

    private static Path obtenerRutaFichero(String mensaje) {
        System.out.print(mensaje);
        String nombreFichero = sc.nextLine();
        System.out.println();
        return Path.of(System.getProperty("user.dir"),nombreFichero);
    }

    private static class ObjectOutputStreamSinCabecera extends ObjectOutputStream{

        public ObjectOutputStreamSinCabecera(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() {
            //No hacemos nada
        }
    }
}