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
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(50),
                    ciclo VARCHAR(50)
                ) AUTO_INCREMENT = 1000 ;
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

        String sql = "INSERT INTO alumno (nombre, ciclo) VALUES (?, ?)";

        try (PreparedStatement pst = conexion.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS)) {
            //pst.setInt(1, alumno.getId());
            pst.setString(1, alumno.getNombre());
            pst.setString(2, alumno.getCiclo());
            int filasInsertadas = pst.executeUpdate();
            if (filasInsertadas == 0) {
                return -1;
            }

            //Recuperar la clave autogenerada
            try (ResultSet rs = pst.getGeneratedKeys()){
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    return idGenerado;
                }else{
                    System.err.println("Error al leer las claves");
                    return -1;
                }
            }

        } catch (SQLException sqle) {
            System.err.println("Error al insertar Alumno " + sqle.getMessage());
            return -1;
        }
    }

    @Override
    public List<Integer> insertarVariosAlumnosBatch(List<Alumno> alumnos) {

        final String SQL = "INSERT INTO alumno (nombre, ciclo) VALUES (?, ?)";

        List<Integer> idsGenerados = new ArrayList<>();

        try (PreparedStatement pst = conexion.prepareStatement(
                SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            //pst.setInt(1, alumno.getId());

            for (Alumno alumno : alumnos) {
                pst.setString(1, alumno.getNombre());
                pst.setString(2, alumno.getCiclo());
                pst.addBatch();
            }

            pst.executeBatch();

            //Recuperar la clave autogenerada
            try (ResultSet rs = pst.getGeneratedKeys()){
                while (rs.next()) {
                    idsGenerados.add(rs.getInt(1));
                }
            }
        } catch (SQLException sqle) {
            System.err.println("Error al insertar Alumno " + sqle.getMessage());
        }
        return idsGenerados;
    }

    @Override
    public Integer cogerUltimoID() {
        String sql = "SELECT id FROM alumno";
        int ultimoID = 0;

        try (Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                ultimoID = rs.getInt(1);
            }
            return ultimoID;
        } catch (SQLException sqle) {
            System.err.println("Error en coger el ultimo ID " + sqle.getMessage());
        }
        return 0;

    }

    @Override
    public int obtenerUltimaClave() {

        final String SQL = "SELECT id FROM alumno ORDER BY id DESC LIMIT 1";
        // SELECT MAX(id) FROM alumno
        try (Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(SQL)){

            if (rs.next()) return rs.getInt(1);

        }catch (SQLException sqle){
            System.err.println("Error al obtener ultima clave " + sqle.getMessage());

        }
        return 0;
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




}
