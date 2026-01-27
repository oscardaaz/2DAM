package app;

import data.JPAUtil;
import data.dao.AlumnoDAOImplJPA;
import jakarta.persistence.*;
import model.Alumno;

import java.util.List;

public class MainJPA {

    public static void main(String[] args) {

        AlumnoDAOImplJPA alumnoDAO = new AlumnoDAOImplJPA();

//        int insertarOK = alumnoDAO.insertarAlumno(new Alumno("JorgeMetodo2","2DAM"));
//        System.out.println(insertarOK > 0 ? "Insertado ok, el id insertado es: " + insertarOK : "ID no encontrado al insertar o exception generada");

//        int borradoOK = alumnoDAO.eliminarAlumno(9);
//        System.out.println(borradoOK > 0 ? "Borrado OK" : "ID no encontrado al eliminar o exception generada");
//
//        boolean modificadoOK = alumnoDAO.modificarAlumno(12,"NuevoNombre","NuevoCiclo");
//        System.out.println(modificadoOK ? "Modificado OK el alumno con id" : "ID no encontrado al modificar o exception generada");

//        List<Alumno> listaAlumnos = alumnoDAO.leerTodosLosAlumnos();
//        if (!listaAlumnos.isEmpty()){
//            System.out.println("--- Lista de alumnos: ---");
//            for (Alumno a : listaAlumnos){
//                System.out.println(a);
//            }
//        }else {
//            System.out.println("No hay alumnos, la lista esta vacía");
//        }

//        Alumno alumno = alumnoDAO.mostrarAlumnoID(5);
//        System.out.println(alumno != null ? "Mostrado único correcto, alumno:\n"
//                + alumno : "ID no encontrado al buscar o exception generada");

        System.out.println("Listado de alumnos:");
        for (Alumno a : alumnoDAO.leerTodosLosAlumnos()){
            System.out.println(a);
        }

//        boolean modificado = alumnoDAO.modificarAlumnoProfe(8,"nuevo","nuevo");
//        System.out.println(modificado ? "Alumno modificado correctamente " : "No se pudo modificar el usuario");

        Boolean borrado = alumnoDAO.eliminarAlumnoProfe(12);
        System.out.println(borrado ? "Alumno borrado correctamente " : "No se pudo eliminar el usuario");



        System.out.println("Listado de alumnos:");
        for (Alumno a : alumnoDAO.leerTodosLosAlumnos()){
            System.out.println(a);
        }

        JPAUtil.close();



    }

}
