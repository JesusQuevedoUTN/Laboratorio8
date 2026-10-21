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
import Main.Conexion;

public class ControladorEjemplar {

    
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
                                modificarEstado(ISBN);}
                 }
                
            }catch (InputMismatchException e) {
                System.out.println("Error: Tipo de dato incorrecto (Ingresó letras o formato numérico inválido).");
            }
            teclado.nextLine();
        } while(menu != 0);
    }
    
    public void agregarEjemplar(){
        Scanner teclado = new Scanner(System.in);
        int nro = 0;
        
        System.out.println("Ingres ISBN: ");
        String isbn = teclado.nextLine();
        if (isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío.");
        }
       
    boolean valido = false;
    while (!valido) {
        try {
            System.out.println("Ingrese numero de ejemplar: ");
            nro = Integer.parseInt(teclado.nextLine());
            valido = true;
        } catch (NumberFormatException e) {
            System.out.println("Error: el numero de ejemplar debe ser un entero. Intente de nuevo.");
        }
    }
    
    String sql = "INSERT INTO ejemplar (ISBN, nro_ejemplar) VALUES (?, ?)";
    
         try (Connection con = Conexion.getConexion();PreparedStatement ps = con.prepareStatement(sql)){
             
             ps.setString(1, isbn);
             ps.setInt(2, nro);
             
         }catch (SQLException e) {
            System.out.println("Error al agregar ejemplar: " + e.getMessage());
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
    
    public void modificarEstado(String isbn){
        Scanner teclado = new Scanner(System.in);
        int nro = 0;
        int estado = 0;
        
    boolean valido = false;
    while (!valido) {
        try {
            System.out.println("Ingrese numero de ejemplar: ");
            nro = Integer.parseInt(teclado.nextLine());
            valido = true;
        } catch (NumberFormatException e) {
            System.out.println("Error: el numero de ejemplar debe ser un entero. Intente de nuevo.");
        }
    }
    System.out.println("Que estado quiere darle al ejemplar[1:Disponible|2:Prestado]: ");
    while(estado < 1 || estado > 2){
        estado = Integer.parseInt(teclado.nextLine());
        if(estado < 1 || estado > 2){
            System.out.println("Numero invalido ingrese otra vez: ");
        }
    }
        
       String sql = "UPDATE EJEMPLAR SET estado = ?" + "WHERE ISBN = ? AND nro_ejemplar = ?";
    
       try (Connection con = Conexion.getConexion();PreparedStatement ps = con.prepareStatement(sql)){
            
           ps.setString(2, isbn);
           ps.setInt(3, nro);
           if(estado == 1){
               ps.setString(1, "Disponible");
           }else{
               ps.setString(1, "Prestado");
           }
           
           int filas = ps.executeUpdate();

        if (filas > 0) {
            System.out.println("Estado actualizado " );
        } else {
            System.out.println("No se pudo cambiar: el ejemplar no existe o no estaba ");
        }
 
    }catch (SQLException e) {
            System.out.println("Error al agregar ejemplar: " + e.getMessage());
        }
    
    }
    
}
