package Objetos;

import java.io.Serial;
import java.io.Serializable;

public class Empleado implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    String nombre;
    int departamento;
    double salario;

    public Empleado(String nombre, int departamento, double salario) {
        this.nombre = nombre;
        this.departamento = departamento;
        this.salario = salario;
    }

    @Override
    public String toString() {
        return String.format("Nombre: %-10s  Depto: %-5d  Salario: %-5.2f €", nombre, departamento, salario);
    }


}
