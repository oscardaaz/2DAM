package model;

public class Producto {

    private int codigo, cod_fabricante;
    private String nombre;
    private double precio;

    public Producto(String nombre, double precio, int cod_fabricante) {

        this.nombre = nombre;
        this.precio = precio;
        this.cod_fabricante = cod_fabricante;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCod_fabricante() {
        return cod_fabricante;
    }

    public void setCod_fabricante(int cod_fabricante) {
        this.cod_fabricante = cod_fabricante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return String.format("Producto { COD: %4d, Nombre: %-10s, Precio: %-5.2f Cod_Fabricante: %-5d}"
                ,codigo,nombre,precio,cod_fabricante);
    }
}
