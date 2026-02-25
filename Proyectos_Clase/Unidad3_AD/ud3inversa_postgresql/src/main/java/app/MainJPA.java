package app;

import jakarta.persistence.*;
import model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainJPA {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ud3-inversa-postgres");

    public static void main(String[] args) {

        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            tarea1_insertarDepartamento(em);
            tarea2_insertarEmpleadoConDatosProf(em);
            tarea3_consultarEmpleadosDeSede(em);
            tarea4_modificarSueldoEmpleado(em);
            tarea5_asignarProyectoASede(em);
            tarea6_borrarEmpleado(em);

        } catch (Exception e) {
            System.err.println("Error en el main " + e.getMessage());

        } finally {
            if (em != null && em.isOpen()) em.close();
            if (emf != null && emf.isOpen()) emf.close();
        }


    }

    // -----------------------------------------------------------------
    // TAREA 1: Insertar un departamento en una sede existente
    // -----------------------------------------------------------------
    private static void tarea1_insertarDepartamento(EntityManager em) {

        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            Sede sede = em.find(Sede.class, 2);
            Departamento departamento = new Departamento();
            departamento.setNomDepto("Recortes");
            departamento.setSede(sede);

            sede.addDepartamento(departamento);

            em.persist(departamento);

            tx.commit();

            System.out.println("Departamento: " + departamento.getNomDepto() + " en sede con id: " + sede.getId());
        } catch (Exception e) {

            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error al insertar un departamento " + e.getMessage());

        }

    }

    // -----------------------------------------------------------------
    // TAREA 2: Insertar un empleado con sus datos profesionales
    // en un departamento existente
    // -----------------------------------------------------------------
    private static void tarea2_insertarEmpleadoConDatosProf(EntityManager em) {

        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();

            Departamento departamento = em.find(Departamento.class, 5);
            Empleado empleado = new Empleado();
            empleado.setDepartamento(departamento);
            empleado.setDni("689155T");
            empleado.setNomEmp("Jorge Viana Gomez");
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

        EntityTransaction tx = null;

        try {

            tx = em.getTransaction();
            Sede sede = em.find(Sede.class, 1);
            if (sede != null) {
                int i = 1;
                System.out.println("Empleados de la sede: " + sede.getNomSede());
                for (Departamento departamento : sede.getDepartamentos()) {
                    System.out.println("Departamento: " + departamento.getNomDepto());
                    for (Empleado empleado : departamento.getEmpleados()) {
                        System.out.println("\tEmpleado " + i++ + ": " + empleado.getNomEmp() + " con DNI: " + empleado.getDni());
                    }
                }
            } else {
                System.out.println("No existe la sede con id: ");
            }

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error al buscar todos los empleados: " + e.getMessage());
        }

    }

    // -----------------------------------------------------------------
    // TAREA 4: Modificar el sueldo de un empleado
    // -----------------------------------------------------------------
    private static void tarea4_modificarSueldoEmpleado(EntityManager em) {
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            Integer id = 3;
            EmpleadoDatosProf empleado = em.find(EmpleadoDatosProf.class, id);
            if (empleado != null) {
                tx.begin();
                empleado.setSueldoBrutoAnual(BigDecimal.valueOf(99999.99));
                tx.commit();
            } else {
                System.out.println("El empleado con id: " + id + " no existe");
            }

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error al modificar el sueldo de un empleado: " + e.getMessage());
        }


    }

    // -----------------------------------------------------------------
    // TAREA 5: Asignar un proyecto a una sede
    // -----------------------------------------------------------------
    private static void tarea5_asignarProyectoASede(EntityManager em) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();

            Integer idSede = 2, idProyecto = 1;

            Sede sede = em.find(Sede.class, idSede);
            Proyecto proyecto = em.find(Proyecto.class, idProyecto);

            ProyectoSede proyectoSede = new ProyectoSede();

            ProyectoSedeId id = new ProyectoSedeId();
            id.setIdSede(sede.getId());
            id.setIdProy(proyecto.getId());

            proyectoSede.setId(id);
            proyectoSede.setSede(sede);
            proyectoSede.setProyecto(proyecto);
            proyectoSede.setNumEmpleadosAsignados(8);

            proyecto.addProyectoSede(proyectoSede);
            sede.addProyectoSede(proyectoSede);

            em.persist(proyectoSede);
            tx.commit();
            System.out.println("Proyecto: " + proyecto.getId() + " asignado a sede: " + sede.getId());
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error al asignar un proyecto a una sede: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------
    // TAREA 6: Borrar un empleado (sin cascade ni orphanRemoval)
    // -----------------------------------------------------------------
    private static void tarea6_borrarEmpleado(EntityManager em) {

        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();

            Integer id = 9;
            EmpleadoDatosProf edp = em.find(EmpleadoDatosProf.class, id);

            if (edp != null) {

                Empleado empleado = edp.getEmpleado();
                if (empleado != null) {
                    tx.begin();
                    Departamento departamento = empleado.getDepartamento();
                    departamento.removeEmpleado(empleado);

                    em.remove(edp);
                    em.remove(empleado);
                    System.out.println("Empleado eliminado: " + empleado.getNomEmp() + " con DNI: " + empleado.getDni());
                    tx.commit();
                }

            } else {
                System.out.println("El empleado con id: " + id + " no ha sido encontrado");
            }


        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error al borrar todos los empleados: " + e.getMessage());
        }

    }

}