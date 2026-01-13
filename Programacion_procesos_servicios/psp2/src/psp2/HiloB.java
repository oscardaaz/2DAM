/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psp2;

import static java.lang.Thread.sleep;

/**
 *
 * @author oscar.domalo
 */
public class HiloB extends Thread {

    private Contador objetoContador;

    public HiloB(String n, Contador c) {
        super(n);
        objetoContador = c;
    }

    @Override
    public void run() {
        synchronized (objetoContador) {
            for (int i = 0; i < 300; i++) {
                objetoContador.decrementa();
                try {
                    sleep(100);

                } catch (InterruptedException e) {
                }
            }
            System.out.println(getName() + " contador vale " + objetoContador.valorContador());
        }
    }

}
