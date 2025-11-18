package Libreria;

import java.sql.Array;
import java.util.ArrayList;

public class Estanteria {

    private ArrayList<Libro> libros = new ArrayList<>();
    private final int CAPACIDAD;
    private boolean produccionTerminada = false;

    public Estanteria(int capacidad) {
        CAPACIDAD = capacidad;
    }

    public synchronized void agregar(Libro libro) throws InterruptedException {
        while  (libros.size() >= CAPACIDAD) wait();
        libros.add(libro);
        System.out.println(Thread.currentThread().getName() + " Anade el libro: "
                + libro);
        notifyAll();
    }

    public synchronized Libro sacar() throws InterruptedException {
        while (libros.isEmpty() && !produccionTerminada) wait();
        // ✅ NUEVO: Si está vacío Y producción terminada → señal para terminar
        if (libros.isEmpty() && produccionTerminada) {
            return null;
        }
        Libro libro = libros.removeFirst();
        System.out.println(Thread.currentThread().getName() + " saca el libro " + libro);
        notifyAll();
        return libro;
    }

    // ✅ NUEVO MÉTODO: Para avisar que no se producirán más libros
    public synchronized void terminarProduccion() {
        produccionTerminada = true;
        notifyAll();  // ← Despertar a todos los que esperan
    }
}
