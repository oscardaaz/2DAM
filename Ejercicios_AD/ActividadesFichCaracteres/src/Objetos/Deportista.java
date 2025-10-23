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
        //Poner un %n si uso el pw.write y si uso el pw.println no es necesario
        return String.format(Locale.US, "%s %d %.2f", nombre, edad, estatura);
    }

}
