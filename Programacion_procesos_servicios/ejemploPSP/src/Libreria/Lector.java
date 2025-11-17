package Libreria;

public class Lector extends Thread{

    private  Estanteria estanteria;

    public Lector(Estanteria estanteria, String nombre){
        super(nombre);
        this.estanteria = estanteria;
    }

    @Override
    public void run() {

        try {
            while (true){
                Libro libro = estanteria.sacar();
                System.out.println(getName() + " ,lee el libro: " + libro.getTitulo()
                        + " ,del autor: " +libro.getAutor());
                Thread.sleep(1000);
            }

        }catch (InterruptedException e){
            System.err.println(getName() + " interrumpido");
        }
    }
}
