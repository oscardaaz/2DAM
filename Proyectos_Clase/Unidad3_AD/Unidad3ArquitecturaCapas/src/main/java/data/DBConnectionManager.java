package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Patrón Singleton
 */
public class DBConnectionManager {

    private static Connection conexion = null;

    private DBConnectionManager() {
    }

    public static boolean inicializarConexion(
            String url,
            String usuario,
            String password
    ) {
        if (conexion != null) {
            System.err.println("La conexion ya estaba establecida");
            return true;
        }
        try {
            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexion establecida correctamente");
            return true;
        } catch (SQLException sqle) {
            System.err.println("Fallo al establecer la conexion " + sqle.getMessage());
            return false;
        }
    }

    public static Connection getConexion() {

        if (conexion == null) {
            // Lanzar excepción
        }

        try {
            if (conexion.isClosed()) {
                // Lanzar excepción
            }

        } catch (SQLException sqle) {
            System.err.println("Error en getConexion() " + sqle.getMessage());
        }

        return conexion;
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada correctamente");
            } catch (SQLException e) {
                System.err.println("Error en cerrarConexion() " + e.getMessage());
            } finally {
                conexion = null; //Buena práctica
            }

        }
    }
}
