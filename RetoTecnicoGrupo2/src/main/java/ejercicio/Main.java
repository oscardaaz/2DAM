package ejercicio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {

    public static final EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("RetoTecnicoGrupo2");
    public static void main(String[] args) {

            Empleado empleado = new Empleado(2,100);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(empleado);
            tx.commit();
            System.out.println("Empleado guardado en la base de datos.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al guardar: " + e.getMessage());
        } finally {

            em.close();
        }
    }
}
