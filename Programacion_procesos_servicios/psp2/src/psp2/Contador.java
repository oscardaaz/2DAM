/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psp2;

/**
 *
 * @author oscar.domalo
 */
public class Contador {
    
    private int c = 0;
    Contador(int c){
        this.c = c;
    }
    
    public synchronized void incrementa() {c = c + 1;}
    public synchronized void decrementa() { c = c - 1;}
    public synchronized int valorContador() { return c;}
    
}
