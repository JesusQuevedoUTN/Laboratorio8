package Main;

import java.util.InputMismatchException;
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
                        + "\n"
                        + "1. Gestionar libros\n"
                        + "2. Gestionar socios\n"
                        + "3. Gestionar ejemplares\n"
                        + "4. Gestionar préstamos\n"
                        + "0. Salir");
                menu = teclado.nextInt();

                if (menu < 0 || menu > 4) {
                    System.out.println("El numero ingresado es invalido");
                }

                switch (menu) {
                    case 1 ->
                        System.out.println("null");
                    case 2 ->
                        System.out.println("null");
                    case 3 ->
                        System.out.println("null");
                    case 4 ->
                        System.out.println("null");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Tipo de dato incorrecto (Ingresó letras o formato numérico inválido).");
            }
            teclado.nextLine(); // Limpiamos el buffer del Scanner para evitar bucle infinito
        } while (menu < 0 || menu > 4);
    }

}
