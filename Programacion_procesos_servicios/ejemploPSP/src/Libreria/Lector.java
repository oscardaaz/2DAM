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
                if (libro == null) {
                    System.out.println(getName() + " no encuentra más libros. Se va a casa.");
                    break;  // ← Sale del bucle
                }
                System.out.println(getName() + " ,lee el libro: " + libro.getTitulo()
                        + " ,del autor: " +libro.getAutor());
                Thread.sleep(1000 + (int)(Math.random() * 2000));  // ← 1-3 segundos
            }

        }catch (InterruptedException e){
            System.err.println(getName() + " interrumpido");
        }
        System.out.println(getName() + " termina de leer");
    }
}
