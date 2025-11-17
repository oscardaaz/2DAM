package Libreria;

import java.sql.Array;
import java.util.ArrayList;

public class Estanteria {

    private ArrayList<Libro> libros = new ArrayList<>();
    private final int CAPACIDAD;

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
        while (libros.isEmpty()) wait();
        Libro libro = libros.removeFirst();
        System.out.println(Thread.currentThread().getName() + " saca el libro " + libro);
        notifyAll();
        return libro;
    }
}
