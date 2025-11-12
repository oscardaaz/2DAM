package data.dao;


import model.Alumno;

import java.util.List;

public interface AlumnoDAO {

    boolean crearTabla();
    boolean insertarAlumno(Alumno alumno);
    List<Alumno> leerTodosLosAlumnos();
    boolean eliminarAlumno(int id);
    boolean modificarAlumno(int id, String nuevoNombre, String nuevoCiclo);


}
