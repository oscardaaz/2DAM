package ejercicio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Scanner;


public class Main {

    public static final EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("RetoTecnicoGrupo2");

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            String menu = """
                    
                    Menu de opciones:
                    1. Insertar un nuevo Empleado.
                    2. Mostrar un único Empleado.
                    3. Mostrar todos los Empleados.
                    4. Salir.
                    """;
            int opcion;
            do {

                System.out.println(menu);
                System.out.print("Introduce una opción: ");
                opcion = sc.nextInt();
                if (opcion == 1) insertarEmpleadoSC();
                if (opcion == 2) mostrarEmpleadoSC();
                if (opcion == 3) listarEmpleadosTablaEmpleado();

            } while (opcion != 4);
            System.out.println("Saliendo...");

//            insertarEmpleado(new Empleado(101,6050));
//            mostrarEmpleado(106);
//            insertarEmpleado(new Empleado(112,4200));
//            mostrarEmpleado(112);
//            listarEmpleados();
//            borrarEmpleado(101);
//            listarEmpleados();
//            listarEmpleadosTablaObject();
//            listarEmpleadosTablaEmpleado();
        } finally {

            emf.close();
            sc.close();
        }
    }

   /* private static void insertarEmpleado(Empleado empleado){

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
            em.close(); // Solo cerramos el EntityManager
        }
    }*/

    private static void insertarEmpleado(Empleado empleado) {

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

    private static void insertarEmpleadoSC() {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            System.out.print("Ingresa el id del empleado: ");
            int id = sc.nextInt();
            System.out.print("Introduce el salario del Empleado: ");
            int salario = sc.nextInt();
            sc.nextLine();

            Empleado empleado = new Empleado(id, salario);

            Empleado existe = em.find(Empleado.class, empleado.getId());

            if (existe != null) {
                System.out.println("Empleado con ID " + empleado.getId() + " ya existe.");
                return;
            }

            tx.begin();
            em.persist(empleado);
            tx.commit();
            System.out.println("Nuevo empleado guardado con id: " + empleado.getId());

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Error al guardar: " + e.getMessage());
        } finally {
            em.close();
        }
    }


    private static void mostrarEmpleado(int id) {

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

    private static void mostrarEmpleadoSC() {

        EntityManager em = emf.createEntityManager();
        try {
            System.out.print("Introduce el id del empleado a buscar: ");
            int id = sc.nextInt();
            sc.nextLine();
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

    public static void borrarEmpleado(int id) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Empleado empleado = null;
        try {
            empleado = em.find(Empleado.class, id);
            if (empleado != null) {
                tx.begin();
                em.remove(empleado);
                tx.commit();
                System.out.println("Empleado con id:" + empleado.getId() + " eliminado.");
            } else {
                System.out.println("Empleado con id: " + id + " no encontrado.");
            }
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al eliminar: "
                    + e.getMessage());
            //empleado = null;
        } finally {
            em.close();
        }
    }

    public static void listarEmpleados() {
        EntityManager em = emf.createEntityManager();
        List<Empleado> lista;
        try {
            lista = em.createQuery("SELECT e FROM Empleado e"
                    + " ORDER BY e.id", Empleado.class).getResultList();
            if (lista.isEmpty()) {
                System.out.println("No hay Empleados.");
            } else {
                System.out.println("\nLista de empleados:");
                for (Empleado empleado : lista) {
                    System.out.println(empleado);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void listarEmpleadosTablaObject() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Object[]> lista = em.createQuery(
                    "SELECT e.id, e.salario FROM Empleado e ORDER BY e.id"
            ).getResultList();

            if (lista.isEmpty()) {
                System.out.println("No hay Empleados.");
                return;
            }

            // Encabezado
            System.out.println("\nListar empleados con Object[] en Tabla:");
            System.out.printf("%-5s %-10s%n", "ID", "SALARIO");
            System.out.println("--------------------");

            for (Object[] fila : lista) {
                int id = (int) fila[0];
                int salario = (int) fila[1];
                System.out.printf("%-5d %6d€%n", id, salario);
            }

        } finally {
            em.close();
        }
    }


    public static void listarEmpleadosTablaEmpleado() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Empleado> lista = em.createQuery(
                    "SELECT e FROM Empleado e ORDER BY e.id", Empleado.class
            ).getResultList();

            if (lista.isEmpty()) {
                System.out.println("No hay Empleados.");
                return;
            }

            // Encabezado
            System.out.println("\nListar empleados con tabla Empleado[]");
            System.out.printf("%-12s %-10s%n", "ID", "SALARIO");
            System.out.println("--------------------");

            for (Empleado e : lista) {
                System.out.printf("%-12d %6d€%n", e.getId(), e.getSalario());
            }

        } finally {
            em.close();
        }
    }
}
