package app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import model.Estudiante;
import model.Matricula;
import model.Sede;

import java.util.List;

public class MainJPQL {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ud3-demo-universidad");
    public static void main(String[] args) {

        //EntityManager em = null;
        try {
            //   em = emf.createEntityManager();

            listarTodosLosEstudiantesYCursos();
        } catch (Exception e) {
            System.err.println("Error en el main " + e.getMessage());

        } finally {
            // if (em != null && em.isOpen()) em.close();
            if (emf != null && emf.isOpen()) emf.close();
        }
    }


    // 1. Obtener todos los estudiantes y los cursos en los que están matriculados

    private static void listarTodosLosEstudiantesYCursos(){
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            String jpql = "SELECT m FROM Matricula m";
            TypedQuery<Matricula> q = em.createQuery(jpql, Matricula.class);
            List<Matricula> listaEstudiantes = q.getResultList();
            for (Matricula m : listaEstudiantes) {
//                System.out.println(m);
                System.out.println(m.getEstudiante() +""+ m.getCurso());
//                System.out.println(e.getNombre() + e.getMatriculas());

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error en listar todas las sedes " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }





} // FIN DE LA CLASE