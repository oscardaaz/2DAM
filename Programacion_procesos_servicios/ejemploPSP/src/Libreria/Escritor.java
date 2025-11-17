package Libreria;

public class Escritor extends Thread{

    private Estanteria estanteria;

    public Escritor(Estanteria estanteria, String nombre) {
        super(nombre);
        this.estanteria = estanteria;
    }

    @Override
    public void run() {
        try {
            int cantidad = 3;
            for (int i = 0; i < cantidad; i++) {
                Libro libro = new Libro();
                estanteria.agregar(libro);
                Thread.sleep(1000);
            }
            System.out.println(getName() + " termino");
        }catch (InterruptedException e){
                System.err.println(getName() + " interrumpido");
        }

        }
    }

