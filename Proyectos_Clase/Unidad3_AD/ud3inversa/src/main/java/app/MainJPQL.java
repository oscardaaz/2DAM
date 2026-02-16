package app;

import jakarta.persistence.*;
import model.*;

import java.math.BigDecimal;
import java.util.List;

public class MainJPQL {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ud3-demo-ing-inversa");

    public static void main(String[] args) {

        //EntityManager em = null;
        try {
            //   em = emf.createEntityManager();

//        Ejercicios Bloque A — SELECT básicos (entidades completas)
//        listarTodasLasSedes();
//        listarDepartamentosDeUnaSedePorNombre();
//        buscarEmpleadoDNI();

        } catch (Exception e) {
            System.err.println("Error en el main " + e.getMessage());

        } finally {
            // if (em != null && em.isOpen()) em.close();
            if (emf != null && emf.isOpen()) emf.close();
        }


    }

//    Bloque A — SELECT básicos (entidades completas)
//    1) Listar todas las sedes
//    Devuelve: List<Sede>
//    Pista: consulta simple FROM ... con alias.

    private static void listarTodasLasSedes() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            String jpql = "SELECT e FROM Sede e";
            TypedQuery<Sede> q = em.createQuery(jpql, Sede.class);
            List<Sede> listaSedes = q.getResultList();
            for (Sede sede : listaSedes) {
                System.out.println(sede.getNomSede());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error en listar todas las sedes " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    private static void listarDepartamentosDeUnaSedePorNombre() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String jpql = "SELECT d FROM Departamento d WHERE d.sede.nomSede = :nomSede";
//            String jpql = "SELECT d FROM Departamento d JOIN d.sede s WHERE s.nomSede = :nomSede";

            TypedQuery<Departamento> q = em.createQuery(jpql, Departamento.class);
            q.setParameter("nomSede", "Madrid");

            System.out.println("Departamentos de la sede Madrid");
            List<Departamento> listaDepartamentos = q.getResultList();
            for (Departamento d : listaDepartamentos) {
                System.out.println(d.getNomDepto());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error en listar departamentos de una sede por nombre" + e.getMessage());

        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    private static void buscarEmpleadoDNI() {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            String jpql = "SELECT e FROM Empleado e WHERE e.dni = :DNI";
            TypedQuery<Empleado> q = em.createQuery(jpql, Empleado.class);
            q.setParameter("DNI", "56789012E");
            Empleado e = q.getSingleResult();
//            System.out.println(e);
            System.out.println(e.getNomEmp());
            System.out.println(e.getDni());

        } catch (Exception e) {
            System.err.println("Error en listar todas las sedes " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    private static void estructuraEjemplo() {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            String jpql = "SELECT e FROM Sede e";
            TypedQuery<Sede> q = em.createQuery(jpql, Sede.class);
            List<Sede> listaSedes = q.getResultList();
            for (Sede sede : listaSedes) {
                System.out.println(sede);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
                System.err.println("Error en listar todas las sedes " + e.getMessage());
            }

        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
} // Fin de la Clase

