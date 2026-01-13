/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package psp2;

import static java.lang.Thread.sleep;

/**
 *
 * @author oscar.domalo
 */
public class Psp2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /*// TODO code application logic here
        Hilo h1 = new Hilo("Hilo1");
        Hilo h2 = new Hilo("Hilo2");
        Hilo h3 = new Hilo("Hilo3");

        h1.setPriority(Thread.NORM_PRIORITY);
        h2.setPriority(Thread.MAX_PRIORITY);
        h3.setPriority(Thread.MIN_PRIORITY);
        
        h1.start();
        h2.start();
        h3.start();

        try {
            sleep(10000);
        } catch (InterruptedException e) {}
        h1.pararHilo();
        h2.pararHilo();
        h3.pararHilo();*/
        
        
            
    Contador contadorCompartido = new Contador(100);
    HiloA a = new HiloA("HiloA",contadorCompartido);
    HiloB b = new HiloB("HiloB",contadorCompartido);
    a.start();
    b.start();
        
        
    }
    
    
}
