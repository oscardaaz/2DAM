package ejercicioHolaMundoRunnable;

public class HiloRunnable implements Runnable {

    /**
     * Crea una clase que implemente la interfaz Runnable cuya única funcionalidad
     * sea visualizar el mensaje “Hola mundo” seguido de una cadena que se recibirá
     * en el constructor (es decir al crear un objeto de este tipo se enviará una cadena)
     * y seguido del identificador del hilo.
     *
     * Crea un programa Java que visualice el mensaje anterior 5 veces creando para ello
     * 5 hilos diferentes usando la clase creada anteriormente. Luego haz que antes de
     * visualizar el mensaje el hilo espere un tiempo proporcional a su identificador;
     * usa para ello el metodo sleep(). ¿Qué diferencias observas al ejecutar el
     * programa usando o no el metodo sleep()?
     *
     */
    private String mensaje;

    public HiloRunnable(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public void run() {
        long idHilo = Thread.currentThread().getId();
        // Esperar un tiempo proporcional al identificador
        try {
            Thread.sleep(idHilo % 1000);
        } catch (InterruptedException e) {
            System.out.println("Hilo interrumpido: " + e.getMessage());
        }

        // Visualizar el mensaje
        System.out.println("Hola mundo " + mensaje + " - ID del hilo: " + idHilo);
    }
}