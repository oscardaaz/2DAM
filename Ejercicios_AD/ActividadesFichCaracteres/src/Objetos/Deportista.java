package Objetos;

import java.util.Locale;

public class Deportista {

    String nombre ; int edad ; Double estatura;

    public Deportista(String nombre, int edad, Double estatura) {
        this.nombre = nombre;
        this.edad = edad;
        this.estatura = estatura;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s;%d;%.2f%n", nombre, edad, estatura);
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Double getEstatura() {
        return estatura;
    }

    public void setEstatura(Double estatura) {
        this.estatura = estatura;
    }
}
