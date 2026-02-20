package data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ud3-examen");

    public static EntityManager createEntityManager(){
        return emf.createEntityManager();
    }

    public static void close(){
        if (emf != null && emf.isOpen()) emf.close();
    }

}
