/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author delosrios
 */
public class DatabaseConnection {
    
    private static final String DB_NAME = "notaria";
    private static final String URL = "jdbc:mariadb://192.168.1.3:3306/" + DB_NAME;
    private static final String USER = "gdelosrios";
    private static final String PASSWORD = Vars.password;
    
    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource ds = null;
    
    public static void setUp() {
        final String SETUP_URL = "jdbc:mariadb://192.168.1.3:3306/";
        Connection conn = null;
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(SETUP_URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10);
        try(var ds = new HikariDataSource(config); ) {
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            var stmt = conn.createStatement();
            final String createDBQuery = "CREATE DATABASE IF NOT EXISTS " + DB_NAME + ";";
            stmt.executeUpdate(createDBQuery);
            final String useDBQuery = "USE " + DB_NAME + ";";
            stmt.executeUpdate(useDBQuery);
            final String createClienteTableQuery = "CREATE TABLE IF NOT EXISTS `cliente` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `esta_activo` tinyint(1) NOT NULL DEFAULT 1,\n" +
                "  `fecha_de_alta` char(10) NOT NULL DEFAULT cast(current_timestamp() as date),\n" +
                "  `nombre` varchar(60) DEFAULT NULL,\n" +
                "  `apellido` varchar(60) DEFAULT NULL,\n" +
                "  `dni` varchar(8) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `dni` (`dni`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci";
            stmt.executeUpdate(createClienteTableQuery);
            final String createPropiedadTableQuery = "CREATE TABLE IF NOT EXISTS `propiedad` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `esta_activo` tinyint(1) NOT NULL DEFAULT 1,\n" +
                        "  `fecha_de_alta` char(10) NOT NULL DEFAULT cast(current_timestamp() as date),\n" +
                        "  `ubicacion` varchar(255) DEFAULT NULL,\n" +
                        "  `superficie` double DEFAULT NULL,\n" +
                        "  `anios_de_antiguedad` int(11) DEFAULT NULL,\n" +
                        "  `duenio` int(11) DEFAULT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `duenio` (`duenio`),\n" +
                        "  CONSTRAINT `propiedad_ibfk_1` FOREIGN KEY (`duenio`) REFERENCES `cliente` (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;";
            stmt.executeUpdate(createPropiedadTableQuery);
            final String createEscrituraTableQuery = "CREATE TABLE IF NOT EXISTS `escritura` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `esta_activo` tinyint(1) NOT NULL DEFAULT 1,\n" +
                        "  `fecha_de_alta` char(10) NOT NULL DEFAULT cast(current_timestamp() as date),\n" +
                        "  `propiedad` int(11) NOT NULL,\n" +
                        "  `propietario` int(11) NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `propiedad` (`propiedad`),\n" +
                        "  KEY `propietario` (`propietario`),\n" +
                        "  CONSTRAINT `escritura_ibfk_1` FOREIGN KEY (`propiedad`) REFERENCES `propiedad` (`id`) ON DELETE CASCADE,\n" +
                        "  CONSTRAINT `escritura_ibfk_2` FOREIGN KEY (`propietario`) REFERENCES `cliente` (`id`) ON DELETE CASCADE\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;";
            stmt.executeUpdate(createEscrituraTableQuery);
            conn.commit();
        } catch(java.sql.SQLException e){
            try {
                if(conn != null) {
                    conn.rollback();
                }
                System.out.println(e.getMessage());
                System.out.println("Ha ocurrido un error al intentar incializar la base de datos");
            }catch(java.sql.SQLException rbEx) {
                System.out.println(rbEx.getMessage());
            }
        }


    }
    public static Connection getConnection() throws SQLException {
        setUp();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10);
        ds = new HikariDataSource(config);   
        if(URL == null || URL.isEmpty() || USER == null || USER.isEmpty() || PASSWORD == null || PASSWORD.isEmpty()) {
            throw new SQLException("Configuracion de la BD incompleta o invalida");
        }
        return ds.getConnection();
    } 
}
