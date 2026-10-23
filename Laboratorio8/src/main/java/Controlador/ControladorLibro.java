package Controlador;

import Libro.Libro;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;
import Main.Conexion;


public class ControladorLibro {

    
    public void menuLibro(){
        Scanner teclado = new Scanner(System.in);
        int menu = -1;
        Libro libro;
        String ISBN = null;
        
        do{
            try{
                System.out.println("=== Libro Menu ===");
                System.out.println("1. Agregar libro ");
                System.out.println("2. Listar libros ");
                System.out.println("3. Buscar libro ");
                System.out.println("4. Modificar libro ");
                System.out.println("5. Eliminar libro ");
                System.out.println("0. Salir");
                System.out.println("Opcion: ");
                menu = teclado.nextInt();
                
                if (menu < 0 || menu > 6) {
                    System.out.println("El numero ingresado es invalido");
                }
                
                 switch (menu){
                     case 1 -> agregarLibro();
                     case 2 -> listarLibros();
                     case 3 ->{ System.out.println("Ingrese el ISBN de libro:");
                               ISBN = teclado.nextLine();
                               buscarLibroPorISBN(ISBN);
                               }
                     case 4 -> {System.out.println("Ingrese el ISBN de libro:");
                               ISBN = teclado.nextLine();
                         modificarLibro(ISBN);}
                     case 5 -> {System.out.println("Ingrese el ISBN de libro:");
                               ISBN = teclado.nextLine();
                         eliminarLibro(ISBN);}
                 }
                
            }catch (InputMismatchException e) {
                System.out.println("Error: Tipo de dato incorrecto (Ingresó letras o formato numérico inválido).");
            }
            teclado.nextLine();
        } while(menu != 0);
    }
    
    public void agregarLibro(){
        Scanner teclado = new Scanner(System.in);
        int anio = 0;
        
        System.out.println("Ingres ISBN: ");
        String isbn = teclado.nextLine();
        if (isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío.");
        }
        System.out.println("Ingrese titulo: ");
        String titulo = teclado.nextLine();
        if (titulo == null || titulo.isEmpty()) {
            throw new IllegalArgumentException("El titulo no puede estar vacío.");
        }
        System.out.println("Ingrese autor: ");
        String autor = teclado.nextLine();
        if (autor == null || autor.isEmpty()) {
            throw new IllegalArgumentException("El autor no puede estar vacío.");
        }
        System.out.println("Ingrese editorial: ");
        String editorial = teclado.nextLine();
        if (editorial == null || editorial.isEmpty()) {
            throw new IllegalArgumentException("La editorial no puede estar vacío.");
        }
        try{
        System.out.println("Ingrese anio de publicacion: ");
        anio = Integer.parseInt(teclado.nextLine());
        }catch(NumberFormatException e){
         System.out.println("Error: el año debe ser un número entero. Intente de nuevo.");
        }
        
        String sql = "INSERT INTO LIBRO (ISBN, titulo, autor, editorial, anio_publicacion) VALUES (?, ?, ?, ?, ?)";
        
         try (Connection con = Conexion.getConexion();PreparedStatement ps = con.prepareStatement(sql)) {

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
    public void listarLibros(){
        String sql = "SELECT ISBN, titulo, autor, editorial, anio_publicacion FROM LIBRO";
        
        try(Connection con = Conexion.getConexion();PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            
            while(rs.next()){
            String isbn = rs.getString("ISBN");
            String titulo = rs.getString("titulo");
            String autor = rs.getString("autor");
            String editorial = rs.getString("editorial");
            int anio = rs.getInt("anio_publicacion");
            
            System.out.println("ISBN: " + isbn + " Titulo: " + titulo + " Autor: " + autor + " Editorial: " + editorial + " Anio de salida: " + anio);
            }
        }catch (SQLException e) {
        System.out.println("Error al agregar el libro: " + e.getMessage());
    }   
    }
    
    public void buscarLibroPorISBN(String isbn){
       String sql = "SELECT * FROM LIBRO WHERE ISBN = ?";
        
        try(Connection con = Conexion.getConexion();PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, isbn);
            
            try(ResultSet rs = ps.executeQuery()){
             if(rs.next()){
            String titulo = rs.getString("titulo");
            String autor = rs.getString("autor");
            String editorial = rs.getString("editorial");
            int anio = rs.getInt("anio_publicacion");
            
                 System.out.println("\n--- ¡Libro Encontrado! ---");
                    System.out.println("ISBN: " + isbn);
                    System.out.println("Titulo: " + titulo);
                    System.out.println("Autor: " + autor);
                    System.out.println("Editorial: " + editorial);
                    System.out.println("Anio de oublicacion: " + anio);
                    System.out.println("--------------------------\n");
                }else{
                 System.out.println("Libro no encontrado");
             }
            }
            
        }catch (SQLException e) {
            System.out.println("Error al buscar libro: " + e.getMessage());
        }
    }
    
    public void modificarLibro(String ISBN){
      Scanner teclado = new Scanner(System.in);
      int anio = 0;
      System.out.println("Ingrese titulo: ");
        String titulo = teclado.nextLine();
        if (titulo == null || titulo.isEmpty()) {
            throw new IllegalArgumentException("El titulo no puede estar vacío.");
        }
        System.out.println("Ingrese autor: ");
        String autor = teclado.nextLine();
        if (autor == null || autor.isEmpty()) {
            throw new IllegalArgumentException("El autor no puede estar vacío.");
        }
        System.out.println("Ingrese editorial: ");
        String editorial = teclado.nextLine();
        if (editorial == null || editorial.isEmpty()) {
            throw new IllegalArgumentException("La editorial no puede estar vacío.");
        }
        try{
        System.out.println("Ingrese anio de publicacion: ");
         anio = Integer.parseInt(teclado.nextLine());
        }catch(NumberFormatException e){
         System.out.println("Error: el año debe ser un número entero. Intente de nuevo.");
        }
      String sql = "UPDATE LIBRO SET titulo = ?, autor = ?, editorial = ?, anio_publicacion = ? WHERE ISBN = ?";
      
      try (Connection con = Conexion.getConexion();PreparedStatement ps = con.prepareStatement(sql)){
        ps.setString(1, titulo);
        ps.setString(2, autor);
        ps.setString(3, editorial);
        ps.setInt(4, anio);
        ps.setString(5, ISBN);
        
        int filas = ps.executeUpdate();
        
        if(filas > 0){
            System.out.println("Libro modificado correctamente");
        }else{
            System.out.println("No existe uh libro con ese ISBN");
        }
      }catch (SQLException e) {
            System.out.println("Error al modificar libro: " + e.getMessage());
        }
    }
    
    public void eliminarLibro(String ISBN){
        String sql = "DELETE FROM LIBRO WHERE ISBN = ?";
        try (Connection con = Conexion.getConexion();PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, ISBN);  
            int filas = ps.executeUpdate();
        if (filas > 0) {
            System.out.println("Libro eliminado correctamente.");
        } else {
            System.out.println("No existe un libro con ese ISBN.");
        }
            
        }catch (SQLException e) {
            System.out.println("Error al eliminar libro: " + e.getMessage());
        }
    }
    
}
