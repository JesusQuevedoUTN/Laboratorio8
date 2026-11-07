package Controlador;

import herramientas.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ControladorSocio {

    // 4.1. Agregar socio
    public void agregarSocio(String nombre, String dni, String telefono, LocalDate fechaAlta) {
        String sql
                = "INSERT INTO SOCIO (nombre, DNI, telefono, fecha_alta) \n"
                + "VALUES (?, ?, ?, ?)";

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

    // 4.5. Eliminar socio (Validando préstamos)
    public void eliminarSocio(int idSocio) {
        // Validación obligatoria: verificar si tiene préstamos activos
        String sqlPrestamos
                = "SELECT COUNT(*) AS total FROM PRESTAMO \n"
                + "WHERE id_socio = ?";

        String sqlSocioABorrar
                = "DELETE FROM SOCIO \n"
                + "WHERE id_socio = ?";

        //Conexion
        try (Connection con = Conexion.getConexion()) {

            boolean sePuedeEliminar = true;

            // Consultamos la tabla de prestamos completa
            try (PreparedStatement instruccionPrestamos = con.prepareStatement(sqlPrestamos)) {
                instruccionPrestamos.setInt(1, idSocio);
                
                // Verificamos si tiene préstamos                
                try (ResultSet consulta = instruccionPrestamos.executeQuery()) {
                    if (consulta.next() && consulta.getInt("total") > 0) {
                        System.out.println("Error: No se puede eliminar el socio porque tiene préstamos asociados.");
                        sePuedeEliminar = false;
                    }
                }
            }

            if (sePuedeEliminar) {
                try (PreparedStatement instruccionesSocioABorrar = con.prepareStatement(sqlSocioABorrar)) {
                    instruccionesSocioABorrar.setInt(1, idSocio);
                    int filas = instruccionesSocioABorrar.executeUpdate();

                    if (filas > 0) {
                        System.out.println("¡Socio eliminado exitosamente!");
                    } else {
                        System.out.println("No se encontró un socio con el DNI: " + idSocio);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar socio: " + e.getMessage());
        }
    }
}
