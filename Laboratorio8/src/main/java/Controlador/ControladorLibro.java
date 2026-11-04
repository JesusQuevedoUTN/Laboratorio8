package Controlador;

import Gestor.GestorLibro;
import herramientas.ControladorMenu;
import java.util.Scanner;

public class ControladorLibro implements ControladorMenu {

    private GestorLibro gestor = new GestorLibro();
    private Scanner teclado = new Scanner(System.in);

    @Override
    public void ejecutarMenu() {
        int menu = -1;

        do {
            try {
                System.out.println("=== Libro Menu ===");
                System.out.println("1. Agregar libro ");
                System.out.println("2. Listar libros ");
                System.out.println("3. Buscar libro ");
                System.out.println("4. Modificar libro ");
                System.out.println("5. Eliminar libro ");
                System.out.println("0. Salir");
                System.out.println("Opcion: ");
                menu = Integer.parseInt(teclado.nextLine());

                switch (menu) {
                    case 1 ->
                        pedirYAgregarLibro();
                    case 2 ->
                        gestor.listarLibros();
                    case 3 ->
                        pedirYBuscarLibro();
                    case 4 ->
                        pedirYModificarLibro();
                    case 5 ->
                        pedirYEliminarLibro();
                    case 0 ->
                        System.out.println("Volviendo al menú principal...");
                    default ->
                        System.out.println("El numero ingresado es invalido");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Tipo de dato incorrecto.");
            }
        } while (menu != 0);
    }

    private void pedirYAgregarLibro() {
        System.out.println("Ingrese ISBN: ");
        String isbn = teclado.nextLine();
        System.out.println("Ingrese titulo: ");
        String titulo = teclado.nextLine();
        System.out.println("Ingrese autor: ");
        String autor = teclado.nextLine();
        System.out.println("Ingrese editorial: ");
        String editorial = teclado.nextLine();
        System.out.println("Ingrese anio de publicacion: ");
        int anio = Integer.parseInt(teclado.nextLine());

        gestor.agregarLibro(isbn, titulo, autor, editorial, anio);
    }

    private void pedirYBuscarLibro() {
        System.out.println("Ingrese el ISBN a buscar: ");
        String isbn = teclado.nextLine();
        gestor.buscarLibroPorISBN(isbn);
    }

    private void pedirYModificarLibro() {
        System.out.println("Ingrese el ISBN a modificar: ");
        String isbn = teclado.nextLine();
        System.out.println("Ingrese nuevo titulo: ");
        String titulo = teclado.nextLine();
        System.out.println("Ingrese nuevo autor: ");
        String autor = teclado.nextLine();
        System.out.println("Ingrese nueva editorial: ");
        String editorial = teclado.nextLine();
        System.out.println("Ingrese nuevo anio: ");
        int anio = Integer.parseInt(teclado.nextLine());

        gestor.modificarLibro(isbn, titulo, autor, editorial, anio);
    }

    private void pedirYEliminarLibro() {
        System.out.println("Ingrese el ISBN a eliminar: ");
        String isbn = teclado.nextLine();
        gestor.eliminarLibro(isbn);
    }
}
