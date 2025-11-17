package Jugueteria;

import java.util.Random;

public class Elfo extends Thread {
    private String nombre;
    private Estanteria estanteria;
    private static final String[] TIPOS_JUGUETES = {"muñeco", "vehículo", "arma", "pelota"};
    
    public Elfo(String nombre, Estanteria estanteria) {
        this.nombre = nombre;
        this.estanteria = estanteria;
    }
    
    @Override
    public void run() {
        Random random = new Random();
        int cantidadJuguetes = random.nextInt(11) + 10; // 10-20 juguetes
        
        System.out.println("🧝 " + nombre + " comenzará a crear " + cantidadJuguetes + " juguetes");
        
        for (int i = 0; i < cantidadJuguetes; i++) {
            try {
                // Crear juguete (tarda 1-3 segundos)
                int tiempoCreacion = random.nextInt(3) + 1;
                Thread.sleep(tiempoCreacion * 1000);
                
                // Elegir tipo aleatorio
                String tipo = TIPOS_JUGUETES[random.nextInt(TIPOS_JUGUETES.length)];
                Juguete juguete = new Juguete(tipo);
                
                System.out.println("🧝 " + nombre + " creó: " + juguete);
                
                // Colocar en estantería
                estanteria.colocarJuguete(juguete);
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("🧝 " + nombre + " terminó de crear todos sus juguetes");
    }
}
