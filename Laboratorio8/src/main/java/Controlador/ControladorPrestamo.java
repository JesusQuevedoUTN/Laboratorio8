package Controlador;

import Gestor.GestorPrestamo;
import herramientas.ControladorMenu;
import herramientas.Mensaje;
import herramientas.Pausa;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ControladorPrestamo implements ControladorMenu {

    private GestorPrestamo gestor = new GestorPrestamo();
    private Scanner teclado = new Scanner(System.in);
    private DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-mm-yyyy");

    @Override
    public void ejecutarMenu() {
        int menu = -1;

        do {
            try {
                System.out.print(
                        "\n=== Prestamo Menú ===\n"
                        + "1. Registrar prestamo\n"
                        + "2. Registrar devolucion\n"
                        + "3. Listar prestamos\n"
                        + "0. Salir\n"
                        + "Opcion: ");

                String entrada = teclado.nextLine();
                menu = Integer.parseInt(entrada);

                switch (menu) {
                    case 1 ->
                        pedirYRegistrarPrestamo();
                    case 2 ->
                        pedirYRegistrarDevolucion();
                    case 3 ->
                        gestor.listarPrestamos();
                    case 0 ->
                        System.out.println("<--\n\n");
                    default ->
                        Mensaje.opcionInvalida();
                }

            } catch (NumberFormatException e) {
                Mensaje.tipoDatoIncorrecto();
            }

            if (menu != 0) {
                Pausa.pausar();
            }
        } while (menu != 0);
    }

    private void pedirYRegistrarPrestamo() {
        try {
            System.out.println("Ingrese el ID del usuario: ");
            int id = Integer.parseInt(teclado.nextLine());

            System.out.println("Ingrese el ISBN del libro: ");
            String isbn = teclado.nextLine();

            System.out.println("Ingrese el numero del ejemplar: ");
            int nro = Integer.parseInt(teclado.nextLine());

            System.out.println("Ingrese la fecha del prestamo (dd-mm-yyyy): ");
            LocalDate fechaP = LocalDate.parse(teclado.nextLine(), formato);

            System.out.println("Ingrese la fecha limite del prestamo (dd-mm-yyyy): ");
            LocalDate fechaPrev = LocalDate.parse(teclado.nextLine(), formato);

            gestor.registrarPrestamo(id, isbn, nro, fechaP, fechaPrev);

        } catch (NumberFormatException e) {
            System.out.println("Error: debe ingresar un numero entero.");
        } catch (DateTimeParseException e) {
            System.out.println("Error: fecha invalida. Use el formato dd-mm-yyyy.");
        }
    }

    private void pedirYRegistrarDevolucion() {
        try {
            System.out.println("Ingrese el ID del prestamo: ");
            int id = Integer.parseInt(teclado.nextLine());

            System.out.println("Ingrese la fecha de devolucion (dd-mm-yyyy): ");
            LocalDate fechaDev = LocalDate.parse(teclado.nextLine(), formato);

            gestor.registrarDevolucion(id, fechaDev);

        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un numero entero.");
        } catch (DateTimeParseException e) {
            System.out.println("Error: fecha invalida. Use el formato dd-mm-yyyy.");
        }
    }
}
