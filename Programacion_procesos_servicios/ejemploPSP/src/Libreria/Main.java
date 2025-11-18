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
        List<Thread> todosLosHilos = new ArrayList<>();

        // ✅ GUARDAR SOLO LOS ESCRITORES en lista separada
        List<Escritor> escritores = new ArrayList<>();

        // 2 escritores
        for (int i = 1; i <= 2; i++) {
            Escritor escritor = new Escritor(estanteria, "Escritor-" + i);
            escritores.add(escritor);
            todosLosHilos.add(escritor);
            escritor.start();
        }

        // 3 lectores
        for (int i = 1; i <= 3; i++) {
            Lector lector = new Lector(estanteria, "Lector-" + i);
            todosLosHilos.add(lector);
            lector.start();
        }

        // ✅ PRIMERO: Esperar solo a que terminen los ESCRITORES
        for (Escritor escritor : escritores) {
            escritor.join();
        }

        // ✅ SEGUNDO: Avisar que la producción terminó
        System.out.println("📢 Todos los escritores han terminado. No habrá más libros.");
        estanteria.terminarProduccion();

        // ✅ TERCERO: Esperar a que terminen los LECTORES
        for (Thread hilo : todosLosHilos) {
            hilo.join();  // Esto incluye escritores y lectores
        }

        System.out.println("🏪 La tienda ha cerrado");
    }
}
