package model;

public class Cursos {

    private String tematica, titulo;
    private int num_curso, num_creditos, id_curso;


    public Cursos(String tematica, int num_curso, int num_creditos, String titulo) {
        this.tematica = tematica;
        this.titulo = titulo;
        this.num_curso = num_curso;
        this.num_creditos = num_creditos;

    }

    public Cursos(String tematica, int num_curso, int num_creditos, String titulo, int id_curso) {
        this.tematica = tematica;
        this.titulo = titulo;
        this.num_curso = num_curso;
        this.num_creditos = num_creditos;
        this.id_curso = id_curso;
    }

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNum_curso() {
        return num_curso;
    }

    public void setNum_curso(int num_curso) {
        this.num_curso = num_curso;
    }

    public int getNum_creditos() {
        return num_creditos;
    }

    public void setNum_creditos(int num_creditos) {
        this.num_creditos = num_creditos;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }
}
