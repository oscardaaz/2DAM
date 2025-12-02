package app;

import model.Cursos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Control {

    public Connection conexion;
    public Control(Connection conexion) {

        if (conexion == null) {
            throw new IllegalArgumentException("La conexión no puede ser nula");

        }
        this.conexion = conexion;
    }
    public boolean crearTabla() {

        String sql = """
                CREATE TABLE IF NOT EXISTS cursos (
                    tematica varchar(50),
                    num_curso int(11),
                    num_creditos int(11),
                    titulo VARCHAR(50),
                    id_curso int(11) AUTO_INCREMENT PRIMARY KEY
                ) AUTO_INCREMENT = 11111;
                """;

        try (Statement st = conexion.createStatement()) {

            st.executeUpdate(sql);
            System.out.println("Tabla cursos creada");
            return true;

        } catch (SQLException sqle) {
            System.err.println("Error en crear tabla " + sqle.getMessage());
            return false;
        }

    }

    public int insertarCursos(Cursos curso){

        String sql = "INSERT INTO cursos (tematica, titulo, num_curso, num_creditos) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = conexion.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS)) {
            //pst.setInt(1, alumno.getId());
            pst.setString(1, curso.getTematica());
            pst.setString(2, curso.getTitulo());
            pst.setInt(3, curso.getNum_curso());
            pst.setInt(4, curso.getNum_creditos());
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

    public boolean modificarCreditos(String titulo, int nuevoCredito) {
        String sql = "UPDATE cursos SET num_creditos = ? WHERE titulo = ?";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(2, titulo);
            pst.setInt(1, nuevoCredito);

            return pst.executeUpdate() > 0;
        } catch (SQLException sqle) {
            System.err.println("Error al modificar Creditos " + sqle.getMessage());
            return false;
        }
    }

    public int eliminarCursos (int id){
        String sql = "DELETE FROM cursos WHERE id_curso = ?";
        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate();
        } catch (SQLException sqle) {
            System.err.println("Error al borrar curso " + sqle.getMessage());
            return -1;
        }

    }

    public List<String> obtenerNombresColumnas() {
        List<String> columnas = new ArrayList<>();

        final String SQL = "SELECT * FROM cursos LIMIT 0";

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

    public List<Cursos> leerTodosLosCursos() {
        String sql = "SELECT * FROM cursos";
        List<Cursos> lista = new ArrayList<>();

        try (Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
//                lista.add(mapearCursos(rs));
                lista.add(mapearCursos(rs));
            }


        } catch (SQLException sqle) {
            System.err.println("Error en leer todos los cursos " + sqle.getMessage());

        }

        return lista;
    }

    private static Cursos mapearCursos(ResultSet rs) throws SQLException {
        //Utilizando el nombre de las columnas
//        return new Alumno(
//                rs.getInt("id"),
//                rs.getString("nombre"),
//                rs.getString("ciclo")
//        );

        //Utilizando el nº (índice) de las columnas
        return new Cursos(
                rs.getString(1),
                rs.getInt(2),
                rs.getInt(3),
                rs.getString(4),
                rs.getInt(5)
        );

    }

}
