package model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Table(name = "empleado_datos_prof", schema = "inversa", uniqueConstraints = {
        @UniqueConstraint(name = "dni", columnNames = {"dni"})
})
public class EmpleadoDatosProf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado_datos_prof", nullable = false)
    private Integer id;

    // Opcional el FetchType.LAZY
    // @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OneToOne(optional = false)
    @JoinColumn(name = "dni", nullable = false)
    private Empleado empleado;

    @Column(name = "categoria", nullable = false, length = 2)
    private String categoria;

    @ColumnDefault("0.00")
    @Column(name = "sueldo_bruto_anual", nullable = false, precision = 8, scale = 2)
    private BigDecimal sueldoBrutoAnual;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getSueldoBrutoAnual() {
        return sueldoBrutoAnual;
    }

    public void setSueldoBrutoAnual(BigDecimal sueldoBrutoAnual) {
        this.sueldoBrutoAnual = sueldoBrutoAnual;
    }

}