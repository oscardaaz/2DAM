package model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "proyecto", schema = "inversa", uniqueConstraints = {
        @UniqueConstraint(name = "nom_proy", columnNames = {"nom_proy"})
})
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proy", nullable = false)
    private Integer id;

    @Column(name = "nom_proy", nullable = false, length = 20)
    private String nomProy;

    @Column(name = "f_inicio", nullable = false)
    private LocalDate fInicio;

    @Column(name = "f_fin")
    private LocalDate fFin;

    @OneToMany(mappedBy = "proyecto")
    private Set<ProyectoSede> proyectoSedes = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomProy() {
        return nomProy;
    }

    public void setNomProy(String nomProy) {
        this.nomProy = nomProy;
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

    public Set<ProyectoSede> getProyectoSedes() {
        return proyectoSedes;
    }

//   public void setProyectoSedes(Set<ProyectoSede> proyectoSedes) {
//        this.proyectoSedes = proyectoSedes;
//    }

    public void addProyectoSede(ProyectoSede ps) {
        proyectoSedes.add(ps);
        ps.setProyecto(this);
    }

    public void removeProyectoSede(ProyectoSede ps) {
        proyectoSedes.remove(ps);
        ps.setProyecto(null);
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "id=" + id +
                ", nomProy='" + nomProy + '\'' +
                ", fInicio=" + fInicio +
                ", fFin=" + fFin +
                '}';
    }
}