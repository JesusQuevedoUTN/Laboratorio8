package Gestor;

import Controlador.ControladorSocio;
import herramientas.Mensaje;
import herramientas.Pausa;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import herramientas.GestorMenu;

public class GestorSocio implements GestorMenu {

    private ControladorSocio gestor = new ControladorSocio();
    private Scanner teclado = new Scanner(System.in);
    private DateTimeFormatter formato = DateTimeFormatter.ofPattern("d-M-yyyy");

    @Override
    public void ejecutarMenu() {
        int menu = -1;

        do {
            try {
                System.out.print(
                        "\n=== Socios Menú ===\n"
                        + "1. Agregar socio\n"
                        + "2. Listar socios\n"
                        + "3. Buscar socio\n"
                        + "4. Modificar socio\n"
                        + "5. Eliminar socio\n"
                        + "0. Volver al menú principal\n"
                        + "Opción: ");

                menu = Integer.parseInt(teclado.nextLine());

                switch (menu) {
                    case 1 ->
                        pedirYAgregarSocio();
                    case 2 ->
                        gestor.listarSocios();
                    case 3 ->
                        pedirYBuscarSocio();
                    case 4 ->
                        pedirYModificarSocio();
                    case 5 ->
                        pedirYEliminarSocio();
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

    private void pedirYAgregarSocio() {
        try {
            System.out.print("Ingrese nombre: ");
            String nombre = teclado.nextLine();

            System.out.print("Ingrese DNI: ");
            String dni = teclado.nextLine();

            System.out.print("Ingrese teléfono: ");
            String tel = teclado.nextLine();

            if (nombre.isBlank() || dni.isBlank() || tel.isBlank()) {
                System.out.println("Error: Los campos no pueden estar vacíos.");
            } else {
                System.out.print("Ingrese fecha de alta (dia-mes-año): ");
                LocalDate fecha = LocalDate.parse(teclado.nextLine(), formato);

                gestor.agregarSocio(nombre, dni, tel, fecha);
            }

        } catch (DateTimeParseException e) {
            System.out.println("Error: Fecha inválida. Utilice dd-MM-yyyy.");
        }
    }

    private void pedirYBuscarSocio() {
        System.out.print("Ingrese el DNI a buscar: ");
        String dni = teclado.nextLine();
        gestor.buscarSocioPorDni(dni);
    }

    private void pedirYModificarSocio() {
        System.out.print("Ingrese el DNI del socio a modificar: ");
        String dniModificar = teclado.nextLine();
        System.out.print("Ingrese el nuevo nombre: ");
        String nuevoNombre = teclado.nextLine();
        System.out.print("Ingrese el nuevo teléfono: ");
        String nuevoTelefono = teclado.nextLine();

        gestor.modificarSocio(dniModificar, nuevoNombre, nuevoTelefono);
    }

    private void pedirYEliminarSocio() {
        System.out.print("Ingrese el ID del socio a eliminar: ");
        try {
            int dniEliminar = Integer.parseInt(teclado.nextLine());
            gestor.eliminarSocio(dniEliminar);
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número entero.");
        }
    }
}
