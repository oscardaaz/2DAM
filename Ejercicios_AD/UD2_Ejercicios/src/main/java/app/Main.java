package app;

import data.DBConnectionManager;
import model.Cursos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        Connection conexion = ejecutarConexion(args);

        Control control = new Control(conexion);
        control.crearTabla();


//        int idGenerado1 = control.insertarCursos(new cursos("Miedo","La fabada",2,3));
//        System.out.println(idGenerado1 != -1 ? "id 1 = " + idGenerado1 : "Error");

//        int idGenerado2 = control.insertarCursos(new Cursos("Miedo",5,2,"Otra prueba"));
//         System.out.println(idGenerado2 != -1 ? "id 1 = " + idGenerado2 : "Error");
//        int idGenerado3 = control.insertarCursos(new Cursos("Miedo",2,20,"DAW"));
//        System.out.println(idGenerado3 != -1 ? "id 1 = " + idGenerado3 : "Error");
//        int idGenerado4 = control.insertarCursos(new Cursos("Miedo",6,30,"DAM"));
//        System.out.println(idGenerado4 != -1 ? "id 1 = " + idGenerado4 : "Error");


        mostrarAlumnosComoTabla(control);

//        control.modificarCreditos("La fabada",2023);
//        control.eliminarCursos(11112);
//        control.eliminarCursos(11113);
//        control.eliminarCursos(11114);

        mostrarAlumnosComoTabla(control);


        DBConnectionManager.cerrarConexion();
    }

    private static void mostrarAlumnosComoTabla(Control dao) {
        List<String> columnas = dao.obtenerNombresColumnas();
        List<Cursos> cursos = dao.leerTodosLosCursos();

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
        for (Cursos alumno : cursos){
            System.out.printf("%-30s%-30d%-30d%-30s%-30d\n", alumno.getTematica(),alumno.getNum_curso(), alumno.getNum_creditos(), alumno.getTitulo(), alumno.getId_curso());
        }

    }


    private static Connection ejecutarConexion(String[] args) {

        Connection conexion = null;

        if (args.length < 5) {
            System.err.println("Se requiere ip puerto dbName user password");
            return null;
        }

        // Extracción de credenciales
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
            return null;
        }

        try {
            conexion = DBConnectionManager.getConexion();
            System.out.println("¿Conexión activa? " + !conexion.isClosed());
            return conexion;
        } catch (SQLException sqle){
            System.err.println("Error al obtener la conexión: " + sqle.getMessage());
            return null;
        }
    }
}
