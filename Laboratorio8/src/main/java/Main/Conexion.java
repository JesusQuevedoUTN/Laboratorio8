package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String DRIVER = "org.mariadb.jdbc.Driver";
    private static final String URL = "jdbc:mariadb://localhost:3306/biblioteca_db";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "Casa1407SQL"; 

    public static Connection getConexion() {
        Connection conexion = null;
        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conectado");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver JDBC de MariaDB -> " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de conexión a la Base de Datos -> " + e.getMessage());
        }
        return conexion;
    }
}
