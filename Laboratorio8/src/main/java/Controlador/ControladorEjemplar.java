package Controlador;

import Gestor.GestorEjemplar;
import herramientas.ControladorMenu;
import herramientas.Estado;
import java.util.Scanner;

public class ControladorEjemplar implements ControladorMenu {

    private GestorEjemplar gestor = new GestorEjemplar();
    private Scanner teclado = new Scanner(System.in);

    @Override
    public void ejecutarMenu() {
        int menu = -1;

        do {
            try {
                System.out.print("=== Ejemplar Menú ===\n"
                        + "1. Agregar ejemplar\n"
                        + "2. Listar ejemplares de un libro\n"
                        + "3. Modificar estado\n"
                        + "0. Salir\n"
                        + "Opcion: ");
                
                String entrada = teclado.nextLine();
                menu = Integer.parseInt(entrada);

                switch (menu) {
                    case 1 ->
                        pedirYAgregarEjemplar();
                    case 2 ->
                        pedirYListarEjemplares();
                    case 3 ->
                        pedirYModificarEstado();
                    default ->
                        System.out.println("El número ingresado es inválido");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Tipo de dato incorrecto.");
            }
        } while (menu != 0);
    }

    private void pedirYAgregarEjemplar() {
        System.out.println("Ingres ISBN: ");
        String isbn = teclado.nextLine();
        System.out.println("Ingrese nro de ejemplar: ");
        int nro = Integer.parseInt(teclado.nextLine());
        System.out.println("Que estado quiere darle al ejemplar [1:Disponible|2:Prestado]: ");
        int estado = Integer.parseInt(teclado.nextLine());
        if (estado == 1) {
            gestor.agregarEjemplar(isbn, nro, Estado.Disponible.name());
        } else {

            gestor.agregarEjemplar(isbn, nro, Estado.Prestado.name());
        }
    }

    private void pedirYModificarEstado() {
        System.out.println("Ingrese el ISBN del Ejemplar a Buscar:");
        String isbn = teclado.nextLine();
        System.out.println("Ingrese nro de ejemplar: ");
        int nro = Integer.parseInt(teclado.nextLine());
        System.out.println("Que estado quiere darle al ejemplar [1:Disponible|2:Prestado]: ");
        int estado = Integer.parseInt(teclado.nextLine());

        if (estado == 1) {
            gestor.modificarEstado(isbn, nro, Estado.Disponible.name());
        } else {

            gestor.modificarEstado(isbn, nro, Estado.Prestado.name());
        }
    }

    private void pedirYListarEjemplares() {
        String ISBN = null;

        System.out.println("Ingrese el ISBN del libro buscado: ");
        ISBN = teclado.nextLine();
        if (ISBN == null || ISBN.isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío.");
        }

        gestor.listarEjemplares(ISBN);
    }

}
