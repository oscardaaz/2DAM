package model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "sede", schema = "inversa")
public class Sede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sede", nullable = false)
    private Integer id;

    @Column(name = "nom_sede", nullable = false, length = 20)
    private String nomSede;

    @OneToMany(mappedBy = "sede")
    private Set<Departamento> departamentos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "sede")
    private Set<ProyectoSede> proyectoSedes = new LinkedHashSet<>();

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

    public void setDepartamentos(Set<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public Set<ProyectoSede> getProyectoSedes() {
        return proyectoSedes;
    }

    public void setProyectoSedes(Set<ProyectoSede> proyectoSedes) {
        this.proyectoSedes = proyectoSedes;
    }

}