package aleatorios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

public class AccesoAleatorio_v1 {

    private static final Path RUTA = Path.of("aleatorios.dat");
    public static void main(String[] args) {

        escribirRegistros();
        leerRegistro();

    }

    private static void escribirRegistros(){
        String[] apellidos = {"Fernández","Gil","Nuñez"};
        int[] dept = {10,20,30};
        double[] salarios = {1000.45,1100.00,25000.00};

        try(RandomAccessFile raf = new RandomAccessFile(
                RUTA.toFile(),"rw")){

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void leerRegistro(){

    }

}
