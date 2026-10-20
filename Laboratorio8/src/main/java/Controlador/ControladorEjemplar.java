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
        
    }
    
}
