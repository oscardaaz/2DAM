package main;

import java.io.Serializable;

public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;

    private String dni;
    private String nombre;
    private int edad;
    private String departamento;
    private double salario;

    public Empleado(String dni, String nombre, int edad, String departamento, double salario) {
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
        this.departamento = departamento;
        this.salario = salario;
    }

    public String getDni() {
        return dni;
    }

    @Override
    public String toString() {
        return "Empleado (" +
                "DNI= '" + dni + '\'' +
                ", nombre= '" + nombre + '\'' +
                ", edad= " + edad +
                ", departamento= '" + departamento + '\'' +
                ", salario= " + salario +
                "€ )";
    }


}
