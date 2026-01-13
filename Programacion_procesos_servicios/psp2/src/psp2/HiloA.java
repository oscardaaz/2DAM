/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psp2;

/**
 *
 * @author oscar.domalo
 */
public class HiloA extends Thread {

    private Contador objetoContador;

    public HiloA(String n, Contador c) {
        super(n);
        objetoContador = c;
    }

    @Override
    public void run() {
        synchronized (objetoContador) {
            for (int i = 0; i < 300; i++) {
                objetoContador.incrementa();
                try {
                    sleep(100);

                } catch (InterruptedException e) {
                }
            }
            System.out.println(getName() + " contador vale " + objetoContador.valorContador());
        }
    }

}
