package Controlador;

import Gestor.GestorSocio;
import herramientas.ControladorMenu;
import herramientas.Pausa;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ControladorSocio implements ControladorMenu {

    private GestorSocio gestor = new GestorSocio();
    private Scanner teclado = new Scanner(System.in);
    private DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-mm-yyyy");

    @Override
    public void ejecutarMenu() {
        int menu = -1;

        do {
            try {
                System.out.print("\n=== Socios Menú ===\n"
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
                        System.out.println("<--");
                    default ->
                        System.out.println("El número ingresado es inválido");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Tipo de dato incorrecto.");
            }

            Pausa.pausar();
        } while (menu != 0);
    }

    private void pedirYAgregarSocio() {
        System.out.print("Ingrese nombre: ");
        String nombre = teclado.nextLine();
        System.out.print("Ingrese DNI: ");
        String dni = teclado.nextLine();
        System.out.print("Ingrese teléfono: ");
        String tel = teclado.nextLine();
        System.out.print("Ingrese fecha de alta (dd/MM/yyyy): ");
        LocalDate fecha = LocalDate.parse(teclado.nextLine(), formato);

        gestor.agregarSocio(nombre, dni, tel, fecha);
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
        System.out.print("Ingrese el DNI del socio a eliminar: ");
        try {
            int dniEliminar = Integer.parseInt(teclado.nextLine());
            gestor.eliminarSocio(dniEliminar);
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número entero.");
        }
    }
}
