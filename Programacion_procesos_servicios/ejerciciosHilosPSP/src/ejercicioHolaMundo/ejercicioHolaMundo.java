package ejercicioHolaMundo;

public class ejercicioHolaMundo {
    public static void main(String[] args) {
        // Crear 5 hilos diferentes
        for (int i = 0; i < 5; i++) {
            Hilo hilo = new Hilo();
            hilo.start(); // Iniciar el hilo
        }
    }
}

