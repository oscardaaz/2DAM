package ejercicio;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
public class Empleado implements Serializable {

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
