package Gestor;

import Controlador.ControladorPrestamo;
import herramientas.Mensaje;
import herramientas.Pausa;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import herramientas.GestorMenu;

public class GestorPrestamo implements GestorMenu {

    private ControladorPrestamo gestor = new ControladorPrestamo();
    private Scanner teclado = new Scanner(System.in);
    private DateTimeFormatter formato = DateTimeFormatter.ofPattern("d-M-yyyy");

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
            System.out.println("Ingrese el id del socio: ");
            int id = Integer.parseInt(teclado.nextLine());

            System.out.println("Ingrese el ISBN del libro: ");
            String isbn = teclado.nextLine();

            System.out.println("Ingrese el numero del ejemplar: ");
            int nro = Integer.parseInt(teclado.nextLine());

            System.out.println("Ingrese la fecha del prestamo (dia-mes-año): ");
            LocalDate fechaPrestamo = LocalDate.parse(teclado.nextLine(), formato);

            System.out.println("Ingrese la fecha limite del prestamo (dia-mes-año): ");
            LocalDate fechaLimite = LocalDate.parse(teclado.nextLine(), formato);

            if (fechaLimite.isBefore(fechaPrestamo)) {
                System.out.println("Error: La fecha límite no puede ser anterior a la fecha del préstamo.");
            } else {
                gestor.registrarPrestamo(id, isbn, nro, fechaPrestamo, fechaLimite);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: debe ingresar un numero entero.");
        } catch (DateTimeParseException e) {
            System.out.println("Error: fecha invalida.");
        }
    }

    private void pedirYRegistrarDevolucion() {
        try {
            System.out.println("Ingrese el ID del prestamo: ");
            int id = Integer.parseInt(teclado.nextLine());

            System.out.println("Ingrese la fecha de devolucion (dia-mes-año): ");
            LocalDate fechaDev = LocalDate.parse(teclado.nextLine(), formato);

            gestor.registrarDevolucion(id, fechaDev);

        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un numero entero.");
        } catch (DateTimeParseException e) {
            System.out.println("Error: fecha invalida.");
        }
    }
}
