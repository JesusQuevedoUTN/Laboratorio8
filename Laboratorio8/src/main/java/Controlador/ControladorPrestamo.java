package Controlador;

import herramientas.Conexion;
import herramientas.Estado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ControladorPrestamo {

    public void registrarPrestamo(int idSocio, String isbn, int nroEjemplar, LocalDate fechaPrestamo, LocalDate fechaLimite) {

        boolean sePuedeSeguir = true;

        //Conectar
        try (Connection con = Conexion.getConexion()) {

            // Buscar Socio
            String sqlSocio = "SELECT id_socio FROM SOCIO WHERE id_socio = ?";
            try (PreparedStatement buscarSocio = con.prepareStatement(sqlSocio)) {
                buscarSocio.setInt(1, idSocio);

                try (ResultSet consultaSocio = buscarSocio.executeQuery()) {
                    if (!consultaSocio.next()) {
                        System.out.println("Error: Socio no encontrado en la base de datos.");
                        sePuedeSeguir = false;
                    }
                }
            }

            if (sePuedeSeguir) {

                // Buscar Libro
                String sqlLibro = "SELECT ISBN FROM LIBRO WHERE ISBN = ?";
                try (PreparedStatement buscarLibro = con.prepareStatement(sqlLibro)) {
                    buscarLibro.setString(1, isbn);
                    try (ResultSet consultaLibro = buscarLibro.executeQuery()) {
                        if (!consultaLibro.next()) {
                            System.out.println("Error: ISBN no encontrado en la base de datos.");
                            sePuedeSeguir = false;
                        }
                    }
                }

                if (sePuedeSeguir) {
                    // Buscar el Ejemplar del Libro
                    String sqlEjemplar = "SELECT estado FROM EJEMPLAR WHERE ISBN = ? AND nro_ejemplar = ?";
                    try (PreparedStatement buscarEjemplar = con.prepareStatement(sqlEjemplar)) {
                        buscarEjemplar.setString(1, isbn);
                        buscarEjemplar.setInt(2, nroEjemplar);

                        // Revisar que esté disponible
                        try (ResultSet consultaEjemplar = buscarEjemplar.executeQuery()) {
                            if (!consultaEjemplar.next()) {
                                System.out.println("Error: Numero de ejemplar no encontrado.");
                                return;
                            }
                            if (!consultaEjemplar.getString("estado").equals(Estado.Disponible.name())) {
                                System.out.println("Error: El ejemplar no está disponible para préstamo.");
                                sePuedeSeguir = false;
                            }
                        }
                    }

                    
                    if (sePuedeSeguir) {
                        // Esta todo bien, registrar Préstamo
                        String sqlPrestamo = "INSERT INTO PRESTAMO (id_socio, ISBN, nro_ejemplar, fecha_prestamo, fecha_devolucion_prevista) VALUES (?, ?, ?, ?, ?)";
                        String sqlActualizar = "UPDATE EJEMPLAR SET estado = 'Prestado' WHERE ISBN = ? AND nro_ejemplar = ?";

                        // Se desactiva el commit automático para que si falla un paso, no lo registre
                        con.setAutoCommit(false);

                        try (PreparedStatement registrarPrestamo = con.prepareStatement(sqlPrestamo); PreparedStatement actualizarEjemplar = con.prepareStatement(sqlActualizar)) {

                            registrarPrestamo.setInt(1, idSocio);
                            registrarPrestamo.setString(2, isbn);
                            registrarPrestamo.setInt(3, nroEjemplar);
                            registrarPrestamo.setDate(4, java.sql.Date.valueOf(fechaPrestamo));
                            registrarPrestamo.setDate(5, java.sql.Date.valueOf(fechaLimite));

                            actualizarEjemplar.setString(1, isbn);
                            actualizarEjemplar.setInt(2, nroEjemplar);

                            int filasPrestamo = registrarPrestamo.executeUpdate();
                            int filasEjemplar = actualizarEjemplar.executeUpdate();

                            if (filasPrestamo == 1 && filasEjemplar == 1) {
                                // Confirmar ambas operaciones y se manda la instrucción
                                con.commit();
                                System.out.println("¡Prestamo registrado con éxito!");
                            } else {
                                // Con rollback le decimos que no haga el commit
                                con.rollback();
                                System.out.println("Error: No se pudo completar el préstamo.");
                            }

                        } catch (SQLException e) {
                            con.rollback();
                            throw e;
                        }
                    }
                }
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
            String isbn = null;
            int nro = -1;
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
                    if (sePuedeSeguir && consultaDatos.getDate("fecha_devolucion_real") != null) {
                        System.out.println("Aviso: Este prestamo ya fue devuelto previamente.");
                        sePuedeSeguir = false;
                    }

                    if (sePuedeSeguir) {
                        isbn = consultaDatos.getString("ISBN");
                        nro = consultaDatos.getInt("nro_ejemplar");
                    }

                }

            }

            if (sePuedeSeguir) {

                // Desactivar el commit automático
                con.setAutoCommit(false);

                try (PreparedStatement instruccionActualizarPrestamos = con.prepareStatement(sqlActualizarPrestamo); PreparedStatement instruccionActualizarEjemplar = con.prepareStatement(sqlActualizarEjemplar)) {

                    instruccionActualizarPrestamos.setDate(1, java.sql.Date.valueOf(fechaDev));
                    instruccionActualizarPrestamos.setInt(2, idPrestamo);

                    instruccionActualizarEjemplar.setString(1, Estado.Disponible.name());
                    instruccionActualizarEjemplar.setString(2, isbn);
                    instruccionActualizarEjemplar.setInt(3, nro);

                    // Ejecutamos
                    int filasPrestamo = instruccionActualizarPrestamos.executeUpdate();
                    int filasEjemplar = instruccionActualizarEjemplar.executeUpdate();

                    if (filasPrestamo == 1 && filasEjemplar == 1) {
                        // Confirmar que ambas operaciones hayan sido un éxito
                        con.commit();
                        System.out.println("¡Devolucion exitosa! El ejemplar vuelve a estar disponible.");
                    } else {
                        // si no se borra
                        con.rollback();
                        System.out.println("Error: No se pudo completar la devolución.");
                    }
                } catch (SQLException e) {
                    con.rollback();
                    throw e;
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
