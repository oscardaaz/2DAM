package app;
/*
import data.DBConnectionManager;
import data.dao.AlumnoDAOImpl;
import model.Alumno;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

       /* Connection conexion = null;

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
        //int insertarOK = alumnoDAO.insertarAlumno(new Alumno(1,"Oscar","2DAM"));
        //System.out.println(insertarOK > 0 ? "Borrado OK" : "ID no encontrado al insertar o exception generada");

        // Leer todos los alumnos
        for (Alumno a : alumnoDAO.leerTodosLosAlumnos()){
            System.out.println(a);
        }

        //Modificar alumno
        boolean modificarOk = alumnoDAO.modificarAlumno(2,"Nuevo nombre","Integración social");
        System.out.println(modificarOk ? "Modificado OK" : "ID no encontrado al modificar o excepción generada");

        //Borrar Alumno
        int filasEliminadas = alumnoDAO.eliminarAlumno(3);
        System.out.println(filasEliminadas > 0 ? "Borrado OK" : "ID no encontrado al borrar o exception generada");



        // Leer todos los alumnos
        for (Alumno a : alumnoDAO.leerTodosLosAlumnos()){
            System.out.println(a);
        }

        DBConnectionManager.cerrarConexion();

    }
}
*/