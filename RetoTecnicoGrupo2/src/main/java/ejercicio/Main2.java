package ejercicio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main2 {

    // emf de instancia, no static
    private final EntityManagerFactory emf;

    // Constructor inicializa el EntityManagerFactory
    //Aqui no es estatico como en Main, usamos instancias
    public Main2() {
        this.emf = Persistence.createEntityManagerFactory("RetoTecnicoGrupo2");
    }

    public static void main(String[] args) {
        // Creamos la instancia de Main2
        Main2 app = new Main2();

        try {
            // Aquí llamamos a los métodos usando la instancia
            //app.insertarEmpleado(new Empleado(101, 6050));
            //app.mostrarEmpleado(106);
            //app.insertarEmpleado(new Empleado(112, 4200));
            //app.mostrarEmpleado(112);
            app.listarEmpleados();
            app.borrarEmpleado(101);
            app.listarEmpleados();
            app.listarEmpleadosTablaObject();
            app.listarEmpleadosTablaEmpleado();
        } finally {
            // Cerramos el emf al final
            app.close();
        }
    }

    // Método para cerrar emf
    public void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }

    // ======================== CRUD ========================

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
        } catch (Exception e) {
            System.err.println("Error al consultar: " + e.getMessage());
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
            List<Empleado> lista = em.createQuery("SELECT e FROM Empleado e ORDER BY e.id", Empleado.class)
                    .getResultList();
            if (lista.isEmpty()) {
                System.out.println("No hay Empleados.");
            } else {
                System.out.println("\nLista de empleados:");
                for (Empleado empleado : lista) {
                    System.out.println(empleado);
                }
            }
        } finally {
            em.close();
        }
    }

    public void listarEmpleadosTablaObject() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Object[]> lista = em.createQuery("SELECT e.id, e.salario FROM Empleado e ORDER BY e.id")
                    .getResultList();

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
            List<Empleado> lista = em.createQuery("SELECT e FROM Empleado e ORDER BY e.id", Empleado.class)
                    .getResultList();

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
