package metodosuspend;
/**
 *
 * @author oscar.domalo
 */
import javax.swing.*;

public class Hilo extends Thread {

    private String nombre;
    private JLabel cuadroEstado;
    private JLabel contador;
    private int cuenta = 0;

    public Hilo(String nombre, JLabel cuadroEstado, JLabel contador) {
        this.nombre = nombre;
        this.cuadroEstado = cuadroEstado;
        this.contador = contador;
    }

    @Override
    public void run() {
        cuadroEstado.setText(nombre + " Corriendo");

        try {
            while (!isInterrupted()) {
                Thread.sleep(1000);
                cuenta++;
                contador.setText(String.valueOf(cuenta));
            }
            
        } catch (InterruptedException e) {
            cuadroEstado.setText(nombre + " Interrumpido");
        }
        cuadroEstado.setText(nombre + " Detenido");
    }

    public void pararHilo() {
        interrupt();
        cuadroEstado.setText(nombre + " Detenido");
    }
}
