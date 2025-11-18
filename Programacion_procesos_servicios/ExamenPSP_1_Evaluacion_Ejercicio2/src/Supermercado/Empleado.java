package Supermercado;

public class Empleado extends Thread {

    private Bandeja bandeja;
    private int numeroReposiciones;

    public Empleado (Bandeja bandeja, int numeroReposiciones, String nombre){
        super(nombre);
        this.bandeja = bandeja;
        this.numeroReposiciones = numeroReposiciones;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < numeroReposiciones; i++) {
                bandeja.agregar(1);
                Thread.sleep(1000);

                for (bandeja : elemento){
                    System.out.println(elemento);
                }
            }
            System.out.println(getName() + " termino");
        }catch (InterruptedException e){
            System.err.println(getName() + " interrumpido");
        }

    }

    }

