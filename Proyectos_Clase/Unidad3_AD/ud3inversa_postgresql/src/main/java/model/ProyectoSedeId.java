package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProyectoSedeId implements Serializable {
    // private static final long serialVersionUID = 3634027638709231000L;
    @Column(name = "id_proy", nullable = false)
    private Integer idProy;

    @Column(name = "id_sede", nullable = false)
    private Integer idSede;

    public Integer getIdProy() {
        return idProy;
    }

    public void setIdProy(Integer idProy) {
        this.idProy = idProy;
    }

    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProyectoSedeId that = (ProyectoSedeId) o;
        return Objects.equals(this.idProy, that.idProy) &&
                Objects.equals(this.idSede, that.idSede);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProy, idSede);
    }

}