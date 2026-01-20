package primer_proyecto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import model.Alumno;

public class MainEstados {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ud3-demo");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        // creamos un objeto (Entidad en el contexto Hibernate)
        Alumno alumno = new Alumno("Patricia Gómez", "DAM");
        // CHK 1 - New/Transient
        System.out.println("Alumno antes de persistir: " + alumno); // Alumno{id=0, nombre='Patricia Gómez', ciclo='DAM'}

        tx.begin();
        // guardamos
        em.persist(alumno);
        // CHK 2 - Managed (Persisted)
        System.out.println("Alumno después de persistir: " + alumno); // Alumno{id=1, nombre='Patricia Gómez', ciclo='DAM'}

        // modificamos
        alumno.setNombre("Nombre Modificado");
        // recuperamos el ID que habrá generado el SGBD
        int idAlu = alumno.getId();
        tx.commit();
        em.close();
        // CHK 3 - Detached (Después de cerrar el EntityManager)

        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
        // recuperamos mediante el idAlu
        alumno = em.find(Alumno.class, idAlu);
        // CHK 4 - Managed (después de find)

        tx.commit();
        em.close();

        // CHK 5 - Detached (Después de cerrar el EntityManager)

        // Modificamos el ciclo
        alumno.setCiclo("Administración");
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();


        // fusionamos
        alumno = em.merge(alumno);
        // CHK 6 - Estaba en Detached, ahora Managed (después de merge)

        tx.commit(); // Se refleja el cambio de ciclo en la BD
        em.close();
        System.out.println(alumno);

        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        // recuperamos y eliminamos
        alumno = em.find(Alumno.class, idAlu);
        // CHK 7 - Sigue Managed (después de find)
        em.remove(alumno);
        // CHK 8 - Removed (después de remove)

        tx.commit(); // Se elimina el registro de la BD
        em.close();
        emf.close();
    }
}