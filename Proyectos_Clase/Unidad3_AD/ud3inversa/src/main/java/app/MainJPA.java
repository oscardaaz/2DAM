package app;

import jakarta.persistence.*;
import model.*;

import java.math.BigDecimal;
import java.util.HashSet;

public class MainJPA {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ud3-demo-ing-inversa");

    public static void main(String[] args) {

        EntityManager em = null;
        try {

            em = emf.createEntityManager();

//            tarea1_insertarDepartamento(em);
//            tarea2_insertarEmpleadoConDatosProf(em);
            tarea3_consultarEmpleadosDeSede(em);
//            tarea4_modificarSueldoEmpleado(em);
//            tarea5_asignarProyectoASede(em);
//            tarea6_borrarEmpleado(em);

        } catch (Exception e) {
            if (em != null && em.isOpen()) em.close();
            if (emf != null && emf.isOpen()) emf.close();
        }


    }

    // -----------------------------------------------------------------
    // TAREA 1: Insertar un departamento en una sede existente
    // -----------------------------------------------------------------
    private static void tarea1_insertarDepartamento(EntityManager em) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Sede sede = em.find(Sede.class, 1);
            Departamento departamento = new Departamento();
            departamento.setNomDepto("Informatica");
            departamento.setSede(sede);

            sede.addDepartamento(departamento);
            em.persist(departamento);
            tx.commit();

        } catch (Exception e) {

            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error al insertar departamento " + e.getMessage());

        }

    }

    // -----------------------------------------------------------------
    // TAREA 2: Insertar un empleado con sus datos profesionales
    // en un departamento existente
    // -----------------------------------------------------------------
    private static void tarea2_insertarEmpleadoConDatosProf(EntityManager em) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Departamento departamento = em.find(Departamento.class, 5);
            Empleado empleado = new Empleado();
            empleado.setDepartamento(departamento);
            empleado.setDni("689155T");
            empleado.setNomEmp("Daniel Montero Ruiz");
            departamento.addEmpleado(empleado);

            EmpleadoDatosProf edp = new EmpleadoDatosProf();
            edp.setSueldoBrutoAnual(BigDecimal.valueOf(45650.45));
            edp.setCategoria("A1");

            empleado.setEmpleadoDatosProf(edp);
            edp.setEmpleado(empleado);

            em.persist(empleado);
            em.persist(edp);

            tx.commit();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error al insertar un empleado: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------
    // TAREA 3: Consultar empleados de una sede (navegación de relaciones)
    // mostrando sus nombres
    // -----------------------------------------------------------------
    private static void tarea3_consultarEmpleadosDeSede(EntityManager em) {


    }

    // -----------------------------------------------------------------
    // TAREA 4: Modificar el sueldo de un empleado
    // -----------------------------------------------------------------
    private static void tarea4_modificarSueldoEmpleado(EntityManager em) {


    }

    // -----------------------------------------------------------------
    // TAREA 5: Asignar un proyecto a una sede
    // -----------------------------------------------------------------
    private static void tarea5_asignarProyectoASede(EntityManager em) {


    }

    // -----------------------------------------------------------------
    // TAREA 6: Borrar un empleado (sin cascade ni orphanRemoval)
    // -----------------------------------------------------------------
    private static void tarea6_borrarEmpleado(EntityManager em) {


    }

}