package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        // No hace falta ya (Desde Java 6)
        //Class.forName("com.mysql.cj.jdbc.Driver");

        Properties config = new Properties();

        //leemos el fichero de configuración
        try {
            config.load(new BufferedReader(new FileReader("src/main/java/org/example/dbConfig.properties")));
        } catch (IOException ioe) {
            System.err.println("Error I/O al leer el fichero de config " + ioe.getMessage());
        }

        String servidor = config.getProperty("server");
        String puerto = config.getProperty("port");
        String usuario = config.getProperty("user");
        String password = config.getProperty("password");
        String dbName = config.getProperty("dbName");
        String unicode = config.getProperty("useUnicode");
        String encoding = config.getProperty("characterEncoding");

        String urlConexion = String.format("jdbc:mysql://%s:%s/%s" +
                        "?user=%s" +
                        "&password=%s" +
                        "&useUnicode=%s" +
                        "&characterEncoding=%s",
                servidor, puerto, dbName, usuario, password, unicode, encoding);

//        Otra opción
//        String urlConexion = "jdbc:mysql://"
//                + servidor
//                + ":" + puerto
//                + "/" + dbName
//                + "?user=" + usuario
//                + "&password=" + password
//                + "&useUnicode=" + unicode
//                + "&characterEncoding=" + encoding;

//        String urlConexion = "jdbc:mysql://localhost:3306/prueba?" +
//                "user=root" +
//                "&password=admin" +
//                "&useUnicode=true" +
//                "&characterEncoding=UTF-8";

        try (Connection conexion = DriverManager.getConnection(urlConexion)) {
            System.out.println("Conexión establecida correctamente");
        } catch (SQLException sqle) {
            System.err.println("Error al conectar " + sqle.getMessage());
        }


    }
}
