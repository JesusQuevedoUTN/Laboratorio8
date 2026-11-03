package Gestor;

import herramientas.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorLibro {

    public void agregarLibro(String isbn, String titulo, String autor, String editorial, int anio) {
        String sql = "INSERT INTO LIBRO (ISBN, titulo, autor, editorial, anio_publicacion) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, isbn);
            ps.setString(2, titulo);
            ps.setString(3, autor);
            ps.setString(4, editorial);
            ps.setInt(5, anio);

            int filas = ps.executeUpdate();
            System.out.println("Libro agregado. Filas insertadas: " + filas);
        } catch (SQLException e) {
            System.out.println("Error al agregar el libro: " + e.getMessage());
        }
    }

    public void listarLibros() {
        String sql = "SELECT ISBN, titulo, autor, editorial, anio_publicacion FROM LIBRO";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String isbn = rs.getString("ISBN");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String editorial = rs.getString("editorial");
                int anio = rs.getInt("anio_publicacion");

                System.out.println("ISBN: " + isbn + " Titulo: " + titulo + " Autor: " + autor + " Editorial: " + editorial + " Anio de salida: " + anio);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar libros: " + e.getMessage());
        }
    }

    public void buscarLibroPorISBN(String isbn) {
        String sql = "SELECT * FROM LIBRO WHERE ISBN = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\n--- ¡Libro Encontrado! ---");
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
        String sql = "UPDATE LIBRO SET titulo = ?, autor = ?, editorial = ?, anio_publicacion = ? WHERE ISBN = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, titulo);
            ps.setString(2, autor);
            ps.setString(3, editorial);
            ps.setInt(4, anio);
            ps.setString(5, isbn);

            int filas = ps.executeUpdate();
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
        String sql = "DELETE FROM LIBRO WHERE ISBN = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, isbn);
            int filas = ps.executeUpdate();
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
