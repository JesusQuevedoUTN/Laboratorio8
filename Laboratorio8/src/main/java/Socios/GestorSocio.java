package Socios;

import Main.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorSocio {
// 4.1. Agregar socio

    public void agregarSocio(String nombre, String dni, String telefono, String fechaAlta) {
        String sql = "INSERT INTO SOCIO (nombre, DNI, telefono, fecha_alta) VALUES (?, ?, ?, ?)";

        // try-with-resources cierra el PreparedStatement y la conexión automáticamente
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, dni);
            ps.setString(3, telefono);
            ps.setString(4, fechaAlta); // Formato esperado por SQL: "YYYY-MM-DD"

            ps.executeUpdate();
            System.out.println("¡Socio agregado exitosamente!");

        } catch (SQLException e) {
            System.out.println("Error al agregar socio (¿DNI duplicado?): " + e.getMessage());
        }
    }

    // 4.2. Listar socios
    public void listarSocios() {
        String sql = "SELECT id_socio, nombre, DNI, telefono FROM SOCIO";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            System.out.println("\nID\tNOMBRE\t\t\tDNI\t\tTELÉFONO");
            System.out.println("---------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id_socio");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("DNI");
                String telefono = rs.getString("telefono");

                System.out.println(id + "\t" + nombre + "\t\t" + dni + "\t" + telefono);
            }
            System.out.println();

        } catch (SQLException e) {
            System.out.println("Error al listar socios: " + e.getMessage());
        }
    }

    // 4.3. Buscar socio por DNI
    public void buscarSocioPorDni(String dniBuscado) {
        String sql = "SELECT id_socio, nombre, DNI, telefono, fecha_alta FROM SOCIO WHERE DNI = ?";

        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dniBuscado);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_socio");
                    String nombre = rs.getString("nombre");
                    String dni = rs.getString("DNI");
                    String telefono = rs.getString("telefono");
                    String fechaAlta = rs.getString("fecha_alta");

                    System.out.println("\n--- ¡Socio Encontrado! ---");
                    System.out.println("ID: " + id);
                    System.out.println("Nombre: " + nombre);
                    System.out.println("DNI: " + dni);
                    System.out.println("Teléfono: " + telefono);
                    System.out.println("Fecha de Alta: " + fechaAlta);
                    System.out.println("--------------------------\n");
                } else {
                    System.out.println("No se encontró un socio con el DNI: " + dniBuscado);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar socio: " + e.getMessage());
        }
    }

    // 4.4. Modificar socio (Solo Nombre y Teléfono)
    public void modificarSocio(String dni, String nuevoNombre, String nuevoTelefono) {
        String sql = "UPDATE SOCIO SET nombre = ?, telefono = ? WHERE DNI = ?";

        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoNombre);
            ps.setString(2, nuevoTelefono);
            ps.setString(3, dni);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("¡Socio modificado correctamente!");
            } else {
                System.out.println("No se encontró un socio con el DNI: " + dni);
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar socio: " + e.getMessage());
        }
    }

    // 4.5. Eliminar socio (Validando préstamos)
    public void eliminarSocio(int idSocio) {
        // Validación obligatoria: verificar si tiene préstamos activos
        String sqlCheck = "SELECT COUNT(*) AS total FROM PRESTAMO WHERE id_socio = ?";
        String sqlDelete = "DELETE FROM SOCIO WHERE id_socio = ?";

        try (Connection con = Conexion.getConexion()) {
            
            // 1. Verificamos los préstamos
            try (PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {
                psCheck.setInt(1, idSocio);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next() && rs.getInt("total") > 0) {
                        System.out.println("Error: No se puede eliminar el socio porque tiene préstamos asociados.");
                        return; // Cortamos acá
                    }
                }
            }

            // 2. Si no tiene préstamos, lo eliminamos
            try (PreparedStatement psDelete = con.prepareStatement(sqlDelete)) {
                psDelete.setInt(1, idSocio);
                int filas = psDelete.executeUpdate();
                if (filas > 0) {
                    System.out.println("¡Socio eliminado exitosamente!");
                } else {
                    System.out.println("No se encontró un socio con el ID: " + idSocio);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar socio: " + e.getMessage());
        }
    }
}