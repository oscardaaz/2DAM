/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package heladeria;

import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aleja
 */
public class Nevera {

    static Stack<Helado> pila = new Stack<Helado>();
    static ReentrantLock lock = new ReentrantLock();

    public Nevera() {
    }

    public static Stack<Helado> getPila() {
        return pila;
    }

    public static void setPila(Stack<Helado> pila) {
        Nevera.pila = pila;
    }

    public static void añadirHelado(Helado helado, String nombre) {

        lock.lock();
        while (pila.size() >= 25) {
            lock.unlock();
            try {
                Thread.sleep((long) (Math.random() * 3000 + 1000));
            } catch (InterruptedException ex) {
                Logger.getLogger(Nevera.class.getName()).log(Level.SEVERE, null, ex);
            }

            lock.lock();
        }
        pila.push(helado);
        System.out.println("Soy el heladero:" + nombre + " y he metido el helado " + helado.toString());
        lock.unlock();

    }

    public static Helado sacarHelado(String nombre) {

        lock.lock();
        while (pila.empty()) {
            lock.unlock();
            try {
                Thread.sleep((long) (Math.random() * 3000 + 1000));
            } catch (InterruptedException ex) {
                Logger.getLogger(Nevera.class.getName()).log(Level.SEVERE, null, ex);
            }

            lock.lock();
        }
        Helado h = pila.pop();
        System.out.println("Soy el niño:" + nombre + " y he sacado el helado " + h.toString());
        lock.unlock();

        return h;
    }

    @Override
    public String toString() {
        return "Nevera{" + '}';
    }

}
