package Supermercado;

import java.util.ArrayList;

public class Bandeja {

    private ArrayList<Integer> lista = new ArrayList<>();
    private final int CAPACIDAD;
    private boolean produccionTerminada = false;

    public Bandeja(int capacidad) {
        CAPACIDAD = capacidad;
    }

    public synchronized void agregar(int valor) throws InterruptedException {
        while  (lista.size() >= CAPACIDAD) wait();
        lista.add(valor);
        System.out.println(Thread.currentThread().getName() + " Anade la porcion: "
                );
        notifyAll();
    }

    public synchronized ArrayList<Integer> sacar() throws InterruptedException {
        while (lista.isEmpty() && !produccionTerminada) wait();
        // ✅ NUEVO: Si está vacío Y producción terminada → señal para terminar
        if (lista.isEmpty() && produccionTerminada) {
            return null;
        }
        lista.removeFirst();
        System.out.println(Thread.currentThread().getName() + " saca una porcion" );
        notifyAll();
        return lista;
    }

    // ✅ NUEVO MÉTODO: Para avisar que no se producirán más libros
    public synchronized void terminarProduccion() {
        produccionTerminada = true;
        notifyAll();  // ← Despertar a todos los que esperan
    }
}
