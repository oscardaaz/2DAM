package model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "departamento", schema = "inversa", indexes = {
        @Index(name = "id_sede", columnList = "id_sede")
}, uniqueConstraints = {
        @UniqueConstraint(name = "nom_depto", columnNames = {"nom_depto", "id_sede"})
})
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_depto", nullable = false)
    private Integer id;

    @Column(name = "nom_depto", nullable = false, length = 32)
    private String nomDepto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_sede", nullable = false)
    private Sede sede;

    @OneToMany(mappedBy = "departamento")
    private Set<Empleado> empleados = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomDepto() {
        return nomDepto;
    }

    public void setNomDepto(String nomDepto) {
        this.nomDepto = nomDepto;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Set<Empleado> getEmpleados() {
        return empleados;
    }

   /* public void setEmpleados(Set<Empleado> empleados) {
        this.empleados = empleados;
    }*/

    public void addEmpleado(Empleado e) {
        empleados.add(e);
        e.setDepartamento(this);
    }

    public void removeEmpleado(Empleado e) {
        empleados.remove(e);
        e.setDepartamento(null);
    }


}