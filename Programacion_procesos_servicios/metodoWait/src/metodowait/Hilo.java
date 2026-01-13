package metodowait;

/**
 *
 * @author oscar.domalo
 */

import javax.swing.*;

public class Hilo extends Thread {
    private String nombre;
    private JLabel etiquetaContador;
    private JLabel etiquetaEstado;  
    private boolean suspendido = false;
    private boolean ejecutando = true;
    private int contador = 0;

    public Hilo(String nombre, JLabel etiquetaContador, JLabel etiquetaEstado) {
        this.nombre = nombre;
        this.etiquetaContador = etiquetaContador;
        this.etiquetaEstado = etiquetaEstado;
    }

    @Override
    public void run() {
        
        actualizarEstado(nombre + " Corriendo");
        
        while (ejecutando) {
            try {
                Thread.sleep(1000);
                contador++;
                
                synchronized(this) {
                    while (suspendido && ejecutando) {
                        wait();
                    }
                }
                
                if (ejecutando) {
                    actualizarContador(String.valueOf(contador));
                }
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        actualizarEstado(nombre + " Detenido");
    }

    public synchronized void suspender() {
        suspendido = true;
        actualizarEstado(nombre + " Suspendido");
    }

    public synchronized void reanudar() {
        suspendido = false;
        notify();
        actualizarEstado(nombre + " Corriendo");
    }

    public synchronized void detener() {
        ejecutando = false;
        suspendido = false;
        notify();
    }

    private void actualizarEstado(String mensaje) {
        etiquetaEstado.setText(mensaje);
    }

    private void actualizarContador(String contador) {
        etiquetaContador.setText(contador);
    }
}
