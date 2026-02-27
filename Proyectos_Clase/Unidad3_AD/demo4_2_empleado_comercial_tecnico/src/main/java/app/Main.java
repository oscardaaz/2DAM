package app;

import data.ConexionBD;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        try (Connection conexion = ConexionBD.getConexion()) {
            System.out.println("------ Datos de todos los empleados -------\n");
            mostrarDatosEmpleados(conexion);
            System.out.println("\n------ Datos de los comerciales -------\n");
            mostrarDatosComerciales(conexion);
            System.out.println("\n------ Datos de los técnicos -------\n");
            mostrarDatosTecnicos(conexion);
        } catch (SQLException ex) {
            System.err.println("Error general de BD: " + ex.getMessage());
        } finally {
            // Si tu clase maneja otros recursos internos (pool, singleton, etc.)
            // mantenemos la llamada para asegurar el cierre "lógico" del helper.
            ConexionBD.desconectar();
        }
    }

    public static void mostrarDatosEmpleados(Connection conn) {
        String sql = "select * from empleados";
        // Try-with-resources para Statement y ResultSet
        try (Statement st = conn.createStatement();
             ResultSet rst = st.executeQuery(sql)) {
            ResultSetMetaData rstmd = rst.getMetaData();
            for (int i = 1; i <= rstmd.getColumnCount(); i++) {
                System.out.println(rstmd.getColumnName(i) + " - " +
                        rstmd.getColumnTypeName(i));
            }
            while (rst.next()) {
                int idEmpleado = rst.getInt(1);
                String nombre = rst.getString(2);
                String direccion = rst.getString(3);
                Array telef = rst.getArray(4);
                int idJefe = rst.getInt(5);
                System.out.println(String.format("%4s %-20s %-35s %4d",
                        idEmpleado, nombre, direccion, idJefe));
                if (telef != null) {
                    // El Array JDBC también conviene liberarlo explícitamente
                    try {
                        String[] telefonos = (String[]) telef.getArray();
                        int i = 1;
                        for (String telefono : telefonos) {
                            System.out.println("\tTelefono " + (i++) + " " + telefono);
                        }
                    } finally {
                        try {
                            telef.free();
                        } catch (SQLException ignore) {
                        }
                    }
                } else {
                    System.out.println("\tSin telefonos");
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error en mostrar Empleados: " + ex.getMessage());
        }
    }

    private static void mostrarDatosComerciales(Connection conexion) {
        String sql = "select * from comerciales";
        try (Statement st = conexion.createStatement();
             ResultSet rst = st.executeQuery(sql)) {
            ResultSetMetaData rstmd = rst.getMetaData();
            for (int i = 1; i <= rstmd.getColumnCount(); i++) {
                System.out.println(rstmd.getColumnName(i) + " - " +
                        rstmd.getColumnTypeName(i));
            }
            while (rst.next()) {
                int idEmpleado = rst.getInt(1);
                String nombre = rst.getString(2);
                String direccion = rst.getString(3);
                Array telef = rst.getArray(4);
                int idJefe = rst.getInt(5);
                double comision = rst.getDouble(6);
                String rol = rst.getString(7);
                System.out.println(String.format("%4s %-20s %-35s %4d %4.2f %-10s",
                        idEmpleado, nombre, direccion, idJefe, comision, rol));
                if (telef != null) {
                    try {
                        String[] telefonos = (String[]) telef.getArray();
                        int i = 1;
                        for (String telefono : telefonos) {
                            System.out.println("\tTelefono " + (i++) + " " + telefono);
                        }
                    } finally {
                        try {
                            telef.free();
                        } catch (SQLException ignore) {
                        }
                    }
                } else {
                    System.out.println("\tSin telefonos");
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error en mostrar Comerciales: " + ex.getMessage());
        }
    }

    private static void mostrarDatosTecnicos(Connection conexion) {
        String sql = "select * from tecnicos";
        try (Statement st = conexion.createStatement();
             ResultSet rst = st.executeQuery(sql)) {
            ResultSetMetaData rstmd = rst.getMetaData();
            for (int i = 1; i <= rstmd.getColumnCount(); i++) {
                System.out.println(rstmd.getColumnName(i) + " - " +
                        rstmd.getColumnTypeName(i));
            }
            while (rst.next()) {
                int idEmpleado = rst.getInt(1);
                String nombre = rst.getString(2);
                String direccion = rst.getString(3);
                Array telef = rst.getArray(4);
                int idJefe = rst.getInt(5);
                double variable = rst.getDouble(6);
                String zona = rst.getString(7);
                System.out.println(String.format("%4s %-20s %-35s %4d %4.2f %-10s",
                        idEmpleado, nombre, direccion, idJefe, variable, zona));
                if (telef != null) {
                    try {
                        String[] telefonos = (String[]) telef.getArray();
                        int i = 1;
                        for (String telefono : telefonos) {
                            System.out.println("\tTelefono " + (i++) + " " + telefono);
                        }
                    } finally {
                        try {
                            telef.free();
                        } catch (SQLException ignore) {
                        }
                    }
                } else {
                    System.out.println("\tSin telefonos");
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error en mostrar Tecnicos: " + ex.getMessage());
        }
    }
}