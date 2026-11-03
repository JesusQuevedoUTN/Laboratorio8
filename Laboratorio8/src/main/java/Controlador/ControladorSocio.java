package Controlador;

import Gestor.GestorSocio;
import herramientas.ControladorMenu;
import java.util.Scanner;

public class ControladorSocio implements ControladorMenu {

    private GestorSocio gestor = new GestorSocio();
    private Scanner teclado = new Scanner(System.in);

    @Override
    public void ejecutarMenu() {
        int menu = -1;

        do {
            try {
                System.out.println("\n=== Menú Socios ===");
                System.out.println("1. Agregar socio");
                System.out.println("2. Listar socios");
                System.out.println("3. Buscar socio");
                System.out.println("4. Modificar socio");
                System.out.println("5. Eliminar socio");
                System.out.println("0. Volver al menú principal");
                System.out.print("Opción: ");
                
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
                        System.out.println("Volviendo al menú principal...");
                    default ->
                        System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        } while (menu != 0);
    }

    private void pedirYAgregarSocio() {
        System.out.print("Ingrese nombre: ");
        String nombre = teclado.nextLine();
        System.out.print("Ingrese DNI: ");
        String dni = teclado.nextLine();
        System.out.print("Ingrese teléfono: ");
        String tel = teclado.nextLine();
        System.out.print("Ingrese fecha de alta (YYYY-MM-DD): ");
        String fecha = teclado.nextLine();

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
        System.out.print("Ingrese el ID del socio a eliminar: ");
        try {
            int idEliminar = Integer.parseInt(teclado.nextLine());
            gestor.eliminarSocio(idEliminar);
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número entero.");
        }
    }
}
