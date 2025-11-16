package teoria;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        //Clase Thread
        MiHilo hilo1 = new MiHilo();
        MiHilo hilo2 = new MiHilo();
        hilo1.start();
        //hilo2.start();

        HiloMain hiloMain = new HiloMain();
        hiloMain.start();

        hilo1.join();
        hilo2.join();
        hiloMain.join();

       // Opcion 1 Interfaz Runnable
       //MiRunnable r1 = new MiRunnable();
       //Thread nuevo = new Thread(r1);

        // Opcion 2 (mas facil) Interfaz Runnable
//        Thread r1 = new Thread(new MiRunnable());
//        r1.start();


//        r1.join();

        System.out.println("Los hilos han finalizado");
    }
}

class HiloMain extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Soy MiHilo " + i);
        }

    }
}
