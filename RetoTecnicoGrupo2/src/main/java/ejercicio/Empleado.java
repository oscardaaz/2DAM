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

    @Override
    public String toString() {
        return "Empleado { " +
                "id= " + id +
                ", salario= " + salario +
                " }";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }
}
