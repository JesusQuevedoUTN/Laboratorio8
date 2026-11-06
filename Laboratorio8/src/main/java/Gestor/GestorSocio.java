package Gestor;

import herramientas.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class GestorSocio {
    
    // 4.1. Agregar socio
    public void agregarSocio(String nombre, String dni, String telefono, LocalDate fechaAlta) {
        String sql 
                = "INSERT INTO SOCIO (nombre, DNI, telefono, fecha_alta) \n"
                + "VALUES (?, ?, ?, ?)";

        // try-with-resources cierra el PreparedStatement y la conexión automáticamente
        try (Connection con = Conexion.getConexion(); PreparedStatement instruccion = con.prepareStatement(sql)) {

            instruccion.setString(1, nombre);
            instruccion.setString(2, dni);
            instruccion.setString(3, telefono);
            instruccion.setDate(4, java.sql.Date.valueOf(fechaAlta)); // Formato esperado por SQL: "YYYY-MM-DD"

            instruccion.executeUpdate();
            System.out.println("Socio agregado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al agregar socio (¿DNI duplicado?): " + e.getMessage());
        }
    }

    // 4.2. Listar socios
    public void listarSocios() {
        String sql
                = "SELECT id_socio, nombre, DNI, telefono FROM SOCIO \n"
                + "ORDER BY id_socio ASC";

        try (Connection con = Conexion.getConexion(); PreparedStatement instruccion = con.prepareStatement(sql); ResultSet consulta = instruccion.executeQuery()) {

            System.out.printf(
                    "%-5s %-25s %-15s %-15s%n",
                    "ID", "NOMBRE", "DNI", "TELÉFONO");
            System.out.println(
                    "---------------------------------------------------------");

            while (consulta.next()) {
                int id = consulta.getInt("id_socio");
                String nombre = consulta.getString("nombre");
                String dni = consulta.getString("DNI");
                String telefono = consulta.getString("telefono");

                System.out.printf(
                        "%-5d %-25s %-15s %-15s\n",
                        id, nombre, dni, telefono);
            }
            System.out.println();

        } catch (SQLException e) {
            System.out.println("Error al listar socios: " + e.getMessage());
        }
    }

    // 4.3. Buscar socio por DNI
    public void buscarSocioPorDni(String dniBuscado) {
        String sql 
                = "SELECT id_socio, nombre, DNI, telefono, fecha_alta FROM SOCIO \n"
                + "WHERE DNI = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement instruccion = con.prepareStatement(sql)) {

            instruccion.setString(1, dniBuscado);

            try (ResultSet consulta = instruccion.executeQuery()) {
                if (consulta.next()) {
                    int id = consulta.getInt("id_socio");
                    String nombre = consulta.getString("nombre");
                    String dni = consulta.getString("DNI");
                    String telefono = consulta.getString("telefono");
                    String fechaAlta = consulta.getString("fecha_alta");

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
        String sql 
                = "UPDATE SOCIO SET nombre = ?, telefono = ? \n"
                + "WHERE DNI = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement instruccion = con.prepareStatement(sql)) {

            instruccion.setString(1, nuevoNombre);
            instruccion.setString(2, nuevoTelefono);
            instruccion.setString(3, dni);

            int filas = instruccion.executeUpdate();
            if (filas > 0) {
                System.out.println("¡Socio modificado correctamente!");
            } else {
                System.out.println("No se encontró un socio con el DNI: " + dni);
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar socio: " + e.getMessage());
        }
    }

    
    //VERIFICAR QUE ESTE BIEN
    
    // 4.5. Eliminar socio (Validando préstamos)
    public void eliminarSocio(int dniSocio) {
        // Validación obligatoria: verificar si tiene préstamos activos
        String sqlCheck 
                = "SELECT COUNT(*) AS total FROM PRESTAMO \n"
                + "WHERE DNI = ?";
        
        String sqlDelete 
                = "DELETE FROM SOCIO \n"
                + "WHERE DNI = ?";

        try (Connection con = Conexion.getConexion()) {

            // 1. Verificamos los préstamos
            try (PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {
                psCheck.setInt(1, dniSocio);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next() && rs.getInt("total") > 0) {
                        System.out.println("Error: No se puede eliminar el socio porque tiene préstamos asociados.");
                        return; // Cortamos acá
                    }
                }
            }

            // 2. Si no tiene préstamos, lo eliminamos
            try (PreparedStatement psDelete = con.prepareStatement(sqlDelete)) {
                psDelete.setInt(1, dniSocio);
                int filas = psDelete.executeUpdate();
                if (filas > 0) {
                    System.out.println("¡Socio eliminado exitosamente!");
                } else {
                    System.out.println("No se encontró un socio con el DNI: " + dniSocio);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar socio: " + e.getMessage());
        }
    }
}
