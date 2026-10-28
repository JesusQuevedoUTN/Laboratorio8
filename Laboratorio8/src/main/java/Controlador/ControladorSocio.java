package Controlador;
import Controlador.ControladorLibro;
import Controlador.ControladorEjemplar;
import Socios.GestorSocio;
// import Controlador.ControladorSocio; // Asegurate de importarlo cuando lo crees

import java.util.InputMismatchException;
import java.util.Scanner;

public class ControladorSocio {
    
    // Instanciamos tu gestor para poder usar sus métodos
    private GestorSocio gestorSocio = new GestorSocio();

    public void menuSocio() {
        Scanner teclado = new Scanner(System.in);
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
                menu = Integer.parseInt(teclado.nextLine()); // Usamos parseInt para evitar el bug del Scanner
                
                switch (menu) {
                    case 1 -> {
                        System.out.print("Ingrese nombre: ");
                        String nombre = teclado.nextLine();
                        System.out.print("Ingrese DNI: ");
                        String dni = teclado.nextLine();
                        System.out.print("Ingrese teléfono: ");
                        String tel = teclado.nextLine();
                        System.out.print("Ingrese fecha de alta (YYYY-MM-DD): ");
                        String fecha = teclado.nextLine();
                        gestorSocio.agregarSocio(nombre, dni, tel, fecha);
                    }
                    case 2 -> gestorSocio.listarSocios();
                    case 3 -> {
                        System.out.print("Ingrese el DNI a buscar: ");
                        String dni = teclado.nextLine();
                        gestorSocio.buscarSocioPorDni(dni);
                    }
                    case 4 -> {
                        System.out.print("Ingrese el DNI del socio a modificar: ");
                        String dniModificar = teclado.nextLine();
                        System.out.print("Ingrese el nuevo nombre: ");
                        String nuevoNombre = teclado.nextLine();
                        System.out.print("Ingrese el nuevo teléfono: ");
                        String nuevoTelefono = teclado.nextLine();
                        gestorSocio.modificarSocio(dniModificar, nuevoNombre, nuevoTelefono);
                    }
                    case 5 -> {
                        System.out.print("Ingrese el ID del socio a eliminar: ");
                        try {
                            int idEliminar = Integer.parseInt(teclado.nextLine());
                            gestorSocio.eliminarSocio(idEliminar);
                        } catch (NumberFormatException e) {
                            System.out.println("Error: El ID debe ser un número entero.");
                        }
                    }
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        } while (menu != 0);
    }
}