package Supermercado;

public class Cliente extends Thread {

    private  Bandeja bandeja;

    public Cliente(Bandeja bandeja, String nombre){
        super(nombre);
        this.bandeja = bandeja;
    }

    @Override
    public void run() {

        try {
            while (true){
                bandeja.sacar();
                if (bandeja == null) {
                    System.out.println(getName() + " no encuentra más libros. Se va a casa.");
                    break;  // ← Sale del bucle
                }
                System.out.println(getName() + " ,lee el libro: " + bandeja);
                Thread.sleep(1000 + (int)(Math.random() * 2000));  // ← 1-3 segundos
            }

        }catch (InterruptedException e){
            System.err.println(getName() + " interrumpido");
        }
        System.out.println(getName() + " termina de leer");
    }
}
