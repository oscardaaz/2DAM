package data.dao;

import model.Fabricante;
import model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO{

    private Connection conexion;
    public ProductoDAOImpl(Connection conexion) {

        if (conexion == null) {
            throw new IllegalArgumentException("La conexión no puede ser nula");

        }
        this.conexion = conexion;
    }
    @Override
    public boolean crearTablaProducto() {
        String sql = """
                CREATE TABLE IF NOT EXISTS producto (
                    codigo INT (10) AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    precio DECIMAL(8,2) NOT NULL,
                    codigo_fabricante INT(10), 
                     FOREIGN KEY (codigo_fabricante) REFERENCES fabricante(codigo)
                );
                """;

        try (Statement st = conexion.createStatement()) {

            st.executeUpdate(sql);
            System.out.println("Tabla producto creada");
            return true;

        } catch (SQLException sqle) {
            System.err.println("Error en crear tabla " + sqle.getMessage());
            return false;
        }
    }

    @Override
    public boolean crearTablaFabricante() {
        String sql = """
                CREATE TABLE IF NOT EXISTS fabricante (
                    codigo INT (10) AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL           
                );
                """;

        try (Statement st = conexion.createStatement()) {

            st.executeUpdate(sql);
            System.out.println("Tabla fabricante creada");
            return true;

        } catch (SQLException sqle) {
            System.err.println("Error en crear tabla " + sqle.getMessage());
            return false;
        }
    }

    @Override
    public int insertarProducto(Producto producto) {

        String sql = "INSERT INTO producto (nombre,precio,codigo_fabricante) VALUES (?, ?, ?)";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {

            pst.setString(1, producto.getNombre());
            pst.setDouble(2, producto.getPrecio());
            pst.setInt(3, producto.getCod_fabricante());
            int filasInsertadas = pst.executeUpdate();


            System.out.println("Producto añadido correctamente");
            return filasInsertadas;

        } catch (SQLException sqle) {
            System.err.println("Error al insertar Producto " + sqle.getMessage());
            return -1;
        }
    }

    public int insertarFabricante(Fabricante fabricante) {

        String sql = "INSERT INTO fabricante VALUES (?, ?)";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setInt(1, fabricante.getCodigo());
            pst.setString(2, fabricante.getNombre());
            int filasInsertadas = pst.executeUpdate();

            System.out.println("Fabricante añadido correctamente");
            return filasInsertadas;

        } catch (SQLException sqle) {
            System.err.println("Error al insertar Fabricante " + sqle.getMessage());
            return -1;
        }
    }

    @Override
    public List<String> leerProductos(Producto producto, Fabricante fabricante) {
        String sql = "SELECT p.codigo, p.nombre, p.precio, f.nombre FROM producto p, fabricante f WHERE p.codigo_fabricante = f.codigo;";
        List<String> lista = new ArrayList<>();


        try (Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(rs.getInt(1)+ producto.getCodigo() + rs.getString(2)+rs.getDouble(3) + rs.getString(4));

            }


        } catch (SQLException sqle) {
            System.err.println("Error en crear tabla " + sqle.getMessage());

        }

        return lista;
    }

    @Override
    public int descuentoPrecios() {
        return 0;
    }
}
