package primer_proyecto;

import jakarta.persistence.*;
import model.Alumno;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            emf = Persistence.createEntityManagerFactory("ud3-demo");
            em = emf.createEntityManager();
            tx = em.getTransaction();

            Alumno alumno1 = new Alumno("Test1", "ASIR");
            Alumno alumno2 = new Alumno("Test2", "DAW");
            Alumno alumno3 = new Alumno("Test3", "SMR");


            // Iniciamos la transacción
            tx.begin();

            em.persist(alumno1);
            em.persist(alumno2);
            em.persist(alumno3);


            // Finalizamos la transacción
            tx.commit();

            System.out.println("Conexión y persistencia OK");
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error durante la operación: " + e.getMessage());
        } finally {
            // Evitamos NullPointerException al cerrar
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }
}