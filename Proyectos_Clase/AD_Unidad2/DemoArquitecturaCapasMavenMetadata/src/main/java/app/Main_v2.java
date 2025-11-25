package app;

import data.DBConnectionManager;
import data.dao.AlumnoDAOImpl;
import model.Alumno;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Version con los nombres de las columnas obtenidos como metadatos
 */
public class Main_v2 {
    public static void main(String[] args) {

        Connection conexion = null;

        if (args.length < 5) {
            System.err.println("Se requiere ip puerto dbName user password");
            return;
        }

        //Extracción de credenciales
        String ip = args[0];
        int puerto = Integer.parseInt(args[1]);
        String dbName = args[2];
        String user = args[3];
        String password = args[4];

        String url = "jdbc:mysql://" + ip + ":" + puerto + "/" + dbName;

        // Conectamos...
        boolean conexionOK = DBConnectionManager
                .inicializarConexion(url, user, password);

        if (!conexionOK){
            System.err.println("Error. Fin del programa");
            return;
        }

        try {
            conexion = DBConnectionManager.getConexion();
            System.out.println("Conexión activa? " + !conexion.isClosed());
        } catch (SQLException sqle){
            System.err.println("Error al obtener la conexión" + sqle.getMessage());
        }

        // CRUD

        AlumnoDAOImpl alumnoDAO = new AlumnoDAOImpl(conexion);
        //alumnoDAO.crearTabla();

        // Insertar alumno
        //alumnoDAO.insertarAlumno(new Alumno(1,"Oscar","2DAM"));
        int insertarOK = alumnoDAO.insertarAlumno(new Alumno(1,"Oscar","2DAM"));
        System.out.println(insertarOK > 0 ? "Borrado OK" : "ID no encontrado al insertar o exception generada");
        alumnoDAO.insertarAlumno(new Alumno(1,"Ana","DAM"));
        alumnoDAO.insertarAlumno(new Alumno(2,"Juan","DAW"));
        alumnoDAO.insertarAlumno(new Alumno(3,"Pedro","Peluqueria"));
        alumnoDAO.insertarAlumno(new Alumno(4,"Jorge","Marketing"));

//        // Leer todos los alumnos
//        for (Alumno a : alumnoDAO.leerTodosLosAlumnos()){
//            System.out.println(a);
//        }

        mostrarAlumnosComoTabla(alumnoDAO);
        //Modificar alumno
        boolean modificarOk = alumnoDAO.modificarAlumno(2,"Nuevo nombre","Integración social");
        System.out.println(modificarOk ? "Modificado OK" : "ID no encontrado al modificar o excepción generada");

        //Borrar Alumno
        int filasEliminadas = alumnoDAO.eliminarAlumno(3);
        System.out.println(filasEliminadas > 0 ? "Borrado OK" : "ID no encontrado al borrar o exception generada");



//        // Leer todos los alumnos
//        for (Alumno a : alumnoDAO.leerTodosLosAlumnos()){
//            System.out.println(a);
//        }
        mostrarAlumnosComoTabla(alumnoDAO);

        DBConnectionManager.cerrarConexion();

    }

    private static void mostrarAlumnosComoTabla(AlumnoDAOImpl dao) {
        List<String> columnas = dao.obtenerNombresColumnas();
        List<Alumno> alumnos = dao.leerTodosLosAlumnos();

        // Encabezados
        for (String col : columnas){
            System.out.printf("%-30s",col);
        }
        System.out.println();
        // Separación
        for (int i = 0; i < columnas.size(); i++) {
            System.out.printf("%-30s","--------------------------");
        }
        System.out.println();
        // Datos
        for (Alumno alumno : alumnos){
            System.out.printf("%-30d%-30s%-30s\n",
                    alumno.getId(),
                    alumno.getNombre(),
                    alumno.getCiclo());
        }

    }
}
