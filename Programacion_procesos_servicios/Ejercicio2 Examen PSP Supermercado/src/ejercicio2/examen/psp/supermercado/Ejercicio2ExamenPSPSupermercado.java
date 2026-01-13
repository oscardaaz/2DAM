/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejercicio2.examen.psp.supermercado;

/**
 *
 * @author oscar.domalo
 */
public class Ejercicio2ExamenPSPSupermercado {

   
    public static void main(String[] args) throws InterruptedException {
        
        
        Bandeja bandeja = new Bandeja(6);
        
        Empleado empleado = new Empleado(bandeja,30);
       
        
        Cliente cliente1 = new Cliente(bandeja, 10, "Pedro");
        Cliente cliente2 = new Cliente(bandeja, 12, "Juan");
        Cliente cliente3 = new Cliente(bandeja, 8, "Alvaaro");
        
        empleado.start();
        
        
        cliente1.start();
        cliente2.start();
        cliente3.start();
       
    }
    
}
