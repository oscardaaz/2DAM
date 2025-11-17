package Libreria;

public class Libro {
    private String titulo;
    private String autor;
    private static final String AUTORES[]={"Pepe" , "Juan", "Pedro"};
    private static final String LIBROS[]={"La prueba" , "La hotia0", "El manuel"};
    private static int contadorID = 1;

    public Libro() {
        this.titulo = LIBROS[(int)(Math.random() * LIBROS.length)];;
        this.autor = AUTORES[(int)(Math.random() * AUTORES.length)];
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return String.format("Libro: %s ,del autor: %s",titulo,autor);
    }
}
