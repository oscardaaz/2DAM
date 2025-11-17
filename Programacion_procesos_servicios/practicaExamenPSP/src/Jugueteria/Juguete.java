package Jugueteria;

public class Juguete {
    private static int contadorId = 1;
    private int id;
    private String tipo;
    
    public Juguete(String tipo) {
        this.id = contadorId++;
        this.tipo = tipo;
    }
    
    public int getId() {
        return id;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    @Override
    public String toString() {
        return "Juguete #" + id + " (" + tipo + ")";
    }
}
