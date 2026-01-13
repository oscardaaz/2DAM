/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicio2.examen.psp.supermercado;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oscar.domalo
 */
public class Bandeja {
    private final int capacidad;
    private List<Integer> porciones;
    private int id = 1;

    public Bandeja(int capacidad) {
        this.capacidad = capacidad;
        this.porciones = new ArrayList<>();
    }
    
    public synchronized void agregarPorcion(String nombreEmpleado) throws InterruptedException{
        while (porciones.size() >= capacidad) wait();
        porciones.add(id++);
        System.out.println(nombreEmpleado + " repone una porcion, total de porciones: " + porciones.size());
        notifyAll();
    }
    
    public synchronized void cogerPorcion(String nombreCliente) throws InterruptedException {
        while (porciones.isEmpty()) wait();
        //Integer p = 
                porciones.remove(0);
        System.out.println(nombreCliente + " coge una porcion, total de porciones: " + porciones.size());
        notifyAll();
         
           
        }
    }

