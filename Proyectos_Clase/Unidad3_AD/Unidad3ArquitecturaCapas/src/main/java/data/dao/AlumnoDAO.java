package data.dao;


import model.Alumno;

import java.util.List;

public interface AlumnoDAO {

    //boolean crearTabla();
    int insertarAlumno(Alumno alumno);
    List<Alumno> leerTodosLosAlumnos();
    Alumno mostrarAlumnoID(int id);
    int eliminarAlumno(int id);
    boolean eliminarAlumnoProfe(int id);
    boolean modificarAlumno(int id, String nuevoNombre, String nuevoCiclo);
    boolean modificarAlumnoProfe(int id, String nuevoNombre, String nuevoCiclo);

}
