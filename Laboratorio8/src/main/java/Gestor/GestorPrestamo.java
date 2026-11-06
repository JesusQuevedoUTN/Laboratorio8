package Gestor;

import herramientas.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class GestorPrestamo {

    //Revisar que esté bien
    
    public void registrarPrestamo(int idSocio, String isbn, int nroEjemplar, LocalDate fechaP, LocalDate fechaPrev) {
        String sqlUs = "SELECT id_socio FROM SOCIO WHERE id_socio = ?";
        String sqlLi = "SELECT ISBN FROM LIBRO WHERE ISBN = ?";
        String sqlEj = "SELECT estado FROM EJEMPLAR WHERE ISBN = ? AND nro_ejemplar = ?";
        String sqlPr = "INSERT INTO PRESTAMO (id_socio, ISBN, nro_ejemplar, fecha_prestamo, fecha_devolucion_prevista) VALUES (?, ?, ?, ?, ?)";
        String sqlUp = "UPDATE EJEMPLAR SET estado = 'Prestado' WHERE ISBN = ? AND nro_ejemplar = ?";

        try (Connection con = Conexion.getConexion()) {

            // 1. Validar Usuario
            try (PreparedStatement psUs = con.prepareStatement(sqlUs)) {
                psUs.setInt(1, idSocio);
                try (ResultSet rs = psUs.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Error: ID de usuario no encontrado en la base de datos.");
                        return;
                    }
                }
            }

            // 2. Validar Libro
            try (PreparedStatement psLi = con.prepareStatement(sqlLi)) {
                psLi.setString(1, isbn);
                try (ResultSet rs = psLi.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Error: ISBN no encontrado en la base de datos.");
                        return;
                    }
                }
            }

            // 3. Validar Ejemplar y su estado
            try (PreparedStatement psEj = con.prepareStatement(sqlEj)) {
                psEj.setString(1, isbn);
                psEj.setInt(2, nroEjemplar);
                try (ResultSet rs = psEj.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Error: Numero de ejemplar no encontrado.");
                        return;
                    }
                    if (!rs.getString("estado").equals("Disponible")) {
                        System.out.println("Error: El ejemplar no está disponible para préstamo.");
                        return;
                    }
                }
            }

            // 4. Registrar Préstamo y Actualizar Ejemplar
            try (PreparedStatement psPr = con.prepareStatement(sqlPr); PreparedStatement psUp = con.prepareStatement(sqlUp)) {

                psPr.setInt(1, idSocio);
                psPr.setString(2, isbn);
                psPr.setInt(3, nroEjemplar);
                psPr.setDate(4, java.sql.Date.valueOf(fechaP));
                psPr.setDate(5, java.sql.Date.valueOf(fechaPrev));
                psPr.executeUpdate();

                psUp.setString(1, isbn);
                psUp.setInt(2, nroEjemplar);
                psUp.executeUpdate();

                System.out.println("¡Prestamo registrado con éxito!");
            }

        } catch (SQLException e) {
            System.out.println("Error de base de datos al registrar prestamo: " + e.getMessage());
        }
    }

    public void registrarDevolucion(int idPrestamo, LocalDate fechaDev) {
        String sql1 = "SELECT fecha_devolucion_real, ISBN, nro_ejemplar FROM PRESTAMO WHERE id_prestamo = ?";
        String sql2 = "UPDATE PRESTAMO SET fecha_devolucion_real = ? WHERE id_prestamo = ?";
        String sql3 = "UPDATE EJEMPLAR SET estado = ? WHERE ISBN = ? AND nro_ejemplar = ?";

        try (Connection con = Conexion.getConexion()) {
            String isbn;
            int nro;

            // 1 y 2: buscar el préstamo y verificar que no esté devuelto
            try (PreparedStatement ps1 = con.prepareStatement(sql1)) {
                ps1.setInt(1, idPrestamo);
                try (ResultSet rs = ps1.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Error: ID de préstamo no encontrado.");
                        return;
                    }
                    if (rs.getDate("fecha_devolucion_real") != null) {
                        System.out.println("Aviso: Este prestamo ya fue devuelto previamente.");
                        return;
                    }
                    isbn = rs.getString("ISBN");
                    nro = rs.getInt("nro_ejemplar");
                }
            }

            // 3: registrar la fecha de devolución y dejar el ejemplar como Disponible
            try (PreparedStatement ps2 = con.prepareStatement(sql2); PreparedStatement ps3 = con.prepareStatement(sql3)) {

                ps2.setDate(1, java.sql.Date.valueOf(fechaDev));
                ps2.setInt(2, idPrestamo);
                ps2.executeUpdate();

                ps3.setString(1, "Disponible");
                ps3.setString(2, isbn);
                ps3.setInt(3, nro);
                ps3.executeUpdate();

                System.out.println("¡Devolucion exitosa! El ejemplar vuelve a estar disponible.");
            }

        } catch (SQLException e) {
            System.out.println("Error de base de datos al modificar prestamo: " + e.getMessage());
        }
    }

    public void listarPrestamos() {

        String sql 
                = "SELECT p.id_prestamo, s.nombre, l.titulo, p.fecha_prestamo "
                + "FROM PRESTAMO p "
                + "JOIN SOCIO s ON p.id_socio = s.id_socio "
                + "JOIN LIBRO l ON p.ISBN = l.ISBN";

        try (Connection con = Conexion.getConexion(); PreparedStatement instruccion = con.prepareStatement(sql); ResultSet consulta = instruccion.executeQuery()) {

            System.out.printf(
                "%-8s %-25s %-35s %-15s%n",
                "ID", "SOCIO", "LIBRO", "FECHA");
        System.out.println("-------------------------------------------------------------------------------------");

        while (consulta.next()) {
            int idP = consulta.getInt("id_prestamo");
            String nom = consulta.getString("nombre");
            String titu = consulta.getString("titulo");
            LocalDate fechaP = consulta.getDate("fecha_prestamo").toLocalDate();

            System.out.printf(
                    "%-8d %-25s %-35s %-15s%n",
                    idP, nom, titu, fechaP);
        }
        System.out.println();

        } catch (SQLException e) {
            System.out.println("Error al listar prestamos: " + e.getMessage());
        }
    }
}
