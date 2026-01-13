/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebahilos;

/**
 *
 * @author oscar.domalo
 */
public class Tac extends Thread {
    
    @Override
    public void run(){
        while (true){
            System.out.println("TAC");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.getLogger(Tac.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }
}
