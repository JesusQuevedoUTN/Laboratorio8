package Main;

import Gestor.GestorEjemplar;
import Gestor.GestorLibro;
import Gestor.GestorPrestamo;
import Gestor.GestorSocio;
import herramientas.Mensaje;
import java.util.Scanner;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import herramientas.GestorMenu;

public class SelectorMenu {

    private Scanner teclado = new Scanner(System.in);

    public void seleccionar() {

        //Controladores creados para no tener que hacer New en cada vuelta que da el do while
        GestorLibro menuLibro = new GestorLibro();
        GestorSocio menuSocio = new GestorSocio();
        GestorEjemplar menuEjemplar = new GestorEjemplar();
        GestorPrestamo menuPrestamo = new GestorPrestamo();

        // Forzar la consola a UTF-8 para que los acentos salgan bien en consola, recomendado por Gemini
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        //seleccion
        GestorMenu seleccion = null;
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
                        System.out.println("\n\nAdios\n\n");}
                    default ->
                        Mensaje.opcionInvalida();
                }

                if (seleccion != null) {
                    seleccion.ejecutarMenu();
                }

            } catch (NumberFormatException e) {
                Mensaje.tipoDatoIncorrecto();
            }
        } while (menu != 0);
    }
}
