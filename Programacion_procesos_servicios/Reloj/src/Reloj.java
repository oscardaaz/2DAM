import javax.swing.*;
import java.util.Calendar;

public class Reloj extends Thread {

    private JLabel label;

    public Reloj (JLabel jlabel){

        this.label = label;
    }

    public void run(){
        while (true){
            try{
                Calendar c = Calendar.getInstance();
                int hora = c.get(Calendar.HOUR);
                int minutos = c.get(Calendar.MINUTE);
                int segundos = c.get(Calendar.SECOND);
                String tiempo = String.format("%d:%d:%d",hora,minutos,segundos);
                this.label.setText(tiempo);
                Thread.sleep(1000);
            }catch(InterruptedException ex){
                System.err.println("Error del hilo reloj");
            }
        }
    }
}
