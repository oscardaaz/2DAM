package model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "proyecto_sede", schema = "inversa", indexes = {
        @Index(name = "id_sede", columnList = "id_sede")
})
public class ProyectoSede {
    @EmbeddedId
    private ProyectoSedeId id;

    // @MapsId hace referencia a que es PK y FK a la vez en la tabla proyectoSede
    @MapsId("idProy")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_proy", nullable = false)
    private Proyecto proyecto;

    // @MapsId hace referencia a que es PK y FK a la vez en la tabla proyectoSede
    @MapsId("idSede")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_sede", nullable = false)
    private Sede sede;

    @ColumnDefault("0")
    @Column(name = "num_empleados_asignados", nullable = false)
    private Integer numEmpleadosAsignados;

    public ProyectoSedeId getId() {
        return id;
    }

    public void setId(ProyectoSedeId id) {
        this.id = id;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Integer getNumEmpleadosAsignados() {
        return numEmpleadosAsignados;
    }

    public void setNumEmpleadosAsignados(Integer numEmpleadosAsignados) {
        this.numEmpleadosAsignados = numEmpleadosAsignados;
    }

}