package ejercicioHolaMundoRunnable;

public class ejercicioHolaMundoRunnable {

    public static void main(String[] args) {
        System.out.println("Iniciando programa con 5 hilos...");

        // Crear y ejecutar 5 hilos diferentes
        for (int i = 1; i <= 5; i++) {
            String cadena = "Mensaje " + i;
            Thread hilo = new Thread(new HiloRunnable("Javaa"));
            hilo.start();
        }

        System.out.println("Todos los hilos han sido iniciados");
    }
}

