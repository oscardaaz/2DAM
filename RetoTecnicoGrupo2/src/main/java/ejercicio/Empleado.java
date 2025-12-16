package ejercicio;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Empleado {

    @Id
    private int id;

    private int salario;


    public Empleado(){

    }
    public Empleado(int id, int salario) {
        this.id = id;
        this.salario = salario;
    }
}
