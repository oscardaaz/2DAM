package model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "sede", schema = "inversa", uniqueConstraints = {
        @UniqueConstraint(name = "nom_sede", columnNames = {"nom_sede"})
})
public class Sede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sede", nullable = false)
    private Integer id;

    @Column(name = "nom_sede", nullable = false, length = 20)
    private String nomSede;

    @OneToMany(mappedBy = "sede")
    private Set<Departamento> departamentos = new HashSet<>();

    @OneToMany(mappedBy = "sede")
    private Set<ProyectoSede> proyectoSedes = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomSede() {
        return nomSede;
    }

    public void setNomSede(String nomSede) {
        this.nomSede = nomSede;
    }

    public Set<Departamento> getDepartamentos() {
        return departamentos;
    }

//    public void setDepartamentos(Set<Departamento> departamentos) {
//        this.departamentos = departamentos;
//    }

    public void addDepartamento(Departamento d) {
        departamentos.add(d);
        d.setSede(this);
    }

    public void removeDepartamento(Departamento d) {
        departamentos.remove(d);
        d.setSede(null);
    }

    public Set<ProyectoSede> getProyectoSedes() {
        return proyectoSedes;
    }

//    public void setProyectoSedes(Set<ProyectoSede> proyectoSedes) {
//        this.proyectoSedes = proyectoSedes;
//    }


    public void addProyectoSede(ProyectoSede ps) {
        proyectoSedes.add(ps);
        ps.setSede(this);
    }

    public void removeProyectoSede(ProyectoSede ps) {
        proyectoSedes.remove(ps);
        ps.setSede(null);
    }
}