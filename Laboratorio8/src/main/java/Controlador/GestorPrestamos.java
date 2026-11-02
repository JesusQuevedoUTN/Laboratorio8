package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import Main.Conexion;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

public class GestorPrestamos {
    
    
    public void menuPrestamo(){
        Scanner teclado = new Scanner(System.in);
        int menu = -1;
        
        do{
            try{
                System.out.println("=== Libro Menu ===");
                System.out.println("1. Registrar prestamo ");
                System.out.println("2. Registrar devolucion ");
                System.out.println("3. Listar prestamos ");
                System.out.println("0. Salir");
                System.out.println("Opcion: ");
                menu = teclado.nextInt();
                
                if (menu < 0 || menu > 3) {
                    System.out.println("El numero ingresado es invalido");
                }
                
                 switch (menu){
                     case 1 -> registrarPrestamo();
                     case 2 -> registrarDevolucion();
                     case 3 -> listarPrestamos();
                 }
                
            }catch (InputMismatchException e) {
                System.out.println("Error: Tipo de dato incorrecto (Ingresó letras o formato numérico inválido).");
            }
            teclado.nextLine();
        } while(menu != 0);
    }
    
    public void registrarPrestamo(){
        Scanner teclado = new Scanner(System.in);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
    String sqlUs = "SELECT id_socio FROM SOCIO WHERE id_socio = ?";
    String sqlLi = "SELECT ISBN FROM LIBRO WHERE ISBN = ?";
    String sqlEj = "SELECT estado FROM EJEMPLAR WHERE ISBN = ? AND nro_ejemplar = ?";
    String sqlPr = "INSERT INTO PRESTAMO (id_socio, ISBN, nro_ejemplar, fecha_prestamo, fecha_devolucion_prevista) VALUES (?, ?, ?, ?, ?)";
    String sqlUp = "UPDATE EJEMPLAR SET estado = 'Prestado' WHERE ISBN = ? AND nro_ejemplar = ?";
        
try (Connection con = Conexion.getConexion()) {

        System.out.println("Ingrese el ID del usuario: ");
        int id = Integer.parseInt(teclado.nextLine());

        try (PreparedStatement psUs = con.prepareStatement(sqlUs)) {
            psUs.setInt(1, id);
            try (ResultSet rs = psUs.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("ID no encontrado");
                    return;
                }
            }
        }

        System.out.println("Ingrese el ISBN del libro: ");
        String isbn = teclado.nextLine();

        try (PreparedStatement psLi = con.prepareStatement(sqlLi)) {
            psLi.setString(1, isbn);
            try (ResultSet rs = psLi.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("ISBN no encontrado");
                    return;
                }
            }
        }

        System.out.println("Ingrese el numero del ejemplar: ");
        int nro = Integer.parseInt(teclado.nextLine());

        try (PreparedStatement psEj = con.prepareStatement(sqlEj)) {
            psEj.setString(1, isbn);
            psEj.setInt(2, nro);
            try (ResultSet rs = psEj.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Numero de ejemplar no encontrado");
                    return;
                }
                if (!rs.getString("estado").equals("Disponible")) {
                    System.out.println("Ejemplar no disponible");
                    return;
                }
            }
        }

        System.out.println("Ingrese la fecha del prestamo (dd/MM/yyyy): ");
        LocalDate fechaP = LocalDate.parse(teclado.nextLine(), formato);
        System.out.println("Ingrese la fecha limite del prestamo (dd/MM/yyyy): ");
        LocalDate fechaPrev = LocalDate.parse(teclado.nextLine(), formato);

        try (PreparedStatement psPr = con.prepareStatement(sqlPr);
             PreparedStatement psUp = con.prepareStatement(sqlUp)) {

            psPr.setInt(1, id);
            psPr.setString(2, isbn);
            psPr.setInt(3, nro);
            psPr.setDate(4, java.sql.Date.valueOf(fechaP));
            psPr.setDate(5, java.sql.Date.valueOf(fechaPrev));
            psPr.executeUpdate();

            psUp.setString(1, isbn);
            psUp.setInt(2, nro);
            psUp.executeUpdate();

            System.out.println("Prestamo registrado!!");
        }

    } catch (SQLException e) {
        System.out.println("Error al registrar prestamo: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.out.println("Error: debe ingresar un numero entero.");
    } catch (DateTimeParseException e) {
        System.out.println("Error: fecha invalida. Use el formato dd/MM/yyyy.");
    }
    }
  
    public void registrarDevolucion() {
    Scanner teclado = new Scanner(System.in);
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    String sql1 = "SELECT fecha_devolucion_real, ISBN, nro_ejemplar FROM PRESTAMO WHERE id_prestamo = ?";
    String sql2 = "UPDATE PRESTAMO SET fecha_devolucion_real = ? WHERE id_prestamo = ?";
    String sql3 = "UPDATE EJEMPLAR SET estado = ? WHERE ISBN = ? AND nro_ejemplar = ?";

    try (Connection con = Conexion.getConexion()) {

        System.out.println("Ingrese el ID del prestamo: ");
        int id = Integer.parseInt(teclado.nextLine());

        String isbn;
        int nro;

        // 1 y 2: buscar el préstamo y verificar que no esté devuelto
        try (PreparedStatement ps1 = con.prepareStatement(sql1)) {
            ps1.setInt(1, id);
            try (ResultSet rs = ps1.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("ID no encontrado");
                    return;
                }
                if (rs.getDate("fecha_devolucion_real") != null) {
                    System.out.println("Prestamo ya devuelto");
                    return;
                }
                isbn = rs.getString("ISBN");
                nro = rs.getInt("nro_ejemplar");
            }
        }

        // 3: pedir y registrar la fecha de devolución
        System.out.println("Ingrese la fecha de devolucion (dd/MM/yyyy): ");
        LocalDate fechaDev = LocalDate.parse(teclado.nextLine(), formato);

        try (PreparedStatement ps2 = con.prepareStatement(sql2);
             PreparedStatement ps3 = con.prepareStatement(sql3)) {

            ps2.setDate(1, java.sql.Date.valueOf(fechaDev));
            ps2.setInt(2, id);
            ps2.executeUpdate();

            // 4: dejar el ejemplar como Disponible
            ps3.setString(1, "Disponible");
            ps3.setString(2, isbn);
            ps3.setInt(3, nro);
            ps3.executeUpdate();

            System.out.println("Devolucion exitosa!!");
        }

    } catch (SQLException e) {
        System.out.println("Error al modificar prestamo: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.out.println("Error: el ID debe ser un numero entero.");
    } catch (DateTimeParseException e) {
        System.out.println("Error: fecha invalida. Use el formato dd/MM/yyyy.");
    }
}
    
    public void listarPrestamos(){
        
        String sql = "SELECT p.id_prestamo, s.nombre, l.titulo, p.fecha_prestamo "
               + "FROM PRESTAMO p "
               + "JOIN SOCIO s ON p.id_socio = s.id_socio "
               + "JOIN LIBRO l ON p.ISBN = l.ISBN";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        System.out.println("ID --- Usuario --- Libro --- Fecha de Prestamo");

        while (rs.next()) {
            int idP = rs.getInt("id_prestamo");
            String nom = rs.getString("nombre");
            String titu = rs.getString("titulo");
            LocalDate fechaP = rs.getDate("fecha_prestamo").toLocalDate();

            System.out.println(idP + " ||  " + nom + " ||  " + titu + " ||  " + fechaP);
        }
        
    } catch (SQLException e) {
        System.out.println("Error al listar prestamos: " + e.getMessage());
    }
    }
}
