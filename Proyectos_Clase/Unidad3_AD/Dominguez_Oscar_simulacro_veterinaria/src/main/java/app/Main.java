package app;

import data.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import model.Consulta;
import model.Especie;
import model.Mascota;
import model.Propietario;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {


//        Ejercicio 2
//        mostrarEspecies();

//        Ejercicio 3
//        mostrarMascotas(em);

//        Ejercicio 4
//        insertarPropietario("Manuel","Soto","6643523475");

//        Ejercicio 5
//        mostrarMascotasPorEspecie(1);

//         Ejercicio 6
//        mostrarConsultasPorPropietario("Pérez");

//        Ejercicio 7
            eliminarConsultasAnteriores(2023);


        JPAUtil.close();
    }

    private static void insertar(){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
           em = JPAUtil.createEntityManager();
           tx = em.getTransaction();
           tx.begin();

           tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Excepción al insertar animal: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // Ejercicio 2 CON JPQL — mostrarEspecies

    private static void mostrarEspecies(){

        EntityManager em = null;
        try {
            em = JPAUtil.createEntityManager();

            String jpql = "FROM Especie";


            TypedQuery<Especie> q = em.createQuery(jpql, Especie.class);
            List<Especie> listaEspecies = q.getResultList();

            System.out.println("*** Método mostrarEspecies");
            System.out.println("*** Contenido de la tabla 'especie':");
            for (Especie e : listaEspecies) System.out.println(e);

        }catch (Exception e) {
            System.err.println("Excepción al consultar las especies: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) em.close();
        }

    }

    // Ejercicio3 CON JPQL — mostrarMascotas
    /* Muestra por consola los datos de todas las mascotas, sobreescribiendo
    el metodo toString en la entidad Mascota. La salida debe mostrar el nombre
    de la especie y el nombre y apellidos del propietario, no sus
    códigos.*/

    private static void mostrarMascotas(){

        EntityManager em = null;
        try {
            em = JPAUtil.createEntityManager();

            String jpql = "FROM Mascota";

            TypedQuery<Mascota> q = em.createQuery(jpql, Mascota.class);
            List<Mascota> listaMascotas = q.getResultList();

            System.out.println("*** Método mostrarMascotas");
            System.out.println("*** Datos de todas las mascotas:");
            for (Mascota m : listaMascotas){
//                System.out.println(m.getId() + m.getNombre() + m.getEspecie().getNomEspecie() + m.getPropietario().getNombre() + m.getPropietario().getApellidos());

                System.out.println(m);
            }

        }catch (Exception e) {
            System.err.println("Excepción al consultar las mascotas: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // Ejercicio 4
    /* Apartado 4 (1,75 puntos) — SIN JPQL — insertarPropietario
    Recibe como parámetros el nombre, los apellidos y el teléfono de un nuevo   propietario e inserta el registro en
    la base de datos sin usar JPQL. A continuación, muestra el contenido actualizado de la tabla propietario */

    private static void insertarPropietario(
            String nombre,
            String apellidos,
            String telefono
        ){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            System.out.println("*** Método insertarPropietario");
            Propietario p = new Propietario();
            p.setNombre(nombre);
            p.setApellidos(apellidos);
            p.setTelefono(telefono);
            em.persist(p);
            tx.commit();
            System.out.println("Propietario insertado correctamente");


            TypedQuery<Propietario> q = em.createQuery("FROM Propietario",Propietario.class);
            List<Propietario> listaProp = q.getResultList();

            for (Propietario p2 : listaProp){
                System.out.println(p2);
            }


        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Excepción al insertar propietario: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }


    // Ejercicio 5
    /* Recibe un cod_especie y muestra las mascotas que pertenecen a esa especie.
    Si no existe ninguna mascota con ese código de especie, se indicará mediante
    un mensaje en consola.*/

    private static void mostrarMascotasPorEspecie(int codEspecie){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.createEntityManager();

            String jpql = "SELECT m FROM Mascota m WHERE m.especie.id = :codEspecie";
            TypedQuery<Mascota> q = em.createQuery(jpql,Mascota.class);
            q.setParameter("codEspecie",codEspecie);
            List<Mascota> listaMascotas = q.getResultList();

            if (listaMascotas.isEmpty()) {
                System.out.println("No existe ninguna mascota con el codigo de especie " +codEspecie);
                return;
            }

            System.out.println("*** Método mostrarMascotasPorEspecie");
            //TODO coger nombre especie con el parametro

            System.out.println("Mascotas de la especie: " + listaMascotas.getFirst().getEspecie().getNomEspecie());

            for (Mascota m : listaMascotas){
                System.out.println(m);
            }

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Excepción al mostrar mascotas por especie: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // Ejercicio 6
    /* CON JPQL — mostrarConsultasPorPropietario
    Recibe el apellido de un propietario y muestra todas las consultas de sus mascotas, mostrando: nombre de la
    mascota, fecha, motivo y precio. Ordenado por fecha descendente. Las consultas JPQL deben tener todos los
    valores parametrizados.
    */

    private static void mostrarConsultasPorPropietario (String apellidoPropietario){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.createEntityManager();

//            String jpql = "SELECT c FROM Consulta c" +
//                    " JOIN c.mascota m" +
//                    " JOIN c.mascota.propietario p" +
//                    " WHERE p.apellidos = :apellidoPropietario" +
//                    " ORDER BY c.fecha DESC";

            String jpql = "FROM Consulta c " +
                    "WHERE c.mascota.propietario.apellidos = :apellidoPropietario ORDER BY c.fecha DESC";

            TypedQuery<Consulta> q = em.createQuery(jpql, Consulta.class);
            q.setParameter("apellidoPropietario",apellidoPropietario.trim());

            List<Consulta> listaConsultas = q.getResultList();

            System.out.println("*** Método mostrarConsultasPorPropietario");
            System.out.println("Consultas de mascotas del propietario '"+ listaConsultas.getFirst().getMascota().getPropietario().getApellidos()+ "':");
            System.out.println();
            for (Consulta c : listaConsultas) {
                System.out.println(c);
            }


            // Otra forma:



        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Excepción al mostrar consultas por propietario: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }


//    Ejercicio 7
    /* — CON JPQL — eliminarConsultasAnteriores
    Recibe un año como entero y elimina todas las consultas cuya fecha sea anterior al 1 de enero de ese año.
    Muestra el número de consultas eliminadas y el contenido actualizado de la tabla. Todos los valores deben
    estar parametrizados.
    */

    private static void eliminarConsultasAnteriores(int anio)
    {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.createEntityManager();
            tx = em.getTransaction();

            tx.begin();
            String jpql = "DELETE FROM Consulta c WHERE c.fecha < :date";

            Query q = em.createQuery(jpql);
            LocalDate anioFiltrado = java.time.LocalDate.of(anio,1,1);

            q.setParameter("date",anioFiltrado);
            int consultasBorradas = q.executeUpdate();

            tx.commit();

            String jpqlSearch = "SELECT c FROM Consulta c";
            TypedQuery<Consulta> qSearch = em.createQuery(jpqlSearch, Consulta.class);
            List<Consulta> listaConsultas = qSearch.getResultList();

            System.out.println("*** Método eliminarConsultasAnteriores");
            System.out.println("Consultas eliminadas anteriores a "+ anio +": "+consultasBorradas);
            System.out.println("*** Contenido actualizado de la tabla 'consulta':");

            for (Consulta c : listaConsultas) System.out.println(c);


        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Excepción al borrar consultas anteriores a date: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

} // FIN DE LA CLASE