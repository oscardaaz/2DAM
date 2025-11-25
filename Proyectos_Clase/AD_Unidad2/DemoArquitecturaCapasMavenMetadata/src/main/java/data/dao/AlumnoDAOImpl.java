package data.dao;

import model.Alumno;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAOImpl implements AlumnoDAO {

    private Connection conexion;

    public AlumnoDAOImpl(Connection conexion) {

        if (conexion == null) {
            throw new IllegalArgumentException("La conexión no puede ser nula");

        }
        this.conexion = conexion;
    }

    @Override
    public boolean crearTabla() {

        String sql = """
                CREATE TABLE IF NOT EXISTS alumno (
                    id INT PRIMARY KEY,
                    nombre VARCHAR(50),
                    ciclo VARCHAR(50)
                );
                """;

        try (Statement st = conexion.createStatement()) {

            st.executeUpdate(sql);
            System.out.println("Tabla alumno creada");
            return true;

        } catch (SQLException sqle) {
            System.err.println("Error en crear tabla " + sqle.getMessage());
            return false;
        }

    }

    @Override
    public int insertarAlumno(Alumno alumno) {

        String sql = "INSERT INTO alumno VALUES (?, ?, ?)";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setInt(1, alumno.getId());
            pst.setString(2, alumno.getNombre());
            pst.setString(3, alumno.getCiclo());
            int filasInsertadas = pst.executeUpdate();


            System.out.println("Alumno añadido correctamente");
            return filasInsertadas;

        } catch (SQLException sqle) {
            System.err.println("Error al insertar Alumno " + sqle.getMessage());
            return -1;
        }
    }

    @Override
    public List<Alumno> leerTodosLosAlumnos() {
        String sql = "SELECT * FROM alumno";
        List<Alumno> lista = new ArrayList<>();

        try (Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearAlumno(rs));
            }


        } catch (SQLException sqle) {
            System.err.println("Error en crear tabla " + sqle.getMessage());

        }

        return lista;
    }

    private static Alumno mapearAlumno(ResultSet rs) throws SQLException {
        //Utilizando el nombre de las columnas
//        return new Alumno(
//                rs.getInt("id"),
//                rs.getString("nombre"),
//                rs.getString("ciclo")
//        );

        //Utilizando el nº (índice) de las columnas
        return new Alumno(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3)
        );

    }

    @Override
    public int eliminarAlumno(int id) {

        String sql = "DELETE FROM alumno WHERE id = ?";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate();
        } catch (SQLException sqle) {
            System.err.println("Error al borrar Alumno " + sqle.getMessage());
            return -1;
        }

    }

    @Override
    public boolean modificarAlumno(int id, String nuevoNombre, String nuevoCiclo) {
        String sql = "UPDATE alumno SET nombre = ?, ciclo = ? WHERE id = ?";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setInt(3, id);
            pst.setString(1, nuevoNombre);
            pst.setString(2, nuevoCiclo);

            return pst.executeUpdate() > 0;
        } catch (SQLException sqle) {
            System.err.println("Error al modificar Alumno " + sqle.getMessage());
            return false;
        }

    }

    @Override
    public List<String> obtenerNombresColumnas() {
        List<String> columnas = new ArrayList<>();

        final String SQL = "SELECT * FROM alumno LIMIT 0";

        try (Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(SQL)) {
            ResultSetMetaData meta = rs.getMetaData();
            for (int i = 1 ; i <= meta.getColumnCount(); i++){
                columnas.add(meta.getColumnName(i) + "("
                        + meta.getColumnTypeName(i) + ")");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los nombres de las columnas " + e.getMessage());
            e.printStackTrace();
        }

        return columnas;
    }
}
