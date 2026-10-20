package Controlador;
import Libro.Ejemplar;
import Libro.Estado;
import Libro.Libro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ControladorEjemplar {
    private final Session session;

    public ControladorEjemplar(Session session) {
        this.session = session;
    }
    
    public void menu(){
        Scanner teclado = new Scanner(System.in);
        int menu = -1;
        String ISBN = null;
        int nro = 0;
        Ejemplar ejemplar;
        
         do{
            try{
                System.out.println("=== Libro Menu ===");
                System.out.println("1. Agregar ejemplar ");
                System.out.println("2. Listar ejemplares de un libro ");
                System.out.println("3. Modificar estado ");
                System.out.println("0. Salir");
                System.out.println("Opcion: ");
                menu = teclado.nextInt();
                
                if (menu < 0 || menu > 3) {
                    System.out.println("El numero ingresado es invalido");
                }
                
                 switch (menu){
                     case 1 -> agregarEjemplar();
                     case 2 -> listarEjemplares();
                     case 3 -> {System.out.println("Ingrese el ISBN de libro:");
                                ISBN = teclado.nextLine();
                                System.out.println("Ingrese el nro de ejmplar");
                                nro = Integer.parseInt(teclado.nextLine());
                                modificarEstado(ISBN, nro);}
                 }
                
            }catch (InputMismatchException e) {
                System.out.println("Error: Tipo de dato incorrecto (Ingresó letras o formato numérico inválido).");
            }
            teclado.nextLine();
        } while(menu != 0);
    }
    
    public void agregarEjemplar(){
        Scanner teclado = new Scanner(System.in);
        String isbn;
        int nro = 0;
        Libro libro;
        
        System.out.println("Ingrese el ISBN del libro: ");
        isbn = teclado.nextLine();
        
        libro = session.find(Libro.class, isbn);
        
        if(libro != null){
            try{
            System.out.println("Ingrese el numero de ejemplar: ");
            nro = Integer.parseInt(teclado.nextLine());
            }catch(NumberFormatException e){
                System.out.println("El numero tiene que ser entero. Intente de nuevo");
            }
        
            String sql = "INSERT INTO EJEMPLAR (ISBN, nro_ejemplar, estado) VALUES (?, ?, ?,)";
        
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tu_base", "root", "tu_contraseña"); //Cambiar tu_base por la base de datos y la contraseña, y cambiar DriverManager con la clase de conexcion
         PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, isbn);
            ps.setInt(2, nro);
            ps.setString(3, "Disponible");
            
            int filas = ps.executeUpdate();
            System.out.println("Ejemplar agregado. Filas insertadas: " + filas);
            
        }catch (SQLException e) {
        System.out.println("Error al agregar el ejemplar: " + e.getMessage());
    }
        }else{
            System.out.println("El ISBN no se conecta a ningun libro");
        }
    }
    
    public void listarEjemplares(){
        Connection connection = null;
        Statement statment = null;
        ResultSet resultSet = null;
        
        String dbUrl = "jdbc:mysql://localhost:3306/tu_base";//cambiar el nombre de la base
        String usuario = "root";
        String contrasenia = "contrasenia";
        
        try{
            connection = DriverManager.getConnection(dbUrl,usuario,contrasenia);//Remplazar DriverManager con la clase de conexion
            
            statment = connection.createStatement();
            
            String sql = "SELECT ISBN, nro_ejemplar, estado FROM EJEMPLAR";
            
            resultSet = statment.executeQuery(sql);
            
            while(resultSet.next()){
            String isbn = resultSet.getString("ISBN");
            int nro_ejemplar = resultSet.getInt("nro_ejemplar");
            String estado = resultSet.getString("estado");
            
            System.out.println("ISBN: " + isbn + " Numero: " + nro_ejemplar + " Estado: " + estado);
            }
        }catch (SQLException e) {
        System.out.println("Error al agregar el libro: " + e.getMessage());
    }   
    }
    
    public void modificarEstado(String isbn, int nro){
        Scanner teclado = new Scanner(System.in);
        Ejemplar ejemplar;
        int res = -1;
        
        try{
        Transaction trx = session.beginTransaction();
       //ejemplar = session.find(Ejemplar.class, ISBN);
       ejemplar = session.createNativeQuery("SELECT * FROM productos WHERE ISBN =:isbn && nro_ejemplar =: nro", Ejemplar.class).setParameter("id", isbn).setParameter("nro", nro).getSingleResult();
         if(ejemplar != null){
             while(res < 1 || res > 2){
             System.out.println("Ingrese el estado(1:Disponible|2:Prestado)");
             res = Integer.parseInt(teclado.nextLine());
             if(res == 1){
                 ejemplar.setEstado(Estado.Disponible);
             }else if(res == 2){
                 ejemplar.setEstado(Estado.Prestado);
             }else{
                 System.out.println("Numero invalido, ingrese de nuevo");
             }
             }
         }else{
            System.out.println();
            System.out.println("Ejemplar no encontrado");
        }
        session.merge(ejemplar);
        session.getTransaction().commit();
        System.out.println("Ejemplar actualizado..");
        System.out.println();
             }catch(Exception e){
                 System.out.println("Hubo un problema al actualizar el ejemplar");
             }
        }
    }
