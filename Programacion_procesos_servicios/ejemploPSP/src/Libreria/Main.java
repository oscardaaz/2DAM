package Libreria;

import java.util.ArrayList;
import java.util.List;

public class Main {
    /**
     * Enunciado:
     * Una tienda de libros tiene:
     * Estantería con capacidad para 5 libros
     *
     * 2 escritores que escriben libros continuamente
     * Cada escritor escribe 3 libros
     * Cada libro tarda 1-2 segundos en escribirse
     * Los libros tienen: título (Libro-1, Libro-2...)
     * y autor (nombre del escritor)
     *
     * 3 lectores que leen libros de la estantería
     * Cada lector coge un libro, lo lee durante 1-3 segundos, y coge otro
     * Si no hay libros, esperan
     * Implementa la simulación con hilos.
     *
     */
    public static void main(String[] args) throws InterruptedException {

        Estanteria estanteria = new Estanteria(5);
        
        //List<Thread> escritor = new ArrayList<>();
        //List<Thread> lector = new ArrayList<>();

        for (int i = 1; i < 3; i++) {
            //escritor.add(new Escritor(estanteria, "Escritor " + i));
            //Thread escritor = new Thread(new Escritor(estanteria, "Escritor " + i));
            Escritor escritor = new Escritor(estanteria, "Escritor " + i);
            escritor.start();
//escritor.join();
        }

        for (int i = 1; i < 4; i++) {
            //lector.add(new Lector (estanteria, "Lector " + i));
            //Thread lector = new Thread(new Lector(estanteria, "Escritor " + i));
            Lector lector = new Lector(estanteria, "Lector " + i);
            lector.start();
           // lector.join();
        }

//        for (Thread hilo : escritor){
//            hilo.start();
//
//        }
//        for (Thread hilo : escritor){
//            hilo.join();
//
//        }
//
//        for (Thread hilo : lector){
//            hilo.start();
//
//        }
//        for (Thread hilo : lector){
//            hilo.join();
//
//        }

//        for (Thread hilo : hilos ){
//            hilo.join();
//        }

        System.out.println("la tienda ha cerrado");
    }
}
