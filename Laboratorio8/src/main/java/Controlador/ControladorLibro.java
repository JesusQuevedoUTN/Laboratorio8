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
import java.util.Scanner;


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
                               libro = buscarLibro(ISBN);
                               System.out.println(libro);}
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
        
        String sql = "INSERT INTO LIBRO (ISBN, titulo, autor, editorial, anio_publicacion) VALUES (?, ?, ?, ?, ?)";
        
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
        
        String dbUrl = "jdbc:mysql://localhost:3306/tu_base";//cambiar el nombre de la base
        String usuario = "root";
        String contrasenia = "contrasenia";//cambiar contraseña
        
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
            int anio = resultSet.getInt("anio");
            }
        }catch (SQLException e) {
        System.out.println("Error al agregar el libro: " + e.getMessage());
    }   
    }
    
    public Libro buscarLibro(String ISBN){
        Libro libro;
        libro = session.find(Libro.class, ISBN);
        if(libro != null){
          return libro;
        }else{
            System.out.println();
            System.out.println("Libro no encontrado");
            return null;
        }
    }
    
    public void modificarLibro(String ISBN){
        Scanner teclado = new Scanner(System.in);
        Libro libro;
        libro = session.find(Libro.class, ISBN);
        if(libro != null){
        System.out.println("Ingrese el nuevo titulo: ");
        libro.setTitulo(teclado.nextLine());
        System.out.println("Ingrese el nuevo autor: ");
        libro.setAutor(teclado.nextLine());
        System.out.println("Ingrese la nuevo editorial: ");
        libro.setEditorial(teclado.nextLine());
        System.out.println("Ingrese el nuevo anio de publicacion: ");
        libro.setAniopublicacion(Integer.parseInt(teclado.nextLine()));
        session.beginTransaction();
        session.merge(libro);
        session.getTransaction().commit();
        System.out.println("Libro actualizado..");
        System.out.println();
        }else{
          System.out.println("No se pudo encontrar el libro");
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
