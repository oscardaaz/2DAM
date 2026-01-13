/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package heladeria;

/**
 *
 * @author aleja
 */
public class Helado {
    private String sabor;
    private double precio;
    private enum sabores {Naranja, Fresa, Chocolate, Oreo}
    
    public Helado(String sabor, double precio) {
        this.sabor = sabor;
        this.precio = precio;
    }

    public Helado() {
        
        this.precio= Math.random()*2.55+0.45;
        this.sabor= sabores.values()[(int) (Math.random()*3)].toString();
        
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Helado{" + "sabor=" + sabor + ", precio=" + precio + '}';
    }
    
    
    
    
    
    
    
}
