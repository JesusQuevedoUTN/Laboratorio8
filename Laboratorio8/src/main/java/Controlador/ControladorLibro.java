package Controlador;

import Libro.Libro;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.hibernate.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Transaction;


public class ControladorLibro {
    private final Session session;

    public ControladorLibro(Session session) {
        this.session = session;
    }
    
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
        
         try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tu_base", "root", "Casa1407SQL"); //Cambiar tu_base por la base de datos y la contraseña
         PreparedStatement ps = con.prepareStatement(sql)) {

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
        Connection connection = null;
        Statement statment = null;
        ResultSet resultSet = null;
        
        String dbUrl = "jdbc:mysql://localhost:3306/tu_base";//cambiar el nombre de la base
        String usuario = "root";
        String contrasenia = "Casa1407SQL";//cambiar contraseña
        
        try{
            connection = DriverManager.getConnection(dbUrl,usuario,contrasenia);//Remplazar DriverManager con la clase de conexion
            
            statment = connection.createStatement();
            
            String sql = "SELECT ISBN, titulo, autor, editorial, anio_publicacion FROM LIBRO";
            
            resultSet = statment.executeQuery(sql);
            
            while(resultSet.next()){
            String isbn = resultSet.getString("ISBN");
            String titulo = resultSet.getString("titulo");
            String autor = resultSet.getString("autor");
            String editorial = resultSet.getString("editorial");
            int anio = resultSet.getInt("anio_publicacion");
            
            System.out.println("ISBN: " + isbn + " Titulo: " + titulo + " Autor: " + autor + " Editorial: " + editorial + " Anio de salida: " + anio);
            }
        }catch (SQLException e) {
        System.out.println("Error al agregar el libro: " + e.getMessage());
    }   
    }
    
    public void buscarLibroPorISBN(String isbn){
       String sql = "SELECT * FROM LIBRO WHERE ISBN = ?";
        
        try(Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tu_base", "root", "Casa1407SQL");
                PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, isbn);
            
            try(ResultSet rs = ps.executeQuery()){
             if(rs.next()){
            String titulo = rs.getString("titulo");
            String autor = rs.getString("autor");
            String editorial = rs.getString("editorial");
            int anio = rs.getInt("anio_publicacion");
            
                 System.out.println("\n--- ¡Socio Encontrado! ---");
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
      String sql = "UPDATE Libro SET titulo = ?, autor = ?, editorial = ?, anio_publicacion = ? WHERE isbn = ?";
      
      try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tu_base", "root", "Casa1407SQL");
         PreparedStatement ps = con.prepareStatement(sql)){
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
        Scanner teclado = new Scanner(System.in);
        Libro libro;
        libro = session.find(Libro.class, ISBN);
        if(libro != null){
            System.out.println(libro);
            session.beginTransaction();
            session.remove(libro);
            session.getTransaction().commit();
            System.out.println("Libro eliminado...");
        }else{
            System.out.println("No se pudo encontrar el libro");
        }
    }
    
}
