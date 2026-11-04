package Main;

import Controlador.ControladorEjemplar;
import Controlador.ControladorLibro;
import Controlador.ControladorPrestamo;
import Controlador.ControladorSocio;
import herramientas.ControladorMenu;
import java.util.Scanner;

public class SelectorMenu {

    public void seleccionar() {
        Scanner teclado = new Scanner(System.in);
        int menu = -1;

        do {
            try {
                System.out.println("====================================\n"
                        + "       SISTEMA DE BIBLIOTECA\n"
                        + "====================================\n"
                        + "1. Gestionar libros\n"
                        + "2. Gestionar socios\n"
                        + "3. Gestionar ejemplares\n"
                        + "4. Gestionar préstamos\n"
                        + "0. Salir");
                menu = Integer.parseInt(teclado.nextLine());

                ControladorMenu controlador = null;

                switch (menu) {
                    case 1 ->
                        controlador = new ControladorLibro();
                    case 2 ->
                        controlador = new ControladorSocio();
                    case 3 ->
                        controlador = new ControladorEjemplar();
                    case 4 ->
                        controlador = new ControladorPrestamo();
                    case 0 ->
                        System.out.println("Saliendo del sistema...");
                    default ->
                        System.out.println("El número ingresado es inválido");
                }

                if (controlador != null) {
                    controlador.ejecutarMenu();
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Tipo de dato incorrecto.");
            }
        } while (menu != 0);
    }
}
