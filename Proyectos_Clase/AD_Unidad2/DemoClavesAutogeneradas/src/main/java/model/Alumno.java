package model;

public class Alumno {

    private int id;
    private String nombre, ciclo;

    public Alumno(String nombre, String ciclo) {
        this.nombre = nombre;
        this.ciclo = ciclo;
    }

    public Alumno(int id, String nombre, String ciclo) {
        this.id = id;
        this.nombre = nombre;
        this.ciclo = ciclo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    @Override
    public String toString() {
        return String.format("Alumno (id= %d, Nombre= %s, Ciclo= %s)",id,nombre,ciclo);
    }
}
