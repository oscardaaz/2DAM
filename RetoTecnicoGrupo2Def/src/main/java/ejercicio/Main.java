package ejercicio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static final EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("RetoTecnicoGrupo2Def");

    private static final Scanner sc = new Scanner(System.in).useLocale(Locale.US);

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
                sc.nextLine();
                switch (opcion) {
                    case 1 -> insertarEmpleado();
                    case 2 -> mostrarEmpleado();
                    case 3 -> listarEmpleadosTabla();
                    case 4 -> System.out.println("Saliendo de la base de datos...");
                    default -> System.out.println("Opción inválida");
                }
            } while (opcion != 4);

        } finally {
            if (emf.isOpen()) emf.close();
            sc.close();
        }
    }
    //test
    private static void insertarEmpleado() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            System.out.print("Ingresa el nombre del empleado: ");
            String nombre = sc.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío!");
                System.out.println("Volviendo al menú...");
                return;
            }

            System.out.print("Ingresa el departamento del empleado: ");
            String departamento = sc.nextLine().trim();

            if (departamento.isEmpty()) {
                System.out.println("El departamento no puede estar vacío!");
                System.out.println("Volviendo al menú...");
                return;
            }

            System.out.print("Ingresa el salario del empleado (Decimales con punto): ");

            if (!sc.hasNextDouble()) {
                System.out.println("Error: El salario debe ser un número (double).");
                sc.nextLine();
                System.out.println("Volviendo al menú...");
                return;
            }

            double salario = sc.nextDouble();
            sc.nextLine();

            if (salario < 0) {
                System.out.println("El salario no puede ser negativo!");
                System.out.println("Volviendo al menú...");
                return;
            }

            Empleado empleado = new Empleado(nombre, departamento, salario);

            tx.begin();
            em.persist(empleado);
            tx.commit();
            System.out.println("Empleado guardado con id: " + empleado.getId());

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Error al insertar un nuevo empleado " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private static void mostrarEmpleado() {
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
            System.err.println("Error al consultar un único empleado por id: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void listarEmpleadosTabla() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Empleado> listaEmpleados = em.createQuery(
                    "SELECT e FROM Empleado e ORDER BY e.id", Empleado.class).getResultList();

            if (listaEmpleados.isEmpty()) {
                System.out.println("No hay Empleados.");
                return;
            }

            System.out.println("\nLista de Empleados: ");
            System.out.printf("%-8s %-20s %-24s %-10s%n", "ID", "NOMBRE", "DEPARTAMENTO", "SALARIO");
            System.out.println("--------------------------------------------------------------");

            for (Empleado e : listaEmpleados) {
                System.out.printf(Locale.US,"%-8d %-20s %-20s %10.2f€%n",
                        e.getId(), e.getNombre(), e.getDepartamento(), e.getSalario());
            }

        } catch (Exception e) {
            System.err.println("Error al consultar todos los empleados: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}