package Controlador;

import Gestor.GestorPrestamo;
import herramientas.ControladorMenu;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ControladorPrestamo implements ControladorMenu {

    private GestorPrestamo gestor = new GestorPrestamo();
    private Scanner teclado = new Scanner(System.in);

    @Override
    public void ejecutarMenu() {
        int menu = -1;

        do {
            try {
                System.out.println("=== Prestamo Menu ===");
                System.out.println("1. Registrar prestamo ");
                System.out.println("2. Registrar devolucion ");
                System.out.println("3. Listar prestamos ");
                System.out.println("0. Salir");
                System.out.println("Opcion: ");
                menu = teclado.nextInt();

                if (menu < 0 || menu > 3) {
                    System.out.println("El numero ingresado es invalido");
                }

                switch (menu) {
                    case 1 ->
                        gestor.registrarPrestamo();
                    case 2 ->
                        gestor.registrarDevolucion();
                    case 3 ->
                        gestor.listarPrestamos();
                }

            } catch (InputMismatchException e) {
                System.out.println("Error: Tipo de dato incorrecto (Ingresó letras o formato numérico inválido).");
            }
            teclado.nextLine();
        } while (menu != 0);
    }
}
