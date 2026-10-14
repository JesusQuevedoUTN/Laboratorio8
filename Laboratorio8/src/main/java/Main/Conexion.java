package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String DRIVER = "org.mariadb.jdbc.Driver";
    // Pasamos el usuario y contraseña directo en la URL para evitar conflictos con el driver
    private static final String URL = "jdbc:mariadb://localhost:3306/Biblioteca_DB?user=root&password=";

    // O si prefieres mantener separado usuario y password:
    // private static final String URL = "jdbc:mariadb://localhost:3306/Biblioteca_DB";
    // private static final String USUARIO = "root";
    // private static final String PASSWORD = "";
    public static Connection getConexion() {
        Connection conexion = null;
        try {
            Class.forName(DRIVER);
            // Si usas la URL con los parámetros user y password adentro, solo pasas la URL:
            conexion = DriverManager.getConnection(URL);

            // Si prefieres separarlos, descomenta las variables de arriba y usa:
            // conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("¡Conexión exitosa a la base de datos!");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver JDBC de MariaDB -> " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de conexión a la Base de Datos -> " + e.getMessage());
        }
        return conexion;
    }
}
