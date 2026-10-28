package Main;

import Socios.GestorSocio;

public class Prueba {
    public static void main(String[] args) {
        GestorSocio gestor = new GestorSocio();

        System.out.println("=== 1. LISTANDO SOCIOS ACTUALES ===");
        gestor.listarSocios();

        System.out.println("=== 2. AGREGANDO SOCIO DE PRUEBA ===");
        // Usamos un DNI inventado para no chocar con los reales
        gestor.agregarSocio("Socio Prueba", "99887766", "1122334455", "2023-10-26");

        System.out.println("\n=== 3. BUSCANDO SOCIO RECIÉN AGREGADO ===");
        gestor.buscarSocioPorDni("99887766");

        System.out.println("\n=== 4. MODIFICANDO SOCIO DE PRUEBA ===");
        gestor.modificarSocio("99887766", "Socio Modificado", "0000000000");

        System.out.println("\n=== 5. VERIFICANDO LA MODIFICACIÓN ===");
        gestor.buscarSocioPorDni("99887766");
        
        System.out.println("\n=== PRUEBA DE ELIMINACIÓN ===");
        System.out.println("Para probar el DELETE, mirá el ID que le asignó la base de datos al 'Socio Modificado' arriba,");
        System.out.println("entrá a tu menú principal (SelectorMenu) -> opción 2 -> opción 5, e ingresá ese ID.");
    }
}