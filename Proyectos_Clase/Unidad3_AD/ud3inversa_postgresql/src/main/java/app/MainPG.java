package app;

import jakarta.persistence.*;
import model.Sede;
import java.util.List;

public class MainPG {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ud3-inversa-postgres");

    public static void main(String[] args) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();


            tx = em.getTransaction();
            tx.begin();
            Sede sede = new Sede();
            sede.setNomSede("Madrid");
            em.persist(sede);
            tx.commit();


            String psql = "SELECT s FROM Sede s";

            TypedQuery<Sede> q = em.createQuery(psql, Sede.class);
            List<Sede> listaSedes = q.getResultList();

            listaSedes.forEach(s -> System.out.println(s));


        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
                System.err.println("Error en listar todas las sedes " + e.getMessage());
            }
        } finally {

            if (em != null && em.isOpen()) em.close();
            emf.close();
        }

    }
}
