package model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "especie", schema = "clinicaveterinaria")
public class Especie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_especie", nullable = false)
    private Integer id;

    @Column(name = "nom_especie", nullable = false, length = 20)
    private String nomEspecie;

    @OneToMany(mappedBy = "especie")
    private Set<Mascota> mascotas = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomEspecie() {
        return nomEspecie;
    }

    public void setNomEspecie(String nomEspecie) {
        this.nomEspecie = nomEspecie;
    }

    public Set<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(Set<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    @Override
    public String toString() {
        return String.format("codEspecie=%d nomEspecie=%s", id, nomEspecie);
    }

}