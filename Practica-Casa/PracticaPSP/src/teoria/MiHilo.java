package teoria;

public class MiHilo extends Thread {

    @Override
    public void run() {
        for (int i = 0; i <10 ; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Soy el hilo thread: "+ Thread.currentThread().getName() + i);
        }

    }
}
