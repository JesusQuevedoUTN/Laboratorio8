package herramientas;

import java.util.Scanner;

public class Pausa {

    static public void pausar() {

        System.out.println("\nPulsa ENTER para continuar...");
        Scanner teclado = new Scanner(System.in);
        teclado.nextLine();
    }
}
