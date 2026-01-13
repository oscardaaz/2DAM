/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicio2.examen.psp.supermercado;

/**
 *
 * @author oscar.domalo
 */
public class Empleado extends Thread{
    private Bandeja bandeja;
    private int cantidad;

    public Empleado(Bandeja bandeja, int cantidad) {
        this.bandeja = bandeja;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        try {
            System.out.println("Soy el empleado y comienzo mi turno para reponer " + cantidad + "porciones");
            for (int i = 1; i <= cantidad; i++) {
                bandeja.agregarPorcion("Empleado");
                Thread.sleep(200);
            }
            System.out.println("Empleado termino");
        } catch (InterruptedException ex) {
            System.err.println("Error " + ex.getMessage());
        }
    }

   
}
