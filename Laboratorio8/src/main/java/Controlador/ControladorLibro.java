package Controlador;

import Gestor.GestorLibro;
import herramientas.ControladorMenu;
import herramientas.Pausa;
import java.util.Scanner;

public class ControladorLibro implements ControladorMenu {

    private GestorLibro gestor = new GestorLibro();
    private Scanner teclado = new Scanner(System.in);

    @Override
    public void ejecutarMenu() {
        int menu = -1;

        do {
            try {
                System.out.print("=== Libro Menú ===\n"
                        + "1. Agregar libro\n"
                        + "2. Listar libros\n"
                        + "3. Buscar libro\n"
                        + "4. Modificar libro\n"
                        + "5. Eliminar libro\n"
                        + "0. Salir\n"
                        + "Opcion: ");

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
                        System.out.println("<--");
                    default ->
                        System.out.println("El número ingresado es inválido");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Tipo de dato incorrecto.");
            }

            Pausa.pausar();
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
