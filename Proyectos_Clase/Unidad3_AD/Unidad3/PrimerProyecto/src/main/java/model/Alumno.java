package model;

import jakarta.persistence.*;

@Entity
@Table(name = "alumnos")  // Si la entidad y la tabla se llamaran igual,
// no haría falta esta anotación.
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String ciclo;

    // Constructor sin parametros. Necesario para JPA
    public Alumno() {}

    public Alumno(String nombre, String ciclo) {
        this.nombre = nombre;
        this.ciclo = ciclo;
    }

    public Alumno(int id, String nombre, String ciclo) {
        this.id = id;
        this.nombre = nombre;
        this.ciclo = ciclo;
    }

    public int getId() { return id; }

    public void setId (int id) {this.id = id;}

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCiclo() { return ciclo; }

    public void setCiclo(String ciclo) { this.ciclo = ciclo; }

    @Override
    public String toString() {
        return "Alumno{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ciclo='" + ciclo + '\'' +
                '}';
    }
}
