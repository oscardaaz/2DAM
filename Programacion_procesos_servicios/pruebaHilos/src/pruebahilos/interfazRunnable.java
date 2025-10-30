/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebahilos;

/**
 *
 * @author oscar.domalo
 */
public class interfazRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("Hola desde el hilo Runnable " + Thread.currentThread());
    }
    
    
}
