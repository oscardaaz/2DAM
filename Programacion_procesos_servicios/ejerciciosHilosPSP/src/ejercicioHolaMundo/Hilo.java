package ejercicioHolaMundo;

public class Hilo extends Thread{


    @Override
    public void run() {
        System.out.println("Hola mundo" +
                " - ID del hilo: " + Thread.currentThread().getId());

    }
}
