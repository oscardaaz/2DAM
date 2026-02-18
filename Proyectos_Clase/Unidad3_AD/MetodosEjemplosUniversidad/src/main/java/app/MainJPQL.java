package app;

import jakarta.persistence.*;
import model.*;
import java.math.BigDecimal;
import java.util.List;

public class MainJPQL {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ud3-demo-universidad");

    public static void main(String[] args) {

        //EntityManager em = null;
        try {
//               em = emf.createEntityManager();

//            CRUD
//            listarTodosLosEstudiantesYCursos();
//            listarTodosLosCursosYnomProf();
//            listarEstudicantesMatriculadosEnCursoEspecifico();
//            listarProfesoresYsede();
            obtenerCursosQueImparteUnProfesor();
//            notaMasAltaCurso();
//            cantidadEstudiantes();
//            aumentarCreditosEn2();
//            eliminarEstudiantesNoMatriculados();


        } catch (Exception e) {
            System.err.println("Error en el main " + e.getMessage());

        } finally {
            // if (em != null && em.isOpen()) em.close();
            if (emf != null && emf.isOpen()) emf.close();
        }
    }


    // 1. Obtener todos los estudiantes y los cursos en los que están matriculados

    private static void listarTodosLosEstudiantesYCursos() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            String jpql = "SELECT m FROM Matricula m";
            TypedQuery<Matricula> q = em.createQuery(jpql, Matricula.class);
            List<Matricula> listaEstudiantes = q.getResultList();
            for (Matricula m : listaEstudiantes) {
//                System.out.println(m);
                System.out.println("El estudiante: " + m.getEstudiante().getNombre() + " esta matriculado en: " + m.getCurso().getNombre());
//                System.out.println(e.getNombre() + e.getMatriculas());
            }

            // Otra forma

            String jpql2 = "SELECT e.nombre, c.nombre FROM Estudiante e, Curso c, Matricula m WHERE e.id = m.estudiante.id AND c.id = m.curso.id";

            String otraSelect = """
                    SELECT e.nombre, c.nombre 
                    FROM Matricula m 
                    JOIN m.estudiante e 
                    JOIN m.curso c
                    """;
            Query q2 = em.createQuery(jpql2);

            List<Object[]> lista = q2.getResultList();
            for (Object[] object : lista) {
                System.out.println("El estudiante: " + object[0] + " esta matriculado en el curso: " + object[1]);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error en listar todas las sedes " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // 2. Listar los cursos con el nombre del profesor que los imparte

    private static void listarTodosLosCursosYnomProf() {

        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            String jpql = "SELECT c.nombre , p.nombre FROM Curso c, Profesor p WHERE c.profesor.id = p.id";
            Query q = em.createQuery(jpql);

            List<Object[]> lista = q.getResultList();

            for (Object[] object : lista) {
                System.out.println("El curso: " + object[0] + " lo imparte el profesor: " + object[1]);
            }

            // Otro metodo:

            String jpql2 = "FROM Curso";
            TypedQuery<Curso> cursos = em.createQuery(jpql2, Curso.class);
            List<Curso> listaCursos = cursos.getResultList();

            for (Curso c : listaCursos) {
                System.out.println("El curso: " + c.getNombre() + " lo imparte: " + c.getProfesor().getNombre());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error en listar todas las sedes " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // 3. Obtener los estudiantes matriculados en un curso específico (por nombre)

    private static void listarEstudicantesMatriculadosEnCursoEspecifico() {

        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            // Aqui cogiendo solo el dato del campo, pero te devuelve un String con dos campos un Object[]
            String jpql = "SELECT e.nombre FROM Matricula m " +
                    "JOIN m.estudiante e " +
                    "JOIN m.curso c WHERE c.nombre = :NOMBRE";

            Query q = em.createQuery(jpql);
            q.setParameter("NOMBRE","Programación Avanzada");
            List<String> lista = q.getResultList();
            System.out.println("Estudiantes en, por ejemplo, '" + q.getParameterValue("NOMBRE")+"'");
            for (String e : lista){
                System.out.println("El estudiante: " + e + " esta matriculado en el curso: " + q.getParameterValue("NOMBRE"));
            }

            // Otro metodo con una lista de Estudiantes
            System.out.println("Estudiantes en, por ejemplo, '" + q.getParameterValue("NOMBRE")+"'");
            String jpql2 = "SELECT e FROM Matricula m " +
                    "JOIN m.estudiante e " +
                    "JOIN m.curso c WHERE c.nombre = :NOMBRE";

            TypedQuery<Estudiante> q2 = em.createQuery(jpql2,Estudiante.class);
            q2.setParameter("NOMBRE","Programación Avanzada");
            List<Estudiante> listaEstudiantes = q2.getResultList();
            for (Estudiante e : listaEstudiantes){
                System.out.println("El estudiante: " + e.getNombre() + " esta matriculado en el curso: " + q.getParameterValue("NOMBRE"));
            }


        }catch (Exception e){
            e.printStackTrace();
            System.err.println("Error en listar alumnos matriculados en un curso en especifico " + e.getMessage());
        }finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // 4. Listar los profesores con la sede en la que trabajan

    private static void listarProfesoresYsede(){
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            // Metodo facil
            String jpql = "FROM Profesor";
            TypedQuery<Profesor> q = em.createQuery(jpql,Profesor.class);
            List<Profesor> listaProf = q.getResultList();

            for (Profesor p : listaProf){
//                System.out.println(p);
                // Otra forma mas directa
                System.out.println("El profesor: "+p.getNombre() + " trabaja en la sede: " + p.getSede().getNombre());
            }

            // Otra forma con Object cogemos los dos campos necesarios

            String jpql2 = "SELECT p.nombre , s.nombre FROM Profesor p, Sede s WHERE p.sede.id = s.id";

            String jpql2Joins = "SELECT p.nombre, s.nombre" +
                    "FROM Profesor p" +
                    "JOIN p.sede s";
            Query q2 = em.createQuery(jpql2);
            List<Object[]> listaObject = q2.getResultList();

            for (Object[] object: listaObject){
//                System.out.println(Arrays.toString(object));
                System.out.println("El profesor: "+object[0] + " trabaja en la sede: "+object[1]);
            }

        }catch (Exception e){
            e.printStackTrace();
            System.err.println("Error en listar profesores y las sedes donde trabajan" + e.getMessage());
        }finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // 5. Obtener los cursos que imparte un profesor específico (por nombre)

    private static void obtenerCursosQueImparteUnProfesor(){
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
//            String jpql = "SELECT c FROM Curso c, Profesor p WHERE p.id = c.profesor.id AND p.nombre = :NOMBREPROF";

//            String jpql = "SELECT c FROM Curso c WHERE c.profesor.nombre = :NOMBREPROF";

            String jpql = "SELECT c FROM Curso c JOIN c.profesor p WHERE p.nombre = :nomProf";

            TypedQuery<Curso> q = em.createQuery(jpql,Curso.class);
            q.setParameter("nomProf","Dr. Juan Pérez");

            System.out.println("Cursos de, por ejemplo, " + q.getParameterValue("nomProf"));
            List<Curso> listaCursos = q.getResultList();

            for (Curso c : listaCursos){
                System.out.println(c.getNombre());
            }

            // OTRA OPCION
            String jpql2 = "SELECT c.nombre FROM Curso c JOIN c.profesor p WHERE p.nombre = :nomProf";

            TypedQuery<String> q2 = em.createQuery(jpql2,String.class);
            q2.setParameter("nomProf","Dr. Juan Pérez");

            System.out.println("Cursos de, por ejemplo, " + q2.getParameterValue("nomProf"));
            List<String> listaCursos2 = q2.getResultList();

            for (String s : listaCursos2){
                System.out.println(s);
            }

        }catch (Exception e){
            e.printStackTrace();
            System.err.println("Error en obtener los cursos que imparte un profesor especifico " + e.getMessage());
        }finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // 6. Obtener la nota más alta de un curso específico

    private static void notaMasAltaCurso(){
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            String jpql = "SELECT MAX(e.nota) FROM Matricula m " +
                    "JOIN m.evaluacions e " +
                    "JOIN m.curso c " +
                    "WHERE c.nombre = :nombreCurso";

            TypedQuery<BigDecimal> q = em.createQuery(jpql,BigDecimal.class);
            q.setParameter("nombreCurso","Programación Avanzada");
            BigDecimal notaMaxima = q.getSingleResult();

            System.out.println(notaMaxima);

            q.setParameter("nombreCurso","Programación Avanzada");


        }catch (Exception e){
            e.printStackTrace();
            System.err.println("Error en obtener la nota mas alta de un curso " + e.getMessage());
        }finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // 7. Obtener la cantidad de estudiantes por curso

    private static void cantidadEstudiantes(){
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

//            String jpql = "SELECT COUNT(e.id) FROM Matricula m " +
//                    "JOIN m.estudiante e" +
//                    " JOIN m.curso c WHERE c.nombre = :nombreCurso";
//
//            //Query q = em.createQuery(jpql);
//            TypedQuery<Long> q = em.createQuery(jpql, Long.class);
//            q.setParameter("nombreCurso","Bases de Datos");
//            Long numeroEstudiantes = q.getSingleResult();
//
//            System.out.println(numeroEstudiantes);

            // Forma correcta si tiene que ser una lista de Object[] Juraria

            String jpql2 = "SELECT c.nombre, count(e.id) FROM Matricula m " +
                    "JOIN m.estudiante e " +
                    "JOIN m.curso c " +
                    "GROUP BY c.nombre " +
                    "ORDER BY c.nombre ASC ";

            Query q2 = em.createQuery(jpql2);
            List<Object[]> lista = q2.getResultList();

            for (Object[] object : lista){
                System.out.println(object[0] +" - " + object[1]);
            }

        }catch (Exception e){
            e.printStackTrace();
            System.err.println("Error en obtener cantidad estudiantes por curso " + e.getMessage());
        }finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // 8. Aumentar los créditos de un curso en 2 unidades

    private static void aumentarCreditosEn2(){

        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            // Actualizar la edad de los alumnos menores de 20 años
            String jpqlUpdate = "UPDATE Curso c SET creditos = creditos + 2 WHERE c.nombre = :nomCurso";

            Query updateQuery = em.createQuery(jpqlUpdate);
            updateQuery.setParameter("nomCurso","Programación Avanzada");
            int updatedRows = updateQuery.executeUpdate();

            System.out.println("Cursos actualizados: " + updatedRows);

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null && tx.isActive()) tx.rollback();

            System.err.println("Error en aumentar creditos en 2: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // 9. Eliminar los estudiantes que no estén matriculados en ningún curso
        private static void eliminarEstudiantesNoMatriculados(){
            EntityManager em = null;
            EntityTransaction tx = null;
            try {
                em = emf.createEntityManager();
                tx = em.getTransaction();
                tx.begin();

                // Primero borrar los exámenes de los alumnos repetidores
//                String jpql1 = "DELETE FROM Examen e WHERE e.alumno "
//                        + "IN (SELECT a FROM Alumno a WHERE a.repetidor = true)";

//                Query q1 = em.createQuery(jpql1);

//                int examenesBorrados = q1.executeUpdate();

                tx.commit();
//                System.out.println("Número de exámenes borrados: " + examenesBorrados);

            } catch (Exception e) {
                e.printStackTrace();
                if (tx != null && tx.isActive()) {
                    tx.rollback();
                }
                System.out.println("Error en borrar alumnos no matriculados: " + e.getMessage());
            } finally {
                if (em != null && em.isOpen()) em.close();
            }
        }


    // 10. Obtener los cursos que no tienen ningún estudiante matriculado


} // FIN DE LA CLASE