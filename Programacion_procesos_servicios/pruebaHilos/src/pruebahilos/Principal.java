/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebahilos;

/**
 *
 * @author oscar.domalo
 */
public class Principal {

    public static void main(String[] args) {
        
        /*Tic h1 = new Tic();
        Tac h2 = new Tac();

        h1.start();
        h2.start();*/
        
        
        
        interfazRunnable h = new interfazRunnable();
        Thread h1 = new Thread(h);
        h1.start();
        
        new Thread(h).start();//Lo mismo
        
        
    }

    
    
    
}
