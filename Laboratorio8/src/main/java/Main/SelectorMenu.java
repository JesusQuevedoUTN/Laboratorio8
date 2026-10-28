package Main;

import Controlador.ControladorEjemplar;
import Controlador.ControladorLibro;
import Controlador.ControladorSocio;
import java.util.Scanner;

public class SelectorMenu {



public void seleccionar() {
        Scanner teclado = new Scanner(System.in);
        int menu = -1;

        // Instanciamos los controladores de ambos
        ControladorLibro controlLibro = new ControladorLibro();
        ControladorEjemplar controlEjemplar = new ControladorEjemplar();
        ControladorSocio controlSocio = new ControladorSocio(); 

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
                    case 1 -> controlLibro.menuLibro();       // Llama a la clase de tu compañero
                    case 2 -> controlSocio.menuSocio();       // Llama a TU clase
                    case 3 -> controlEjemplar.menu();         // Llama a la clase de tu compañero
                    case 4 -> System.out.println("Módulo de préstamos en construcción..."); 
                    case 0 -> System.out.println("Saliendo...");
                    default -> System.out.println("El número ingresado es inválido");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Tipo de dato incorrecto.");
            }
        } while (menu != 0);
    }
}