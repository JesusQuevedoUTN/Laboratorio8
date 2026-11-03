package Gestor;

import herramientas.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorEjemplar {

    /*public void agregarEjemplar() {
        Scanner teclado = new Scanner(System.in);
        int nro = 0;

        System.out.println("Ingres ISBN: ");
        String isbn = teclado.nextLine();
        if (isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío.");
        }

        boolean valido = false;
        while (!valido) {
            try {
                System.out.println("Ingrese numero de ejemplar: ");
                nro = Integer.parseInt(teclado.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: el numero de ejemplar debe ser un entero. Intente de nuevo.");
            }
        }

        String sql = "INSERT INTO ejemplar (ISBN, nro_ejemplar, estado) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, isbn);
            ps.setInt(2, nro);
            ps.setString(3, "Disponible");

            int filas = ps.executeUpdate();
            System.out.println("Ejemplar agregado. Filas insertadas: " + filas);

        } catch (SQLException e) {
            System.out.println("Error al agregar ejemplar: " + e.getMessage());
        }
    }
     */
    public void agregarEjemplar(String isbn, int nro, String estado) {
        String sql = "INSERT INTO ejemplar (ISBN, nro_ejemplar, estado) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, isbn);
            ps.setInt(2, nro);
            ps.setString(3, estado);

            int filas = ps.executeUpdate();
            System.out.println("Ejemplar agregado. Filas insertadas: " + filas);

        } catch (SQLException e) {
            System.out.println("Error al agregar ejemplar: " + e.getMessage());
        }
    }

    public void listarEjemplares(String ISBN) {
        String sql = "SELECT * FROM EJEMPLAR WHERE ISBN = ? ";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ISBN);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int nro_ejemplar = rs.getInt("nro_ejemplar");
                    String estado = rs.getString("estado");

                    System.out.println(" Numero: " + nro_ejemplar + " Estado: " + estado);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los ejemplares: " + e.getMessage());
        }
    }

    /*
    public void modificarEstado() {
        Scanner teclado = new Scanner(System.in);
        String isbn;
        int nro = 0;
        int estado = 0;

        System.out.println("Ingrese el ISBN de libro:");
        isbn = teclado.nextLine();

        boolean valido = false;
        while (!valido) {
            try {
                System.out.println("Ingrese numero de ejemplar: ");
                nro = Integer.parseInt(teclado.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: el numero de ejemplar debe ser un entero. Intente de nuevo.");
            }
        }
        System.out.println("Que estado quiere darle al ejemplar[1:Disponible|2:Prestado]: ");
        while (estado < 1 || estado > 2) {
            estado = Integer.parseInt(teclado.nextLine());
            if (estado < 1 || estado > 2) {
                System.out.println("Numero invalido ingrese otra vez: ");
            }
        }

        String sql = "UPDATE EJEMPLAR SET estado = ?" + "WHERE ISBN = ? AND nro_ejemplar = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(2, isbn);
            ps.setInt(3, nro);
            if (estado == 1) {
                ps.setString(1, "Disponible");
            } else {
                ps.setString(1, "Prestado");
            }

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Estado actualizado ");
            } else {
                System.out.println("No se pudo cambiar: el ejemplar no existe o no estaba ");
            }

        } catch (SQLException e) {
            System.out.println("Error al modificar el estado: " + e.getMessage());
        }

    }
     */
    public void modificarEstado(String isbn, int nro, String estado) {

        String sql = "UPDATE EJEMPLAR SET estado = ?" + "WHERE ISBN = ? AND nro_ejemplar = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, isbn);
            ps.setInt(2, nro);
            ps.setString(3, estado);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Estado actualizado ");
            } else {
                System.out.println("No se pudo cambiar: el ejemplar no existe o no estaba ");
            }

        } catch (SQLException e) {
            System.out.println("Error al modificar el estado: " + e.getMessage());
        }

    }

}
