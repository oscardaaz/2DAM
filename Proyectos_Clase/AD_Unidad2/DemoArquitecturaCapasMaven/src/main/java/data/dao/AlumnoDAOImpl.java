package data.dao;

import model.Alumno;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AlumnoDAOImpl implements AlumnoDAO{

    private Connection conexion;

    public AlumnoDAOImpl(Connection conexion) {

        if (conexion == null){
            throw new IllegalArgumentException("La conexión no puede ser nula");

        }
        this.conexion = conexion;
    }

    @Override
    public boolean crearTabla() {

        String sql = """
                CREATE TABLE IF NOT EXISTS alumno(
                    id INT PRIMARY KEY,
                    nombre VARCHAR(50),
                    ciclo VARCHAR(50)
                );
                """;

        try (Statement st = conexion.createStatement()) {

            st.execute(sql);
            System.out.println("Tabla alumno creada");
            return true;

        }catch (SQLException sqle) {
            System.err.println("Error en crear tabla " + sqle.getMessage());
            return false;
        }

    }

    @Override
    public boolean insertarAlumno(Alumno alumno) {
        return false;
    }

    @Override
    public List<Alumno> leerTodosLosAlumnos() {
        return List.of();
    }

    @Override
    public boolean eliminarAlumno(int id) {
        return false;
    }

    @Override
    public boolean modificarAlumno(int id, String nuevoNombre, String nuevoCiclo) {
        return false;
    }
}
