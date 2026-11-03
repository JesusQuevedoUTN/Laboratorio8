package Controlador;

import Gestor.GestorPrestamo;
import herramientas.ControladorMenu;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ControladorPrestamo implements ControladorMenu {

    private GestorPrestamo gestor = new GestorPrestamo();
    private Scanner teclado = new Scanner(System.in);
    private DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
                        pedirYRegistrarPrestamo();
                    case 2 ->
                        pedirYRegistrarDevolucion();
                    case 3 ->
                        gestor.listarPrestamos();
                }

            } catch (InputMismatchException e) {
                System.out.println("Error: Tipo de dato incorrecto (Ingresó letras o formato numérico inválido).");
            }
            teclado.nextLine();
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

            System.out.println("Ingrese la fecha del prestamo (dd/MM/yyyy): ");
            LocalDate fechaP = LocalDate.parse(teclado.nextLine(), formato);

            System.out.println("Ingrese la fecha limite del prestamo (dd/MM/yyyy): ");
            LocalDate fechaPrev = LocalDate.parse(teclado.nextLine(), formato);

            gestor.registrarPrestamo(id, isbn, nro, fechaP, fechaPrev);

        } catch (NumberFormatException e) {
            System.out.println("Error: debe ingresar un numero entero.");
        } catch (DateTimeParseException e) {
            System.out.println("Error: fecha invalida. Use el formato dd/MM/yyyy.");
        }
    }

    private void pedirYRegistrarDevolucion() {
        try {
            System.out.println("Ingrese el ID del prestamo: ");
            int id = Integer.parseInt(teclado.nextLine());

            System.out.println("Ingrese la fecha de devolucion (dd/MM/yyyy): ");
            LocalDate fechaDev = LocalDate.parse(teclado.nextLine(), formato);

            gestor.registrarDevolucion(id, fechaDev);

        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un numero entero.");
        } catch (DateTimeParseException e) {
            System.out.println("Error: fecha invalida. Use el formato dd/MM/yyyy.");
        }
    }
}
