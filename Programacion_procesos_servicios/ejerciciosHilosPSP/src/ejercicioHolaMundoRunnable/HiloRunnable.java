package ejercicioHolaMundoRunnable;

public class HiloRunnable implements Runnable {

    private String mensaje;

    public HiloRunnable(String mensaje) {
        this.mensaje = mensaje;
    }
    @Override
    public void run() {
        // Obtener el identificador del hilo
        long idHilo = Thread.currentThread().getId();

        // Esperar un tiempo proporcional al identificador
        try {
            Thread.sleep(idHilo % 1000); // Usamos módulo para no esperar demasiado
        } catch (InterruptedException e) {
            System.out.println("Hilo interrumpido: " + e.getMessage());
        }

        // Visualizar el mensaje
        System.out.println("Hola mundo " + mensaje + " - ID del hilo: " + idHilo);
    }
}