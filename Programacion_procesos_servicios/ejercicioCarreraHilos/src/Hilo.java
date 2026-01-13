
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;

/**
 *
 * @author oscar.domalo
 */

public class Hilo extends Thread {
    Random rd = new Random();
    private JSlider slider;
    private String nombre;
    private JProgressBar progressbar;
    private JLabel jLabelContador;
    private JLabel jLabelNombre;
    private int tiempoSleep;
    private JLabel prioridad;
    private JLabel jLabelGanador;
    private JButton jButtonComenzarCarrera;
    private long contador = 0;
    private static boolean stopHilo = false;
    
    private static AtomicBoolean finCarrera = new AtomicBoolean();
    
    public Hilo(String nombre, JSlider slider, JLabel jLabelNombre, JProgressBar progressbar,
            JLabel jLabelContador, JLabel prioridad, int tiempoSleep, JButton jButtonComenzarCarrera, JLabel jLabelGanador){
         this.nombre = nombre;
         this.jLabelNombre = jLabelNombre;
         this.jLabelContador = jLabelContador;
         this.tiempoSleep = tiempoSleep;
         this.prioridad = prioridad;
         this.progressbar = progressbar;
         this.jLabelGanador = jLabelGanador;
         this.jButtonComenzarCarrera = jButtonComenzarCarrera;
    }
    
    public static void pararHilo(){
        stopHilo = true;
    }
    
    public static void setStopHilo(boolean valor){
        stopHilo = valor;
    }
    @Override
    public void run(){
        this.jLabelNombre.setText(nombre);
        finCarrera.set(false);
        while(!stopHilo && contador <=100){
            this.jLabelContador.setText(Long.toString(contador));
            this.progressbar.setValue((int) contador);
            contador++;
            
            try{
                Thread.sleep(rd.nextInt(tiempoSleep));
            }catch (InterruptedException e){}
            
        }
        if(!finCarrera.get()){
            finCarrera.set(true);
            jLabelGanador.setText("Hilo ganador: " + this.nombre);
            jButtonComenzarCarrera.setEnabled(true);
             Hilo.pararHilo();
        }
        
    }
    
} 