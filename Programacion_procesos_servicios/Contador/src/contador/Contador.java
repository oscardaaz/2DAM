/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package contador;

import javax.swing.JLabel;

/**
 *
 * @author oscar.domalo
 */
public class Contador extends Thread{

    private JLabel label;
    long contador = 0;
    private boolean stopHilo = false;
    private solicitaSuspender suspender = new solicitaSuspender();
    
    public Contador(JLabel label,long valorInicial){
        this.label = label; 
        this.contador = valorInicial;
    }
    public long getContador(){
        return this.contador;
    }
    
    public void pararHilo() {stopHilo = true;}
    
    public void Suspende(){suspender.set(true);}
    
    public void Reanudar(){suspender.set(false);}
    
    
    
    @Override
    public void run(){
        Thread hiloActual = Thread.currentThread();
        
        while (!stopHilo){
            try{
                this.label.setText(Long.toString(contador));
                Thread.sleep(500);
                suspender.esperandoParaReanudar();
            }catch (InterruptedException e){}
            contador++;
        }
    }
    
    
    
}
