package data.dao;


import model.Alumno;

import java.util.List;

public interface AlumnoDAO {

    boolean crearTabla();
    int insertarAlumno(Alumno alumno);
    List<Alumno> leerTodosLosAlumnos();
    int eliminarAlumno(int id);
    boolean modificarAlumno(int id, String nuevoNombre, String nuevoCiclo);
    List<String> obtenerNombresColumnas();

}
