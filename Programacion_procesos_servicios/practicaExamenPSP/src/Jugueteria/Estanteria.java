package Jugueteria;

import java.util.ArrayList;
import java.util.List;

public class Estanteria {
    private static final int CAPACIDAD_MAXIMA = 7;
    private final List<Juguete> juguetes = new ArrayList<>();
    private boolean produccionTerminada = false; // nueva bandera

    public synchronized void colocarJuguete(Juguete juguete) throws InterruptedException {
        while (juguetes.size() >= CAPACIDAD_MAXIMA) {
            wait();
        }
        juguetes.add(juguete); // FIFO: añadimos al final
        System.out.println("  📦 Juguete colocado en estantería: " + juguete + " [Total: " + juguetes.size() + "]");
        notifyAll();
    }
    
    public synchronized Juguete cogerJuguete() throws InterruptedException {
        // Esperar mientras no haya juguetes y la producción no haya terminado
        while (juguetes.isEmpty() && !produccionTerminada) {
            wait();
        }
        // Si no hay juguetes y ya no se producirán más, devolver null para indicar cierre
        if (juguetes.isEmpty() && produccionTerminada) {
            return null;
        }
        Juguete juguete = juguetes.remove(0); // FIFO: coger el primero
        notifyAll();
        return juguete;
    }
    
    public synchronized void devolverJuguete(Juguete juguete) throws InterruptedException {
        while (juguetes.size() >= CAPACIDAD_MAXIMA) {
            wait();
        }
        juguetes.add(juguete);
        System.out.println("  ✅ Juguete devuelto a estantería: " + juguete + " [Total: " + juguetes.size() + "]");
        notifyAll();
    }
    
    public synchronized boolean hayJuguetes() {
        return !juguetes.isEmpty();
    }

    // Nuevo: marcar que ya no se producirán más juguetes y despertar a quien espere
    public synchronized void finalizarProduccion() {
        produccionTerminada = true;
        notifyAll();
    }
}
