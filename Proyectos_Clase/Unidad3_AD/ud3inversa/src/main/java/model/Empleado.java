package model;

import jakarta.persistence.*;

@Entity
@Table(name = "empleado", schema = "inversa", indexes = {
        @Index(name = "id_depto", columnList = "id_depto")
})
public class Empleado {
    @Id
    @Column(name = "dni", nullable = false, length = 9)
    private String dni;

    @Column(name = "nom_emp", nullable = false, length = 40)
    private String nomEmp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_depto", nullable = false)
    private Departamento departamento;

    @OneToOne(mappedBy = "empleado")
    private EmpleadoDatosProf empleadoDatosProf;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNomEmp() {
        return nomEmp;
    }

    public void setNomEmp(String nomEmp) {
        this.nomEmp = nomEmp;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public EmpleadoDatosProf getEmpleadoDatosProf() {
        return empleadoDatosProf;
    }

//    public void setEmpleadoDatosProf(EmpleadoDatosProf empleadoDatosProf) {
//        this.empleadoDatosProf = empleadoDatosProf;
//    }

    public void setEmpleadoDatosProf(EmpleadoDatosProf empleadoDatosProf) {
        this.empleadoDatosProf = empleadoDatosProf;
        if (empleadoDatosProf != null) {
            empleadoDatosProf.setEmpleado(this);
        }
    }

}