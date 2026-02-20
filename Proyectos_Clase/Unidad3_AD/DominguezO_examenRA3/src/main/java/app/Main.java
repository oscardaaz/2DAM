package app;

import data.JPAUtil;
import jakarta.persistence.*;
import model.Libro;
import model.Prestamo;
import model.Usuario;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager em = null;
        try {
            em = JPAUtil.createEntityManager();

            insertarDatos(em);
            mostrarTodo(em);
            borrarUsuario(em,"Laura","Gómez");
            actualizarDatos(em);
            mostrarTodo(em);
            mostrarPrestamosActivos(em,"Gabriel García");
            mostrarPrestamosActivos(em,"George Orwell");

        } catch (Exception e) {
            System.err.println("Error en el main al ejecutar");
            throw new RuntimeException(e);
        }finally {
            if (em != null && em.isOpen()) em.close();
            JPAUtil.close();
        }



    }

    private static void insertarDatos(EntityManager em){
        EntityTransaction tx = null;
        try {
                tx = em.getTransaction();
               // Creación de objetos para la carga inicial de datos
               tx.begin();
               // ######  Usuarios  #####
               Usuario u1 = new Usuario();
               u1.setNombre("Carlos");  u1.setApellido("Pérez");   u1.setEmail("carlos.perez@mail.com");
               Usuario u2 = new Usuario(); u2.setNombre("Laura");   u2.setApellido("Gómez");   u2.setEmail("laura.gomez@mail.com");
               Usuario u3 = new Usuario(); u3.setNombre("Marta");   u3.setApellido("Ruiz");    u3.setEmail("marta.ruiz@mail.com");
               Usuario u4 = new Usuario(); u4.setNombre("Javier");  u4.setApellido("López");   u4.setEmail("javier.lopez@mail.com");
               Usuario u5 = new Usuario(); u5.setNombre("Elena");   u5.setApellido("Sánchez"); u5.setEmail("elena.sanchez@mail.com");
               em.persist(u1);  em.persist(u2); em.persist(u3); em.persist(u4); em.persist(u5);
                // ######  Libros  #####
               Libro l1 = new Libro(); l1.setIsbn("978-1111"); l1.setTitulo("El nombre de la rosa");      l1.setAutor("Umberto Eco");     l1.setAnioPublicacion(1980);
               Libro l2 = new Libro(); l2.setIsbn("978-2222"); l2.setTitulo("Cien años de soledad");      l2.setAutor("Gabriel García");  l2.setAnioPublicacion(1967);
               Libro l3 = new Libro(); l3.setIsbn("978-3333"); l3.setTitulo("Matar un ruiseñor");         l3.setAutor("Harper Lee");      l3.setAnioPublicacion(1960);
               Libro l4 = new Libro(); l4.setIsbn("978-4444"); l4.setTitulo("1984");                      l4.setAutor("George Orwell");   l4.setAnioPublicacion(1949);
               Libro l5 = new Libro(); l5.setIsbn("978-5555"); l5.setTitulo("El señor de los anillos");   l5.setAutor("J.R.R. Tolkien"); l5.setAnioPublicacion(1954);
               em.persist(l1);  em.persist(l2); em.persist(l3); em.persist(l4); em.persist(l5);

               // ######### Préstamos ##########
               Prestamo p1 = new Prestamo(); p1.setUsuario(u1); p1.setLibro(l1); p1.setFechaPrestamo(LocalDate.of(2024, 1, 10));  p1.setFechaDevolucion(LocalDate.of(2024, 1, 25));
               Prestamo p2 = new Prestamo(); p2.setUsuario(u2); p2.setLibro(l2); p2.setFechaPrestamo(LocalDate.of(2024, 2,  1));
               Prestamo p3 = new Prestamo(); p3.setUsuario(u1); p3.setLibro(l3); p3.setFechaPrestamo(LocalDate.of(2024, 2, 15));  p3.setFechaDevolucion(LocalDate.of(2024, 3,  1));
               Prestamo p4 = new Prestamo(); p4.setUsuario(u3); p4.setLibro(l4); p4.setFechaPrestamo(LocalDate.of(2024, 3,  5));
               Prestamo p5 = new Prestamo(); p5.setUsuario(u2); p5.setLibro(l5); p5.setFechaPrestamo(LocalDate.of(2023,11, 20));  p5.setFechaDevolucion(LocalDate.of(2023,12, 10));
               Prestamo p6 = new Prestamo(); p6.setUsuario(u4); p6.setLibro(l1); p6.setFechaPrestamo(LocalDate.of(2023,10,  1));  p6.setFechaDevolucion(LocalDate.of(2023,10, 20));
               Prestamo p7 = new Prestamo(); p7.setUsuario(u5); p7.setLibro(l2); p7.setFechaPrestamo(LocalDate.of(2024, 3, 10));
               Prestamo p8 = new Prestamo(); p8.setUsuario(u3); p8.setLibro(l3); p8.setFechaPrestamo(LocalDate.of(2023, 9, 15));  p8.setFechaDevolucion(LocalDate.of(2023,10,  1));
               em.persist(p1);em.persist(p2);em.persist(p3);em.persist(p4);em.persist(p5);em.persist(p6);em.persist(p7);em.persist(p8);
               tx.commit();
            System.out.println("Datos insertados correctamente");
           } catch (Exception e) {
               if (tx != null && tx.isActive()) tx.rollback();
               System.err.println("Excepción al insertar animal: " + e.getMessage());
           }
    }

    private static void mostrarLibros(EntityManager em){

        try {
            String jpql = "FROM Libro";
            TypedQuery<Libro> q = em.createQuery(jpql, Libro.class);
            List<Libro> listaLibros = q.getResultList();

            System.out.println("Contenido de la tabla 'libros': ");
            listaLibros.forEach(libro -> System.out.println(libro));
            System.out.println("Mostrar libros ejecutado correctamente. "+ listaLibros.size() + " libros encontrados");
        }catch (Exception e){
            System.err.println("Excepción al mostrarLibros: " + e.getMessage());
        }
    }

    private static void mostrarUsuarios(EntityManager em){

        try {
            String jpql = "FROM Usuario";
            TypedQuery<Usuario> q = em.createQuery(jpql, Usuario.class);
            List<Usuario> listaUsuarios= q.getResultList();

            System.out.println("Contenido de la tabla 'usuarios': ");
            listaUsuarios.forEach(usuario -> System.out.println(usuario));
            System.out.println("Mostrar usuarios ejecutado correctamente. " + listaUsuarios.size() + " usuarios encontrados");
        }catch (Exception e){
            System.err.println("Excepción al mostrarLibros: " + e.getMessage());
        }
    }

    private static void mostrarPrestamos(EntityManager em){

        try {
            String jpql = "FROM Prestamo";
            TypedQuery<Prestamo> q = em.createQuery(jpql, Prestamo.class);
            List<Prestamo> listaPrestamos = q.getResultList();

            System.out.println("Contenido de la tabla 'prestamos': ");
            listaPrestamos.forEach(prestamo -> System.out.println(prestamo));
            System.out.println("Mostrar prestamos ejecutado correctamente. "+ listaPrestamos.size() + " préstamos encontrados");
        }catch (Exception e){
            System.err.println("Excepción al mostrarLibros: " + e.getMessage());
        }
    }

    private static void mostrarTodo(EntityManager em){
        mostrarLibros(em);
        mostrarUsuarios(em);
        mostrarPrestamos(em);
    }

    private static void borrarUsuario(EntityManager em,
                                      String nombre,
                                      String apellido)
    {
        EntityTransaction tx = null;
        try{
            tx = em.getTransaction();

            String jpqlID = "SELECT u.id FROM Usuario u WHERE u.nombre = :nombre AND u.apellido = :apellido";
            TypedQuery<Integer> qID = em.createQuery(jpqlID, Integer.class);
            qID.setParameter("nombre",nombre.trim());
            qID.setParameter("apellido",apellido.trim());
            Integer usuarioID = qID.getSingleResult();
            System.out.println(usuarioID);

            tx.begin();
            String jpqlBorrarPrestamos = "DELETE FROM Prestamo p WHERE p.usuario.id = :usuarioID";
            Query qBorrarPrestamos = em.createQuery(jpqlBorrarPrestamos);
            qBorrarPrestamos.setParameter("usuarioID",usuarioID);
            int prestamosBorrados = qBorrarPrestamos.executeUpdate();
            System.out.println(prestamosBorrados);

            String jpqlBorrarUsuario = "DELETE FROM Usuario u WHERE u.id = :usuarioID";
            Query qBorrarUsuario = em.createQuery(jpqlBorrarUsuario);
            qBorrarUsuario.setParameter("usuarioID",usuarioID);
            int usuariosBorradas = qBorrarUsuario.executeUpdate();
            System.out.println(usuariosBorradas);
            em.clear();
            tx.commit();
        }catch (Exception e){
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Excepción al borrar un usuario por nom y ape: " + e.getMessage());
        }

    }

    private static void actualizarDatos(EntityManager em){
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();

            // Actualizar la edad de los alumnos menores de 20 años
            String jpqlUpdate = "UPDATE Usuario u SET apellido = :apellido, email = :email " +
                    "WHERE u.nombre = :nombre AND u.apellido = :apellidoOriginal";

            Query updateQuery = em.createQuery(jpqlUpdate);
            updateQuery.setParameter("apellidoOriginal","Sánchez".trim());
            updateQuery.setParameter("nombre","Elena".trim());
            updateQuery.setParameter("apellido","Martínez");
            updateQuery.setParameter("email","elena.martinez@mail.com");

            int updatedRows = updateQuery.executeUpdate();

            System.out.println("Usuarios actualizados: " + updatedRows);

            tx.commit();
            System.out.println("Usuaria actualizada con exito");
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null && tx.isActive()) tx.rollback();

            System.err.println("Error en aumentar creditos en 2: " + e.getMessage());
        }

    }

    private static void mostrarPrestamosActivos(EntityManager em,String autor){
        try {

            String jpql = "SELECT u.apellido, l.titulo,p.fechaPrestamo FROM Prestamo p" +
                    " JOIN p.libro l" +
                    " JOIN p.usuario u" +
                    " WHERE p.libro.autor = :autor AND p.fechaDevolucion IS NULL" +
                    " ORDER BY p.fechaPrestamo";


            Query q  = em.createQuery(jpql);
            q.setParameter("autor",autor);

            List<Object[]> lista = q.getResultList();
            if (lista.isEmpty()) System.out.println("lista vacia");
            for (Object[] o : lista){
                System.out.println("apellido= "+o[0] +" titulo= "+o[1] +" fechaPrestamo= "+o[2]);
            }


        }catch (Exception e){
            System.err.println("Excepción al mostrarLibros: " + e.getMessage());
        }
    }

} // FIN DE LA CLASE