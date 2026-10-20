package Main;

import Socios.GestorSocio;

public class Laboratorio8 {

    public static void main(String[] args) {
// Creamos una instancia de nuestro gestor
        GestorSocio gestor = new GestorSocio();

        // Probamos buscar un DNI que sabemos que existe en el script SQL (por ejemplo, Ana García)
        System.out.println("Buscando socio con DNI 30111222...");
        gestor.buscarSocioPorDni("30111222");

        // Probamos buscar un DNI que NO existe para ver si salta la validación
        System.out.println("\nBuscando socio con DNI inventado...");
        gestor.buscarSocioPorDni("99999999");
    }
}
