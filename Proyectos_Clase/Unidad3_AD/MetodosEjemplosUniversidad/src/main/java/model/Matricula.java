package model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "matricula", schema = "universidad_db", indexes = {
        @Index(name = "id_estudiante", columnList = "id_estudiante"),
        @Index(name = "id_curso", columnList = "id_curso")
})
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;

    @Column(name = "fecha_matricula", nullable = false)
    private LocalDate fechaMatricula;

    @OneToMany(mappedBy = "matricula")
    private Set<Evaluacion> evaluacions = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDate fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public Set<Evaluacion> getEvaluacions() {
        return evaluacions;
    }

    public void setEvaluacions(Set<Evaluacion> evaluacions) {
        this.evaluacions = evaluacions;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "id=" + id +
                ", estudiante=" + estudiante +
                ", curso=" + curso +
                ", fechaMatricula=" + fechaMatricula +
                '}';
    }
}