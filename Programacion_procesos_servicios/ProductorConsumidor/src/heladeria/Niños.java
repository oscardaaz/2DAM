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
public class Niños extends Thread {

    private String nombre;

    public Niños(String nombre) {
        this.nombre = nombre;
    }

    public Niños() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        //COSITAS
        while (true) {

            Helado h = Nevera.sacarHelado(nombre);
        
            try {
                Thread.sleep((long) (Math.random() * 4000 + 1000));
            } catch (InterruptedException ex) {
                Logger.getLogger(Heladero.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @Override
    public String toString() {
        return "Ni\u00f1os{" + "nombre=" + nombre + '}';
    }

}
