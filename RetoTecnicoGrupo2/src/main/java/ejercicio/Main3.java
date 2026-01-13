package ejercicio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main3 {

    // EntityManagerFactory ahora NO es static
    //Aqui creamos instancias no es estatico como en Main2, pero no usamos constructor
    private final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("RetoTecnicoGrupo2");

    public static void main(String[] args) {
        // Creamos el objeto de Main3 para poder usar los métodos de instancia
        Main3 app = new Main3();

        try {
            // Aquí llamamos a los métodos a través del objeto
            app.listarEmpleados();
            app.borrarEmpleado(101);
            app.listarEmpleados();
            app.listarEmpleadosTablaObject();
            app.listarEmpleadosTablaEmpleado();
        } finally {
            app.close();
        }
    }

    // Método para cerrar el EMF
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    public void insertarEmpleado(Empleado empleado) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Empleado existe = em.find(Empleado.class, empleado.getId());
            if (existe != null) {
                System.out.println("Empleado con ID " + empleado.getId() + " ya existe.");
                return;
            }

            tx.begin();
            em.persist(empleado);
            tx.commit();
            System.out.println("Empleado guardado: " + empleado.getId());

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Error al guardar: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void mostrarEmpleado(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Empleado empleado = em.find(Empleado.class, id);
            if (empleado != null) {
                System.out.println("\n" + empleado);
            } else {
                System.out.println("\nEmpleado con ID " + id + " no encontrado.");
            }
        } finally {
            em.close();
        }
    }

    public void borrarEmpleado(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            Empleado empleado = em.find(Empleado.class, id);
            if (empleado != null) {
                tx.begin();
                em.remove(empleado);
                tx.commit();
                System.out.println("Empleado con id:" + empleado.getId() + " eliminado.");
            } else {
                System.out.println("Empleado con id: " + id + " no encontrado.");
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Error al eliminar: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void listarEmpleados() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Empleado> lista = em.createQuery(
                    "SELECT e FROM Empleado e ORDER BY e.id", Empleado.class
            ).getResultList();

            if (lista.isEmpty()) {
                System.out.println("No hay Empleados.");
            } else {
                System.out.println("\nLista de empleados:");
                for (Empleado e : lista) {
                    System.out.println(e);
                }
            }
        } finally {
            em.close();
        }
    }

    public void listarEmpleadosTablaObject() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Object[]> lista = em.createQuery(
                    "SELECT e.id, e.salario FROM Empleado e ORDER BY e.id"
            ).getResultList();

            if (lista.isEmpty()) {
                System.out.println("No hay Empleados.");
                return;
            }

            System.out.println("\nListar empleados con Object[] en Tabla:");
            System.out.printf("%-5s %-10s%n", "ID", "SALARIO");
            System.out.println("--------------------");

            for (Object[] fila : lista) {
                int id = (int) fila[0];
                int salario = (int) fila[1];
                System.out.printf("%-5d %-10d%n", id, salario);
            }
        } finally {
            em.close();
        }
    }

    public void listarEmpleadosTablaEmpleado() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Empleado> lista = em.createQuery(
                    "SELECT e FROM Empleado e ORDER BY e.id", Empleado.class
            ).getResultList();

            if (lista.isEmpty()) {
                System.out.println("No hay Empleados.");
                return;
            }

            System.out.println("\nListar empleados con tabla Empleado[]");
            System.out.printf("%-5s %-10s%n", "ID", "SALARIO");
            System.out.println("--------------------");

            for (Empleado e : lista) {
                System.out.printf("%-5d %5d€%n", e.getId(), e.getSalario());
            }
        } finally {
            em.close();
        }
    }
}
