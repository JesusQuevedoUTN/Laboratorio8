package Main;

import Controlador.ControladorEjemplar;
import Controlador.ControladorLibro;
import Controlador.ControladorPrestamo;
import Controlador.ControladorSocio;
import herramientas.ControladorMenu;
import herramientas.Pausa;
import java.util.Scanner;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class SelectorMenu {

    private Scanner teclado = new Scanner(System.in);

    public void seleccionar() {

        //Controladores creados para no tener que hacer New en cada vuelta que da el do while
        ControladorLibro menuLibro = new ControladorLibro();
        ControladorSocio menuSocio = new ControladorSocio();
        ControladorEjemplar menuEjemplar = new ControladorEjemplar();
        ControladorPrestamo menuPrestamo = new ControladorPrestamo();

        // Forzar la consola a UTF-8 para que los acentos salgan bien en consola, recomendado por Gemini
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        //seleccion
        ControladorMenu seleccion = null;
        int menu = -1;

        do {
            try {
                System.out.print("====================================\n"
                        + "       SISTEMA DE BIBLIOTECA\n"
                        + "====================================\n"
                        + "1. Gestionar libros\n"
                        + "2. Gestionar socios\n"
                        + "3. Gestionar ejemplares\n"
                        + "4. Gestionar préstamos\n"
                        + "0. Salir\n"
                        + "Opcion: ");

                menu = Integer.parseInt(teclado.nextLine());

                //FALTA QUE AL ENTRAR EN UN MENU Y SALIR, SALGA DE UNA, HAY UN ERROR ACÁ
                switch (menu) {
                    case 1 ->
                        seleccion = menuLibro;
                    case 2 ->
                        seleccion = menuSocio;
                    case 3 ->
                        seleccion = menuEjemplar;
                    case 4 ->
                        seleccion = menuPrestamo;
                    case 0 -> {
                        seleccion = null;
                        System.out.println("Adios");}
                    default ->
                        System.out.println("El número ingresado es inválido");
                }

                if (seleccion != null) {
                    seleccion.ejecutarMenu();
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Tipo de dato incorrecto.");
            }
        } while (menu != 0);
    }
}
