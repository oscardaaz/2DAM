package model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "proyecto", schema = "inversa")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proy", nullable = false)
    private Integer id;

    @Column(name = "f_inicio", nullable = false)
    private LocalDate fInicio;

    @Column(name = "f_fin")
    private LocalDate fFin;

    @Column(name = "nom_proy", nullable = false, length = 20)
    private String nomProy;

    @OneToMany(mappedBy = "proyecto")
    private Set<ProyectoSede> proyectoSedes = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFInicio() {
        return fInicio;
    }

    public void setFInicio(LocalDate fInicio) {
        this.fInicio = fInicio;
    }

    public LocalDate getFFin() {
        return fFin;
    }

    public void setFFin(LocalDate fFin) {
        this.fFin = fFin;
    }

    public String getNomProy() {
        return nomProy;
    }

    public void setNomProy(String nomProy) {
        this.nomProy = nomProy;
    }

    public Set<ProyectoSede> getProyectoSedes() {
        return proyectoSedes;
    }

    public void setProyectoSedes(Set<ProyectoSede> proyectoSedes) {
        this.proyectoSedes = proyectoSedes;
    }

}