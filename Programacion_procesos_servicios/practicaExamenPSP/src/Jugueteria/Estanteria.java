package Jugueteria;

import java.util.LinkedList;
import java.util.Queue;

public class Estanteria {
    private static final int CAPACIDAD_MAXIMA = 7;
    private Queue<Juguete> juguetes = new LinkedList<>();
    
    public synchronized void colocarJuguete(Juguete juguete) throws InterruptedException {
        while (juguetes.size() >= CAPACIDAD_MAXIMA) {
            wait();
        }
        juguetes.add(juguete);
        System.out.println("  📦 Juguete colocado en estantería: " + juguete + " [Total: " + juguetes.size() + "]");
        notifyAll();
    }
    
    public synchronized Juguete cogerJuguete() throws InterruptedException {
        while (juguetes.isEmpty()) {
            wait();
        }
        Juguete juguete = juguetes.poll();
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
