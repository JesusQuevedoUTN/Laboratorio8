package Controlador;

import Gestor.GestorEjemplar;
import herramientas.ControladorMenu;
import herramientas.Estado;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ControladorEjemplar implements ControladorMenu {

    private GestorEjemplar gestor = new GestorEjemplar();
    private Scanner teclado = new Scanner(System.in);

    @Override
    public void ejecutarMenu() {
        int menu = -1;

        do {
            try {
                System.out.println("=== Ejemplar Menu ===");
                System.out.println("1. Agregar ejemplar ");
                System.out.println("2. Listar ejemplares de un libro ");
                System.out.println("3. Modificar estado ");
                System.out.println("0. Salir");
                System.out.print("Opcion: ");
                String entrada = teclado.nextLine();
                menu = Integer.parseInt(entrada);

                if (menu < 0 || menu > 3) {
                    System.out.println("El numero ingresado es invalido");
                }

                switch (menu) {
                    case 1 ->
                        pedirYAgregarEjemplar();
                    case 2 ->
                        pedirYListarEjemplares();
                    case 3 ->
                        pedirYModificarEstado();
                }

            } catch (InputMismatchException e) {
                System.out.println("Error: Tipo de dato incorrecto (Ingresó letras o formato numérico inválido).");
            }
            teclado.nextLine();
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
