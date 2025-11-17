package Jugueteria;

import java.util.Random;

public class Nino extends Thread {
    private String nombre;
    private boolean esBueno;
    private Estanteria estanteria;
    private int juguetesRotos = 0;
    private boolean expulsado = false;
    
    public Nino(String nombre, boolean esBueno, Estanteria estanteria) {
        this.nombre = nombre;
        this.esBueno = esBueno;
        this.estanteria = estanteria;
    }
    
    @Override
    public void run() {
        Random random = new Random();
        
        if (esBueno) {
            System.out.println("😇 " + nombre + " (niño bueno) entró a la tienda");
        } else {
            System.out.println("😈 " + nombre + " (niño malo) entró a la tienda");
        }
        
        while (!expulsado) {
            try {
                // Intentar coger un juguete
                if (!estanteria.hayJuguetes()) {
                    Thread.sleep(500); // Esperar un poco antes de verificar de nuevo
                    if (!estanteria.hayJuguetes()) {
                        break; // No hay más juguetes, salir
                    }
                }
                
                Juguete juguete = estanteria.cogerJuguete();
                
                if (esBueno) {
                    // Niño bueno: juega y devuelve
                    int tiempoJuego = random.nextInt(4) + 2; // 2-5 segundos
                    System.out.println("😇 " + nombre + " está jugando con " + juguete);
                    Thread.sleep(tiempoJuego * 1000);
                    System.out.println("😇 " + nombre + " terminó de jugar con " + juguete);
                    estanteria.devolverJuguete(juguete);
                    
                } else {
                    // Niño malo: usa hasta romper
                    int tiempoUso = random.nextInt(4) + 3; // 3-6 segundos
                    System.out.println("😈 " + nombre + " está usando " + juguete);
                    Thread.sleep(tiempoUso * 1000);
                    juguetesRotos++;
                    System.out.println("💥 " + nombre + " rompió " + juguete + " [Rotos: " + juguetesRotos + "/3]");
                    
                    if (juguetesRotos >= 3) {
                        expulsado = true;
                        System.out.println("🚫 " + nombre + " ha sido EXPULSADO por romper 3 juguetes");
                    }
                }
                
            } catch (InterruptedException e) {
                break; // Salir si es interrumpido
            }
        }
        
        if (esBueno) {
            System.out.println("😇 " + nombre + " se va de la tienda");
        } else if (!expulsado) {
            System.out.println("😈 " + nombre + " se va de la tienda");
        }
    }
}
