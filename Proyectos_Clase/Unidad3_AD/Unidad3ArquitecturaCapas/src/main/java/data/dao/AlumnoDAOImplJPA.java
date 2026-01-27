package data.dao;

import data.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.Alumno;

import java.util.ArrayList;
import java.util.List;

public class AlumnoDAOImplJPA implements AlumnoDAO{


    @Override
    public int insertarAlumno(Alumno alumno) {

        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.persist(alumno);
            tx.commit();
            return alumno.getId();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Excepción al insertar alumno: " + e.getMessage());
            return -1;
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    @Override
    public List<Alumno> leerTodosLosAlumnos() {
        EntityManager em = null;
        List<Alumno> lista = new ArrayList<>();
        try {
            em = JPAUtil.createEntityManager();
//            lista = em.createQuery("SELECT a FROM Alumno a ORDER BY a.id").getResultList();
            TypedQuery<Alumno> query = em.createQuery("SELECT a FROM Alumno a ORDER BY a.id", Alumno.class);
            return query.getResultList();

        }
//        catch (Exception e) {
//            System.err.println("Error al buscar todos los alumnos: " + e.getMessage());
//        }
         finally {
            if (em != null && em.isOpen() ) em.close();
        }

    }

    @Override
    public Alumno mostrarAlumnoID(int id) {

        EntityManager em = null;
        //EntityTransaction tx = null;
        Alumno alumno = null;
        try {
            em = JPAUtil.createEntityManager();
            //tx = em.getTransaction();
            alumno = em.find(Alumno.class,id);

        } catch (Exception e) {
            // if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error al buscar todos los alumnos: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen() ) em.close();
        }
        return alumno;
    }

    @Override
    public int eliminarAlumno(int id) {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.createEntityManager();
            tx = em.getTransaction();
            Alumno alumno = em.find(Alumno.class,id);

            if (alumno != null) {
                tx.begin();
                em.remove(alumno);
                tx.commit();
                return 1;
            }else return -1;

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error al borrar alumno: " + e.getMessage());
            return -1;
        } finally {
            if (em != null && em.isOpen() ) em.close();
        }
    }

    @Override
    public boolean eliminarAlumnoProfe(int id) {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.createEntityManager();
            tx = em.getTransaction();

            Alumno alumno = em.find(Alumno.class,id);
            if (alumno == null)  return  false;
            else {
                tx.begin();
                em.remove(alumno);
                tx.commit();
                return true;
            }

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error DAO (eliminarAlumnoProfe): " + e.getMessage());
            return false;
        } finally {
            if (em != null && em.isOpen() ) em.close();
        }
    }

    @Override
    public boolean modificarAlumno(int id, String nuevoNombre, String nuevoCiclo) {
        EntityManager em = JPAUtil.createEntityManager();
        EntityTransaction tx = null;
        Alumno alumno = null;
        try {
            alumno = em.find(Alumno.class,id);
            tx = em.getTransaction();

            if (alumno != null) {
                tx.begin();
                alumno.setCiclo(nuevoCiclo);
                alumno.setNombre(nuevoNombre);
                //em.merge(alumno);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Exception al modificar alumno" + e.getMessage());
            return false;
        } finally {
            if (em != null && em.isOpen()) em.close();
        }

    }

    @Override
    public boolean modificarAlumnoProfe(int id, String nuevoNombre, String nuevoCiclo) {

        EntityManager em = null;
        EntityTransaction tx = null;
        Alumno alumno = null;

        try {
            em = JPAUtil.createEntityManager();
            tx = em.getTransaction();

            tx.begin();
            alumno = em.find(Alumno.class,id);
            if (alumno == null) {
                tx.rollback();
                return false;
            }
            alumno.setNombre(nuevoNombre);
            alumno.setCiclo(nuevoCiclo);

            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Exception DAO (modificarAlumnoProfe): " + e.getMessage());
            return false;
        } finally {
            if (em != null && em.isOpen()) em.close();
        }

    }
}
