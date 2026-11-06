package Gestor;

import herramientas.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorLibro {

    public void agregarLibro(String isbn, String titulo, String autor, String editorial, int anio) {
        String sql
                = "INSERT INTO LIBRO (ISBN, titulo, autor, editorial, anio_publicacion) \n"
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion(); PreparedStatement instruccion = con.prepareStatement(sql)) {
            instruccion.setString(1, isbn);
            instruccion.setString(2, titulo);
            instruccion.setString(3, autor);
            instruccion.setString(4, editorial);
            instruccion.setInt(5, anio);

            instruccion.executeUpdate();
            System.out.println("Libro agregado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al agregar el libro: " + e.getMessage());
        }
    }

    public void listarLibros() {
        String sql
                = "SELECT ISBN, titulo, autor, editorial, anio_publicacion\n"
                + "FROM LIBRO\n"
                + "ORDER BY ISBN ASC;";

        try (Connection con = Conexion.getConexion(); PreparedStatement instruccion = con.prepareStatement(sql); ResultSet consulta = instruccion.executeQuery()) {
            System.out.printf(
                    "%-20s %-30s %-25s %-25s %-10s%n",
                    "ISBN", "TÍTULO", "AUTOR", "EDITORIAL", "AÑO");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------");

            while (consulta.next()) {
                String isbn = consulta.getString("ISBN");
                String titulo = consulta.getString("titulo");
                String autor = consulta.getString("autor");
                String editorial = consulta.getString("editorial");
                int anio = consulta.getInt("anio_publicacion");

                System.out.printf(
                        "%-20s %-30s %-25s %-25s %-10d%n",
                        isbn, titulo, autor, editorial, anio);
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Error al listar libros: " + e.getMessage());
        }
    }

    public void buscarLibroPorISBN(String isbn) {
        String sql = "SELECT * FROM LIBRO WHERE ISBN = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement instruccion = con.prepareStatement(sql)) {
            instruccion.setString(1, isbn);
            try (ResultSet rs = instruccion.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\n--- Libro Encontrado ---");
                    System.out.println("ISBN: " + isbn);
                    System.out.println("Titulo: " + rs.getString("titulo"));
                    System.out.println("Autor: " + rs.getString("autor"));
                    System.out.println("Editorial: " + rs.getString("editorial"));
                    System.out.println("Anio de publicacion: " + rs.getInt("anio_publicacion"));
                    System.out.println("--------------------------\n");
                } else {
                    System.out.println("Libro no encontrado");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar libro: " + e.getMessage());
        }
    }

    public void modificarLibro(String isbn, String titulo, String autor, String editorial, int anio) {
        String sql
                = "UPDATE LIBRO SET titulo = ?, autor = ?, editorial = ?, anio_publicacion = ? \n"
                + "WHERE ISBN = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement instruccion = con.prepareStatement(sql)) {
            instruccion.setString(1, titulo);
            instruccion.setString(2, autor);
            instruccion.setString(3, editorial);
            instruccion.setInt(4, anio);
            instruccion.setString(5, isbn);

            int filas = instruccion.executeUpdate();
            if (filas > 0) {
                System.out.println("Libro modificado correctamente");
            } else {
                System.out.println("No existe un libro con ese ISBN");
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar libro: " + e.getMessage());
        }
    }

    public void eliminarLibro(String isbn) {
        String sql
                = "DELETE FROM LIBRO \n"
                + "WHERE ISBN = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement instruccion = con.prepareStatement(sql)) {
            instruccion.setString(1, isbn);
            int filas = instruccion.executeUpdate();
            if (filas > 0) {
                System.out.println("Libro eliminado correctamente.");
            } else {
                System.out.println("No existe un libro con ese ISBN.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar libro: " + e.getMessage());
        }
    }

}
