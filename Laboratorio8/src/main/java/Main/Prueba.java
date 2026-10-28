package Main;

import Controlador.ControladorLibro;
import Socios.GestorSocio;

public class Prueba {
    public static void main(String[] args) {
        // Creamos una instancia de nuestro gestor
        GestorSocio gestor = new GestorSocio();
        ControladorLibro lib = new ControladorLibro();
        
        System.out.println("Ingresar Lib: ");
        lib.agregarLibro();
        
        System.out.println("Lista libros: ");
        lib.listarLibros();
        
        // 0. Opcional pero recomendado: Probamos listar todos los socios de la tabla
        System.out.println("=== 1. LISTANDO TODOS LOS SOCIOS ===");
        gestor.listarSocios();
        
        // 1. Probamos buscar un DNI que ya viene en la base de datos de ejemplo (Ana García)
        System.out.println("=== 2. PROBANDO BÚSQUEDA EXITOSA ===");
        gestor.buscarSocioPorDni("30111222");
        
        // 2. Probamos buscar un DNI que NO existe para ver si salta la validación
        System.out.println("=== 3. PROBANDO BÚSQUEDA INEXISTENTE ===");
        gestor.buscarSocioPorDni("99999999");
    }
}