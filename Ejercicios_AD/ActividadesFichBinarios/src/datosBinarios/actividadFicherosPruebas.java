package datosBinarios;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class actividadFicherosPruebas {

    static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        //Actividades ficheros binarios (1.5 y 1.6)


        escribirPersonas();

        leerPersonas();



    }


    private static void escribirPersonas() {

        /*String[] marca = {};
        String[] modelo = {};
        String[] color = {};
        int[] año  = {};
        String[] pinturaMetalizada = {"si","no"};*/

        System.out.println("Escribe el nombre del fichero");

        String nombreFichero = sc.nextLine();
         Path ruta = Path.of("src", nombreFichero);

        try (OutputStream flujoEscritura = Files.newOutputStream(ruta);
             DataOutputStream dos = new DataOutputStream(flujoEscritura)) {

            for (int i = 0; i <= 2; i++) {

                    System.out.println("Escribe la marca del coche");
                    String marca = sc.nextLine();

                    System.out.println("Escribe el modelo del coche");
                    String modelo = sc.nextLine();

                    System.out.println("Escribe el color del coche");
                    String color = sc.nextLine();

                    System.out.println("¿Pintura metalizada? (si/no)");
                    String pinturaMetalizada = sc.nextLine();

                    System.out.println("Escribe el año de adquisicion");
                    int año = sc.nextInt();

                    sc.nextLine();


                dos.writeUTF(marca);
                dos.writeUTF(modelo);
                dos.writeUTF(color);
                dos.writeUTF(pinturaMetalizada);
                dos.writeInt(año);

                i++;
                System.out.println();
            }

            System.out.println("Datos escritos correctamente en: " + ruta.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void leerPersonas() {

        System.out.println("Escribe el nombre del fichero a leer");

        String nombreFichero = sc.nextLine();
        Path ruta = Path.of("src", nombreFichero);
        System.out.println();

        try (InputStream flujoLectura = Files.newInputStream(ruta); DataInputStream dis = new DataInputStream(flujoLectura)){

            while(true){

                try {

                    String marca = dis.readUTF();
                    String modelo = dis.readUTF();
                    String color = dis.readUTF();
                    String pinturaMetalizada = dis.readUTF();
                    int año = dis.readInt();


                    System.out.printf("Nombre: %s, Modelo: %s, Color: %s, Pintura Metalizada %s, año: %d%n%n",marca, modelo,color,pinturaMetalizada,año
                            );



                } catch (EOFException e) {
                    System.out.println("Fin del fichero");
                    break;
                }
            }



        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

    }
}