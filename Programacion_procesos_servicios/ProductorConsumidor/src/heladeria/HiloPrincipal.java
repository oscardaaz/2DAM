/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package heladeria;

/**
 *
 * @author aleja
 */
public class HiloPrincipal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        for (int i = 0; i < 5; i++) {
            Heladero h = new Heladero(i + "");
            Thread t = new Thread(h);
            t.start();
            h.start();
            

        }

        for (int i = 0; i < 10; i++) {
            Niños n = new Niños(i + "");
            Thread t = new Thread(n);
            t.start();

        }
        System.out.println("Soy el hilo principal y me mori -.-");
    }

}
