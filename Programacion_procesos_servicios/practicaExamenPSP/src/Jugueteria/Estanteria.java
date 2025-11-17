package Jugueteria;

import java.util.ArrayList;
import java.util.List;

public class Estanteria {
    private static final int CAPACIDAD_MAXIMA = 7;
    private final List<Juguete> juguetes = new ArrayList<>();

    public synchronized void colocarJuguete(Juguete juguete) throws InterruptedException {
        while (juguetes.size() >= CAPACIDAD_MAXIMA) {
            wait();
        }
        juguetes.add(juguete); // FIFO: añadimos al final
        System.out.println("  📦 Juguete colocado en estantería: " + juguete + " [Total: " + juguetes.size() + "]");
        notifyAll();
    }
    
    public synchronized Juguete cogerJuguete() throws InterruptedException {
        while (juguetes.isEmpty()) {
            wait();
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
}
