package data.dao;


import model.Alumno;

import java.util.List;

public interface AlumnoDAO {

    boolean crearTabla();
    int insertarAlumno(Alumno alumno);
    List<Integer> insertarVariosAlumnosBatch(List<Alumno> alumnos);
    Integer cogerUltimoID();
    int obtenerUltimaClave();
    List<Alumno> leerTodosLosAlumnos();
    int eliminarAlumno(int id);
    boolean modificarAlumno(int id, String nuevoNombre, String nuevoCiclo);



}
