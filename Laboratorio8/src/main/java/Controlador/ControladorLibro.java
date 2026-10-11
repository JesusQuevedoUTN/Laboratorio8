package Controlador;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.hibernate.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class ControladorLibro {
    private final Session session;

    public ControladorLibro(Session session) {
        this.session = session;
    }
    
    public void menuLibro(){
        Scanner teclado = new Scanner(System.in);
        int menu = -1;
        
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
                     case 3 -> buscarLibro("123");
                     case 4 -> modificarLibro("123");
                     case 5 -> eliminarLibro("123");
                 }
                
            }catch (InputMismatchException e) {
                System.out.println("Error: Tipo de dato incorrecto (Ingresó letras o formato numérico inválido).");
            }
            teclado.nextLine();
        } while(menu != 7);
    }
    
    public void agregarLibro(){
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("Ingres ISBN: ");
        String isbn = teclado.nextLine();
        System.out.println("Ingrese titulo: ");
        String titulo = teclado.nextLine();
        System.out.println("Ingrese autor: ");
        String autor = teclado.nextLine();
        System.out.println("Ingrese editorial: ");
        String editorial = teclado.nextLine();
        System.out.println("Ingrese anio de publicacion: ");
        int anio = Integer.parseInt(teclado.nextLine());
        
        String sql = "INSERT INTO LIBRO (isbn, titulo, autor, editorial, anio) VALUES (?, ?, ?, ?, ?)";
        
         try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tu_base", "root", "tu_contraseña"); //Cambiar tu_base por la base de datos y la contraseña
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
        
        String dbUrl = "jdbc:mysql://localhost:3306/tu_base";
        String usuario = "root";
        String contrasenia = "contrasenia";
        
        try{
            connection = DriverManager.getConnection(dbUrl,usuario,contrasenia);//Remplazar DriverManager con la clase de conexion
            
            statment = connection.createStatement();
            
            String sql = "SELECT ISBN, titulo, autor, editorial, anio_publicacion FROM LIBRO";
            
            resultSet = statment.executeQuery(sql);
            
            while(resultSet.next()){
            String isbn = resultSet.getString("isbn");
            String titulo = resultSet.getString("titulo");
            String autor = resultSet.getString("autor");
            String editorial = resultSet.getString("editorial");
            int anio = resultSet.getInt("anio");
            }
        }catch (SQLException e) {
        System.out.println("Error al agregar el libro: " + e.getMessage());
    }   
    }
    
    public void buscarLibro(String ISBN){
        
    }
    
    public void modificarLibro(String ISBN){
        
    }
    
    public void eliminarLibro(String ISBN){
        
    }
    
}
