/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicio2.examen.psp.supermercado;

/**
 *
 * @author oscar.domalo
 */
public class Cliente extends Thread{
    private Bandeja bandeja;
    private int consumir;

    public Cliente(Bandeja bandeja, int consumir, String nombre) {
        super(nombre);
        this.bandeja = bandeja;
        this.consumir = consumir;
    }

    @Override
    public void run() {
        try {
            System.out.println("Soy el cliente " + getName() + " Y vengo a consumir: " + consumir + " porciones");
            for (int i = 1; i <= consumir; i++) {
                bandeja.cogerPorcion(getName());
                Thread.sleep(100);
               
            }
            System.out.println(getName() + " termino");
        }catch (InterruptedException e){
            System.err.println("Error " +e.getMessage());
        }
    }
    
    
    
    
}
