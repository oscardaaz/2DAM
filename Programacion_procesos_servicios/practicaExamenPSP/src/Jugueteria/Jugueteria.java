package Jugueteria;

import java.util.ArrayList;
import java.util.List;

public class Jugueteria {
    public static void main(String[] args) {
        System.out.println("🎄 ¡Bienvenidos a la Juguetería Polo Norte! 🎄\n");
        
        Estanteria estanteria = new Estanteria();
        
        // Crear 3 elfos
        List<Elfo> elfos = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Elfo elfo = new Elfo("Elfo " + i, estanteria);
            elfos.add(elfo);
            elfo.start();
        }
        
        // Dar un poco de tiempo para que se creen algunos juguetes
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Crear 5 niños buenos y 20 niños malos
        List<Nino> ninos = new ArrayList<>();
        
        for (int i = 1; i <= 5; i++) {
            Nino ninoBueno = new Nino("Niño Bueno " + i, true, estanteria);
            ninos.add(ninoBueno);
            ninoBueno.start();
        }
        
        for (int i = 1; i <= 20; i++) {
            Nino ninoMalo = new Nino("Niño Malo " + i, false, estanteria);
            ninos.add(ninoMalo);
            ninoMalo.start();
        }
        
        // Esperar a que todos los elfos terminen
        for (Elfo elfo : elfos) {
            try {
                elfo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("\n🎅 Todos los elfos terminaron de crear juguetes\n");
        
        // Esperar a que todos los niños terminen
        for (Nino nino : ninos) {
            try {
                nino.join(15000); // Timeout de 15 segundos por niño
                if (nino.isAlive()) {
                    nino.interrupt(); // Interrumpir si no ha terminado
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("\n🎄 ¡La Juguetería Polo Norte ha cerrado! 🎄");
    }
}
