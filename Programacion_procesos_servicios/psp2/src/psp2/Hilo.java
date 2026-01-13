/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psp2;

/**
 *
 * @author oscar.domalo
 */
public class Hilo extends Thread {

    private int c = 0;
    private boolean stopHilo = false;

    public Hilo(String nom) {
        super(nom);

    }

    public void pararHilo() {
        stopHilo = true;
    }

    public int getContador() {
        return c;
    }

    @Override
    public void run() {
        while (!stopHilo) {
            try {
                sleep(2);

            } catch (InterruptedException ignore) {
            }
            c++;
        }
        System.out.println("Fin hilo " + this.getName() + " con Prioridad: " + this.getPriority() + "Contador: " + c);
    }

}
