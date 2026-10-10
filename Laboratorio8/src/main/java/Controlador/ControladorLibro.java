package Controlador;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.hibernate.Session;


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
    
    }
    public void listarLibros(){
        
    }
    
    public void buscarLibro(String ISBN){
        
    }
    
    public void modificarLibro(String ISBN){
        
    }
    
    public void eliminarLibro(String ISBN){
        
    }
    
}
