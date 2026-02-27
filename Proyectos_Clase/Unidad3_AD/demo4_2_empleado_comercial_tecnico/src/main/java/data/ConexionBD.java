package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {
    private static Connection conexion = null;

    /**
     * Constructor de la clase. Debe ser private
     */
    private ConexionBD() {
    }

    /**
     * Metodo para conectar con la base de datos. Debe ser private.
     */
    private static void conectar() {
        Properties config = new Properties();
        // Leemos el fichero de configuración
// try {
// config.load(new BufferedReader(
// new FileReader("src/main/resources/dbConfig.properties")));
// } catch (IOException ex) {
// System.err.println("IOException generada. " + ex.getMessage());
// }
        try {
            // Opción segura con ClassLoader
            config.load(ConexionBD.class.getResourceAsStream("/dbConfig.properties"));
        } catch (IOException ex) {
            System.err.println("IOException generada. " + ex.getMessage());
        }
        String ip = config.getProperty("server");
        String puerto = config.getProperty("port");
        String dbName = config.getProperty("dbName");
        String usuario = config.getProperty("user");
        String pwd = config.getProperty("password");
        String useUnicode = config.getProperty("useUnicode");
        String characterEncoding = config.getProperty("characterEncoding");
        String connectionUrl = "jdbc:postgresql://"
                + ip + ":"
                + puerto + "/"
                + dbName
                + "?user=" + usuario
                + "&password=" + pwd
                + "&useUnicode=" + useUnicode
                + "&characterEncoding=" + characterEncoding;
        try {
            conexion = DriverManager.getConnection(connectionUrl);
            System.out.println("Conexión con BD establecida");
        } catch (SQLException ex) {
            System.err.println("Error al conectar: " + ex.getMessage());
        }
    }

    /**
     * Método público utilizado para obtener el objeto que representa esa
     * conexión llamando al método privado conectar()
     *
     * @return El objeto Connection
     */
    public static Connection getConexion() {
        if (conexion == null) {
            conectar();
        }
        return conexion;
    }

    /**
     * Cierra la conexión si está abierta
     */
    public static void desconectar() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Desconexión correcta de la base de datos.");
            } catch (SQLException ex) {
                System.err.println("Error en desconectar: "
                        + ex.getMessage());
            }
        }
    }
} // Fin clase ConexionBD

