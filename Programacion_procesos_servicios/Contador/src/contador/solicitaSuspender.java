/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package contador;

/**
 *
 * @author oscar.domalo
 */

public class solicitaSuspender {
    private boolean suspender;
    public synchronized void set(boolean b){
        suspender = b;
        notifyAll();
    }
    public synchronized void esperandoParaReanudar() throws InterruptedException{
        
        }
    }

