package Main;

import Controlador.ControladorEjemplar;
import Controlador.ControladorLibro;
import Controlador.GestorPrestamos;
import Socios.GestorSocio;

public class Prueba {
    public static void main(String[] args) {
        GestorSocio gestor = new GestorSocio();
        ControladorLibro lib = new ControladorLibro();
        GestorPrestamos pre = new GestorPrestamos();
        ControladorEjemplar ej = new ControladorEjemplar();
        
        
       // lib.menuLibro();
        
       pre.menuPrestamo();
       
       ej.menu();
        
        
        
        System.out.println("Ingresar Prestamo: ");
        //pre.registrarPrestamo();
        
        System.out.println("Registrar devolucion: ");
        //pre.registrarDevolucion();
        
        System.out.println("Listar prestamos: ");
        //pre.listarPrestamos();
        
        
        
       /* 
        // 0. Opcional pero recomendado: Probamos listar todos los socios de la tabla
        System.out.println("=== 1. LISTANDO TODOS LOS SOCIOS ===");
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
        
        // 1. Probamos buscar un DNI que ya viene en la base de datos de ejemplo (Ana García)
        System.out.println("=== 2. PROBANDO BÚSQUEDA EXITOSA ===");
        gestor.buscarSocioPorDni("30111222");
        
        // 2. Probamos buscar un DNI que NO existe para ver si salta la validación
        System.out.println("=== 3. PROBANDO BÚSQUEDA INEXISTENTE ===");
        gestor.buscarSocioPorDni("99999999");*/
    }
}