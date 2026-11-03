package Controlador;

import Gestor.GestorEjemplar;
import herramientas.ControladorMenu;
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
                System.out.println("Opcion: ");
                menu = teclado.nextInt();

                if (menu < 0 || menu > 3) {
                    System.out.println("El numero ingresado es invalido");
                }

                switch (menu) {
                    case 1 ->
                        gestor.agregarEjemplar();
                    case 2 ->
                        gestor.listarEjemplares();
                    case 3 ->
                        gestor.modificarEstado();
                }

            } catch (InputMismatchException e) {
                System.out.println("Error: Tipo de dato incorrecto (Ingresó letras o formato numérico inválido).");
            }
            teclado.nextLine();
        } while (menu != 0);
    }

}
