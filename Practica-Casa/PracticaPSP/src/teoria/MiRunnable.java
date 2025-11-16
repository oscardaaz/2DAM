package teoria;

public class MiRunnable implements Runnable{

    @Override
    public void run() {

        for (int i = 0; i < 10 ; i++) {
            System.out.println("Soy el hilo runnable: " + i);
        }
    }
}
