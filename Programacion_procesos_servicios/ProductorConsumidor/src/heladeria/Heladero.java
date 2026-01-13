/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package heladeria;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aleja
 */
public class Heladero extends Thread {
    
    private String nombre;
    public static int numHeladosPorHacer;
    
    public Heladero(String nombre) {
        this.nombre = nombre;
    }
    
    public Heladero() {
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public static int getNumHeladosPorHacer() {
        return numHeladosPorHacer;
    }
    
    public static void setNumHeladosPorHacer(int numHeladosPorHacer) {
        Heladero.numHeladosPorHacer = numHeladosPorHacer;
    }
    
    @Override
    public void run() {
        //COSITAS
        
        int heladosPorHacer = (int) Math.random() * 20 + 30;
        System.out.println("Soy el heladero "+nombre+" y voy a crear estos helados :"+heladosPorHacer);
        for (int i = 0; i < heladosPorHacer; i++) {
            try {
                Helado h = new Helado();
                Nevera.añadirHelado(h, nombre);
                Thread.sleep((long) (Math.random()*4000+1000));
            } catch (InterruptedException ex) {
                Logger.getLogger(Heladero.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }        
          System.out.println("Soy el heladero :"+nombre+" y me mori -.-");
    }
    
    @Override
    public String toString() {
        return "Heladero{" + "nombre=" + nombre + '}';
    }
    
}
