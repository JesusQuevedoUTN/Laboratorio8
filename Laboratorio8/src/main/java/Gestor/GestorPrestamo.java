package Gestor;

import herramientas.Conexion;
import herramientas.Estado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class GestorPrestamo {
    
    //
    //Revisar que esté bien
    //
    
    public void registrarPrestamo(int idSocio, String isbn, int nroEjemplar, LocalDate fechaP, LocalDate fechaPrev) {
        //
        //que carajo es esto juan? asjajsjsa
        //
        
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

        //Datos
        String sqlDatos
                = "SELECT fecha_devolucion_real, ISBN, nro_ejemplar FROM PRESTAMO \n"
                + "WHERE id_prestamo = ?";

        //Actualizar
        String sqlActualizarPrestamo
                = "UPDATE PRESTAMO SET fecha_devolucion_real = ? \n"
                + "WHERE id_prestamo = ?";
        String sqlActualizarEjemplar
                = "UPDATE EJEMPLAR SET estado = ? \n"
                + "WHERE ISBN = ? AND nro_ejemplar = ?";

        try (Connection con = Conexion.getConexion()) {
            String isbn;
            int nro;
            boolean sePuedeSeguir = true;

            // buscar el préstamo 
            try (PreparedStatement instruccionBusquedaDeDatos = con.prepareStatement(sqlDatos)) {
                instruccionBusquedaDeDatos.setInt(1, idPrestamo);

                // verificar que no esté devuelto
                try (ResultSet consultaDatos = instruccionBusquedaDeDatos.executeQuery()) {
                    if (!consultaDatos.next()) {
                        System.out.println("Error: ID de préstamo no encontrado.");
                        sePuedeSeguir = false;
                    }
                    if (consultaDatos.getDate("fecha_devolucion_real") != null && sePuedeSeguir) {
                        System.out.println("Aviso: Este prestamo ya fue devuelto previamente.");
                        sePuedeSeguir = false;
                    }

                    isbn = consultaDatos.getString("ISBN");
                    nro = consultaDatos.getInt("nro_ejemplar");
                }
            }

            if (sePuedeSeguir) {
                // Actualizar Datos
                try (PreparedStatement instruccionActualizarPrestamos = con.prepareStatement(sqlActualizarPrestamo); PreparedStatement instruccionActualizarEjemplar = con.prepareStatement(sqlActualizarEjemplar)) {
                    instruccionActualizarPrestamos.setDate(1, java.sql.Date.valueOf(fechaDev));
                    instruccionActualizarPrestamos.setInt(2, idPrestamo);
                    instruccionActualizarEjemplar.setString(1, Estado.Disponible.name());
                    instruccionActualizarEjemplar.setString(2, isbn);
                    instruccionActualizarEjemplar.setInt(3, nro);

                    // Ejecutamos
                    instruccionActualizarPrestamos.executeUpdate();
                    instruccionActualizarEjemplar.executeUpdate();

                    System.out.println("¡Devolucion exitosa! El ejemplar vuelve a estar disponible.");
                }

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
