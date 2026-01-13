package app;

import data.DBConnectionManager;
import data.dao.ProductoDAOImpl;
import model.Producto;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

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
        ProductoDAOImpl productoDAO = new ProductoDAOImpl(conexion);
        //productoDAO.crearTablaProducto();
        //productoDAO.crearTablaFabricante();

        productoDAO.insertarProducto(new Producto("Corcho",5.56,5));

    }
}
