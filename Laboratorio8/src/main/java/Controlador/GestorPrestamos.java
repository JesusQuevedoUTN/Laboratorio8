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
    
    //Revisar
    public void registrarPrestamo(){
        Scanner teclado = new Scanner(System.in);
        String estado;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        String sqlUs = "SELECT id_socio FROM SOCIO WHERE id_socio = ?";
        String sqlLi = "SELECT ISBN FROM LIBRO WHERE ISBN = ?";
        String sqlEj = "SELECT nro_ejemplar, estado FROM EJEMPLAR WHERE nro_ejemplar = ?";
        String sqlPr = "INSERT INTO PRESTAMO (id_socio, ISBN, nro_ejemplar, fecha_prestamo, fecha_devolucion_prevista) VALUES (?, ?, ?, ?, ?)";
        
        try(Connection con = Conexion.getConexion();PreparedStatement psUs = con.prepareStatement(sqlUs);PreparedStatement 
                psLi = con.prepareStatement(sqlLi);PreparedStatement psEj = con.prepareStatement(sqlEj);PreparedStatement psPr = con.prepareStatement(sqlPr)){
           
            System.out.println("Ingrese el ID del usuario: ");
            int id = Integer.parseInt(teclado.nextLine());
            try(ResultSet rsUs = psUs.executeQuery();ResultSet rsLi = psLi.executeQuery();ResultSet rsEj = psEj.executeQuery()){
                psUs.setInt(1, id);
                if(rsUs.next()){
                    System.out.println("Ingrese el ISBN del libro: ");
                    String isbn = teclado.nextLine();
                    psLi.setString(1, isbn);
                    if(rsLi.next()){
                        System.out.println("Ingrese el numero del ejemplar: ");
                        int nro = Integer.parseInt(teclado.nextLine());
                        psEj.setInt(1, nro);
                        if(rsEj.next()){
                            estado = rsEj.getString("estado");
                            if(estado == "Disponible"){
                                
                                System.out.println("Ingrese la fecha del prestamo: ");
                                LocalDate fechaP = LocalDate.parse(teclado.nextLine(), formato);
                                System.out.println("Ingrese la fecha limite del prestamo: ");
                                LocalDate fechaPrev = LocalDate.parse(teclado.nextLine(), formato);
                               
                                psPr.setInt(1, id);
                                psPr.setString(2, isbn);
                                psPr.setInt(3, nro);
                                psPr.setDate(4, java.sql.Date.valueOf(fechaP));
                                psPr.setDate(5, java.sql.Date.valueOf(fechaPrev));
                                
                                System.out.println("Prestamo registrado!!");
                            }else{
                                System.out.println("Ejemplar no disponible");
                            }
                        }else{
                            System.out.println("Numero no encontrado ");
                        }
                    }else{
                        System.out.println("ISBN no encontrado");
                    }
                }else{
                    System.out.println("ID no encontrado");
                }
                
            }
            
        }catch (SQLException e) {
            System.out.println("Error al registrar prestamo: " + e.getMessage());
        }
    }
    
    //Revisar
    public void registrarDevolucion(){
        Scanner teclado = new Scanner(System.in);
        LocalDate fechaDev;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String sql1 = "SELECT id_prestamo, fecha_devolucion_real, ISBN, nro_ejemplar FROM PRESTAMO WHERE id_prestamo = ?";
        String sql2 = "UPDATE PRESTAMO SET fecha_devolucion_real = ? WHERE id_prestamo = ?";
        String sql3 = "UPDATE EJEMPLAR SET estado = ?" + "WHERE ISBN = ? AND nro_ejemplar = ?";
        
        System.out.println("Ingrese el ID del prestamo: ");
        int id = Integer.parseInt(teclado.nextLine());
        
        try(Connection con = Conexion.getConexion();PreparedStatement ps1 = con.prepareStatement(sql1);PreparedStatement ps2 = con.prepareStatement(sql2);PreparedStatement ps3 = con.prepareStatement(sql3)){
            ps1.setInt(1, id);
            try(ResultSet rs = ps1.executeQuery()){
                if(rs.next()){
                fechaDev = rs.getDate("fecha_devolucion_real").toLocalDate();
                String isbn = rs.getString("ISBN");
                int nro = rs.getInt("nro_ejemplar");
                
                if(fechaDev != null){
                System.out.println("Prestamo ya devuelto");
            }else{
                System.out.println("Ingrese la fecha de devolucion: ");
                fechaDev = LocalDate.parse(teclado.nextLine(), formato);
                ps2.setDate(1, java.sql.Date.valueOf(fechaDev));
                ps2.setInt(2, id);
                
                ps3.setString(1, "Disponible");
                ps3.setString(2, isbn);
                ps3.setInt(3, nro);
            
                System.out.println("Devolucion exitosa!!");
            }
               }else{
                    System.out.println("ID no encontrada");
                }
            }
        }catch (SQLException e) {
            System.out.println("Error al modificar prestamo: " + e.getMessage());
        }
    }
    
    public void listarPrestamos(){
        
        String sql1 = "SELECT nombre FROM SOCIO WHERE id_socio = ?";
        String sql2 = "SELECT titulo FROM LIBRO WHERE ISBN =?";
        String sql3 = "SELECT id_prestamo, id_socio, ISBN, fecha_prestamo FROM PRESTAMO";
        
        try(Connection con = Conexion.getConexion(); PreparedStatement ps1 = con.prepareStatement(sql1);PreparedStatement ps2 = con.prepareStatement(sql2);PreparedStatement ps3 = con.prepareStatement(sql3)){
            try(ResultSet rs1 = ps1.executeQuery();ResultSet rs2 = ps2.executeQuery();ResultSet rs3 = ps3.executeQuery()){
                System.out.println("ID --- Usuario --- Libro --- Fecha de Presatamo ");
                while(rs3.next()){
                    int idP = rs3.getInt("id_prestamo");
                    LocalDate fechaP = rs3.getDate("fecha_prestamo").toLocalDate();
                    int idS = rs3.getInt("id_socio");
                    ps1.setInt(2, idS);
                    String isbn = rs3.getString("ISBN");
                    ps2.setString(2, isbn);
                    String nom = rs1.getString("nombre");
                    String titu = rs2.getString("titulo");
                    System.out.println(idP + "   " + nom + "   " + titu + "   " + fechaP);
                    
                }
            }
            
        }catch (SQLException e) {
        System.out.println("Error al listar prestamos: " + e.getMessage());
    }   
    }
}
