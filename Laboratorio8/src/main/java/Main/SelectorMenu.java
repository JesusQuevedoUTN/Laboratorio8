package Main;

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

                switch (menu) {
                    case 1 ->
                        System.out.println("En Construccion...");
                    case 2 ->
                        System.out.println("En Construccion...");
                    case 3 ->
                        System.out.println("En Construccion...");
                    case 4 ->
                        System.out.println("En Construccion...");
                    case 0 ->
                        System.out.println("En Construccion...");
                    default ->
                        System.out.println("El número ingresado es inválido");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Tipo de dato incorrecto.");
            }
        } while (menu != 0);
    }
}
