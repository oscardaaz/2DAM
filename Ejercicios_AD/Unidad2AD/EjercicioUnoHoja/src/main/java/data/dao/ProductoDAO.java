package data.dao;

import model.Fabricante;
import model.Producto;

import java.util.List;

public interface ProductoDAO {

    boolean crearTablaProducto();
    boolean crearTablaFabricante();
    int insertarProducto(Producto producto);
    int insertarFabricante(Fabricante fabricante);
    List<String> leerProductos(Producto producto, Fabricante fabricante);
    int descuentoPrecios();
}
