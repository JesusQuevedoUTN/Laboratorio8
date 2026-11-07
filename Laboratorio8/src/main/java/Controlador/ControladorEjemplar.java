package Controlador;

import herramientas.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorEjemplar {

    public void agregarEjemplar(String isbn, int nro, String estado) {
        String sql
                = "INSERT INTO EJEMPLAR (ISBN, nro_ejemplar, estado) \n"
                + "VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConexion(); PreparedStatement instruccion = con.prepareStatement(sql)) {

            instruccion.setString(1, isbn);
            instruccion.setInt(2, nro);
            instruccion.setString(3, estado);

            instruccion.executeUpdate();
            System.out.println("Ejemplar agregado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al agregar ejemplar: " + e.getMessage());
        }
    }

    public void listarEjemplares(String ISBN) {
        String sql
                = "SELECT * FROM EJEMPLAR \n"
                + "WHERE ISBN = ? ";

        try (Connection con = Conexion.getConexion(); PreparedStatement instruccion = con.prepareStatement(sql)) {
            instruccion.setString(1, ISBN);

            try (ResultSet consulta = instruccion.executeQuery()) {
                System.out.printf(
                        "%-15s %-20s%n",
                        "N° EJEMPLAR", "ESTADO");
                System.out.println("-----------------------------------");

                while (consulta.next()) {
                    int nro_ejemplar = consulta.getInt("nro_ejemplar");
                    String estado = consulta.getString("estado");

                    System.out.printf(
                            "%-15d %-20s%n",
                            nro_ejemplar, estado);
                }
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los ejemplares: " + e.getMessage());
        }
    }

    public void modificarEstado(String isbn, int nro, String estado) {

        String sql
                = "UPDATE EJEMPLAR SET estado = ?"
                + "WHERE ISBN = ? AND nro_ejemplar = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement instruccion = con.prepareStatement(sql)) {

            instruccion.setString(1, estado);
            instruccion.setString(2, isbn);
            instruccion.setInt(3, nro);

            int filas = instruccion.executeUpdate();

            if (filas > 0) {
                System.out.println("Estado actualizado");
            } else {
                System.out.println("No se pudo cambiar: el ejemplar no existe o no estaba ");
            }

        } catch (SQLException e) {
            System.out.println("Error al modificar el estado: " + e.getMessage());
        }

    }

}
