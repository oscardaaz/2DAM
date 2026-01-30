package model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "proyecto_sede", schema = "inversa", indexes = {
        @Index(name = "id_sede", columnList = "id_sede")
})
public class ProyectoSede {
    @EmbeddedId
    private ProyectoSedeId id;

    @MapsId("idProy")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_proy", nullable = false)
    private Proyecto proyecto;

    @MapsId("idSede")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_sede", nullable = false)
    private Sede sede;

    @Column(name = "f_inicio", nullable = false)
    private LocalDate fInicio;

    @Column(name = "f_fin")
    private LocalDate fFin;

    public ProyectoSedeId getId() {
        return id;
    }

    public void setId(ProyectoSedeId id) {
        this.id = id;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto idProy) {
        this.proyecto = idProy;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede idSede) {
        this.sede = idSede;
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

}