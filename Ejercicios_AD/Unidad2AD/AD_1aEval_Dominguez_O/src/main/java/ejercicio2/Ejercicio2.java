package ejercicio2;

import java.sql.*;
import java.util.Scanner;

public class Ejercicio2 {

    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        Connection conexion = null;

        if (args.length < 5) {
            System.err.println("Se requiere ip puerto dbName user password");
            return;
        }

        //Extracción de credenciales
        String ip = args[0];
        int puerto = Integer.parseInt(args[1]);
        String dbName = args[2];
        String user = args[3];
        String password = args[4];

        String url = "jdbc:mysql://" + ip + ":" + puerto + "/" + dbName;

        // Conectamos...
        boolean conexionOK = DBConnectionManager
                .inicializarConexion(url, user, password);

        if (!conexionOK){
            System.err.println("Error. Fin del programa");
            return;
        }

        try {
            conexion = DBConnectionManager.getConexion();
            System.out.println("Conexión activa? " + !conexion.isClosed());
        } catch (SQLException sqle){
            System.err.println("Error al obtener la conexión" + sqle.getMessage());
        }

        //CRUD
        
        
     crearTablaMarcas(conexion);
        crearTablaVehiculos(conexion);

        insertarMarcas(conexion);
        insertarVehiculos(conexion);

        mostrarConsulta(conexion);
        modificarPrecios(conexion);
        mostrarConsulta(conexion);

        DBConnectionManager.cerrarConexion();



    }

    public static boolean crearTablaMarcas(Connection conexion) {
        String sql = """
                CREATE TABLE IF NOT EXISTS marcas (
                    codigo INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(25) NOT NULL
                );
                """;
        try (Statement st = conexion.createStatement()) {

            st.executeUpdate(sql);
            System.out.println("Tabla marcas creada correctamente");
            return true;

        } catch (SQLException sqle) {
            System.err.println("Error en crear tabla marcas " + sqle.getMessage());
            return false;
        }
    }

    public static boolean crearTablaVehiculos(Connection conexion) {
        String sql = """
                CREATE TABLE IF NOT EXISTS vehiculos (
                    codigo INT AUTO_INCREMENT PRIMARY KEY,
                    modelo VARCHAR(30) NOT NULL,
                    precio_dia DECIMAL NOT NULL,
                    codigo_marca INT,
                    FOREIGN KEY (codigo_marca) REFERENCES marcas(codigo)
                );
                """;
        try (Statement st = conexion.createStatement()) {

            st.executeUpdate(sql);
            System.out.println("Tabla vehiculos creada correctamente");
            return true;

        } catch (SQLException sqle) {
            System.err.println("Error en crear tabla " + sqle.getMessage());
            return false;
        }
    }
    public static void insertarMarcas(Connection conexion){
        marcas(conexion, "Toyota");
        marcas(conexion, "Kia");
        marcas(conexion, "Ford");
        marcas(conexion, "BMW");
        marcas(conexion, "Seat");
        System.out.println("Datos de marcas insertados correctamente");
    }
    public static void marcas(Connection conexion, String marca) {

        String sql = "INSERT INTO marcas (nombre) VALUES (?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, marca);
            int filas = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void insertarVehiculos(Connection conexion){
        vehiculos(conexion,"Yaris",90.0,1);
        vehiculos(conexion,"Corolla",120.0,1);
        vehiculos(conexion,"Focus",110.0,3);
        vehiculos(conexion,"Mustang",200.0,3);
        vehiculos(conexion,"Serie 1",150.0,4);
        vehiculos(conexion,"Serie 3",190.0,4);
        vehiculos(conexion,"Ibiza",80.0,5);
        vehiculos(conexion,"Leon",100.0,5);
        vehiculos(conexion,"Picanto",70.0,2);
        vehiculos(conexion,"Sportage",130.0,2);
        System.out.println("Datos de vehiculos insertados correctamente");
    }

    public static void vehiculos(Connection conexion, String modelo, Double precio, int codigo_marca) {

            String sql = "INSERT INTO vehiculos (modelo, precio_dia, codigo_marca) VALUES (?, ?, ?)";

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, modelo);
                ps.setDouble(2, precio);
                ps.setInt(3, codigo_marca);
                int filas = ps.executeUpdate();

            } catch (SQLException e) {
                System.err.println("Error al insertar vehiculos " + e.getMessage());
            }

    }

    public static void mostrarConsulta(Connection conexion) {

        System.out.println("\n--- LISTADO DE VEHICULOS ---\n");
        String sql = "SELECT v.codigo, v.modelo, v.precio_dia, m.nombre FROM vehiculos v, marcas m WHERE v.codigo_marca = m.codigo ORDER BY v.codigo";

        try (Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt(1) + " | "
                                + rs.getString(2) + " | "
                                + rs.getDouble(3) + "€ | "
                        + rs.getString(4)
                );
            }
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void modificarPrecios(Connection conexion) {


        System.out.print("Introduce un descuento (1-99): ");
        double descuento = (100.0 - sc.nextInt()) / 100.0;
        sc.nextLine();

        String sql = "UPDATE vehiculos SET precio_dia = (? * precio_dia) WHERE precio_dia >= 120";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setDouble(1, descuento);

            int filas = ps.executeUpdate();
            System.out.println("Registros modificados: " + filas);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
